package com.whotel.company.enums;

import com.whotel.common.base.Labeled;

public enum PublicType implements Labeled {
	SERVICE("服务号"),
	SUBSCRIBE("订阅号");

	private String label;
	
	private PublicType(String label) {
		this.label = label;
	}
	@Override
	public String getLabel() {
		return label;
	}

}
