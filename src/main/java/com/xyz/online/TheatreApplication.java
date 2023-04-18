package com.xyz.online;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.xyz.online.customDB.BuildOffers;
import com.xyz.online.customDB.BuildScreens;
import com.xyz.online.customDB.BuildShows;
import com.xyz.online.customDB.BuildTheatres;

@SpringBootApplication
public class TheatreApplication {

	public static void main(String[] args) {
		SpringApplication.run(TheatreApplication.class, args);
		BuildOffers.build();
		BuildShows.buildShows();
		BuildScreens.buildScreens();
		BuildTheatres.build();
	}

}
