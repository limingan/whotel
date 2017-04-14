package com.whotel.company.entity;

import java.util.Date;

import org.mongodb.morphia.annotations.Entity;

import com.whotel.common.entity.BaseEntity;
import com.whotel.common.entity.OwnerCheck;

/**
 * 公司公告
 * @author 冯勇
 *
 */
@Entity(noClassnameStored=true)
public class CompanyNotice extends BaseEntity implements OwnerCheck {

	private static final long serialVersionUID = -5032104917151822289L;
	
	private String companyId;
	
	private String title;
	
	private String content;
	
	private Date createTime;

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

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
}
