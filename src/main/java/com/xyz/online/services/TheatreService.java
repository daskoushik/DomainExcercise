package com.xyz.online.services;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


import com.xyz.online.customDB.BuildTheatres;
import com.xyz.online.dtos.ShowDTO;
import com.xyz.online.dtos.TheatreDTO;
import com.xyz.online.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TheatreService {
	
	@Autowired
	ScreenService screenService;
	
	public List<Theatre> getTheatres() {
		return BuildTheatres.theatres;
	}
	
	public List<TheatreDTO> getTheatresInCity(String cityName) {
		return BuildTheatres.theatres.stream()
				.filter(t -> t.getCity().equalsIgnoreCase(cityName))
				.map(th -> entityToDTO(th))
				.collect(Collectors.toList());
	}
	
	public List<TheatreDTO> getTheatresShowingInCity(String showName, String cityName) {
		List<Theatre> theatres = BuildTheatres.theatres;
		Map<Theatre, List<Show>> theatreShowingInCity = theatres.stream()
				.filter(theatre -> theatre.getCity().equalsIgnoreCase(cityName))
				.collect(toMap(theatre -> theatre, theatre -> getShowsInTheatre(theatre.getScreens(), showName)));
		
		List<TheatreDTO> theatresDTOList = new ArrayList<>();
		theatreShowingInCity.forEach((theatre, shows) -> {
			TheatreDTO th = new TheatreDTO(theatre.getTheatreName(), theatre.getCity(), theatre.getPinCode());
			addShowsToTheatre(th, shows);			
			theatresDTOList.add(th);
		});
		return theatresDTOList.stream().filter(th -> !th.getShows().isEmpty()).collect(toList());
	}
	
	public List<TheatreDTO> getTheatreShowingInCityByTime(String showName, String cityName, CustomCalendar cc) {
		List<Theatre> theatres = BuildTheatres.theatres;
		Map<Theatre, List<Show>> theatreShowingInCity = theatres.stream()
				.filter(theatre -> theatre.getCity().equalsIgnoreCase(cityName))
				.collect(toMap(theatre -> theatre, theatre -> getShowsInTheatreByTime(theatre.getScreens(), showName, cc)));
		
		List<TheatreDTO> theatresDTOList = new ArrayList<>();
		theatreShowingInCity.forEach((theatre, shows) -> {
			TheatreDTO th = new TheatreDTO(theatre.getTheatreName(), theatre.getCity(), theatre.getPinCode());
			addShowsToTheatre(th, shows);
			if(!shows.isEmpty())
				theatresDTOList.add(th);
		});
		return theatresDTOList.stream().filter(th -> !th.getShows().isEmpty()).collect(toList());
	}
	
	public Theatre getTheatreEntityByNameAndLocation(String theatreName, String city, String pinCode) {
		return BuildTheatres.theatres.stream()
				.filter(th -> th.getTheatreName().equalsIgnoreCase(theatreName)
						&& th.getCity().equalsIgnoreCase(city) && th.getPinCode().equals(pinCode))
				.findFirst().orElse(BuildTheatres.getDummyTheatre());
	}
	
	public List<Show> getShowsInTheatre(List<Screen> screens, String showName) {
		List<Show> shows = screens.stream()
				.flatMap(screen -> screenService.getShowsOnScreen(screen, showName).stream())
				.collect(toList());
		return shows;
	}
	
	private void addShowsToTheatre(TheatreDTO th, List<Show> shows) {
		List<ShowDTO> shs = th.getShows();
		for(Show show : shows) {
			if(show.getType().equals(ShowType.DUMMY))
				continue;
			Screen screen = screenService.getScreenByID(show.getScreen());
			ShowDTO sh = new ShowDTO(show.getShowName(), screen.getScreenName(), 
					show.getStartTime().getDayOfMonth()+"/"+show.getStartTime().getMonthValue()+"/"+show.getStartTime().getYear(),
					Integer.toString(show.getStartTime().getHour()), Integer.toString(show.getStartTime().getMinute()));
			shs.add(sh);
			th.getScreens().add(sh.getScreenName());
			List<String> screenOffers = screen.getOffers().stream().map(off -> off.getDescription()).collect(toList());
			List<String> showOffers = show.getOffer().stream().map(off -> off.getDescription()).collect(toList());
			th.getOffers().addAll(screenOffers);
			th.getOffers().addAll(showOffers);
		}
	}
	
	private List<Show> getShowsInTheatreByTime(List<Screen> screens, String showName, CustomCalendar cc) {
		String[] split = cc.date.split("/");
		LocalDateTime time = LocalDateTime.of(Integer.parseInt(split[2]), Integer.parseInt(split[1]), Integer.parseInt(split[0]), Integer.parseInt(cc.hour), Integer.parseInt(cc.min));
		return screens.stream()
				.map(screen -> screenService.getShowOnScreenByTime(screen, showName, time))
				.collect(toList());
	}
	
	public TheatreDTO entityToDTO(Theatre theatre) {
		TheatreDTO theatreDTO = new TheatreDTO(theatre.getTheatreName(), theatre.getCity(), theatre.getPinCode());
		List<String> screens = theatre.getScreens().stream().map(Screen::getScreenName).collect(toList());
		theatreDTO.getScreens().addAll(screens);
		List<String> offers = theatre.getScreens().stream()
		.flatMap(sc -> sc.getOffers().stream())
		.map(Offer::getDescription)
		.collect(toList());
		theatreDTO.setOffers(offers);
		return theatreDTO;
	}
	
	public boolean addShowToScreen(String theatreName, String city, String pinCode, Show show) {
		Theatre theatre = getTheatreEntityByNameAndLocation(theatreName, city, pinCode);
		Screen screen = theatre.getScreens()
				.stream().filter(sc -> sc.getScreenID().equalsIgnoreCase(show.getScreen()))
				.findFirst().orElse(null);

		if(screen == null)
			return false;
		return screenService.addShow(theatreName, screen, show, true);
	}
	
	public Show updateShowInTheatre(String theatreName, String cityName, String pinCode, Show show) {
		Show existingShow = getTheatreEntityByNameAndLocation(theatreName, cityName, pinCode).getScreens()
				.stream()
				.filter(sc -> sc.getScreenName().equalsIgnoreCase(screenService.getScreenByID(show.getScreen()).getScreenName()))
				.flatMap(sc -> sc.getShows().stream())
				.filter(sh -> sh.getShowID().equalsIgnoreCase(show.getShowID()))
				.findFirst().orElse(null);
		if(existingShow == null)
			return null;
		if(existingShow != null) {
			Screen screen = screenService.getScreenByID(existingShow.getScreen());
			Show original = screen.getShows().stream()
					.filter(sh -> sh.getShowID().equals(existingShow.getShowID())).findFirst().orElse(existingShow).cloneIt();
			if(screenService.removeShowFromScreen(screen, existingShow)) {
				existingShow.setOffer(show.getOffer());
				existingShow.setScreen(show.getScreen());
				existingShow.setShowPrice(show.getShowPrice());
				existingShow.setStartTime(show.getStartTime());
				if(!screenService.addShow(theatreName, screen, existingShow, false)) {
					screen.getShows().add(original);
					return original;
				}
			}
		}
		return existingShow;
	}
	
}
