package com.demo.assignment.model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Theatre {
	private static AtomicInteger theater_id = new AtomicInteger(158576);
	
	private Integer id;
	private String name;
	private int capacity;
	
	@JsonIgnore
	private City city;
	
	@JsonIgnore
	private List<Show> shows;
	
	@JsonIgnore
	private List<Seat> seats;
	
	public Theatre(String name, int capacity) {
		this.id = theater_id.incrementAndGet();
		this.name = name;
		this.capacity = capacity;
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

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public List<Show> getShows() {
		return shows;
	}

	public void setShows(List<Show> shows) {
		this.shows = shows;
	}
	
	public void addShow(Show show) {
		if(this.shows == null) {
			this.shows = new ArrayList<Show>();
		}
		this.shows.add(show);
	}

	public List<Seat> getSeats() {
		return seats;
	}

	public void setSeats(List<Seat> seats) {
		this.seats = seats;
	}
	
	public void addSeat(Seat seat) {
		if(this.seats == null) {
			this.seats = new ArrayList<Seat>();
		}
		this.seats.add(seat);
	}
}
