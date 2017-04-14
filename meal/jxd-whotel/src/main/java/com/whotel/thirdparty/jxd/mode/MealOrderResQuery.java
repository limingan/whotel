package com.whotel.thirdparty.jxd.mode;

import com.whotel.thirdparty.jxd.util.AbstractInputParam;

/**
 * 封装预订查询
 * @author 冯勇
 * 
 */
public class MealOrderResQuery extends AbstractInputParam {
	
	private String opType = "餐饮微信订单查询";
	
	private String hotelCode;
	
	private String orderDate;
	
	private String refeNo;
	
	private String wxid;//微信id
	
	public String getWxid() {
		return wxid;
	}

	public void setWxid(String wxid) {
		this.wxid = wxid;
	}

	public String getOpType() {
		return opType;
	}

	public void setOpType(String opType) {
		this.opType = opType;
	}

	@Override
	public String getRoot() {
		return null;
	}

	public String getHotelCode() {
		return hotelCode;
	}

	public void setHotelCode(String hotelCode) {
		this.hotelCode = hotelCode;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public String getRefeNo() {
		return refeNo;
	}

	public void setRefeNo(String refeNo) {
		this.refeNo = refeNo;
	}

}
