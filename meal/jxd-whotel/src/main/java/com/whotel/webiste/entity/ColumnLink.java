package com.whotel.webiste.entity;

import org.mongodb.morphia.annotations.Entity;

import com.whotel.common.entity.BaseEntity;

/**
 * 栏目链接信息实体
 * @author 冯勇
 *
 */
@Entity(noClassnameStored=true)
public class ColumnLink extends BaseEntity {

	private static final long serialVersionUID = -2652957189491841811L;
	
	private String name;
	
	private String url;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
