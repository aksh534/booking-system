package com.demo.assignment.util;

public enum BookingStatus {
	AVAILABLE, IN_PROGRESS, BOOKED, UN_BOOKED;
	
	public static BookingStatus statusValueOf(Integer status) {
		if(status == null) {
			return AVAILABLE;
		}
		
		switch (status) {
			case 1:
				return IN_PROGRESS;
			case 2: 
				return BOOKED;
			case 3: 
				return UN_BOOKED;
			default:
				return null;
		}
	}
}
