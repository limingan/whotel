package com.whotel.meal.enums;

import com.whotel.common.base.Labeled;

public enum MealOrderStatus implements Labeled {
	
	ARRIVE("已到店"),NOARRIVE("未到店"), WAIT_PAY("待支付"),CANCELED("已取消"),EXPIRED("已过期");

	private String label;
	
	private MealOrderStatus(String label) {
		this.label = label;
	}
	@Override
	public String getLabel() {
		return label;
	}

	
}
