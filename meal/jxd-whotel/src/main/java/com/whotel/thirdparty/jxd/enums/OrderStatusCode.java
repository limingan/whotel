package com.whotel.thirdparty.jxd.enums;

public enum OrderStatusCode {
	RESERVED("0001", "已确认"),
	CHANGED("0002", "待取消"),
	CANCELED("0003", "已取消"),
	CHECK_IN("0004", "已入住"),
	WAITING("0005", "待确认"),
	CHECK_OUT("0006", "已离店"),
	NO_SHOW("0007", "未到店"),
	REJECTED("0008", "已拒绝");

	private String code;

	private String label;

	private OrderStatusCode(String code, String label) {
		this.code = code;
		this.label = label;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
}
