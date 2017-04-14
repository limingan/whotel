package com.whotel.webiste.entity;

import java.util.Date;

import org.mongodb.morphia.annotations.Entity;

import com.whotel.common.entity.BaseEntity;
import com.whotel.common.entity.OwnerCheck;
import com.whotel.common.util.RepoUtil;

/**
 * 栏目图文信息实体
 * @author 冯勇
 *
 */
@Entity(noClassnameStored=true)
public class ColumnNews extends BaseEntity implements OwnerCheck {

	private static final long serialVersionUID = -2652957189491841811L;
	
	private String companyId;
	
	private String name;
	
	private String templateId;
	
	private Date createTime;

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}
	
	public ColumnTemplate getTemplate() {
		return RepoUtil.getColumnTemplate(templateId);
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
