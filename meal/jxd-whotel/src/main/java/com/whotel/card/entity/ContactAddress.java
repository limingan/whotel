package com.whotel.card.entity;

import java.util.Date;

import org.mongodb.morphia.annotations.Entity;

import com.whotel.common.entity.UnDeletedEntity;
import com.whotel.common.enums.Gender;

@Entity(noClassnameStored=true)
public class ContactAddress extends UnDeletedEntity {

	private static final long serialVersionUID = 6258887558915668430L;
	
	private String openId;
	
	private String name;
	
	private Gender gender;
	
	private String mobile;
	
	private String email;
	
	private String addr;
	
	private String postCode;//邮编
	
	private Boolean def;    //是否默认
	
	private Date createTime;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}
	
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public Boolean getDef() {
		return def;
	}

	public void setDef(Boolean def) {
		this.def = def;
	}
}
