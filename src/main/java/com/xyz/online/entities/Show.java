package com.xyz.online.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Show {
	
	private String showID;
	private String showName;
	private ShowType type;
	private String screenID;
	private LocalDateTime startTime;
	private int durationInMinutes;
	private List<Offer<?>> offers;
	private List<String> bookings;
	private float showPrice;
	
	public Show() {
		
	}
	
	public Show(String showName, ShowType type, int durationInMinutes) {
		this.showID = showName.charAt(0)+""+showName.hashCode()+type+System.currentTimeMillis();
		this.showName = showName;
		this.type = type;
		this.durationInMinutes = durationInMinutes;
		offers = new ArrayList<>();
		bookings = new ArrayList<>();
	}
	
	public Show setShowID() {
		this.showID = showName.charAt(0)+""+showName.hashCode();
		return this;
	}
	
	public Show setShowID(String showID) {
		this.showID = showID;
		return this;
	}
	
	public Show setShowName(String showName) {
		this.showName = showName;
		return this;
	}
	
	public Show copyIt(String theatreName) {
		Show show = new Show(this.getShowName(), this.getType(), this.getDurationInMinutes());
		show.showID = theatreName+show.showID;
		return show;
	}
	
	public Show cloneIt() {
		Show show = new Show();
		show.setShowID(this.showID)
		.setShowName(this.showName)
		.setShowType(this.type)
		.setOffer(this.offers)
		.setScreen(this.screenID)
		.setShowPrice(this.showPrice)
		.setStartTime(this.startTime)
		.setdurationInMinutes(this.durationInMinutes)
		.setBookings(this.bookings);
		return show;
	}
	
	public String getScreen() {
		return screenID;
	}

	public Show setScreen(String screenID) {
		this.screenID = screenID;
		return this;
	}

	public LocalDateTime getStartTime() {
		return startTime;
	}

	public Show setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
		return this;
	}

	public List<Offer<?>> getOffer() {
		return offers;
	}

	public Show setOffer(List<Offer<?>> offer) {
		this.offers = offer;
		return this;
	}

	public float getShowPrice() {
		return showPrice;
	}

	public Show setShowPrice(float showPrice) {
		this.showPrice = showPrice;
		return this;
	}

	public String getShowID() {
		return showID;
	}

	public String getShowName() {
		return showName;
	}
	
	public Show setShowType(ShowType showType) {
		this.type = showType;
		return this;
	}

	public ShowType getType() {
		return type;
	}

	public Show setdurationInMinutes(int min) {
		this.durationInMinutes = min;
		return this;
	}
	
	public int getDurationInMinutes() {
		return durationInMinutes;
	}
	
	public Show setBookings(List<String> bookings) {
		this.bookings = bookings;
		return this;
	}

	public List<String> getBookings() {
		return bookings;
	}
	
}
