package com.demo.assignment.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.assignment.exception.ApiRequestException;
import com.demo.assignment.model.Payment;
import com.demo.assignment.model.Seat;
import com.demo.assignment.service.BookingService;

@RestController
@RequestMapping("/api/v1/booking")
public class BookingController {
	
	@Autowired
	BookingService bookingService;
	
	@RequestMapping(path = "/blockSeats", method = RequestMethod.POST)
	@PreAuthorize("hasRole('ROLE_CUSTOMER')")
	public ResponseEntity<?> blockSeats(@RequestParam(name = "showId") Integer showId, @RequestBody List<Seat> seats) {
		try {
			bookingService.blockSeats(showId, seats);
		}
		catch(ApiRequestException ex) {
			throw ex;
		}
		catch(Exception ex) {
			throw new ApiRequestException(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return ResponseEntity.ok().build();
	}
	
	public ResponseEntity<?> bookSeats(Integer bookingId, Payment payment) {
		return ResponseEntity.ok().build();
	}
	
	@RequestMapping(path = "/seats", method = RequestMethod.GET)
	public List<Seat> getAllAvailableSeats( @RequestParam(name = "theatreId") Integer theatreId, 
											@RequestParam(name = "showId") Integer showId) {
		
		return bookingService.getAllAvailableSeats(theatreId, showId);
	}
	
	
	@RequestMapping(path = "/bookedSeats", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public List<Seat> getBookedSeats(@RequestParam(name = "showId") Integer showId) {
		return bookingService.getBookedSeats(showId);
	}
	
	@RequestMapping(path = "/inProgress", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public List<Seat> getInProgressSeats(@RequestParam(name = "showId") Integer showId) {
		return bookingService.getInProgressSeats(showId);
	}
}
