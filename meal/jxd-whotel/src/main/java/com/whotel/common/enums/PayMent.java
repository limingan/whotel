package com.whotel.common.enums;

import com.whotel.common.base.Labeled;

public enum PayMent implements Labeled {
	
	BALANCEPAY("储值支付"), WXPAY("微信支付"), OFFLINEPAY("到店支付"), ONLINEPAY("线上支付");

	private String label;
	
	private PayMent(String label) {
		this.label = label;
	}
	
	@Override
	public String getLabel() {
		return label;
	}

}
