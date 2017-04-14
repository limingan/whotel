package com.whotel.card.entity;

import java.util.Date;

import org.mongodb.morphia.annotations.Entity;

import com.whotel.common.entity.BaseEntity;

@Entity(noClassnameStored=true)
public class Department extends BaseEntity {

	private String companyId;
	
	private String name;
	
	private String hotelCode;
	
	private String hotelName;
	
	private Date createTime;
	
	private Float thisYearTask;//年任务

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getHotelCode() {
		return hotelCode;
	}

	public void setHotelCode(String hotelCode) {
		this.hotelCode = hotelCode;
	}

	public String getHotelName() {
		return hotelName;
	}

	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Float getThisYearTask() {
		return thisYearTask;
	}

	public void setThisYearTask(Float thisYearTask) {
		this.thisYearTask = thisYearTask;
	}
}
