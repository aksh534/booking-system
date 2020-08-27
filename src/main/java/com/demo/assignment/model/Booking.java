package com.demo.assignment.model;

import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.demo.assignment.repository.BookingsRepository;

public class Booking {
	private static AtomicInteger booking_id = new AtomicInteger(1000000);
	
	private Integer id;
	private Show show;
	private List<Seat> seats;
	private Date creationTime;
	private Payment payment;
	
	private int status;
	
	public Booking(Show show, List<Seat> seats) {
		this.id = booking_id.incrementAndGet();
		this.show = show;
		this.seats = seats;
		this.creationTime = new Date();
		this.status = BookingsRepository.IN_PROGRESS;
	}
	
	public Integer getId() {
		return this.id;
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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
}
