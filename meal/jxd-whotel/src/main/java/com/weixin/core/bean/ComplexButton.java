package com.weixin.core.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 二级菜单上的按钮
 * 
 */
@SuppressWarnings("serial")
public class ComplexButton extends Button {
	private List<Button> sub_button;

	public ComplexButton() {
		sub_button = new ArrayList<Button>();
	}

	public void addButton(Button b) {
		sub_button.add(b);
	}

	public List<Button> getSub_button() {
		return sub_button;
	}

	public void setSub_button(List<Button> sub_button) {
		this.sub_button = sub_button;
	}

}