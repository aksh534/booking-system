package com.demo.assignment.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.assignment.exception.ApiRequestException;
import com.demo.assignment.model.Booking;
import com.demo.assignment.model.Payment;
import com.demo.assignment.model.Seat;
import com.demo.assignment.service.ApplicationUserService;
import com.demo.assignment.service.BookingService;

@RestController
@RequestMapping("/api/v1/booking")
public class BookingController {
	
	@Autowired
	private BookingService bookingService;
	
	@Autowired 
	private ApplicationUserService applicationUserService;
	
	@GetMapping(path = "/seats")
	public List<Seat> getAllAvailableSeats( @RequestParam(name = "showId") Integer showId) {
		
		List<Seat> availableSeats = null;
		try {
			availableSeats = bookingService.getAllAvailableSeats(showId);
		}
		catch(ApiRequestException ex) {
			throw ex;
		}
		catch(Exception ex) {
			throw new ApiRequestException(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return availableSeats;
	}
	
	@GetMapping(path = "/bookedSeats")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public List<Seat> getBookedSeats(@RequestParam(name = "showId") Integer showId) {
		
		List<Seat> bookedSeats = null;
		try {
			bookedSeats = bookingService.getBookedSeats(showId);
		}
		catch(ApiRequestException ex) {
			throw ex;
		}
		catch(Exception ex) {
			throw new ApiRequestException(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return bookedSeats;
	}
	
	@GetMapping(path = "/inProgress")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public List<Seat> getInProgressSeats(@RequestParam(name = "showId") Integer showId) {
		
		List<Seat> inProgressSeats = null;
		try {
			inProgressSeats = bookingService.getInProgressSeats(showId);
		}
		catch(ApiRequestException ex) {
			throw ex;
		}
		catch(Exception ex) {
			throw new ApiRequestException(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return inProgressSeats;
	}
	
	@GetMapping(path = "/unbookedSeats")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public List<Seat> getUnbookedSeats(@RequestParam(name = "showId") Integer showId) {
		
		List<Seat> unbookedSeats = null;
		try {
			unbookedSeats = bookingService.getUnbookedSeats(showId);
		}
		catch(ApiRequestException ex) {
			throw ex;
		}
		catch(Exception ex) {
			throw new ApiRequestException(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return unbookedSeats;
	}
	
	
	@PostMapping(path = "/block")
	@PreAuthorize("hasRole('ROLE_CUSTOMER')")
	public Booking blockSeats(@RequestParam(name = "showId") Integer showId, @RequestBody List<Seat> seats) {
		
		Booking booking = null;
		try { 
			booking = bookingService.block(showId, seats, applicationUserService.getCurrentUser());
		}
		catch(ApiRequestException ex) {
			throw ex;
		}
		catch(Exception ex) {
			throw new ApiRequestException(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return booking;
	}
	
	@PostMapping(path = "/book")
	@PreAuthorize("hasRole('ROLE_CUSTOMER')")
	public Payment bookSeats(@RequestParam(name = "showId") Integer showId, @RequestParam(name = "bookingId") Integer bookingId) {
		
		Payment payment = null;
		try { 
			payment = bookingService.book(showId, bookingId, applicationUserService.getCurrentUser());
		}
		catch(ApiRequestException ex) {
			throw ex;
		}
		catch(Exception ex) {
			throw new ApiRequestException(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return payment;
	}
	
}
