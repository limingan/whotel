package com.weixin.core.bean;

import java.util.ArrayList;
import java.util.List;

import com.weixin.core.common.WeixinBean;

/**
 * 微信公众号菜单
 * 
 */
@SuppressWarnings("serial")
public class Menu implements WeixinBean {
	private List<Button> button;

	public Menu() {
		this.button = new ArrayList<Button>();
	}

	public void addButton(Button b) {
		this.button.add(b);
	}

	public List<Button> getButton() {
		return button;
	}

	public void setButton(List<Button> button) {
		this.button = button;
	}

	@Override
	public String toString() {
		return "Menu [button=" + button + "]";
	}

}