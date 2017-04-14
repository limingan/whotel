package com.weixin.core.bean;

import java.util.Date;

public class MaterialItem {
    
	private String media_id;
	
	private MaterialContent content;
	
	private String name;
	
	private String url;
	
	private Date update_time;

	public String getMedia_id() {
		return media_id;
	}

	public void setMedia_id(String media_id) {
		this.media_id = media_id;
	}

	public MaterialContent getContent() {
		return content;
	}

	public void setContent(MaterialContent content) {
		this.content = content;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Date getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}

	@Override
	public String toString() {
		return "MaterialItem [media_id=" + media_id + ", content=" + content
				+ ", name=" + name + ", url=" + url + ", update_time="
				+ update_time + "]";
	}
}
