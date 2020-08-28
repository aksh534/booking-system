package com.demo.assignment.model;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

public class Payment {
	private static AtomicInteger _id = new AtomicInteger(1000000);
	
	private int paymentId;
	private Date paymentTime;
	private double amount;
	
	public Payment(Date paymentTime, double amount) {
		this.paymentId = _id.incrementAndGet();
		this.paymentTime = paymentTime;
		this.amount = amount;
	}

	public int getId() {
		return paymentId;
	}

	public Date getPaymentTime() {
		return paymentTime;
	}

	public void setPaymentTime(Date paymentTime) {
		this.paymentTime = paymentTime;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}
	
}
