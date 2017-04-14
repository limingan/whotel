package com.whotel.common.dto;

public class ExtActionDTO {

	private String type;

	private String failureType;
	
	private boolean success;
	
	private String msg;
	
	private String url;
	
	private String serverPath;
	
	private String fileUid;
	
    private String fileName;
	
	private long fileSize;
	
	private String mimeType;
	
	private String fileExt;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFailureType() {
		return failureType;
	}

	public void setFailureType(String failureType) {
		this.failureType = failureType;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getServerPath() {
		return serverPath;
	}

	public void setServerPath(String serverPath) {
		this.serverPath = serverPath;
	}

	public String getFileUid() {
		return fileUid;
	}

	public void setFileUid(String fileUid) {
		this.fileUid = fileUid;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public long getFileSize() {
		return fileSize;
	}

	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}

	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	public String getFileExt() {
		return fileExt;
	}

	public void setFileExt(String fileExt) {
		this.fileExt = fileExt;
	}
}
