package com.demo.assignment;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.demo.assignment.model.City;
import com.demo.assignment.repository.DataRepository;
import com.demo.assignment.util.Generator;

@SpringBootApplication
public class Application implements CommandLineRunner {
	
	Logger logger = LoggerFactory.getLogger(Application.class);
	
	private static ApplicationContext context;
	
	public static void main(String[] args) {
		context = SpringApplication.run(Application.class, args);
	}
	
	public static ApplicationContext getApplicationContext() {
		return context;
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
