package com.whotel.common.enums;

import com.whotel.common.base.Labeled;

public enum PayMode implements Labeled {
	CHARGE("充值"),
	BOOKHOTEL("预订酒店"),
	BOOKMEAL("预订餐饮"),
	RESERVATION("预约"),
	BOOKGOODS("购买商品"),
	TICKETBOOK("门票预订"),
	SCENIC_TICKET_BOOK("景区门票预订"),
	COMBOBOOK("套餐预订"),
	MBRUPGRADE("会员卡升级"),
	PAY("支付");

	private String label;

	private PayMode(String label) {
		this.label = label;
	}
	
	@Override
	public String getLabel() {
		return label;
	}
}
