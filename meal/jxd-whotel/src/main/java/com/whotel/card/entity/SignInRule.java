package com.whotel.card.entity;

import org.mongodb.morphia.annotations.Entity;

import com.whotel.common.entity.BaseEntity;

@Entity(noClassnameStored=true)
public class SignInRule extends BaseEntity {

	private static final long serialVersionUID = 6258887558915668430L;
	
	private Integer count;
	
	private Integer number;

	private String type;//连续签:0, 满签到:1
	
	
	
	public SignInRule(Integer count, Integer number, String type) {
		super();
		this.count = count;
		this.number = number;
		this.type = type;
	}

	public SignInRule() {
		
	}
	
	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "SignInRule [count=" + count + ", number=" + number + ", type=" + type + "]";
	}
	
}
