package com.whotel.thirdparty.jxd.mode;

import com.whotel.thirdparty.jxd.util.AbstractInputParam;

public class HotelServiceQuery extends AbstractInputParam {
	
	private String opType = "增值服务项目查询";
	
	private String hotelCode;
	
	private String rateCode;
	
	private String salePromotionId;
	
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
		return null;
	}
}
