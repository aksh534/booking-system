package com.demo.assignment.model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class City {
	private static AtomicInteger cityId = new AtomicInteger(1000);
	
	private Integer id;
	private String name;
	
	@JsonIgnore
	private List<Theatre> theatres;

	public City(String name) {
		this.id = cityId.incrementAndGet();
		this.name = name;
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

	public List<Theatre> getTheatres() {
		return theatres;
	}
	
	public void setTheatres(List<Theatre> theatres) {
		this.theatres = theatres;
	}

	public void addTheatre(Theatre thretre) {
		if(this.theatres == null) {
			this.theatres = new ArrayList<>();
		}
		this.theatres.add(thretre);
	}
}
