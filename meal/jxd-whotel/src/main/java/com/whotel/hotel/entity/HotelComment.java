package com.whotel.hotel.entity;

import org.mongodb.morphia.annotations.Entity;

@Entity(noClassnameStored=true)
public class HotelComment extends BaseComment {
	
	private static final long serialVersionUID = 9160294128003789605L;
	
	private String hotelCode;//酒店
	
	public String getHotelCode() {
		return hotelCode;
	}

	public void setHotelCode(String hotelCode) {
		this.hotelCode = hotelCode;
	}
	
}
