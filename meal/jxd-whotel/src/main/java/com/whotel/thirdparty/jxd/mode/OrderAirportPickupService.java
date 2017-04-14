package com.whotel.thirdparty.jxd.mode;

import com.whotel.thirdparty.jxd.util.XmlBean;

public class OrderAirportPickupService implements XmlBean {

	private Integer detailId;
	private String airportPickupId;
	private String airplanInfo;
	private Integer qty;
	private Float price;
	private Float amount;

	public Integer getDetailId() {
		return detailId;
	}

	public void setDetailId(Integer detailId) {
		this.detailId = detailId;
	}

	public String getAirportPickupId() {
		return airportPickupId;
	}

	public void setAirportPickupId(String airportPickupId) {
		this.airportPickupId = airportPickupId;
	}

	public String getAirplanInfo() {
		return airplanInfo;
	}

	public void setAirplanInfo(String airplanInfo) {
		this.airplanInfo = airplanInfo;
	}

	public Integer getQty() {
		return qty;
	}

	public void setQty(Integer qty) {
		this.qty = qty;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public Float getAmount() {
		return amount;
	}

	public void setAmount(Float amount) {
		this.amount = amount;
	}

	@Override
	public String getRoot() {
		return "OrderAirportPickupService";
	}
}
