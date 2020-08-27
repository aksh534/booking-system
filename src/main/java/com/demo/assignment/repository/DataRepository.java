package com.demo.assignment.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.demo.assignment.model.City;
import com.demo.assignment.model.Seat;
import com.demo.assignment.model.Show;
import com.demo.assignment.model.Theatre;

@Repository
public class DataRepository {
	
	private static Map<Integer, City> cities = new HashMap<>();
	private static Map<Integer, Theatre> theatres = new HashMap<>();
	private static Map<Integer, Show> shows = new HashMap<>(); 
	
	public List<City> getAllCities() {
		return new ArrayList<City>(cities.values());
	} 
	
	public Theatre getTheatre(Integer theatreId) {
		return theatres.get(theatreId);
	}
	
	public Show getShow(Integer showId) {
		return shows.get(showId);
	}
	
	public List<Theatre> getTheatresByCity(Integer cityId) {
		City city = cities.get(cityId);
		if(city == null || city.getTheatres() == null) {
			return new ArrayList<Theatre>();
		}
		
		return city.getTheatres();
	}
	
	public List<Show> getShowsByTheatre(Integer theatreId) {
		Theatre theatre = theatres.get(theatreId);
		if(theatre == null || theatre.getShows() == null) {
			return new ArrayList<Show>();
		}
		
		return theatre.getShows();
	}
	
	public List<Seat> getSeatsByTheatre(Integer theatreId) {
		Theatre theatre = theatres.get(theatreId);
		if(theatre == null || theatre.getSeats() == null) {
			return new ArrayList<Seat>();
		}
		
		List<Seat> seats = new ArrayList<Seat>();
		for(Seat seat: theatre.getSeats()) {
			seats.add(new Seat(seat.getRowNumber(), seat.getSeatNumber(), seat.getAmount()));
		}
		
		return seats;
	}
	
	public static void addData(List<City> AllCities) {
		for(City city: AllCities) {
			cities.put(city.getId(), city);
			for(Theatre theatre: city.getTheatres()) {
				theatres.put(theatre.getId(), theatre);
				for(Show show: theatre.getShows()) {
					shows.put(show.getId(), show);
				}
			}
		}
	}
}
