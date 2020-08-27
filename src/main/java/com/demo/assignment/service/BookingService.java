package com.demo.assignment.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.assignment.model.Seat;
import com.demo.assignment.repository.BookingsRepository;
import com.demo.assignment.repository.DataRepository;

@Service
public class BookingService {
	
	@Autowired
	BookingsRepository bookingsRepository;
	
	@Autowired
	DataRepository dataRepository;
	
	public void blockSeats(Integer showId, List<Seat> seats) {
		for(Seat seat: seats) {
			boolean isAvailable = blockSeat(showId, seat.getRowNumber(), seat.getSeatNumber());
			if(!isAvailable) {
				throw new RuntimeException("Seat: " + seat.getRowNumber() + "-" + seat.getSeatNumber() + " not available!");
			}
		}
		
		bookingsRepository.save(dataRepository.getShow(showId), seats);
	}
	
	private boolean blockSeat(Integer showId, Integer rowNumber, Integer seatNumber) {
		return bookingsRepository.checkSeatAvailablity(showId, rowNumber, seatNumber);
	}
	
	public List<Seat> getAllAvailableSeats(Integer theatreId, Integer showId) {
		List<Seat> seats = dataRepository.getSeatsByTheatre(theatreId);
		return getAllAvailableSeats(seats, showId);
	}

	private List<Seat> getAllAvailableSeats(List<Seat> seats, Integer showId) {
		List<Seat> availableSeats = new ArrayList<Seat>();
		for(Seat seat: seats) {
			boolean isAvailable = bookingsRepository.checkSeatAvailablity(showId, seat.getRowNumber(), seat.getSeatNumber());
			if(!isAvailable) {
				seat.setAvailablilty(false);
			}
			availableSeats.add(seat);
		}
		return availableSeats;
	}

	public List<Seat> getBookedSeats(Integer showId) {
		return bookingsRepository.getBookedSeats(showId);
	}

	public List<Seat> getInProgressSeats(Integer showId) {
		return bookingsRepository.getInProgressSeats(showId);
	}
	
}
