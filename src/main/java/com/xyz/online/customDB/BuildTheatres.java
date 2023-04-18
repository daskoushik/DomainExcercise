package com.xyz.online.customDB;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.xyz.online.entities.Offer;
import com.xyz.online.entities.Screen;
import com.xyz.online.entities.Show;
import com.xyz.online.entities.Theatre;
import org.springframework.stereotype.Repository;



@Repository
public class BuildTheatres {
	
	public static List<Theatre> theatres = new ArrayList<>();
	private static Theatre dummyTheatre = null;
	
	public static void build() {
		Theatre theatre1 = new Theatre("MarvelTheatres", "Mumbai", "400001");
		Theatre theatre2 = new Theatre("DCStudios", "Mumbai", "400002");
		Theatre theatre3 = new Theatre("ApsaraMovies", "Mumbai", "400003");
		Theatre theatre4 = new Theatre("PVR Cinemas", "Pune", "411002");
		theatres.add(theatre1);
		theatres.add(theatre2);
		theatres.add(theatre3);
		theatres.add(theatre4);
		
		buildTheatre1(theatre1);
		buildTheatre2(theatre2);
		buildTheatre3(theatre3);
		buildTheatre4(theatre4);
	}
	
	private static void buildTheatre1(Theatre theatre) {
		List<Screen> availableScreens = BuildScreens.screens;
		List<Show> availableShows = BuildShows.shows;
		List<Screen> theatre1Screens = theatre.getScreens();
		Screen screen1 = availableScreens.get(0);
		Show rrrShow = availableShows.get(0);
		List<Offer<?>> offers = BuildOffers.offers;
		
		theatre1Screens.add(screen1.addOffer(offers.get(0))
					   .addOffer(offers.get(1))
					   .setScreenName("Screen1"));
		
		screen1.getShows().add(rrrShow.copyIt(theatre.getTheatreName())
												.setStartTime(LocalDateTime.of(2022, 04, 17, 10, 30))
												.setScreen(screen1.getScreenID()).setShowPrice(300.00f));
		
		Show showTheLess = availableShows.get(3);
		screen1.getShows().add(showTheLess.copyIt(theatre.getTheatreName())
				.setStartTime(LocalDateTime.of(2022, 04, 17, 13, 50))
				.setScreen(screen1.getScreenID()).setShowPrice(70.00f));
		
		Show showTKF = availableShows.get(1);
		screen1.getShows().add(showTKF.copyIt(theatre.getTheatreName())
				.setStartTime(LocalDateTime.of(2022, 04, 17, 15, 20))
				.setScreen(screen1.getScreenID()).setShowPrice(250.00f));
	}
	
	private static void buildTheatre2(Theatre theatre) {
		List<Screen> availableScreens = BuildScreens.screens;
		List<Show> availableShows = BuildShows.shows;
		List<Screen> theatre1Screens = theatre.getScreens();
		Screen screen5 = availableScreens.get(4);
		Show rrrShow = availableShows.get(0);
		List<Offer<?>> offers = BuildOffers.offers;
		
		theatre1Screens.add(screen5.addOffer(offers.get(0))
					   .setScreenName("Screen5"));
		
		screen5.getShows().add(rrrShow.copyIt(theatre.getTheatreName())
												.setStartTime(LocalDateTime.of(2022, 04, 16, 10, 30))
												.setScreen(screen5.getScreenID()).setShowPrice(300.00f));
		
		Show showTheLess = availableShows.get(3);
		screen5.getShows().add(showTheLess.copyIt(theatre.getTheatreName())
				.setStartTime(LocalDateTime.of(2022, 04, 16, 13, 50))
				.setScreen(screen5.getScreenID()).setShowPrice(100.00f));
		
		Show showTKF = availableShows.get(1);
		screen5.getShows().add(showTKF.copyIt(theatre.getTheatreName())
				.setStartTime(LocalDateTime.of(2022, 04, 16, 15, 20))
				.setScreen(screen5.getScreenID()).setShowPrice(200.00f));
	}
	
	private static void buildTheatre3(Theatre theatre) {
		List<Screen> availableScreens = BuildScreens.screens;
		List<Show> availableShows = BuildShows.shows;
		List<Screen> theatre1Screens = theatre.getScreens();
		Screen screen8 = availableScreens.get(7);
		Show rrrShow = availableShows.get(0);
		List<Offer<?>> offers = BuildOffers.offers;
		
		theatre1Screens.add(screen8.addOffer(offers.get(0))
					   .addOffer(offers.get(1))
					   .setScreenName("Screen8"));
		
		screen8.getShows().add(rrrShow.copyIt(theatre.getTheatreName())
												.setStartTime(LocalDateTime.of(2022, 04, 16, 10, 30))
												.setScreen(screen8.getScreenID()).setShowPrice(250.00f));
		
		Show showTheLess = availableShows.get(3);
		screen8.getShows().add(showTheLess.copyIt(theatre.getTheatreName())
				.setStartTime(LocalDateTime.of(2022, 04, 16, 13, 50))
				.setScreen(screen8.getScreenID()).setShowPrice(50.00f));
		
		Show showTKF = availableShows.get(1);
		screen8.getShows().add(showTKF.copyIt(theatre.getTheatreName())
				.setStartTime(LocalDateTime.of(2022, 04, 16, 15, 20))
				.setScreen(screen8.getScreenID()).setShowPrice(250.00f));
	}
	
	private static void buildTheatre4(Theatre theatre) {
		List<Screen> availableScreens = BuildScreens.screens;
		List<Show> availableShows = BuildShows.shows;
		List<Screen> theatre1Screens = theatre.getScreens();
		Screen screen9 = availableScreens.get(8);
		Show rrrShow = availableShows.get(0);
		List<Offer<?>> offers = BuildOffers.offers;
		
		theatre1Screens.add(screen9.addOffer(offers.get(1))
					   .setScreenName("Screen9"));
		
		screen9.getShows().add(rrrShow.copyIt(theatre.getTheatreName())
												.setStartTime(LocalDateTime.of(2022, 04, 16, 10, 30))
												.setScreen(screen9.getScreenID()).setShowPrice(300.00f));
		
		Show showTheLess = availableShows.get(3);
		screen9.getShows().add(showTheLess.copyIt(theatre.getTheatreName())
				.setStartTime(LocalDateTime.of(2022, 04, 16, 13, 50))
				.setScreen(screen9.getScreenID()).setShowPrice(100.00f));
		
		Show showTKF = availableShows.get(1);
		screen9.getShows().add(showTKF.copyIt(theatre.getTheatreName())
				.setStartTime(LocalDateTime.of(2022, 04, 16, 15, 20))
				.setScreen(screen9.getScreenID()).setShowPrice(300.00f));
	}
	
	public static Theatre getDummyTheatre() {
		if(dummyTheatre != null)
			return dummyTheatre;
		dummyTheatre = new Theatre("dummyTheatre","dummyCity","");
		return dummyTheatre;
	}

}
