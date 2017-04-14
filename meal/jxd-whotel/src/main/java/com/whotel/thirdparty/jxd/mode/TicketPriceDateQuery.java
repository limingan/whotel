package com.whotel.thirdparty.jxd.mode;

import java.util.Date;

import com.whotel.thirdparty.jxd.util.AbstractInputParam;

public class TicketPriceDateQuery extends AbstractInputParam {
	
	private String opType = "可预订分店列表价格查询";
	private Date beginDate;
	private Date endDate;
	private String source = "weixin";
	private String orderCategory = "WqTicket";
	private String resortId;
	private String code;
	private String priceId;
	private String profileId;
	
	public String getOpType() {
		return opType;
	}

	public void setOpType(String opType) {
		this.opType = opType;
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

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getOrderCategory() {
		return orderCategory;
	}

	public void setOrderCategory(String orderCategory) {
		this.orderCategory = orderCategory;
	}

	public String getResortId() {
		return resortId;
	}

	public void setResortId(String resortId) {
		this.resortId = resortId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getPriceId() {
		return priceId;
	}

	public void setPriceId(String priceId) {
		this.priceId = priceId;
	}

	@Override
	public String getRoot() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getProfileId() {
		return profileId;
	}

	public void setProfileId(String profileId) {
		this.profileId = profileId;
	}
}
