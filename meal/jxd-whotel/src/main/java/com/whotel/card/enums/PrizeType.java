package com.whotel.card.enums;

import com.whotel.common.base.Labeled;

public enum PrizeType implements Labeled {
	
	CASH("现金红包"), COUPON("优惠券"),GOODS("实体商品"),CARD_COUPON("卡券");

	private String label;
	
	private PrizeType(String label) {
		this.label = label;
	}
	
	@Override
	public String getLabel() {
		return label;
	}
}
