package com.demo.assignment.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import com.demo.assignment.model.City;
import com.demo.assignment.model.Seat;
import com.demo.assignment.model.Show;
import com.demo.assignment.model.Theatre;

public class Generator {
	
	private static Random random = new Random();
	
	public static List<City> generateData() {
		List<City> cities = Generator.createCities();
		for(City city: cities) {
			Generator.createTheatres(city);
			for(Theatre theatre: city.getTheatres()) {
				Generator.createSeates(theatre);
				Generator.assignShows(theatre);
			}
		}
		return cities;
	}
	
	private static List<City> createCities() {
		List<City> cities = new ArrayList<>();
		String[] cityNames = {"Bangalore", "New Delhi", "Chandigarh", "Hyderabad", "Mumbai", "Pune"};
		for(String cityName: cityNames) {
			cities.add(new City(cityName));
		}
		
		return cities;
	}
	
	private static void createTheatres(City city) {
		List<String> names = new ArrayList<>( Arrays.asList("The Radiance Theatre", "The Desire Theatre", 
															"The Exalted Theatre", "The Imperial Theatre", 
															"The Pyramid Theatre", "The Swanlake Theater",
															"The Resonance Theatre", "The Bluebell Theatre"));
								
		
		int count = random.nextInt(names.size()-1) + 1;
		int[] capacity = {30, 50, 70, 80, 100};
 
		while(count-- > 0) {
			int idx = random.nextInt(names.size());
			Theatre theatre = new Theatre(names.get(idx), capacity[random.nextInt(5)]);
			city.addTheatre(theatre);
			theatre.setCity(city);
			names.remove(idx);
		}
	}
	
	private static void createSeates(Theatre theatre) {
		int rows = theatre.getCapacity() / 10;
		double[] amount = {200.0, 250.0, 400.0}; 
		
		for(int rowNumber=1; rowNumber <= rows; ++rowNumber) {
			for(int seatNumber=1; seatNumber <= 10; ++seatNumber) {
				Seat seat = new Seat(rowNumber, seatNumber, amount[random.nextInt(3)]);
				theatre.addSeat(seat);
			}
		}
		
	}
	
	private static void assignShows(Theatre theatre) {
		List<String> shows = new ArrayList<>(Arrays.asList("Empty Story", "Backwards Legend", "Chronic Villain", "Oracle Story", "Bitter Myths",
															"Awoken Sidekick", "Wondergift", "Lifesize Comics", "Paper Revolution", "Kawaii Stories",
															"Spaceflow", "Twisted Grave", "Twisted Dream", "Online Summer", "Heavenside"));
		
		int count = random.nextInt(shows.size()-1) + 1;
		 
		Date last = addHoursToJavaUtilDate(new Date(), random.nextInt(100) + 72); 
		while(count-- > 0) {
			int idx = random.nextInt(shows.size());
			Date start = addHoursToJavaUtilDate(last, random.nextInt(4) + 2); 
			Date end = addHoursToJavaUtilDate(start, random.nextInt(3)+1);
			last = end;
			Show show = new Show(shows.get(idx), getDetails(), start, end);
			show.setTheatre(theatre);
			theatre.addShow(show);
			shows.remove(idx);
		}
		
	}
	
	private static Date addHoursToJavaUtilDate(Date date, int hours) {
	    Calendar calendar = Calendar.getInstance();
	    calendar.setTime(date);
	    calendar.add(Calendar.HOUR_OF_DAY, hours);
	    return calendar.getTime();
	}	
	
	private static String getDetails() {
		return "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed luctus pharetra ligula, non porta augue maximus eu."
				+ "Vivamus porta ante sit amet diam fringilla aliquam. Curabitur ut mattis magna, et molestie lacus.";
	}
	
}
