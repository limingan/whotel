package com.whotel.card.enums;

import com.whotel.common.base.Labeled;

public enum CouponStates implements Labeled {
	
	USED("已使用"), NOT_USED("未使用"), EXPIRED("已过期"),ALL("全部");

	private String label;
	
	private CouponStates(String label) {
		this.label = label;
	}
	
	@Override
	public String getLabel() {
		return label;
	}
}
