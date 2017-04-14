package com.weixin.core.bean;

import com.weixin.core.common.WeixinBean;

/**
 * 按钮
 * 
 */
@SuppressWarnings("serial")
public class Button implements WeixinBean {

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Button [name=" + name + "]";
	}
}