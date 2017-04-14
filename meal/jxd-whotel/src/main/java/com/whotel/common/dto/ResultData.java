package com.whotel.common.dto;


public class ResultData {
	
	private String message;
	
	private String code;
	
	private Object data;
	
	public ResultData() {
		
	}
	
	public ResultData(String code, String message) {
		this.code = code;
		this.message = message;
	}
	
	public ResultData(String code, String message,Object data) {
		this.code = code;
		this.message = message;
		this.data = data;
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
