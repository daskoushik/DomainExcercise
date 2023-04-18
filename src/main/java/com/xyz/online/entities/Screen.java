package com.xyz.online.entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Screen {
	
	private static long counter = 1l;
	
	private String screenID;
	private String screenName;
	private List<Show> shows;
	private int totalSeats;
	private int availableSeats;
	private int[][] seats;
	private int rows;
	private int cols;
	private List<Offer<?>> offer;
	
	public Screen() {
		
	}
	
	public Screen(int rows, int cols) {
		long currentTimeMillis = System.currentTimeMillis();
		shows = new ArrayList<>();
		this.screenID = "BMS"+currentTimeMillis+(counter++);
		this.rows = rows;
		this.cols = cols;
		totalSeats = rows*cols;
		availableSeats = totalSeats;
		prepareSeats(rows, cols);
		offer = new ArrayList<>();
	}

	public Screen prepareSeats(int rows, int cols) {
		seats = new int[rows][cols];
		for(int i = 0; i < rows; i++) {
			Arrays.fill(seats[i], 1);
		}
		return this;
	}
	
	public void changeShows(List<Show> shows) {
		this.shows = shows;
	}
	
	public boolean removeShow(Show show) {
		return shows.remove(show);
	}
	
	public int[][] getSeats() {
		return seats;
	}

	public String getScreenID() {
		return screenID;
	}

	public List<Show> getShows() {
		return shows;
	}
	
	public boolean addShow(Show show) {
		return shows.add(show);
	}

	public int getTotalSeats() {
		return totalSeats;
	}

	public List<Offer<?>> getOffers() {
		return offer;
	}

	public Screen addOffer(Offer<?> offer) {
		this.offer.add(offer);
		return this;
	}
	
	public Screen removeOffer(Offer<?> offer) {
		this.offer.remove(offer);
		return this;
	}

	public String getScreenName() {
		return screenName;
	}

	public Screen setScreenName(String screenName) {
		this.screenName = screenName;
		return this;
	}

	public int getAvailableSeats() {
		return availableSeats;
	}

	public void setAvailableSeats(int availableSeats) {
		this.availableSeats = availableSeats;
	}
	
}
