package com.whotel.hotel.enums;

import com.whotel.common.base.Labeled;

public enum HotelOrderStatus implements Labeled {
	
	NEW("待确认"), WAIT_PAY("待支付"), CONFIRMED("可入住"), USED("已入住"),LEAVE("已离店"), CANCELED("已取消");

	private String label;
	
	private HotelOrderStatus(String label) {
		this.label = label;
	}
	@Override
	public String getLabel() {
		return label;
	}

	
}
