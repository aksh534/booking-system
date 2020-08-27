package com.demo.assignment.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.demo.assignment.exception.ApiRequestException;
import com.demo.assignment.model.City;
import com.demo.assignment.model.Show;
import com.demo.assignment.model.Theatre;
import com.demo.assignment.repository.DataRepository;

@Service
public class DataService {
	
	@Autowired
	DataRepository repository;
	
	public List<City> getAllCities() {
		List<City> cities = repository.getAllCities();
		if(cities == null) {
			throw new ApiRequestException("Something went very wrong!", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return cities;
	} 
	
	public List<Theatre> getTheatresByCity(Integer cityId) {
		List<Theatre> theatres = repository.getTheatresByCity(cityId);
		if(theatres == null) {
			throw new ApiRequestException("Something went very wrong!", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return theatres;
	}
	
	public List<Show> getShowsByTheatre(Integer theatreId) {
		List<Show> shows = repository.getShowsByTheatre(theatreId);
		if(shows == null) {
			throw new ApiRequestException("Something went very wrong!", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return shows;
	}
}
