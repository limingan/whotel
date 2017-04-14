package com.whotel.card.entity;

import java.util.Date;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Indexed;

import com.whotel.common.entity.BaseEntity;
import com.whotel.common.util.RepoUtil;
import com.whotel.front.entity.WeixinFan;

@Entity(noClassnameStored=true)
public class MarketingFan extends BaseEntity {

	private static final long serialVersionUID = 3200633082514730512L;
	
	private String companyId;
	
	@Indexed(unique = true)
	private String openId;
	
	private int scene;
	
	private String ticket;
	
	private String qrUrl;
	
	private Integer rank;
	
	private Integer fanNums;//粉丝数量
	
	private Long cancelNums;//取消关注的粉丝数量
	
	private Date createTime;
	
	private Department department;//部门
	
	private Float credit;
	
	private Float commissionMoney;
	
	private Integer memberFanNums=0;//已注册会员的粉丝数量
	
	private String name;
	
	private Boolean isDelete;
	
	private String introducer;

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
	
	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public String getQrUrl() {
		return qrUrl;
	}

	public void setQrUrl(String qrUrl) {
		this.qrUrl = qrUrl;
	}

	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}
	
	public Integer getFanNums() {
		return fanNums;
	}

	public void setFanNums(Integer fanNums) {
		this.fanNums = fanNums;
	}

	public int getScene() {
		return scene;
	}

	public void setScene(int scene) {
		this.scene = scene;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Float getCredit() {
		return credit;
	}

	public void setCredit(Float credit) {
		this.credit = credit;
	}

	public Float getCommissionMoney() {
		return commissionMoney;
	}

	public void setCommissionMoney(Float commissionMoney) {
		this.commissionMoney = commissionMoney;
	}

	public Integer getMemberFanNums() {
		return memberFanNums;
	}

	public void setMemberFanNums(Integer memberFanNums) {
		this.memberFanNums = memberFanNums;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getCancelNums() {
		return cancelNums;
	}

	public void setCancelNums(Long cancelNums) {
		this.cancelNums = cancelNums;
	}
	
	public Member getMember(){
		return RepoUtil.getMemberByOpenId(openId);
	}

	public Boolean getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Boolean isDelete) {
		this.isDelete = isDelete;
	}

	public String getIntroducer() {
		return introducer;
	}

	public void setIntroducer(String introducer) {
		this.introducer = introducer;
	}
}
