package com.whotel.thirdparty.jxd.mode.vo;

import java.util.Date;

import com.whotel.common.enums.Gender;
import com.whotel.thirdparty.jxd.enums.OrderStatusCode;
import com.whotel.thirdparty.jxd.enums.PayStatus;

public class OrderVO {

	private Integer orderId;
	private String orderSn;
	private Float totalRevenue;
	private Integer comOk;
	private OrderStatusCode statusCode;
	private PayStatus payStatus;
	private Float totalFee;
	private Float payAmount;
	private Integer roomNum;
	private String guests;
	private Date checkInTime;
	private Date checkOutTime;
	private String contact;
	private Gender contactSex;
	private String contactMobile;
	private String specialRequirements;
	private String arrivalTime;
	private String hotelCode;
	private String roomType;
	private String rateCode;
	private String reservationType; // 担保类型
	private Date createTime;
	private String bookComments;

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Float getTotalRevenue() {
		return totalRevenue;
	}

	public void setTotalRevenue(Float totalRevenue) {
		this.totalRevenue = totalRevenue;
	}

	public Integer getComOk() {
		return comOk;
	}

	public void setComOk(Integer comOk) {
		this.comOk = comOk;
	}

	public OrderStatusCode getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(OrderStatusCode statusCode) {
		this.statusCode = statusCode;
	}

	public Integer getRoomNum() {
		return roomNum;
	}

	public void setRoomNum(Integer roomNum) {
		this.roomNum = roomNum;
	}

	public Date getCheckInTime() {
		return checkInTime;
	}

	public void setCheckInTime(Date checkInTime) {
		this.checkInTime = checkInTime;
	}

	public Date getCheckOutTime() {
		return checkOutTime;
	}

	public void setCheckOutTime(Date checkOutTime) {
		this.checkOutTime = checkOutTime;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getContactMobile() {
		return contactMobile;
	}

	public void setContactMobile(String contactMobile) {
		this.contactMobile = contactMobile;
	}

	public String getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(String arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public String getRoomType() {
		return roomType;
	}

	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}

	public String getSpecialRequirements() {
		return specialRequirements;
	}

	public void setSpecialRequirements(String specialRequirements) {
		this.specialRequirements = specialRequirements;
	}

	public String getHotelCode() {
		return hotelCode;
	}

	public void setHotelCode(String hotelCode) {
		this.hotelCode = hotelCode;
	}

	public String getGuests() {
		return guests;
	}

	public void setGuests(String guests) {
		this.guests = guests;
	}

	public PayStatus getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(PayStatus payStatus) {
		this.payStatus = payStatus;
	}

	public Float getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(Float payAmount) {
		this.payAmount = payAmount;
	}

	public int getAdults() {
		if (guests != null) {
			String[] gs = guests.split(",");
			return gs.length;
		}
		return 1;
	}

	public String getRateCode() {
		return rateCode;
	}

	public void setRateCode(String rateCode) {
		this.rateCode = rateCode;
	}

	public Float getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(Float totalFee) {
		this.totalFee = totalFee;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public Gender getContactSex() {
		return contactSex;
	}

	public void setContactSex(Gender contactSex) {
		this.contactSex = contactSex;
	}

	public String getReservationType() {
		return reservationType;
	}

	public void setReservationType(String reservationType) {
		this.reservationType = reservationType;
	}

	public String getOrderSn() {
		return orderSn;
	}

	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}

	public String getBookComments() {
		return bookComments;
	}

	public void setBookComments(String bookComments) {
		this.bookComments = bookComments;
	}

}
