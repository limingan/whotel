package com.whotel.admin.enums;

import com.whotel.common.base.Labeled;

public enum AdminActionType implements Labeled {
    
	LOGIN("登录"),
	ADD("新增"),
	UPDATE("更新"),
	DELETE("删除");
	
	private String label;
	
	private AdminActionType(String label) {
		this.label = label;
	}
	
	@Override
	public String getLabel() {
		return label;
	}
}
