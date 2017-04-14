package com.whotel.card.enums;

import com.whotel.common.base.Labeled;

public enum ActivityType implements Labeled {
	
	VOICE("语音抽奖"), TURNTABLE("大转盘"),SCRATCH("刮刮乐"),SENDCOUPON("派劵活动");

	private String label;
	
	private ActivityType(String label) {
		this.label = label;
	}
	
	@Override
	public String getLabel() {
		return label;
	}
}
