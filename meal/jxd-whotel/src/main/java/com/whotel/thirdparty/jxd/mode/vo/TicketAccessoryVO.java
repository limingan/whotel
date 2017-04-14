package com.whotel.thirdparty.jxd.mode.vo;

public class TicketAccessoryVO {
	
	private String orderItemCode;//在下一步预订时需要传递回来
	private String orderItemCName;//用于预订时显示
	private String serviceType;//产品类型
	private Float price;//单价
	private String ratecode;//价格体系代码
	private String pricesystemid;//明细价格id
	private String salePromotionId;//优惠活动id
	private Integer qty;//可预订数
	
	public String getOrderItemCode() {
		return orderItemCode;
	}
	public void setOrderItemCode(String orderItemCode) {
		this.orderItemCode = orderItemCode;
	}
	public String getOrderItemCName() {
		return orderItemCName;
	}
	public void setOrderItemCName(String orderItemCName) {
		this.orderItemCName = orderItemCName;
	}
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	public Float getPrice() {
		return price;
	}
	public void setPrice(Float price) {
		this.price = price;
	}
	public String getRatecode() {
		return ratecode;
	}
	public void setRatecode(String ratecode) {
		this.ratecode = ratecode;
	}
	public String getPricesystemid() {
		return pricesystemid;
	}
	public void setPricesystemid(String pricesystemid) {
		this.pricesystemid = pricesystemid;
	}
	public String getSalePromotionId() {
		return salePromotionId;
	}
	public void setSalePromotionId(String salePromotionId) {
		this.salePromotionId = salePromotionId;
	}
	public Integer getQty() {
		return qty;
	}
	public void setQty(Integer qty) {
		this.qty = qty;
	}
	@Override
	public String toString() {
		return "TicketAccessories [orderItemCode=" + orderItemCode + ", orderItemCName=" + orderItemCName
				+ ", serviceType=" + serviceType + ", price=" + price + ", ratecode=" + ratecode + ", pricesystemid="
				+ pricesystemid + ", salePromotionId=" + salePromotionId + ", qty=" + qty + "]";
	}
}
