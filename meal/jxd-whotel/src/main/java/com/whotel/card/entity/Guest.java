package com.whotel.card.entity;

import java.util.Date;

import org.mongodb.morphia.annotations.Entity;

import com.whotel.common.entity.BaseEntity;
import com.whotel.common.enums.Gender;

@Entity(noClassnameStored=true)
public class Guest extends BaseEntity {

	private static final long serialVersionUID = -6485518449591576695L;

	private String openId;
	
	private String name;
	
	private Gender gender;
	
	private String mobile;
	
	private Date createTime;

	private String address;

	private String companyId;

	private Integer isDefault;//是否默认 0-否 1-是

	public Integer getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Integer isDefault) {
		this.isDefault = isDefault;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
