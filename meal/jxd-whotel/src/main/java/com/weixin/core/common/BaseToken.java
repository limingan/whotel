package com.weixin.core.common;


/**
 * 
 */
public class BaseToken extends Token {
	private static final long serialVersionUID = -4763256567438103618L;

	private String access_token;
	
	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}


	@Override
	public String toString() {
		return "Token [errcode=" + errcode + ", errmsg=" + errmsg
				+ ", access_token=" + access_token + ", expires_in="
				+ expires_in + "]";
	}

}
