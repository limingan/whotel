package com.whotel.company.entity;

import java.util.Date;
import java.util.List;

import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;

import com.whotel.common.entity.BaseEntity;
import com.whotel.common.entity.OwnerCheck;

/**
 * 匹配规则
 * @author 冯勇
 */
@SuppressWarnings("serial")
@Entity(noClassnameStored = true)
public class MatchKeywordRule extends BaseEntity implements OwnerCheck {
	
	private Boolean def; //是否为默认匹配规则
	
	private String companyId;
	
	private String name;
	
	@Embedded
	private List<MatchKeyword> keywords;
	
	private ResponseMsg responseMsg;
	
	private Date createTime;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<MatchKeyword> getKeywords() {
		return keywords;
	}

	public void setKeywords(List<MatchKeyword> keywords) {
		this.keywords = keywords;
	}
	
	public ResponseMsg getResponseMsg() {
		return responseMsg;
	}

	public void setResponseMsg(ResponseMsg responseMsg) {
		this.responseMsg = responseMsg;
	}

	public Boolean getDef() {
		return def;
	}

	public void setDef(Boolean def) {
		this.def = def;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
