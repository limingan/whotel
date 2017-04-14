package com.whotel.thirdparty.jxd.mode;

import java.util.Map;

import com.whotel.thirdparty.jxd.util.AbstractInputParam;

/**
 * 封装客房预订请求
 * @author 冯勇
 * 
 */
public class HotelOrderQuery extends AbstractInputParam {
	private String opType = "E云通预订";
	private Map<String, Object> reservation;
	private String orderCategory;

	public String getOpType() {
		return opType;
	}

	public void setOpType(String opType) {
		this.opType = opType;
	}

	public Map<String, Object> getReservation() {
		return reservation;
	}

	public void setReservation(Map<String, Object> reservation) {
		this.reservation = reservation;
	}

	@Override
	public String getRoot() {
		return null;
	}

	public String getOrderCategory() {
		return orderCategory;
	}

	public void setOrderCategory(String orderCategory) {
		this.orderCategory = orderCategory;
	}

}
