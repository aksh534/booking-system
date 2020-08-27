package com.demo.assignment.security;

public enum ApplicationUserPermission {
	
	BOOKED_SEATS("read:booked_seats"),
	UNBOOKED_SEATS("read:unbooked_seats"),
	IN_PROGRESS_SEATS("read:in_progress_seats"),
	
	ALL_SEATS("read:all_seats"),
	BOOK_SEATS("write:book_seats");
	
	private final String permission;
	
	private ApplicationUserPermission(String permission) {
		this.permission = permission;
	}
	
	public String getPermission() {
		return this.permission;
	}
}
