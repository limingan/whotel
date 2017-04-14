package com.whotel.thirdparty.jxd.mode.vo;

import java.util.Date;

import com.whotel.common.enums.Gender;

public class MemberVO  {
	
	private String profileId;      //客历或合约单位id
	private String guestType;      //I:客人 C:合约单位
	private String isMember;       //1：会员  0:非会员
	private String vipType;        //会员卡类型
	private String blackList;      //黑名单类型
	private String mbrCardType; 
	private String mbrCardTypeName; //会员等级
	private String mbrCardNo;      //会员卡号
	private String rfCardNo;       //感应卡ID号
	private String creditLevel;    //信用等级
	private String mbrExpired;     //有效期至
	private String guestCName;     //中文名
	private String guestEName;     //中英名
	private String cttNo;          //合约号
	private String contactor;      //联系人
	private String rateCode;       //房价代码
	private String halfTimeBegin;  //半日租起算时间
	private String dayRentBegin;   //全日租起算时间
	private String salutation;     //称呼
	private String certificateType;//证件类型
	private String certificateNo;  //证件号码
	private Gender gender;         //性别
	private String birthday;       //生日
	private String nationality;    //国籍
	private String visaExpired;    //签证有效期
	private String address;        //住址
	private String zipCode;        //邮政编码
	private String tel;            //家庭电话
	private String officeTel;      //办公电话
	private String mobile;         //移动电话
	private String fax;            //传真
	private String email;          //电邮
	private Float balance;        //储值余额
	private Float debtAmount;     //未结挂帐
	private Float validScore;     //有效积分
	private Date createDate;
	private String remark;
	private Integer ticketqty;      //有效券数量
	private Integer subCardCount;   //会员卡数量，0为不含会员卡
	private Float ticketamt;      //有效券金额
	private String pwd;
	private String weixinId;
	
	private Float totalUsedBalance;
	private Float baseAmtBalance;
	private Float incamount;
	private Float incamt;//充值总额
	private Float chargeamt;//消费总额
	private Float usedScore;//积分兑换总额
	private Float canUseScore;//可兑换积分
	private Float deductamt;//总扣款扣值金额
	private Float scoreToAmount;//
	private Float scoretoamountratio;//
	
	private String sales;//业务员代码
	private String saleName;//业务员姓名
	private String saleMobile;//业务员手机
	private String introducer;//推荐人 客历号
	private String introducerName;//推荐人名称
	
	private Boolean isSendMsg;//是否发生消息通知
	
	private Float gainedScore;//总获取积分
	private String resortID;//酒店id
	private String roomNo;//房号
	private String status;//状态
	private String portraitID;//会员图片id
	private String portraitURL;//图片地址
	
