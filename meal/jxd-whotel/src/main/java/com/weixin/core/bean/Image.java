package com.weixin.core.bean;

import com.weixin.core.common.WeixinBean;

@SuppressWarnings("serial")
public class Image implements WeixinBean {
	
	private String mediaId;

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}
}