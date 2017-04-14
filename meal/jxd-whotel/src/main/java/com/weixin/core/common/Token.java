package com.weixin.core.common;

import java.util.Date;

@SuppressWarnings("serial")
public class Token implements WeixinBean {
	protected int expires_in;
	protected String errcode;
	protected String errmsg;
	// 用来刷新的时候使用 因为是多线程，用来对比是否已刷新过
	protected long createTime = new Date().getTime();

	public int getExpires_in() {
		return expires_in;
	}

	public void setExpires_in(int expires_in) {
		this.expires_in = (expires_in - 60) * 1000;
	}

	public boolean isExceed() {
		Date d = new Date();
		if (d.getTime() - createTime > expires_in) {
			return true;
		} else {
			return false;
		}
	}

	public String getErrcode() {
		return errcode;
	}

	public void setErrcode(String errcode) {
		this.errcode = errcode;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

	public long getCreateTime() {
		return createTime;
	}

	@Override
	public String toString() {
		return "Token [expires_in=" + expires_in + ", errcode=" + errcode
				+ ", errmsg=" + errmsg + ", createTime=" + createTime + "]";
	}
}
