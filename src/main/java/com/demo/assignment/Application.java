package com.demo.assignment;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.demo.assignment.model.City;
import com.demo.assignment.repository.DataRepository;
import com.demo.assignment.util.Generator;

@SpringBootApplication
public class Application implements CommandLineRunner {
	
	Logger logger = LoggerFactory.getLogger(Application.class);
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	public void run(String... args) throws Exception {
		try {
			List<City> cities = Generator.generateData();
			DataRepository.addData(cities);
		}
		catch(Exception ex) {
			ex.printStackTrace();
			System.exit(-1);
		}		
	}		
}
