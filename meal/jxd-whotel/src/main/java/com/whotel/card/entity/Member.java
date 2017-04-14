package com.whotel.card.entity;

import java.util.Date;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Indexed;
import org.mongodb.morphia.annotations.Transient;

import com.whotel.common.entity.BaseEntity;
import com.whotel.common.enums.Gender;
import com.whotel.common.util.RepoUtil;
import com.whotel.company.entity.Company;

@Entity(noClassnameStored=true)
public class Member extends BaseEntity {

	private static final long serialVersionUID = 6258887558915668430L;
	
	private String name;
	
	private String password;
	
	private String profileId;
	
	@Indexed(unique = true)
	private String openId;
	
	private String companyId;
	
	private String mobile;
	
	private String email;
	
	private Gender gender;
	
	private String addr;
	
	private String payPwd;
	
	private Date createTime;
	
	private Boolean isSendMsg;
	
	private Integer signInNum;//连续签到数
	
	private Map<String, Integer> fullSignNum; //满签到次数, 此Map 中只存在一个对象，当前活动的满签次数， 历史记录不管，key:活动ID

	private String unionid;
	
	private String mbrCardNo;
	
	private String mbrCardTypeCode;
	
	private String saleName;
	
	private String introducer;
	
	private String introducerName;
	
	private String mbrCardTypeName;
	
	private String IDCard;
	
	private Integer memberPolicyScore;
	
	private Date updateTime;
	
	public String getMbrCardTypeName() {
		return mbrCardTypeName;
	}

	public void setMbrCardTypeName(String mbrCardTypeName) {
		this.mbrCardTypeName = mbrCardTypeName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
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

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}
	
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public String getPayPwd() {
		return payPwd;
	}

	public void setPayPwd(String payPwd) {
		this.payPwd = payPwd;
	}

	@Transient
	private String oldMobile;

	public String getOldMobile() {
		if(StringUtils.isBlank(oldMobile)) {
			return mobile;
		}
		return oldMobile;
	}

	public void setOldMobile(String oldMobile) {
		this.oldMobile = oldMobile;
	}

	public String getProfileId() {
		return profileId;
	}

	public void setProfileId(String profileId) {
		this.profileId = profileId;
	}

	public Boolean getIsSendMsg() {
		return isSendMsg;
	}

	public void setIsSendMsg(Boolean isSendMsg) {
		this.isSendMsg = isSendMsg;
	}

	public Integer getSignInNum() {
		return signInNum;
	}

	public void setSignInNum(Integer signInNum) {
		this.signInNum = signInNum;
	}

	public String getUnionid() {
		return unionid;
	}

	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}

	public String getMbrCardNo() {
		return mbrCardNo;
	}

	public void setMbrCardNo(String mbrCardNo) {
		this.mbrCardNo = mbrCardNo;
	}

	public String getMbrCardTypeCode() {
		return mbrCardTypeCode;
	}

	public void setMbrCardTypeCode(String mbrCardTypeCode) {
		this.mbrCardTypeCode = mbrCardTypeCode;
	}

	public String getSaleName() {
		return saleName;
	}

	public void setSaleName(String saleName) {
		this.saleName = saleName;
	}

	public String getIDCard() {
		return IDCard;
	}

	public void setIDCard(String iDCard) {
		IDCard = iDCard;
	}

	public Integer getMemberPolicyScore() {
		return memberPolicyScore;
	}

	public void setMemberPolicyScore(Integer memberPolicyScore) {
		this.memberPolicyScore = memberPolicyScore;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getIntroducer() {
		return introducer;
	}

	public void setIntroducer(String introducer) {
		this.introducer = introducer;
	}

	public String getIntroducerName() {
		return introducerName;
	}

	public void setIntroducerName(String introducerName) {
		this.introducerName = introducerName;
	}

	public Map<String, Integer> getFullSignNum() {
		return fullSignNum;
	}

	public void setFullSignNum(Map<String, Integer> fullSignNum) {
		this.fullSignNum = fullSignNum;
	}
	
}
