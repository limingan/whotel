package com.whotel.common.dto;

import com.whotel.common.base.Labeled;


/**
 * 大小条件比较枚举类
 *
 */
public enum CompareCriterion implements Labeled {
	lessThan("小于"),
	lessThanOrEqual("小于等于"),
	greaterThan("大于"),
	greaterThanOrEqual("大于等于"),
	equal("等于")
	;

	private String label;
	private CompareCriterion(String label) {
		this.label = label;
	}
	@Override
	public String getLabel() {
		return label;
	}

}
