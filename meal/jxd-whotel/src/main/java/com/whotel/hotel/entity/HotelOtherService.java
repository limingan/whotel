package com.whotel.hotel.entity;

import org.mongodb.morphia.annotations.Embedded;

@Embedded
public class HotelOtherService {

	private String serviceId;
	
	private String name;
	
	private Float price;
	
	private Integer number;
	
	private String typeCode;
	
	private String airplanInfo;
	
	private Boolean checked;
	
	private String unit;
	
	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}
	
	public String getAirplanInfo() {
		return airplanInfo;
	}

	public void setAirplanInfo(String airplanInfo) {
		this.airplanInfo = airplanInfo;
	}

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
}
