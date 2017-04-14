package com.whotel.thirdparty.jxd.mode;

import java.util.Date;

import com.whotel.thirdparty.jxd.util.AbstractInputParam;

public class TicketPriceQuery extends AbstractInputParam {
	
	private String opType = "房型价格明细查询";
	
	private String hotelCode;
	
	private Date beginDate;
	
	private Date endDate;
	
	private String saleCode;
	
	private String profileId;
	
	private String mbrCardTypeCode;
	
	private String source = "weixin";
	
	private Integer roomQty;
	
	private String orderItemCode;
	
	private String rateCode;
	
	private String salePromotionId;
	
	private String orderCategory = "WqTicket";
	
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

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getSaleCode() {
		return saleCode;
	}

	public void setSaleCode(String saleCode) {
		this.saleCode = saleCode;
	}

	public String getProfileId() {
		return profileId;
	}

	public void setProfileId(String profileId) {
		this.profileId = profileId;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public Integer getRoomQty() {
		return roomQty;
	}

	public void setRoomQty(Integer roomQty) {
		this.roomQty = roomQty;
	}

	public String getOrderItemCode() {
		return orderItemCode;
	}

	public void setOrderItemCode(String orderItemCode) {
		this.orderItemCode = orderItemCode;
	}

	public String getRateCode() {
		return rateCode;
	}

	public void setRateCode(String rateCode) {
		this.rateCode = rateCode;
	}

	public String getSalePromotionId() {
		return salePromotionId;
	}

	public void setSalePromotionId(String salePromotionId) {
		this.salePromotionId = salePromotionId;
	}

	@Override
	public String getRoot() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getOrderCategory() {
		return orderCategory;
	}

	public void setOrderCategory(String orderCategory) {
		this.orderCategory = orderCategory;
	}

	public String getMbrCardTypeCode() {
		return mbrCardTypeCode;
	}

	public void setMbrCardTypeCode(String mbrCardTypeCode) {
		this.mbrCardTypeCode = mbrCardTypeCode;
	}
}
