package com.whotel.common.enums;

import com.whotel.common.base.Labeled;

public enum TradeStatus implements Labeled {
	WAIT_PAY("未支付"), 
	PENDING("审核中"), 
	SUCCESS("成功"),
	FINISHED("已支付"), // 已支付
	CLOSED("交易关闭"),
	CONFIRM("已确认"),
	CANCELED("已取消");

	//点餐状态为 等待处理——>已确认——>已完成——>支付
	//外卖 支付——>等待处理——>已确认——>已完成

	private String label;

	private TradeStatus(String label) {
		this.label = label;
	}
	
	@Override
	public String getLabel() {
		return label;
	}
}
