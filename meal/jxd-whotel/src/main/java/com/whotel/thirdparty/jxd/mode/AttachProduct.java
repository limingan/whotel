package com.whotel.thirdparty.jxd.mode;

import com.whotel.thirdparty.jxd.util.XmlBean;

public class AttachProduct implements XmlBean {

	private String code;//房型代码
	private Integer roomQty;// 数量
	private Float totalAmount;//金额
	private String serviceType;//类型
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Integer getRoomQty() {
		return roomQty;
	}
	public void setRoomQty(Integer roomQty) {
		this.roomQty = roomQty;
	}
	public Float getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(Float totalAmount) {
		this.totalAmount = totalAmount;
	}
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	
	@Override
	public String getRoot() {
		return "AttachProduct";
	}
}
