package com.whotel.card.entity;

import java.util.Date;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Indexed;

import com.whotel.common.entity.BaseEntity;
import com.whotel.common.util.RepoUtil;
import com.whotel.company.entity.Company;
import com.whotel.front.entity.WeixinFan;

@Entity(noClassnameStored=true)
public class RecommendFan extends BaseEntity {

	private static final long serialVersionUID = -8465509888503943149L;

	private String companyId;
	
	@Indexed(unique = true)
	private String openId;
	
	private String recommendOpenId;
	
	private Date createTime;
	
	private String mobile;
	
	private Boolean isMember;
	
	private Boolean isFocus;
	
	private String departmentId;//部门id
	
	private String mbrCardTypeName; //会员卡类型

	public String getMbrCardTypeName() {
		return mbrCardTypeName;
	}

	public void setMbrCardTypeName(String mbrCardTypeName) {
		this.mbrCardTypeName = mbrCardTypeName;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	
	public Company getCompany() {
		return RepoUtil.getCompany(companyId);
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public WeixinFan getWeixinFan() {
		return RepoUtil.getWeixinFan(openId);
	}
	
	public String getRecommendOpenId() {
		return recommendOpenId;
	}

	public void setRecommendOpenId(String recommendOpenId) {
		this.recommendOpenId = recommendOpenId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Boolean getIsMember() {
		return isMember;
	}

	public void setIsMember(Boolean isMember) {
		this.isMember = isMember;
	}

	public Boolean getIsFocus() {
		return isFocus;
	}

	public void setIsFocus(Boolean isFocus) {
		this.isFocus = isFocus;
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

}
