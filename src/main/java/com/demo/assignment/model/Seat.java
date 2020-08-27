package com.demo.assignment.model;

public class Seat {
	private Integer rowNumber;
	private Integer seatNumber;
	private Double amount;
	private boolean isAvailable;
	
	public Seat(Integer rowNumber, Integer seatNumber, Double amount) {
		this.rowNumber = rowNumber;
		this.seatNumber = seatNumber;
		this.amount = amount;
		this.isAvailable = true;
	}

	public Integer getRowNumber() {
		return rowNumber;
	}
		
	public Integer getSeatNumber() {
		return seatNumber;
	}

	public Double getAmount() {
		return amount;
	}
	
	public void updateAmount(Double amount) {
		this.amount = amount;
	}
	
	public boolean isAvailable() {
		return this.isAvailable;
	}
	
	public void setAvailablilty(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}
	
}
