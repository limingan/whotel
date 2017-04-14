package com.whotel.admin.entity;

import java.util.Date;

import org.mongodb.morphia.annotations.Entity;

import com.whotel.common.entity.BaseEntity;

@Entity(noClassnameStored=true)
public class SysNotice extends BaseEntity {

	private static final long serialVersionUID = -6575142254583752689L;
	
	private String title;
	
	private String content;
	
	private Boolean isPublish;//是否发布：已发布true，未发布null，已下架false
	
	private Integer readQty;//阅读量
	
	private Date createTime;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Boolean getIsPublish() {
		return isPublish;
	}

	public void setIsPublish(Boolean isPublish) {
		this.isPublish = isPublish;
	}

	public Integer getReadQty() {
		return readQty;
	}

	public void setReadQty(Integer readQty) {
		this.readQty = readQty;
	}
}
