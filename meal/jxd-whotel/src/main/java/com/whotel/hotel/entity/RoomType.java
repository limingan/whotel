package com.whotel.hotel.entity;

import java.util.Date;

import org.mongodb.morphia.annotations.Entity;

import com.whotel.common.entity.UnDeletedEntity;

/**
 * 酒店客房实体
 * @author 冯勇
 *
 */
@Entity(noClassnameStored=true)
public class RoomType extends UnDeletedEntity {

	private static final long serialVersionUID = -4061631507986939589L;

	private String companyId;
	
	private String hotelId;
	
	private String name;
	
	private String code;        //接口对接code
	
	private String price;
	
	private Date createTime;
	
	private Date updateTime;

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getHotelId() {
		return hotelId;
	}

	public void setHotelId(String hotelId) {
		this.hotelId = hotelId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}
