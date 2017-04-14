package com.whotel.card.entity;

import java.util.Date;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Indexed;

import com.whotel.common.entity.BaseEntity;
import com.whotel.common.util.RepoUtil;
import com.whotel.front.entity.WeixinFan;

@Entity(noClassnameStored=true)
public class MarketingRecord extends BaseEntity {

	private static final long serialVersionUID = 3200633082514730512L;
	
	private String companyId;
	
	private String hotelCode;
	
	private String recommendOpenId;//推荐人openid
	
	private String departmentId;//部门id
	
	private String recommendName;//推荐人姓名
	
	private String openId;//被推荐人
	
	private String name;//被推荐人名称
	
	private Float credit;//积分
	
	private Float money;//金额

	private Date createTime;
	
	private String remark;
	
	private Boolean isMember;
	
	private String mbrCardTypeName; //会员卡类型

	public String getMbrCardTypeName() {
		return mbrCardTypeName;
	}

	public void setMbrCardTypeName(String mbrCardTypeName) {
		this.mbrCardTypeName = mbrCardTypeName;
	} 
	
	public Department getDepartment(){
		return RepoUtil.getDepartmentById(departmentId);
	}
	
	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
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
	
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Float getCredit() {
		return credit;
	}

	public void setCredit(Float credit) {
		this.credit = credit;
	}

	public Float getMoney() {
		return money;
	}

	public void setMoney(Float money) {
		this.money = money;
	}

	public String getRecommendOpenId() {
		return recommendOpenId;
	}

	public void setRecommendOpenId(String recommendOpenId) {
		this.recommendOpenId = recommendOpenId;
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public String getRecommendName() {
		return recommendName;
	}

	public void setRecommendName(String recommendName) {
		this.recommendName = recommendName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getHotelCode() {
		return hotelCode;
	}

	public void setHotelCode(String hotelCode) {
		this.hotelCode = hotelCode;
	}

	public Boolean getIsMember() {
		return isMember;
	}

	public void setIsMember(Boolean isMember) {
		this.isMember = isMember;
	}

}
