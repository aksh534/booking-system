package com.demo.assignment.model;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Show {
	private static AtomicInteger show_id =  new AtomicInteger(124111);
	
	private Integer id;
	private String name;
	private String details;
	private Date startTime;
	private Date endTime;
	
	@JsonIgnore
	private Theatre theatre;
	
	public Show(String name, String description, Date startTime, Date endTime) {
		this.id = show_id.incrementAndGet();
		this.name = name;
		this.details = description;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Theatre getTheatre() {
		return theatre;
	}

	public void setTheatre(Theatre theatre) {
		this.theatre = theatre;
	}
	
}
