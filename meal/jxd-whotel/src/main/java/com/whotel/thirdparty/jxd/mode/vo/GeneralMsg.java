package com.whotel.thirdparty.jxd.mode.vo;

public class GeneralMsg {
	
	private String OpType;//操作类型
	
	private String messageNo;//返回代码 
	
	private String message;//返回消息
	
	private Boolean isSuccess;//是否成功
	
	public String getOpType() {
		return OpType;
	}

	public void setOpType(String opType) {
		OpType = opType;
	}

	public String getMessageNo() {
		return messageNo;
	}

	public void setMessageNo(String messageNo) {
		this.messageNo = messageNo;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Boolean getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(Boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

}
