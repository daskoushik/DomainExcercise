package com.xyz.online.entities;

import java.util.ArrayList;
import java.util.List;

public class Theatre {
	
	private String theatreID;
	private String TheatreName;
	private String city;
	String pinCode;
	private List<Screen> screens;
	
	public Theatre(String theatreName, String city, String pinCode) {
		if(theatreName.length() < 6)
			throw new RuntimeException("Theatre name should be at least 6 characters long....");
		theatreID = theatreName.charAt(0)+""+ theatreName.charAt(1)+theatreName.hashCode()+pinCode;
		TheatreName = theatreName;
		this.city = city;
		this.pinCode = pinCode;
		this.screens = new ArrayList<>();
	}
	
	public String getTheatreID() {
		return theatreID;
	}
	public String getTheatreName() {
		return TheatreName;
	}
	public String getCity() {
		return city;
	}
	public List<Screen> getScreens() {
		return screens;
	}

	public String getPinCode() {
		return pinCode;
	}
	
}
