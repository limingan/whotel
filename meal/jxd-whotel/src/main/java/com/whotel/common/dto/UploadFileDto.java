package com.whotel.common.dto;

import java.io.Serializable;

/**
 * File upload DTO
 * 
 */
@SuppressWarnings("serial")
public class UploadFileDto implements Serializable {
	private String fileName;
	private String type; // 图像类型，通常用于决定图像保存于何目录
	private String url; // 网络访问地址
	private String thumbUrl; //缩略图
	private String path; // 本地地址
	private long size; // 限定大小(字节数)
	private String[] exts; // 文件附加名限定
	private String msg; // 返回信息
	private boolean isDraft; // 是否为草稿
	private boolean isModify; // 是否修改
	private String state;     //文件上传状态
	
	private boolean success;  //文件上传状态
	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public String[] getExts() {
		return exts;
	}

	public void setExts(String[] exts) {
		this.exts = exts;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public boolean isDraft() {
		return isDraft;
	}

	public void setDraft(boolean isDraft) {
		this.isDraft = isDraft;
	}

	public boolean isModify() {
		return isModify;
	}

	public void setModify(boolean isModify) {
		this.isModify = isModify;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	public String getThumbUrl() {
		return thumbUrl;
	}

	public void setThumbUrl(String thumbUrl) {
		this.thumbUrl = thumbUrl;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}
}
