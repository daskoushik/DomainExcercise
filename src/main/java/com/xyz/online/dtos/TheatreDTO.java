package com.xyz.online.dtos;

import java.util.ArrayList;
import java.util.List;

public class TheatreDTO {
	String theatreName;
	String city;
	String pinCode;
	List<ShowDTO> shows;
	List<String> screens;
	List<String> offers;
	
	public TheatreDTO(String theatreName, String city, String pinCode) {
		this.theatreName = theatreName;
		this.city = city;
		this.pinCode = pinCode;
		shows = new ArrayList<>();
		screens = new ArrayList<>();
		offers = new ArrayList<>();
	}

	public String getTheatreName() {
		return theatreName;
	}

	public String getCity() {
		return city;
	}

	public List<ShowDTO> getShows() {
		return shows;
	}

	public String getPinCode() {
		return pinCode;
	}

	public List<String> getScreens() {
		return screens;
	}

	public List<String> getOffers() {
		return offers;
	}

	public void setOffers(List<String> offers) {
		this.offers = offers;
	}
	
}
