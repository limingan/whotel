package com.whotel.thirdparty.jxd.mode;

import com.whotel.thirdparty.jxd.util.XmlBean;

public class OrderValueAddedService implements XmlBean {

	private Integer detailId;
	private String valueAddedId;
	private Integer qty;
	private Float price;
	private Float amount;

	public Integer getDetailId() {
		return detailId;
	}

	public void setDetailId(Integer detailId) {
		this.detailId = detailId;
	}

	public String getValueAddedId() {
		return valueAddedId;
	}

	public void setValueAddedId(String valueAddedId) {
		this.valueAddedId = valueAddedId;
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
		return "OrderValueAddedService";
	}
}
