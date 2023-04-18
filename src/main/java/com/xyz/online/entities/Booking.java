package com.xyz.online.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Booking {

	private String bookingID;
	private Theatre theatre;
	private Show show;
	private int tktsCount;
	private Map<Integer, List<Integer>> seatsLoc;
	private float totalBookingPrice;
	private LocalDateTime bookingTime;
	private BookingStatus status;
	
	public Booking(Theatre theatre, Show show, Map<Integer, List<Integer>> seatsLoc) {
		this.theatre = theatre;
		this.show = show;
		this.seatsLoc = new HashMap<>();
		seatsLoc.forEach((k, v) -> {
			List<Integer> seats = new ArrayList<>();
			seats.addAll(v);
			this.seatsLoc.put(k, Collections.unmodifiableList(seats));
		});
		tktsCount = seatsLoc.keySet().stream().flatMap(k -> seatsLoc.get(k).stream()).collect(Collectors.collectingAndThen(Collectors.counting(), Long::intValue));
		this.bookingTime = LocalDateTime.now();
		status = BookingStatus.INPROGRESS;
		this.bookingID = theatre.getTheatreID().substring(1, 4)+show.getShowID().substring(0,4)+seatsLoc.hashCode()+System.nanoTime();
	}
	
	public Map<Integer, List<Integer>> getSeatsLoc() {
		return Collections.unmodifiableMap(seatsLoc);
	}

	public void setSeatsLoc(Map<Integer, List<Integer>> seatsLoc) {
		this.seatsLoc = seatsLoc;
	}

	public String getBookingID() {
		return bookingID;
	}

	public Theatre getTheatre() {
		return theatre;
	}

	public Show getShow() {
		return show;
	}

	public int getTktsCount() {
		return tktsCount;
	}

	public void setTotalBookingPrice(float totalBookingPrice) {
		this.totalBookingPrice = totalBookingPrice;
	}
	
	public float getTotalBookingPrice() {
		return totalBookingPrice;
	}

	public LocalDateTime getBookingTime() {
		return bookingTime;
	}
	
	public void setStatus(BookingStatus status) {
		this.status = status;
	}

	public BookingStatus getStatus() {
		return status;
	}
	
}
