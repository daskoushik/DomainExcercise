package com.xyz.online.dtos;

public class ShowDTO {
	
	String showName;
	String screenName;
	String date;
	String hour;
	String min;
	
	public ShowDTO(String showName, String screenName, String date, String hour, String min) {
		this.showName = showName;
		this.screenName = screenName;
		this.date = date;
		this.hour = hour;
		this.min = min;
	}

	public String getShowName() {
		return showName;
	}

	public String getScreenName() {
		return screenName;
	}

	public String getDate() {
		return date;
	}

	public String getHour() {
		return hour;
	}

	public String getMin() {
		return min;
	}
	

}
