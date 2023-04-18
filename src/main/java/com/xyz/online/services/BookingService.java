package com.xyz.online.services;

import com.xyz.online.customDB.BookingDB;
import com.xyz.online.dtos.BookingDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xyz.online.entities.Booking;
import com.xyz.online.entities.BookingStatus;
import com.xyz.online.entities.Show;
import com.xyz.online.entities.ShowType;
import com.xyz.online.entities.Theatre;

@Service
public class BookingService {
	
	@Autowired
	OfferService offerService;
	
	@Autowired
	TheatreService theatreService;
	
	@Autowired
	ShowService showService;
	
	@Autowired
	ScreenService screenService;
	
	public BookingDTO confirmBooking(BookingDTO bookingDTO) {
		if(bookingDTO.getStatus().equals(BookingStatus.DUMMY))
			return bookingDTO;
		Booking booking = dtoToEntity(bookingDTO);
		int confirmedTkts = screenService.confirmBooking(booking);
		if(confirmedTkts != 0) {
			booking.setStatus(BookingStatus.CONFIRMED);
			booking.setTotalBookingPrice(getBookingPrice(booking));
			booking.getShow().getBookings().add(booking.getBookingID());
			BookingDB.persistBooking(booking);
			bookingDTO.setStatus(BookingStatus.CONFIRMED);
			bookingDTO.setTotalBookingPrice(booking.getTotalBookingPrice());
			bookingDTO.setInSystemBookingID(booking.getBookingID());
			bookingDTO.setShowPrice(booking.getShow().getShowPrice());
		} else {
			bookingDTO.setStatus(BookingStatus.NOTPOSSIBLE)
			.setShowPrice(booking.getShow().getShowPrice());
		}
		return bookingDTO;
	}
	
	public float getBookingPrice(BookingDTO booking) {
		float totalBookingPrice = booking.getTktsCount()*booking.getShowPrice();
		totalBookingPrice-=offerService.getDiscount(booking);
		return totalBookingPrice;
	}
	
	private float getBookingPrice(Booking booking) {
		BookingDTO bookingDTO = entityToDto(booking);
		return getBookingPrice(bookingDTO);
	}
	
	public BookingDTO getBookingById(String bookingID) {
		return entityToDto(BookingDB.getBookingByID(bookingID));
	}
	
	public BookingDTO getDummyBooking() {
		Booking dummyBooking = BookingDB.getDummyBooking();
		return entityToDto(dummyBooking);
	}
	
	private Booking dtoToEntity(BookingDTO bookingDTO) {
		Theatre theatre = theatreService.getTheatreEntityByNameAndLocation(bookingDTO.getTheatre(), bookingDTO.getCity(), bookingDTO.getPinCode());
		Show show = showService.getShow(theatreService.getShowsInTheatre(theatre.getScreens(), bookingDTO.getShow()), bookingDTO.getShow(), bookingDTO.getShowTime());
		if(show.getType().equals(ShowType.DUMMY))
			return BookingDB.getDummyBooking();
		Booking booking = new Booking(theatre, show, bookingDTO.getSeatsLoc());
		return booking;
	}
	
	private BookingDTO entityToDto(Booking booking) {
		BookingDTO bookingDTO = new BookingDTO(booking.getTheatre().getTheatreName(), booking.getShow().getShowName(), booking.getSeatsLoc());
		bookingDTO = bookingDTO.setInSystemBookingID(booking.getBookingID())
				.setBookingID("")
				.setStatus(booking.getStatus())
				.setTotalBookingPrice(booking.getTotalBookingPrice())
				.setShowTime(booking.getShow().getStartTime())
				.setShowPrice(booking.getShow().getShowPrice())
				.setCity(booking.getTheatre().getCity())
				.setPinCode(booking.getTheatre().getPinCode());
		return bookingDTO;
	}

}
