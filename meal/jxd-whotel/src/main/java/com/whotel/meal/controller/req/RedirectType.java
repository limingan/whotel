package com.whotel.meal.controller.req;

import com.whotel.common.base.Labeled;

public enum RedirectType implements Labeled {

	TAB("桌台"), LIST("外卖首页"),ORDER("订单列表");

	private String label;

	private RedirectType(String label) {
		this.label = label;
	}
	
	@Override
	public String getLabel() {
		return label;
	}

}
