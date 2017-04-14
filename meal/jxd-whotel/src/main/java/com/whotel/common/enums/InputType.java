package com.whotel.common.enums;

import com.whotel.common.base.Labeled;

/**
 * 输入框类型
 * @author 冯勇
 */
public enum InputType implements Labeled {
	
	INPUT("文本框"), 
	TEXTAREA("文本域"), 
	DATE("日期"), 
	FILE("文件"), 
	SELECT("选择框"), 
	RADIO("单选框"), 
	CHECKBOX("多选框"),
	HIDDEN("隐藏域");

	private String label;
	
	private InputType(String label) {
		this.label = label;
	}
	
	@Override
	public String getLabel() {
		return label;
	}

}
