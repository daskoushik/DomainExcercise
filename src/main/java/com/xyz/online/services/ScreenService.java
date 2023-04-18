package com.xyz.online.services;

import static java.util.stream.Collectors.toList;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.xyz.online.customDB.BuildScreens;
import com.xyz.online.customDB.BuildShows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xyz.online.entities.Booking;
import com.xyz.online.entities.BookingStatus;
import com.xyz.online.entities.Screen;
import com.xyz.online.entities.Show;
import com.xyz.online.entities.Theatre;

@Service
public class ScreenService {
	
	@Autowired
	ShowService showService;
	
	public synchronized int confirmBooking(Booking booking) {
		Integer bookedSeats = 0;
		if(booking.getStatus().equals(BookingStatus.DUMMY))
			return bookedSeats;
		Screen screen = getScreenByID(booking.getShow().getScreen());
		int availableSeats = screen.getAvailableSeats();
		int[][] seats = screen.getSeats();
		if(!booking.getShow().getStartTime().isAfter(booking.getBookingTime()) ||
				availableSeats-booking.getTktsCount() < 0)
			return bookedSeats;
		Map<Integer, List<Integer>> seatsLoc = booking.getSeatsLoc();
		Set<Integer> rowNums = seatsLoc.keySet();
		for(int rowNum : rowNums) {
			List<Integer> colNums = seatsLoc.get(rowNum);
			for(int colNum : colNums) {
				if(seats[rowNum][colNum] == 1) {
					bookedSeats++;
					seats[rowNum][colNum] = 0;
				} else
					break;
			}
		}
		if(bookedSeats != booking.getTktsCount())
			return 0;
		availableSeats-=bookedSeats;
		screen.setAvailableSeats(availableSeats);
		return bookedSeats;
	}
	
	public synchronized boolean addShow(String theatreName, Screen screen, Show show, boolean check) {
		List<Show> shows = screen.getShows();
		Show showIt = null;
		if(check) {
			Show inSystemShow = BuildShows.shows.stream()
					.filter(sh -> sh.getShowName().equalsIgnoreCase(show.getShowName()))
					.findAny().orElse(null);
			if(inSystemShow == null)
				return false;
			showIt = inSystemShow.copyIt(theatreName);
		} else
			showIt = show;
		showIt.setStartTime(show.getStartTime());
		showIt.setShowPrice(show.getShowPrice());
		showIt.setScreen(show.getScreen());
		for(Show sh : shows) {
			if(showIt.getStartTime().equals(sh.getStartTime()) ||
					(showIt.getStartTime().isAfter(sh.getStartTime()) && showIt.getStartTime().isBefore(sh.getStartTime().plusMinutes(sh.getDurationInMinutes()).plusMinutes(20))) || 
					(showIt.getStartTime().isBefore(sh.getStartTime()) && showIt.getStartTime().plusMinutes(showIt.getDurationInMinutes()).plusMinutes(20).isAfter(sh.getStartTime())) ||
					showIt.getStartTime().equals(sh.getStartTime().plusMinutes(sh.getDurationInMinutes())))
				return false;
		}
		return screen.addShow(showIt);
	}
	
	public List<Show> getShowsOnScreen(Screen screen, String showName) {
		return screen.getShows().stream()
				.filter(show -> show.getShowName().equalsIgnoreCase(showName))
				.collect(toList());
	}
	
	public Show getShowOnScreenByTime(Screen screen, String showName, LocalDateTime time) {
		return showService.getShow(screen.getShows(), showName, time);
	}
	
	public boolean isPresentScreenByShow(List<Screen> screens, String showName) {
		return screens.stream()
				.filter(screen -> showService.isPresentShowByName(screen.getShows(), showName))
				.findAny().isPresent();
	}
	
	public Screen getScreenByNameAndTheatre(Theatre theatre, String screenName) {
		return theatre.getScreens().stream()
				.filter(sc -> sc.getScreenName().equalsIgnoreCase(screenName))
				.findFirst().orElse(null);
	}
	
	public Screen getScreenByID(String screenID) {
		return BuildScreens.screens.stream()
				.filter(sc -> sc.getScreenID().equalsIgnoreCase(screenID))
				.findFirst().orElse(null);
	}
	
	public boolean removeShowFromScreen(Screen screen, Show show) {
		return screen.getShows().remove(show);
	}

}
