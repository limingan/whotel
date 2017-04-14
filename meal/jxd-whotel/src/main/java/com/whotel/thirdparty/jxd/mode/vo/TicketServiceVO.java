package com.whotel.thirdparty.jxd.mode.vo;

public class TicketServiceVO {
	
	 private String id;
	 private String typeCode;
	 private String servicesId;
	 private String cname;
	 private String ename;
	 private String cdesc;
	 private String edesc;
	 private String pictureId;
	 private String pictureUrl;
	 private Integer includedQty;//已经包含的数量
	 private Float includedPrice;//已经包含的单价
	 private Integer availableQty;//可选数量
	 private Float availablePrice;//可选单价
	 private String unit;
	 
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTypeCode() {
		return typeCode;
	}
	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}
	public String getServicesId() {
		return servicesId;
	}
	public void setServicesId(String servicesId) {
		this.servicesId = servicesId;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public String getEname() {
		return ename;
	}
	public void setEname(String ename) {
		this.ename = ename;
	}
	public String getCdesc() {
		return cdesc;
	}
	public void setCdesc(String cdesc) {
		this.cdesc = cdesc;
	}
	public String getEdesc() {
		return edesc;
	}
	public void setEdesc(String edesc) {
		this.edesc = edesc;
	}
	public String getPictureId() {
		return pictureId;
	}
	public void setPictureId(String pictureId) {
		this.pictureId = pictureId;
	}
	public String getPictureUrl() {
		return pictureUrl;
	}
	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}
	public Integer getIncludedQty() {
		return includedQty;
	}
	public void setIncludedQty(Integer includedQty) {
		this.includedQty = includedQty;
	}
	public Float getIncludedPrice() {
		return includedPrice;
	}
	public void setIncludedPrice(Float includedPrice) {
		this.includedPrice = includedPrice;
	}
	public Integer getAvailableQty() {
		return availableQty;
	}
	public void setAvailableQty(Integer availableQty) {
		this.availableQty = availableQty;
	}
	public Float getAvailablePrice() {
		return availablePrice;
	}
	public void setAvailablePrice(Float availablePrice) {
		this.availablePrice = availablePrice;
	}
	@Override
	public String toString() {
		return "HotelServiceVO [id=" + id + ", typeCode=" + typeCode
				+ ", servicesId=" + servicesId + ", cname=" + cname
				+ ", ename=" + ename + ", cdesc=" + cdesc + ", edesc=" + edesc
				+ ", pictureId=" + pictureId + ", pictureUrl=" + pictureUrl
				+ ", includedQty=" + includedQty + ", includedPrice="
				+ includedPrice + ", availableQty=" + availableQty
				+ ", availablePrice=" + availablePrice + "]";
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
}
