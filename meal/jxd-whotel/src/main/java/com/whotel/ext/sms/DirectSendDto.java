package com.whotel.ext.sms;

public class DirectSendDto {
	private String userID = "";
	private String accountID = "";
	private String password = "";
	private String phones = "";
	private String content = "";
	private String sendType = "";
	private String sendTime = "";
	private String postFixNumber = "";

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getAccountID() {
		return accountID;
	}

	public void setAccountID(String accountID) {
		this.accountID = accountID;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhones() {
		return phones;
	}

	public void setPhones(String phones) {
		this.phones = phones;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSendType() {
		return sendType;
	}

	public void setSendType(String sendType) {
		this.sendType = sendType;
	}

	public String getSendTime() {
		return sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

	public String getPostFixNumber() {
		return postFixNumber;
	}

	public void setPostFixNumber(String postFixNumber) {
		this.postFixNumber = postFixNumber;
	}
}
