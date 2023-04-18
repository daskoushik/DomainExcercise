package com.xyz.online.customDB;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import com.xyz.online.entities.Offer;
import org.springframework.stereotype.Repository;


@Repository
public class BuildOffers {
	
	public static List<Offer<?>> offers = new ArrayList<>();
	public static Offer<Void> nullOffer;
	
	public static void build() {
		Offer<Integer> thirdTktDiscount = new Offer<>((Predicate<Integer>)(p -> p >= 3), Integer.class, "ThirdTktDiscount", "50% discount on the third ticket");
		Offer<LocalDateTime> afternoonTktDiscount = new Offer<>((Predicate<LocalDateTime>)(t -> t.getHour() > 12 && t.getHour() < 5), LocalDateTime.class, "AfternoonTktDiscount",
				"Tickets booked for the afternoon show get a 20% discount");
		offers.add(thirdTktDiscount);
		offers.add(afternoonTktDiscount);
		nullOffer = new Offer<>("No offer available...");
	}

}
