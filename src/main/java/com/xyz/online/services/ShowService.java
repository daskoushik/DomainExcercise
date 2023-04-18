package com.xyz.online.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.xyz.online.customDB.BuildShows;
import com.xyz.online.entities.Show;
import org.springframework.stereotype.Service;

@Service
public class ShowService {
	
	public boolean isPresentShowByName(List<Show> shows, String showName) {
		return shows.stream()
				.anyMatch(show -> show.getShowName().equalsIgnoreCase(showName));

	}
	
	public Show getShow(List<Show> shows, String showName, LocalDateTime time) {
		return shows.stream()
				.filter(show -> check(show, time, showName))
				.findFirst().orElse(BuildShows.getDummyShow());
	}
	
	private boolean check(Show show, LocalDateTime time, String showName) {
		return (show.getShowName().equalsIgnoreCase(showName) && 
				show.getStartTime().getDayOfMonth() == time.getDayOfMonth() && 
				show.getStartTime().getMonth() == time.getMonth() && show.getStartTime().getYear() == time.getYear() && 
				show.getStartTime().getHour() == time.getHour() && show.getStartTime().getMinute() == time.getMinute());
	}
	
	public List<Show> getShowsInSystem(String theatreName) {
		return BuildShows.shows.stream()
				.map(show -> show.copyIt(theatreName))
				.collect(Collectors.toList());
	}

}
