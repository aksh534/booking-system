package com.demo.assignment.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.demo.assignment.exception.ApiRequestException;
import com.demo.assignment.model.ApplicationUser;
import com.demo.assignment.model.Booking;
import com.demo.assignment.model.Payment;
import com.demo.assignment.model.Seat;
import com.demo.assignment.model.Show;
import com.demo.assignment.repository.BookingsRepository;
import com.demo.assignment.repository.DataRepository;
import com.demo.assignment.util.BookingManager;
import com.demo.assignment.util.BookingStatus;

@Service
public class BookingService {
	
	@Autowired
	BookingsRepository bookingsRepository;
	
	@Autowired 
	private BookingManager bookingManager;
	
	@Autowired
	private DataRepository dataRepository;
	
	public List<Seat> getAllAvailableSeats(Integer showId) {
		Show show = dataRepository.getShow(showId);
		if(show == null) {
			throw new ApiRequestException("Invalid Show Information provided!", HttpStatus.BAD_REQUEST);
		}
		List<Seat> seats = dataRepository.getSeatsByTheatre(show.getTheatre().getId());
		return getAllSeats(seats, showId, true);
	}
	
	public List<Seat> getBookedSeats(Integer showId) {
		List<Seat> bookedSeats = new ArrayList<Seat>();
		List<Booking> bookingsForShow = getBookingsForShow(showId);
		
		for(Booking booking: bookingsForShow) {
			if(booking.getStatus() != BookingStatus.BOOKED || booking.getSeats() == null) {
				continue;
			}
			for(Seat seat: booking.getSeats()) {
				bookedSeats.add(seat);
			}
		}
		
		return bookedSeats;
	}
	
	public List<Seat> getInProgressSeats(Integer showId) {
		List<Seat> inProgressSeats = new ArrayList<Seat>();
		List<Booking> bookingsForShow = getBookingsForShow(showId);
		
		for(Booking booking: bookingsForShow) {
			if(booking.getStatus() != BookingStatus.IN_PROGRESS || booking.getSeats() == null) {
				continue;
			}
			List<Seat> seats = booking.getSeats();
			for(Seat seat: seats) {
				inProgressSeats.add(seat);
			}
		}
		return inProgressSeats;
	}
		
	public List<Seat> getUnbookedSeats(Integer showId) {
		Show show = dataRepository.getShow(showId);
		if(show == null) {
			throw new ApiRequestException("Invalid Show Information provided!", HttpStatus.BAD_REQUEST);
		}
		List<Seat> seats = dataRepository.getSeatsByTheatre(show.getTheatre().getId());
		return getAllSeats(seats, showId, false);
	}

	public Booking block(Integer showId, List<Seat> seats, ApplicationUser user) {
		if(seats.isEmpty()) {
			throw new ApiRequestException("Invalid Request!", HttpStatus.BAD_REQUEST);
		}
		
		Show show = dataRepository.getShow(showId);
		if(show == null) {
			throw new ApiRequestException("Invalid Show Information provided!", HttpStatus.BAD_REQUEST);
		}
		
		Booking booking = bookingManager.block(show, seats, user);
		if(booking == null) {
			throw new ApiRequestException("Seats Unavailable, refresh and try again!", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return booking;
	}
	
	public Payment book(Integer showId, Integer bookingId, ApplicationUser user) {
		Show show = dataRepository.getShow(showId);
		if(show == null) {
			throw new ApiRequestException("Invalid Show Information provided!", HttpStatus.BAD_REQUEST);
		}
		Payment payment = bookingManager.book(show, bookingId, user);
		return payment;
	}
	
	private List<Booking> getBookingsForShow(Integer showId) {
		return bookingManager.getBookingsForShow(showId);
	}
	
	private List<Seat> getAllSeats(List<Seat> seats, Integer showId, boolean availabilty) {
		List<Seat> allSeats = new ArrayList<Seat>();
		List<Booking> bookingsForShow = getBookingsForShow(showId);
		for(Seat seat: seats) {
			boolean seatAvailabilty = checkSeatAvailablity(seat, bookingsForShow);
			seat.setAvailablilty(seatAvailabilty);
			if(availabilty || seatAvailabilty)
				allSeats.add(seat);
		}
		return allSeats;
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
