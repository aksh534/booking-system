package com.demo.assignment.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.demo.assignment.model.Booking;
import com.demo.assignment.model.Seat;
import com.demo.assignment.model.Show;

@Repository
public class BookingsRepository {
	
	private static Map<Integer, List<Booking>> bookings = new HashMap<>();
	
	public static final int CONFIRMED = 1, IN_PROGRESS = -1; 
	
	private List<Booking> getBookings(Integer showId) {
		synchronized (bookings) {
			return bookings.get(showId);
		}
	}
	
	private void saveBookings(Integer showId, List<Booking> bookingsForShow) {
		synchronized (bookings) {
			bookings.put(showId, bookingsForShow);
		}
	}
	
	public boolean checkSeatAvailablity(Integer showId, Integer rowNumber, Integer seatNumber) {
		List<Booking> bookingsForShow = getBookings(showId);
		if(bookingsForShow == null) {
			return true;
		}
		
		for(Booking booking: bookingsForShow) {
			List<Seat> seats = booking.getSeats();
			if(seats == null) continue;
			for(Seat seat: seats) {
				if(seat.getRowNumber().equals(rowNumber) && seat.getSeatNumber().equals(seatNumber)) {
					return false;
				}
			}
		}
		
		return true;
	}

	public List<Seat> getBookedSeats(Integer showId) {
		List<Booking> bookingsForShow = getBookings(showId);
		if(bookingsForShow == null) {
			return new ArrayList<Seat>();
		}
		
		List<Seat> bookedSeats = new ArrayList<Seat>();
		for(Booking booking: bookingsForShow) {
			if(booking.getStatus() == IN_PROGRESS || booking.getSeats() == null) {
				continue;
			}
			List<Seat> seats = booking.getSeats();
			for(Seat seat: seats) {
				bookedSeats.add(seat);
			}
		}
		
		return bookedSeats;
	}

	public void save(Show show, List<Seat> seats) {
		for(Seat seat: seats) {
			seat.setAvailablilty(false);
		}
		
		Booking booking = new Booking(show, seats);
		save(show.getId(), booking);
	}
	
	private void save(Integer showId, Booking booking) {
		List<Booking> bookingsForShow = getBookings(showId);
		if(bookingsForShow == null) {
			bookingsForShow = new ArrayList<Booking>();
		}
		bookingsForShow.add(booking);
		saveBookings(showId, bookingsForShow);
	}

	public List<Seat> getInProgressSeats(Integer showId) {
		List<Booking> bookingsForShow = getBookings(showId);
		if(bookingsForShow == null) {
			return new ArrayList<Seat>();
		} 
		
		List<Seat> inProgressSeats = new ArrayList<Seat>();
		for(Booking booking: bookingsForShow) {
			if(booking.getStatus() != IN_PROGRESS || booking.getSeats() == null) {
				continue;
			}
			List<Seat> seats = booking.getSeats();
			for(Seat seat: seats) {
				inProgressSeats.add(seat);
			}
		}
		return inProgressSeats;
	}
}
