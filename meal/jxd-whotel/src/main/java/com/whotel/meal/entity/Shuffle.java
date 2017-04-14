package com.whotel.meal.entity;

import java.util.Date;

import org.mongodb.morphia.annotations.Entity;

import com.whotel.common.entity.UnDeletedEntity;
import com.whotel.common.util.RepoUtil;

/**
 * 市别
 * @author kf
 *
 */
@Entity(noClassnameStored=true)
public class Shuffle extends UnDeletedEntity {
	
	private static final long serialVersionUID = 1L;
	
	private String companyId;

	private String hotelCode;
	
	private String restaurantId;//分厅编码
	
	private String shuffleNo;//市别代码
	
	private String shuffleName;//市别的名称
	
	private String startTime;//开始时间
	
	private String endTime;//结束时间
	
	private String remark;//市别的说明

	public String getRestaurantId() {
		return restaurantId;
	}

	public void setRestaurantId(String restaurantId) {
		this.restaurantId = restaurantId;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getShuffleNo() {
		return shuffleNo;
	}

	public void setShuffleNo(String shuffleNo) {
		this.shuffleNo = shuffleNo;
	}

	public String getShuffleName() {
		return shuffleName;
	}

	public void setShuffleName(String shuffleName) {
		this.shuffleName = shuffleName;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Restaurant getRestaurant(){
		return RepoUtil.getRestaurantById(restaurantId);
	}

	public String getHotelCode() {
		return hotelCode;
	}

	public void setHotelCode(String hotelCode) {
		this.hotelCode = hotelCode;
	}
}
