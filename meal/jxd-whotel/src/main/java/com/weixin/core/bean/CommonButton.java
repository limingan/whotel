package com.weixin.core.bean;

/**
 * 一级菜单上的按钮
 * @author 冯勇
 *  type 按钮类型 key 按钮KEY值，用于消息接口(event类型)推送，不超过128字节 类型为click必须
 */
@SuppressWarnings("serial")
public class CommonButton extends Button {
	private String type;
	private String key;
	private String url;

	public String getType() {
		return type;
	}

	/**
	 * 一旦设置type为click，url字段将被清除； 一旦设置type为view，key字段将被清除；
	 * @param type
	 */
	public void setType(String type) {
		this.type = type;
		if ("click".equals(type)) {
			this.url = null;
		} else if ("view".equals(type)) {
			this.key = null;
		}
	}

	public String getKey() {
		return key;
	}

	/**
	 * 一旦设置，type将联动设置为click
	 * 
	 * @param key
	 */
	public void setKey(String key) {
		this.type = "click";
		this.key = key;
	}

	public String getUrl() {
		return url;
	}

	/**
	 * 一旦设置，type将联动设置为view
	 * 
	 * @param url
	 */
	public void setUrl(String url) {
		this.type = "view";
		this.url = url;
	}

}