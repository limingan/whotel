package com.whotel.meal.enums;

import com.whotel.common.base.Labeled;

/**
 * 餐式
 * @author kf
 *
 */
public enum MealType implements Labeled {
	
	BREAKFAST("早餐"),LUNCH("午餐"),AFTERNOONTEA("下午茶"), DINNER("晚餐");
	
	private MealType(String label) {
		this.label = label;
	}
	
	private String label;
	
	private Boolean checked;
	
	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return label;
	}

}
