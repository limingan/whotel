package com.whotel.thirdparty.jxd.mode;

import java.util.Date;

import com.whotel.thirdparty.jxd.util.AbstractInputParam;

public class TicketAccessoryQuery extends AbstractInputParam {
	
	private String opType = "温泉门票附属产品查询";
	
	private String hotelCode;//酒店代码
	
	private Date beginDate;//开始日期
	
	private Date endDate;//结束日期
	
	private String salePromotionId;//优惠活动ID
	
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

	public String getSalePromotionId() {
		return salePromotionId;
	}

	public void setSalePromotionId(String salePromotionId) {
		this.salePromotionId = salePromotionId;
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

	@Override
	public String getRoot() {
		return null;
	}

}
