package com.whotel.common.dto;

import java.io.Serializable;

public class UploadMessage implements Serializable {

	private static final long serialVersionUID = -4100594586100903099L;

	private String type;
	
	private int error;
	
	private String message;
	
	private String url;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getError() {
		return error;
	}

	public void setError(int error) {
		this.error = error;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
