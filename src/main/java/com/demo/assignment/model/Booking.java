package com.demo.assignment.model;

import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.demo.assignment.util.BookingStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class Booking {
	private static AtomicInteger booking_Id = new AtomicInteger(1546555);
	
	private Integer bookingId;
	private Show show;
	private List<Seat> seats;
	private Date creationTime;
	
	@JsonIgnore
	private Payment payment;
	
	@JsonIgnore
	private ApplicationUser user;
	
	private BookingStatus status;
	
	public Booking(Show show, List<Seat> seats, ApplicationUser user) { 
		this.bookingId = booking_Id.incrementAndGet();
		this.show = show;
		this.seats = seats;
		this.creationTime = new Date();
		this.status = BookingStatus.IN_PROGRESS;
		this.user = user;
	}
	
	public Integer getId() {
		return this.bookingId;
	}
	
	public Show getShow() {
		return show;
	}

	public void setShow(Show show) {
		this.show = show;
	}

	public List<Seat> getSeats() {
		return seats;
	}

	public void setSeats(List<Seat> seats) {
		this.seats = seats;
	}

	public Date getCreationTime() {
		return creationTime;
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

	public BookingStatus getStatus() {
		return status;
	}

	public void setStatus(BookingStatus status) {
		this.status = status;
	}

	public ApplicationUser getUser() {
		return user;
	}
}
