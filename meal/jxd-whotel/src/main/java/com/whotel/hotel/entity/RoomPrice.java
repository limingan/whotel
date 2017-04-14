package com.whotel.hotel.entity;

import java.util.Date;

import org.mongodb.morphia.annotations.Embedded;

@Embedded
public class RoomPrice {
	
	private String id;
	
	private Date date;
	
	private Float price;

	private Integer breakfast;
	
	private Float servicerate;
	
	private String roomCode;
	
	private String bookingNotice;
	
	private String cancelNotice;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public Integer getBreakfast() {
		return breakfast;
	}

	public void setBreakfast(Integer breakfast) {
		this.breakfast = breakfast;
	}

	public Float getServicerate() {
		return servicerate;
	}

	public void setServicerate(Float servicerate) {
		this.servicerate = servicerate;
	}

	public String getRoomCode() {
		return roomCode;
	}

	public void setRoomCode(String roomCode) {
		this.roomCode = roomCode;
	}

	public String getBookingNotice() {
		return bookingNotice;
	}

	public void setBookingNotice(String bookingNotice) {
		this.bookingNotice = bookingNotice;
	}

	public String getCancelNotice() {
		return cancelNotice;
	}

	public void setCancelNotice(String cancelNotice) {
		this.cancelNotice = cancelNotice;
	}
}
