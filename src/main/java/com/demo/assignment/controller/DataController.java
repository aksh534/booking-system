package com.demo.assignment.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.assignment.exception.ApiRequestException;
import com.demo.assignment.model.City;
import com.demo.assignment.model.Show;
import com.demo.assignment.model.Theatre;
import com.demo.assignment.service.DataService;

@RestController
@RequestMapping("/api/v1/filters")
public class DataController {
	
	@Autowired 
	DataService service;
	
	@RequestMapping(path = "/cities", method = RequestMethod.GET)
	public List<City> getAllCities() {
		List<City> cities = null;
		try {
			cities = service.getAllCities();
		}
		catch(ApiRequestException ex) {
			throw ex;
		}
		catch(Exception ex) {
			throw new ApiRequestException(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return cities; 
	}
	
	@RequestMapping(path = "/theatres", method = RequestMethod.GET)
	public List<Theatre> getTheatresByCity(@RequestParam(name = "cityId", required = true) Integer cityId) {
		List<Theatre> theatres = null;
		try {
			theatres = service.getTheatresByCity(cityId);
		}
		catch(ApiRequestException ex) {
			throw ex;
		}
		catch(Exception ex) {
			throw new ApiRequestException(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return theatres;
	}
	
	@RequestMapping(path = "/shows", method = RequestMethod.GET)
	public List<Show> getShowsByTheatre(@RequestParam(name = "theatreId", required = true) Integer theatreId) {
		List<Show> shows;
		try {
			shows = service.getShowsByTheatre(theatreId);
		}
		catch(ApiRequestException ex) {
			throw ex;
		}
		catch(Exception ex) {
			throw new ApiRequestException(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return shows;
	}
}
