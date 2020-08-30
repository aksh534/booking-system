package com.demo.assignment.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import com.demo.assignment.exception.ApiRequestException;
import com.demo.assignment.model.ApplicationUser;
import com.demo.assignment.model.Booking;
import com.demo.assignment.model.Payment;
import com.demo.assignment.model.Seat;
import com.demo.assignment.model.Show;
import com.demo.assignment.util.BookingStatus;

@Repository
public class BookingsRepository {
	
	private static Map<Integer, List<Booking>> bookings = new ConcurrentHashMap<>();
	private static final int MAX_TIME = 10;
		
	public synchronized List<Booking> getBookingsForShow(Integer showId) { 
		return bookings.getOrDefault(showId, new ArrayList<Booking>());
	}
	
	private synchronized void save(Integer showId, Booking booking) {
		List<Booking> bookingsForShow = getBookingsForShow(showId);
		bookingsForShow.add(booking);
		bookings.put(showId, bookingsForShow);
	}
	
	private synchronized void unblock(Integer showId, Booking booking) {
		List<Booking> bookingsForShow = getBookingsForShow(showId);
		bookingsForShow.remove(booking);
		if(bookingsForShow.isEmpty()) {
			bookings.remove(showId);
		}
	}
	
	public synchronized Booking block(Show show, List<Seat> seats, ApplicationUser user) {
		List<Booking> bookingsForShow = getBookingsForShow(show.getId());
		for(Seat seat: seats) {
			boolean isAvailable = checkSeatAvailablity(seat, bookingsForShow);
			if(!isAvailable) {
				return null;
			}
			seat.setAvailablilty(false);
		}
		
		Booking booking = new Booking(show, seats, user);
		save(show.getId(), booking);
		return booking;
	}
	
	public synchronized Payment book(Show show, Integer bookingId, ApplicationUser user) {
		Booking booking = getBookingById(show.getId(), bookingId);
		
		validate(booking, show, bookingId, user);
		
		double amount = 0;
		for(Seat seat: booking.getSeats()) {
			amount += seat.getAmount();
		}
		
		Payment payment = new Payment(new Date(), amount);
		booking.setPayment(payment);
		booking.setStatus(BookingStatus.BOOKED);
		return payment;
	}
	
	private void validate(Booking booking, Show show, Integer bookingId, ApplicationUser user) {
		if(booking == null) {
			throw new ApiRequestException("Invalid Request", HttpStatus.BAD_REQUEST);
		}
		
		if(booking.getStatus().equals(BookingStatus.BOOKED)) {
			throw new ApiRequestException("Invalid Request", HttpStatus.BAD_REQUEST);
		}
		
		if(!user.getUsername().equals(booking.getUser().getUsername())) {
			throw new ApiRequestException("Invalid Request", HttpStatus.BAD_REQUEST);
		}
		
		long diff = new Date().getTime() - booking.getCreationTime().getTime();
		long seconds = diff/1000;
		long minutes = seconds/60;
		
		if(minutes > MAX_TIME || (minutes == MAX_TIME && seconds > 5)) {
			unblock(show.getId(), booking);
			throw new ApiRequestException("Invalid Request, Timeout", HttpStatus.NOT_ACCEPTABLE);
		}
	}
	
	private Booking getBookingById(Integer showId, Integer bookingId) {
		List<Booking> bookingsForShow = getBookingsForShow(showId);
		for(Booking booking: bookingsForShow) {
			if(booking.getId().equals(bookingId)) {
				return booking;
			}
		}
		
		return null;
	}
	
	private boolean checkSeatAvailablity(Seat seat, List<Booking> bookingsForShow ) {
		Integer rowNumber = seat.getRowNumber();
		Integer seatNumber = seat.getSeatNumber();
		
		for(Booking booking: bookingsForShow) {
			List<Seat> seats = booking.getSeats();
			if(seats == null) continue;
			for(Seat _seat: seats) {
				if(_seat.getRowNumber().equals(rowNumber) && _seat.getSeatNumber().equals(seatNumber)) {
					return false;
				}
			}
		}
		
		return true;
	} 
}
