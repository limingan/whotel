package com.whotel.common.enums;

import com.whotel.common.base.Labeled;

public enum TradeType implements Labeled {
	
	CHARGE("充值"), DEDUCT("扣减"), INCOME("收入"), FREEZE("冻结"), UNFREEZE("解冻");

	private String label;
	
	private TradeType(String label) {
		this.label = label;
	}
	
	@Override
	public String getLabel() {
		return label;
	}

}
