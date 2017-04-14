package com.whotel.card.entity;

import java.util.Date;

import org.mongodb.morphia.annotations.Entity;

import com.whotel.common.entity.BaseEntity;

@Entity(noClassnameStored=true)
public class MemberPolicy extends BaseEntity {

	private static final long serialVersionUID = -6485518449591576695L;

	private String companyId;
	
	private Integer score;
	
	private Date createTime;

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}
}
