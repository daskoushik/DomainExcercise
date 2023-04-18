package com.xyz.online.entities;

import java.util.function.Predicate;

public class Offer<T> {
	
	private String offerID;
	private Predicate<T> offer;
	private Class<T> klass;
	private String offerName;
	private String description;
	
	public Offer(String description) {
		this.offerID = "voidOffer:"+description.hashCode();
		this.description = description;
	}
	
	public Offer(Predicate<T> offer, Class<T> klass, String offerName, String description) {
		this.offer = offer;
		this.offerID = "offer"+offer.hashCode();
		this.klass = klass;
		this.offerName = offerName;
		this.description = description;
	}

	public String getOfferID() {
		return offerID;
	}

	public Predicate<T> getOffer() {
		return offer;
	}
	
	public Class<T> getThisClass() {
		return klass;
	}
	
	public String getOfferName() {
		return offerName;
	}
	
	public String getDescription() {
		return description;
	}
	
}
