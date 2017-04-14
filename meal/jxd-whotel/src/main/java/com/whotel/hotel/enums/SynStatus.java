package com.whotel.hotel.enums;

import com.whotel.common.base.Labeled;

public enum SynStatus implements Labeled {
	
	SUCCESS("成功"), FAIL("失败"), PROCESSING("处理中");

	private String label;
	
	private SynStatus(String label) {
		this.label = label;
	}
	
	@Override
	public String getLabel() {
		return label;
	}

	
}
