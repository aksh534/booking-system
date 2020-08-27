package com.demo.assignment.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;

public class ApiException {
	private String message;
	private HttpStatus httpStatus;
	private Date date;
	
	public ApiException(String message, HttpStatus httpStatus) {
		this.message = message;
		this.httpStatus = httpStatus;
		this.date = new Date();
	}

	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public HttpStatus getHttpStatus() {
		return httpStatus;
	}
	
	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}
	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
