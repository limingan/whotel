package com.whotel.webiste.entity;

import java.util.Date;

import org.mongodb.morphia.annotations.Entity;

import com.whotel.common.entity.UnDeletedEntity;
import com.whotel.common.util.QiniuUtils;

@Entity(noClassnameStored=true)
public class ColumnTemplate extends UnDeletedEntity {

	private static final long serialVersionUID = -4008869254904908670L;

	private String name;
	
	private String template;

	private String cssPath;

	private String jsPath;
	
	private String thumbnail;

	private Date createTime;
	
	private Date updateTime;
	
	private String widthHeight;//图片格式:*分割

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCssPath() {
		return cssPath;
	}
	
	public String getCssPathUrl() {
		return QiniuUtils.getResUrl(cssPath);
	}

	public void setCssPath(String cssPath) {
		this.cssPath = cssPath;
	}

	public String getJsPath() {
		return jsPath;
	}
	
	public String getJsPathUrl() {
		return QiniuUtils.getResUrl(jsPath);
	}

	public void setJsPath(String jsPath) {
		this.jsPath = jsPath;
	}
	
	public String getThumbnail() {
		return thumbnail;
	}
	
	public String getThumbnailUrl() {
		return QiniuUtils.getResUrl(thumbnail);
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public String getWidthHeight() {
		return widthHeight;
	}

	public void setWidthHeight(String widthHeight) {
		this.widthHeight = widthHeight;
	}
}
