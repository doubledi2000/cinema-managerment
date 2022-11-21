package com.it.doubledi.cinemamanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class CinemaManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CinemaManagerApplication.class, args);
	}

}
