package com.whotel.common.enums;

import com.whotel.common.base.Labeled;

public enum DishesProperties implements Labeled {

	action("做法"), unit("规格"),request("要求");

	private String label;

	private DishesProperties(String label) {
		this.label = label;
	}
	
	@Override
	public String getLabel() {
		return label;
	}

}
