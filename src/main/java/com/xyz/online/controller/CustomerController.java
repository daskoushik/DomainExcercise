package com.xyz.online.controller;

import java.util.List;
import java.util.stream.Collectors;

import com.xyz.online.dtos.BookingDTO;
import com.xyz.online.dtos.TheatreDTO;
import com.xyz.online.entities.CustomCalendar;
import com.xyz.online.services.BookingService;
import com.xyz.online.services.OfferService;
import com.xyz.online.services.TheatreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/audience")
public class CustomerController {
	
	@Autowired
	TheatreService theatreService;
	
	@Autowired
	BookingService bookingService;
	
	@Autowired
	OfferService offerService;
	
	@GetMapping("/theatres")
	public ResponseEntity<List<String>> getTheatres() {
		List<String> theatres = theatreService.getTheatres()
				.stream().map(t -> t.getTheatreName()).collect(Collectors.toList());
		ResponseEntity<List<String>> re = new ResponseEntity<>(theatres, HttpStatus.OK);
		return re;
	}
	
	@GetMapping("/theatres/city/{city}")
	public ResponseEntity<List<String>> getTheatresByCity(@PathVariable String city) {
		List<String> theatres = 
				theatreService.getTheatresInCity(city).stream().map(t -> t.getTheatreName()).collect(Collectors.toList());
		ResponseEntity<List<String>> re = new ResponseEntity<>(theatres, HttpStatus.OK);
		return re;
	}
	
	@GetMapping("/theatres/theatre/{theatre}/{city}/{pin}/offer")
	public ResponseEntity<List<String>> getOffersInTheatres(@PathVariable(name = "theatre") String theatreName,
			@PathVariable(name = "city") String cityName, @PathVariable(name = "pin") String pinCode) {
		List<String> offersInTheatre = offerService.getOffersInTheatre(theatreName, cityName, pinCode);
		ResponseEntity<List<String>> re = new ResponseEntity<>(offersInTheatre, HttpStatus.OK);
		return re;
	}
	
	@GetMapping("/theatres/city/{city}/show/{show}")
	public ResponseEntity<List<TheatreDTO>> getTheatresByCity_Show(@PathVariable String city, @PathVariable(name = "show") String showName) {
		List<TheatreDTO> theatreShowingInCity = theatreService.getTheatresShowingInCity(showName, city);
		ResponseEntity<List<TheatreDTO>> re = new ResponseEntity<>(theatreShowingInCity, HttpStatus.OK);
		return re;
		
	}
	
	@PostMapping("/theatres/city/{city}/show/{show}")
	public ResponseEntity<List<TheatreDTO>> getTheatresByCity_Show_Calendar(@PathVariable String city,
			@PathVariable(name = "show") String showName , @RequestBody CustomCalendar cc ) {
		List<TheatreDTO> theatreShowingInCityByTime = theatreService.getTheatreShowingInCityByTime(showName, city, cc);
		ResponseEntity<List<TheatreDTO>> re = new ResponseEntity<>(theatreShowingInCityByTime, HttpStatus.OK);
		return re;
	}
	
	@GetMapping("/theatres/booking/{id}")
	public ResponseEntity<BookingDTO> getBookingByID(@PathVariable(name = "id") String bookingID) {
		BookingDTO bookingDTO = bookingService.getBookingById(bookingID);
		ResponseEntity<BookingDTO> re = new ResponseEntity<>(bookingDTO, StringUtils.hasText(bookingDTO.getBookingID()) ? HttpStatus.FOUND : HttpStatus.NOT_FOUND);
		return re;
	}
	
	@GetMapping("/theatres/booking/dummy")
	public ResponseEntity<BookingDTO> getDummyBooking(String bookingID) {
		BookingDTO bookingDTO = bookingService.getDummyBooking();
		ResponseEntity<BookingDTO> re = new ResponseEntity<>(bookingDTO, HttpStatus.FOUND);
		return re;
	}
	
	@PostMapping("/theatres/booking/")
	public ResponseEntity<BookingDTO> confirmBooking(@RequestBody BookingDTO booking) {
		booking.setBookingID(Long.toString(System.nanoTime()));
		BookingDTO confirmBooking = bookingService.confirmBooking(booking);
		ResponseEntity<BookingDTO> re = new ResponseEntity<>(confirmBooking, HttpStatus.CREATED);
		return re;
	}

}
