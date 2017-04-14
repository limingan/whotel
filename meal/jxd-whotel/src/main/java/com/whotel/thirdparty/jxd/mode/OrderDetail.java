package com.whotel.thirdparty.jxd.mode;

import java.util.List;

import com.whotel.thirdparty.jxd.util.XmlBean;

public class OrderDetail implements XmlBean {

	private Integer detailId;
	private String code;
	private String salesPromotionId;
	private String arriveDate;
	private String leaveDate;
	private Integer roomQty;
	private Float totalAmount;
	private Float paidAmount;
	private String payMethod;
	private String guestName;
	private String roomSpecial;
	private String arriveTime;
	private String guestRemark;

	private List<OrderDetailPrice> orderDetailPrices;

	private List<OrderValueAddedService> orderValueAddedServices;

	private List<OrderAirportPickupService> orderAirportPickupServices;
	
	private List<AttachProduct> attachProducts;//附属产品

	public Integer getDetailId() {
		return detailId;
	}

	public void setDetailId(Integer detailId) {
		this.detailId = detailId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getSalesPromotionId() {
		return salesPromotionId;
	}

	public void setSalesPromotionId(String salesPromotionId) {
		this.salesPromotionId = salesPromotionId;
	}

	public String getArriveDate() {
		return arriveDate;
	}

	public void setArriveDate(String arriveDate) {
		this.arriveDate = arriveDate;
	}

	public String getLeaveDate() {
		return leaveDate;
	}

	public void setLeaveDate(String leaveDate) {
		this.leaveDate = leaveDate;
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

	public Float getPaidAmount() {
		return paidAmount;
	}

	public void setPaidAmount(Float paidAmount) {
		this.paidAmount = paidAmount;
	}

	public String getPayMethod() {
		return payMethod;
	}

	public void setPayMethod(String payMethod) {
		this.payMethod = payMethod;
	}

	public String getGuestName() {
		return guestName;
	}

	public void setGuestName(String guestName) {
		this.guestName = guestName;
	}

	public String getRoomSpecial() {
		return roomSpecial;
	}

	public void setRoomSpecial(String roomSpecial) {
		this.roomSpecial = roomSpecial;
	}

	public String getArriveTime() {
		return arriveTime;
	}

	public void setArriveTime(String arriveTime) {
		this.arriveTime = arriveTime;
	}

	public String getGuestRemark() {
		return guestRemark;
	}

	public void setGuestRemark(String guestRemark) {
		this.guestRemark = guestRemark;
	}

	public List<OrderDetailPrice> getOrderDetailPrices() {
		return orderDetailPrices;
	}

	public void setOrderDetailPrices(List<OrderDetailPrice> orderDetailPrices) {
		this.orderDetailPrices = orderDetailPrices;
	}

	public List<OrderValueAddedService> getOrderValueAddedServices() {
		return orderValueAddedServices;
	}

	public void setOrderValueAddedServices(
			List<OrderValueAddedService> orderValueAddedServices) {
		this.orderValueAddedServices = orderValueAddedServices;
	}

	public List<OrderAirportPickupService> getOrderAirportPickupServices() {
		return orderAirportPickupServices;
	}

	public void setOrderAirportPickupServices(
			List<OrderAirportPickupService> orderAirportPickupServices) {
		this.orderAirportPickupServices = orderAirportPickupServices;
	}

	@Override
	public String getRoot() {
		return "OrderDetail";
	}

	public List<AttachProduct> getAttachProducts() {
		return attachProducts;
	}

	public void setAttachProducts(List<AttachProduct> attachProducts) {
		this.attachProducts = attachProducts;
	}
}
