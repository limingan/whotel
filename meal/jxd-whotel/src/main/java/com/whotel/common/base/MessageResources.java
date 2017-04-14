package com.whotel.common.base;

/**
 * 返回请求结果
 * @author Administrator
 *
 */
public class MessageResources {

	private String error;
	
	private Object resources;
	
	public MessageResources(){
		
	}
	
	public MessageResources(String error,Object resources){
		this.error = error;
		this.resources = resources;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public Object getResources() {
		return resources;
	}

	public void setResources(Object resources) {
		this.resources = resources;
	}
}
