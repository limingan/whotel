package com.whotel.webiste.enums;

import com.whotel.common.base.Labeled;

public enum NewsType implements Labeled {

	VERTICAL("竖排"), HORIZONTAL("横排");
	
	private String label;

	private NewsType(String label) {
		this.label = label;
	}

	@Override
	public String getLabel() {
		return label;
	}
}
