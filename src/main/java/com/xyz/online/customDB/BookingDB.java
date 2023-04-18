package com.xyz.online.customDB;

import java.util.*;

import com.xyz.online.entities.Booking;
import com.xyz.online.entities.BookingStatus;

public class BookingDB {
	
	private static Map<String, Booking> bookings = new HashMap<>();
	private static Booking dummyBooking = null;
	
	public static Booking getBookingByID(String bookingID) {
		return bookings.keySet().parallelStream()
				.filter(b -> b.equalsIgnoreCase(bookingID))
				.map(id -> bookings.get(id))
				.findAny().orElse(getDummyBooking());
	}
	
	public static Booking persistBooking(Booking booking) {
		bookings.put(booking.getBookingID(), booking);
		return booking;
	}
	
	public static Booking getDummyBooking() {
		if(dummyBooking != null)
			return dummyBooking;
		Map<Integer, List<Integer>> seatsLoc = new HashMap<>();
		seatsLoc.put(1, new ArrayList<>(Arrays.asList(22,23,24)));
		dummyBooking = new Booking(BuildTheatres.getDummyTheatre(), BuildShows.getDummyShow(), seatsLoc);
		dummyBooking.setStatus(BookingStatus.DUMMY);
		dummyBooking.setTotalBookingPrice(1375.50f);
		return dummyBooking;
	}

}
