package com.whotel.thirdparty.jxd.mode;

import java.util.Date;

import com.whotel.thirdparty.jxd.util.AbstractInputParam;

public class RoomBookQuery extends AbstractInputParam {

	private String opType = "房型预订";
	private String hotelCode;
	private String roomCode;
	private String roomName;
	private String rateCode;
	private String priceSystemId;
	private String salesPromotionId;
	private Date arriveDate;
	private Date leaveDate;
	private Integer roomQty;
	private Float price;
	private String payMent;
	private String priceName;
	
	public String getOpType() {
		return opType;
	}

	public void setOpType(String opType) {
		this.opType = opType;
	}
	
	public String getHotelCode() {
		return hotelCode;
	}

	public void setHotelCode(String hotelCode) {
		this.hotelCode = hotelCode;
	}

	public String getRoomCode() {
		return roomCode;
	}

	public void setRoomCode(String roomCode) {
		this.roomCode = roomCode;
	}
	
	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public String getRateCode() {
		return rateCode;
	}

	public void setRateCode(String rateCode) {
		this.rateCode = rateCode;
	}

	public String getPriceSystemId() {
		return priceSystemId;
	}

	public void setPriceSystemId(String priceSystemId) {
		this.priceSystemId = priceSystemId;
	}

	public String getSalesPromotionId() {
		return salesPromotionId;
	}

	public void setSalesPromotionId(String salesPromotionId) {
		this.salesPromotionId = salesPromotionId;
	}

	public Date getArriveDate() {
		return arriveDate;
	}

	public void setArriveDate(Date arriveDate) {
		this.arriveDate = arriveDate;
	}

	public Date getLeaveDate() {
		return leaveDate;
	}

	public void setLeaveDate(Date leaveDate) {
		this.leaveDate = leaveDate;
	}

	public Integer getRoomQty() {
		return roomQty;
	}

	public void setRoomQty(Integer roomQty) {
		this.roomQty = roomQty;
	}
	
	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public String getPayMent() {
		return payMent;
	}

	public void setPayMent(String payMent) {
		this.payMent = payMent;
	}

	@Override
	public String getRoot() {
		return null;
	}

	public String getPriceName() {
		return priceName;
	}

	public void setPriceName(String priceName) {
		this.priceName = priceName;
	}

}
