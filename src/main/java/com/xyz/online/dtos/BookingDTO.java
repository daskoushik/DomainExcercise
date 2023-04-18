package com.xyz.online.dtos;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.xyz.online.entities.BookingStatus;
import lombok.Data;

@Data
public class BookingDTO {

	private String bookingID;
	private String inSystemBookingID;
	private String theatreName;
	private String showName;
	private int tktsCount;
	private Map<Integer, List<Integer>> seatsLoc;
	private float showPrice;
	private float totalBookingPrice;
	private LocalDateTime bookingTime;
	private LocalDateTime showTime;
	private BookingStatus status;
	private String city;
	private String pinCode;
	
	public BookingDTO(String theatre, String show, Map<Integer, List<Integer>> seatsLoc) {
		this.theatreName = theatre;
		this.showName = show;
		this.seatsLoc = seatsLoc; 
		tktsCount = seatsLoc.keySet().stream().flatMap(k -> seatsLoc.get(k).stream()).collect(Collectors.collectingAndThen(Collectors.counting(), Long::intValue));
		this.bookingTime = LocalDateTime.now();
		status = BookingStatus.INPROGRESS;
		bookingID = Long.toString(System.nanoTime());
	}
	
	public Map<Integer, List<Integer>> getSeatsLoc() {
		return seatsLoc;
	}
	
	public BookingDTO setSeatsLoc(Map<Integer, List<Integer>> seatsLoc) {
		this.seatsLoc = seatsLoc;
		this.tktsCount = seatsLoc.size();
		return this;
	}

	public String getBookingID() {
		return bookingID;
	}

	public String getTheatre() {
		return theatreName;
	}

	public String getShow() {
		return showName;
	}

	public int getTktsCount() {
		return tktsCount;
	}
	
	public BookingDTO setTotalBookingPrice(float price) {
		this.totalBookingPrice = price;
		return this;
	}

	public float getTotalBookingPrice() {
		return totalBookingPrice;
	}

	public LocalDateTime getBookingTime() {
		return bookingTime;
	}
	
	public BookingDTO setStatus(BookingStatus status) {
		this.status = status;
		return this;
	}

	public BookingStatus getStatus() {
		return status;
	}
	
	public BookingDTO setBookingID(String bookingID) {
		this.bookingID = bookingID;
		return this;
	}

	public String getCity() {
		return city;
	}

	public BookingDTO setCity(String city) {
		this.city = city;
		return this;
	}

	public String getPinCode() {
		return pinCode;
	}

	public BookingDTO setPinCode(String pinCode) {
		this.pinCode = pinCode;
		return this;
	}

	public LocalDateTime getShowTime() {
		return showTime;
	}

	public BookingDTO setShowTime(LocalDateTime showTime) {
		this.showTime = showTime;
		return this;
	}

	public float getShowPrice() {
		return showPrice;
	}

	public BookingDTO setShowPrice(float showPrice) {
		this.showPrice = showPrice;
		return this;
	}

	public String getInSystemBookingID() {
		return inSystemBookingID;
	}

	public BookingDTO setInSystemBookingID(String inSystemBookingID) {
		this.inSystemBookingID = inSystemBookingID;
		return this;
	}
	
}
