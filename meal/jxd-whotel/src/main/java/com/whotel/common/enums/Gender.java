package com.whotel.common.enums;

import com.whotel.common.base.Labeled;

public enum Gender implements Labeled {
	MALE("男"), 
	FEMALE("女");

	private String label;

	private Gender(String label) {
		this.label = label;
	}
	
	@Override
	public String getLabel() {
		return label;
	}
}
