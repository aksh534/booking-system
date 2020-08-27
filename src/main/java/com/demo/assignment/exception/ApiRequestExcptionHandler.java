package com.demo.assignment.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiRequestExcptionHandler {
	
	@ExceptionHandler(value = {ApiRequestException.class})
	public ResponseEntity<?> handleException(ApiRequestException ex) {
		ApiException exception = new ApiException(ex.getMessage(), ex.getHttpStatus());
		return new ResponseEntity<>(exception, ex.getHttpStatus());
	}
}
