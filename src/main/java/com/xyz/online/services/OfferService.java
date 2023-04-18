package com.xyz.online.services;

import java.text.DecimalFormat;
import java.util.List;

import com.xyz.online.customDB.BuildOffers;
import com.xyz.online.dtos.BookingDTO;
import com.xyz.online.entities.Offer;
import com.xyz.online.entities.Show;
import com.xyz.online.entities.Theatre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OfferService {
	
	private static final DecimalFormat df = new DecimalFormat("0.00");
	
	@Autowired
	TheatreService theatreService;
	
	@Autowired
	ShowService showService;
	
	@Autowired
	ScreenService screenService;
	
	public List<Offer<?>> getAvailableOffersInSystem() {
		return BuildOffers.offers;
	}
	
	public Offer<?> getOffer(String offerID) {
		return BuildOffers.offers.stream()
				.filter(offer -> offer.getOfferID().equalsIgnoreCase(offerID))
				.findFirst().orElse(BuildOffers.nullOffer);
	}
	
	public List<String> getOffersInTheatre(String theatreName, String city, String pinCode) {
		return theatreService.entityToDTO(
				theatreService.getTheatreEntityByNameAndLocation(theatreName, city, pinCode)).getOffers();
	}
	
	public float getDiscount(BookingDTO booking) {
		float discount = 0;
		int tktsCount = booking.getTktsCount();
		float showPrice = booking.getShowPrice();
		float totalPrice = tktsCount*showPrice;
		Theatre theatre = theatreService.getTheatreEntityByNameAndLocation(booking.getTheatre(), booking.getCity(), booking.getPinCode());
		Show show = showService.getShow(theatreService.getShowsInTheatre(theatre.getScreens(), booking.getShow()),
				booking.getShow(), booking.getShowTime());
		List<Offer<?>> offers = screenService.getScreenByID(show.getScreen()).getOffers();
		offers.addAll(show.getOffer());
		
		for(Offer offer : offers) {
			if(offer.getOfferName().equalsIgnoreCase("ThirdTktDiscount") && offer.getOffer().test(tktsCount)) {
				discount += Float.parseFloat(df.format(showPrice/2));
				totalPrice -=discount;
			}
			else if(offer.getOfferName().equalsIgnoreCase("AfternoonTktDiscount") && offer.getOffer().test(booking.getShowTime())) {
				discount += Float.parseFloat(df.format(totalPrice*0.2));
			}
		}
		return discount;
	}

}
