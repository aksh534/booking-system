package com.demo.assignment.util;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.demo.assignment.Application;
import com.demo.assignment.exception.ApiRequestException;
import com.demo.assignment.model.ApplicationUser;
import com.demo.assignment.model.Booking;
import com.demo.assignment.model.Payment;
import com.demo.assignment.model.Seat;
import com.demo.assignment.model.Show;
import com.demo.assignment.repository.BookingsRepository;

@Component
public class BookingManager {
	private ArrayBlockingQueue<BookingsRepository> connQ;
	private static final int maxConnections = 5; 
	
	public BookingManager() {
		connQ = new ArrayBlockingQueue<BookingsRepository>(maxConnections);
	}
	
	public List<Booking> getBookingsForShow(Integer showId) {
		List<Booking> bookings = null;
		BookingsRepository obj = null;
		try {
			obj = getRepositoryObject();
			bookings = obj.getBookingsForShow(showId);
		} catch (Exception ex) {
			throw new ApiRequestException("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		finally {
			release(obj);
		}
		
		return bookings;
	}
	
	public Booking block(Show show, List<Seat> seats, ApplicationUser user) {
		BookingsRepository obj = null;
		Booking booking = null;
		try {
			obj = getRepositoryObject();
			booking = obj.block(show, seats, user);
		}
		catch(Exception ex) {
			throw new ApiRequestException("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		finally {
			release(obj);
		}
		
		return booking;
	}
	
	public Payment book(Show show, Integer bookingId, ApplicationUser user) {
		BookingsRepository obj = null;
		Payment payment = null;
		try {
			obj = getRepositoryObject();
			payment = obj.book(show, bookingId, user);
		}
		catch(ApiRequestException ex) {
			throw ex;
		}
		catch(Exception ex) {
			throw new ApiRequestException("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		finally {
			release(obj);
		}
		
		return payment;
	}
	
	private BookingsRepository getRepositoryObject() throws InterruptedException {
		BookingsRepository obj = null;
		
		obj = connQ.poll(2000, TimeUnit.MILLISECONDS);
		if(obj == null) {
			obj = Application.getApplicationContext().getBean(BookingsRepository.class);
		}
		return obj;
	}
	
	private void release(BookingsRepository obj) {
		if(obj == null) {
			return;
		}
		
		connQ.offer(obj);
	}
}