	public Float getGainedScore() {
		return gainedScore;
	}
	public void setGainedScore(Float gainedScore) {
		this.gainedScore = gainedScore;
	}
	public String getResortID() {
		return resortID;
	}
	public void setResortID(String resortID) {
		this.resortID = resortID;
	}
	public String getRoomNo() {
		return roomNo;
	}
	public void setRoomNo(String roomNo) {
		this.roomNo = roomNo;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPortraitID() {
		return portraitID;
	}
	public void setPortraitID(String portraitID) {
		this.portraitID = portraitID;
	}
	public String getPortraitURL() {
		return portraitURL;
	}
	public void setPortraitURL(String portraitURL) {
		this.portraitURL = portraitURL;
	}
	public String getProfileId() {
		return profileId;
	}
	public void setProfileId(String profileId) {
		this.profileId = profileId;
	}
	public String getGuestType() {
		return guestType;
	}
	public void setGuestType(String guestType) {
		this.guestType = guestType;
	}
	public String getIsMember() {
		return isMember;
	}
	public void setIsMember(String isMember) {
		this.isMember = isMember;
	}
	public String getVipType() {
		return vipType;
	}
	public void setVipType(String vipType) {
		this.vipType = vipType;
	}
	public String getBlackList() {
		return blackList;
	}
	public void setBlackList(String blackList) {
		this.blackList = blackList;
	}
	public String getMbrCardNo() {
		return mbrCardNo;
	}
	public void setMbrCardNo(String mbrCardNo) {
		this.mbrCardNo = mbrCardNo;
	}
	
	public String getMbrCardTypeName() {
		return mbrCardTypeName;
	}
	public void setMbrCardTypeName(String mbrCardTypeName) {
		this.mbrCardTypeName = mbrCardTypeName;
	}
	public String getRfCardNo() {
		return rfCardNo;
	}
	public void setRfCardNo(String rfCardNo) {
		this.rfCardNo = rfCardNo;
	}
	public String getCreditLevel() {
		return creditLevel;
	}
	public void setCreditLevel(String creditLevel) {
		this.creditLevel = creditLevel;
	}
	public String getMbrExpired() {
		return mbrExpired;
	}
	public void setMbrExpired(String mbrExpired) {
		this.mbrExpired = mbrExpired;
	}
	public String getGuestCName() {
		return guestCName;
	}
	public void setGuestCName(String guestCName) {
		this.guestCName = guestCName;
	}
	public String getGuestEName() {
		return guestEName;
	}
	public void setGuestEName(String guestEName) {
		this.guestEName = guestEName;
	}
	public String getCttNo() {
		return cttNo;
	}
	public void setCttNo(String cttNo) {
		this.cttNo = cttNo;
	}
	public String getContactor() {
		return contactor;
	}
	public void setContactor(String contactor) {
		this.contactor = contactor;
	}
	public String getRateCode() {
		return rateCode;
	}
	public void setRateCode(String rateCode) {
		this.rateCode = rateCode;
	}
	public String getHalfTimeBegin() {
		return halfTimeBegin;
	}
	public void setHalfTimeBegin(String halfTimeBegin) {
		this.halfTimeBegin = halfTimeBegin;
	}
	public String getDayRentBegin() {
		return dayRentBegin;
	}
	public void setDayRentBegin(String dayRentBegin) {
		this.dayRentBegin = dayRentBegin;
	}
	public String getSalutation() {
		return salutation;
	}
	public void setSalutation(String salutation) {
		this.salutation = salutation;
	}
	public String getCertificateType() {
		return certificateType;
	}
	public void setCertificateType(String certificateType) {
		this.certificateType = certificateType;
	}
	public String getCertificateNo() {
		return certificateNo;
	}
	public void setCertificateNo(String certificateNo) {
		this.certificateNo = certificateNo;
	}
	
	public Gender getGender() {
		return gender;
	}
	public void setGender(Gender gender) {
		this.gender = gender;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getNationality() {
		return nationality;
	}
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	public String getVisaExpired() {
		return visaExpired;
	}
	public void setVisaExpired(String visaExpired) {
		this.visaExpired = visaExpired;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getOfficeTel() {
		return officeTel;
	}
	public void setOfficeTel(String officeTel) {
		this.officeTel = officeTel;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public Float getBalance() {
		return balance;
	}
	public void setBalance(Float balance) {
		this.balance = balance;
	}
	public Float getDebtAmount() {
		return debtAmount;
	}
	public void setDebtAmount(Float debtAmount) {
		this.debtAmount = debtAmount;
	}
	public Float getValidScore() {
		return validScore;
	}
	public void setValidScore(Float validScore) {
		this.validScore = validScore;
	}
	public void setTicketqty(Integer ticketqty) {
		this.ticketqty = ticketqty;
	}
	public void setTicketamt(Float ticketamt) {
		this.ticketamt = ticketamt;
	}
	public void setTotalUsedBalance(Float totalUsedBalance) {
		this.totalUsedBalance = totalUsedBalance;
	}
	public void setBaseAmtBalance(Float baseAmtBalance) {
		this.baseAmtBalance = baseAmtBalance;
	}
	public void setIncamount(Float incamount) {
		this.incamount = incamount;
	}
	
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public Integer getTicketqty() {
		return ticketqty;
	}
	public Float getTicketamt() {
		return ticketamt;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getWeixinId() {
		return weixinId;
	}
	public void setWeixinId(String weixinId) {
		this.weixinId = weixinId;
	}
	
	public String getMbrCardType() {
		return mbrCardType;
	}
	public void setMbrCardType(String mbrCardType) {
		this.mbrCardType = mbrCardType;
	}
	
	public Float getTotalUsedBalance() {
		return totalUsedBalance;
	}
	public Float getBaseAmtBalance() {
		return baseAmtBalance;
	}
	public Float getIncamount() {
		return incamount;
	}
	
	@Override
	public String toString() {
		return "MemberVO [profileId=" + profileId + ", guestType=" + guestType
				+ ", isMember=" + isMember + ", vipType=" + vipType
				+ ", blackList=" + blackList + ", mbrCardNo=" + mbrCardNo
				+ ", rfCardNo=" + rfCardNo + ", creditLevel=" + creditLevel
				+ ", mbrExpired=" + mbrExpired + ", guestCName=" + guestCName
				+ ", guestEName=" + guestEName + ", cttNo=" + cttNo
				+ ", contactor=" + contactor + ", rateCode=" + rateCode
				+ ", halfTimeBegin=" + halfTimeBegin + ", dayRentBegin="
				+ dayRentBegin + ", salutation=" + salutation
				+ ", certificateType=" + certificateType + ", certificateNo="
				+ certificateNo + ", gender=" + gender + ", birthday="
				+ birthday + ", nationality=" + nationality + ", visaExpired="
				+ visaExpired + ", address=" + address + ", zipCode=" + zipCode
				+ ", tel=" + tel + ", officeTel=" + officeTel + ", mobile="
				+ mobile + ", fax=" + fax + ", email=" + email + ", balance="
				+ balance + ", debtAmount=" + debtAmount + ", validScore="
				+ validScore + ", createDate=" + createDate + ", remark="
				+ remark + ", ticketqty=" + ticketqty + ", ticketamt="
				+ ticketamt + ", pwd=" + pwd + ", weixinId=" + weixinId + "]";
	}
	public Float getIncamt() {
		return incamt;
	}
	public void setIncamt(Float incamt) {
		this.incamt = incamt;
	}
	public Float getChargeamt() {
		return chargeamt;
	}
	public void setChargeamt(Float chargeamt) {
		this.chargeamt = chargeamt;
	}
	public Float getUsedScore() {
		return usedScore;
	}
	public void setUsedScore(Float usedScore) {
		this.usedScore = usedScore;
	}
	public Float getCanUseScore() {
		return canUseScore;
	}
	public void setCanUseScore(Float canUseScore) {
		this.canUseScore = canUseScore;
	}
	public Float getDeductamt() {
		return deductamt;
	}
	public void setDeductamt(Float deductamt) {
		this.deductamt = deductamt;
	}
	public String getSaleName() {
		return saleName;
	}
	public void setSaleName(String saleName) {
		this.saleName = saleName;
	}
	public String getSaleMobile() {
		return saleMobile;
	}
	public void setSaleMobile(String saleMobile) {
		this.saleMobile = saleMobile;
	}
	public Integer getSubCardCount() {
		return subCardCount;
	}
	public void setSubCardCount(Integer subCardCount) {
		this.subCardCount = subCardCount;
	}
	public Boolean getIsSendMsg() {
		return isSendMsg;
	}
	public void setIsSendMsg(Boolean isSendMsg) {
		this.isSendMsg = isSendMsg;
	}
	public String getSales() {
		return sales;
	}
	public void setSales(String sales) {
		this.sales = sales;
	}
	public Float getScoreToAmount() {
		return scoreToAmount;
	}
	public void setScoreToAmount(Float scoreToAmount) {
		this.scoreToAmount = scoreToAmount;
	}
	public Float getScoretoamountratio() {
		return scoretoamountratio;
	}
	public void setScoretoamountratio(Float scoretoamountratio) {
		this.scoretoamountratio = scoretoamountratio;
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
}
