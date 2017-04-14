package com.whotel.hotel.entity;

import java.util.List;

import org.mongodb.morphia.annotations.Embedded;

import com.whotel.common.util.QiniuUtils;

@Embedded
public class Facilitys {
	
	private Boolean isChecked; //是否勾选
	
	private String hotelFacilitieUrls;//酒店设施图片
	
	private String hotelFacilitieNames;//酒店设施名称

	public String getHotelFacilitieUrls() {
		return hotelFacilitieUrls;
	}

	public void setHotelFacilitieUrls(String hotelFacilitieUrls) {
		this.hotelFacilitieUrls = hotelFacilitieUrls;
	}

	public String getHotelFacilitieUrl() {
		return QiniuUtils.getResUrl(hotelFacilitieUrls);
	}
	
	public String getHotelFacilitieNames() {
		return hotelFacilitieNames;
	}

	public void setHotelFacilitieNames(String hotelFacilitieNames) {
		this.hotelFacilitieNames = hotelFacilitieNames;
	}

	public Boolean getIsChecked() {
		return isChecked;
	}

	public void setIsChecked(Boolean isChecked) {
		this.isChecked = isChecked;
	}
	
}
