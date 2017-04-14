package com.whotel.common.enums;

import com.whotel.common.base.Labeled;

public enum TimeState implements Labeled {
	
	RUN("进行中"), ENDED("已结束"), PAUSE("已暂停");

	private String label;
	
	private TimeState(String label) {
		this.label = label;
	}
	
	@Override
	public String getLabel() {
		return label;
	}

}
