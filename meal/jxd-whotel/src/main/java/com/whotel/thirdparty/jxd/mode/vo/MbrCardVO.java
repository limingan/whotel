package com.whotel.thirdparty.jxd.mode.vo;

import java.util.Date;

/**
 * 会员卡
 * @author 柯鹏程
 *
 */
public class MbrCardVO  {
	
	private String profileId;      //客历id
	private String mbrCardType;    //会员卡类型
	private String mbrCardTypeName;//会员卡类型名称
	private String mbrCardNo; 	   //会员卡号
	private String rFCardNo;       //感应卡ID号
	private String mbrExpired;     //有效期至
	private String onLinePay;      //是否允许在线支付 1 可以支付，0不可以支付
	private Float balance;    	   //储值余额
	private Float baseamtbalance;  //本金余额
	private Float incamount;       //返现余额
	private Float incamt;    	   //总充值金额
	private Float deductamt;       //总扣款扣值金额
	private Float chargeamt;       //总消费金额
	private Integer ispertain;	   //是否为主卡 0 是
	private String mbrGuId;		   //动态id
	private Date genGuIdDate;	   //动态id生成日期
	
	private String picUrl;		   //背景图片
	public String getProfileId() {
		return this.profileId;
	}
	public void setProfileId(String profileId) {
		this.profileId = profileId;
	}
	public String getMbrCardType() {
		return this.mbrCardType;
	}
	public void setMbrCardType(String mbrCardType) {
		this.mbrCardType = mbrCardType;
	}
	public String getMbrCardTypeName() {
		return this.mbrCardTypeName;
	}
	public void setMbrCardTypeName(String mbrCardTypeName) {
		this.mbrCardTypeName = mbrCardTypeName;
	}
	public String getMbrCardNo() {
		return this.mbrCardNo;
	}
	public void setMbrCardNo(String mbrCardNo) {
		this.mbrCardNo = mbrCardNo;
	}
	public String getRFCardNo() {
		return this.rFCardNo;
	}
	public void setRFCardNo(String rFCardNo) {
		this.rFCardNo = rFCardNo;
	}
	public String getMbrExpired() {
		return this.mbrExpired;
	}
	public void setMbrExpired(String mbrExpired) {
		this.mbrExpired = mbrExpired;
	}
	public String getOnLinePay() {
		return this.onLinePay;
	}
	public void setOnLinePay(String onLinePay) {
		this.onLinePay = onLinePay;
	}
	public Float getBalance() {
		return this.balance;
	}
	public void setBalance(Float balance) {
		this.balance = balance;
	}
	public Float getBaseamtbalance() {
		return baseamtbalance;
	}
	public void setBaseamtbalance(Float baseamtbalance) {
		this.baseamtbalance = baseamtbalance;
	}
	public Float getIncamount() {
		return incamount;
	}
	public void setIncamount(Float incamount) {
		this.incamount = incamount;
	}
	public Float getIncamt() {
		return incamt;
	}
	public void setIncamt(Float incamt) {
		this.incamt = incamt;
	}
	public Float getDeductamt() {
		return deductamt;
	}
	public void setDeductamt(Float deductamt) {
		this.deductamt = deductamt;
	}
	public Float getChargeamt() {
		return chargeamt;
	}
	public void setChargeamt(Float chargeamt) {
		this.chargeamt = chargeamt;
	}
	public String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	public Integer getIspertain() {
		return ispertain;
	}
	public void setIspertain(Integer ispertain) {
		this.ispertain = ispertain;
	}
	public String getMbrGuId() {
		return mbrGuId;
	}
	public void setMbrGuId(String mbrGuId) {
		this.mbrGuId = mbrGuId;
	}
	public Date getGenGuIdDate() {
		return genGuIdDate;
	}
	public void setGenGuIdDate(Date genGuIdDate) {
		this.genGuIdDate = genGuIdDate;
	}
	public Integer getResidualTime(){
		if(genGuIdDate!=null){
			return 30-(int) ((new Date().getTime()-genGuIdDate.getTime())/1000);
		}
		return -1;
	}
	@Override
	public String toString() {
		return "MbrCardVO [profileId=" + profileId + ", mbrCardType=" + mbrCardType + ", mbrCardTypeName="
				+ mbrCardTypeName + ", mbrCardNo=" + mbrCardNo + ", rFCardNo=" + rFCardNo + ", mbrExpired=" + mbrExpired
				+ ", onLinePay=" + onLinePay + ", balance=" + balance + ", baseamtbalance=" + baseamtbalance
				+ ", incamount=" + incamount + ", incamt=" + incamt + ", deductamt=" + deductamt + ", chargeamt="
				+ chargeamt + ", ispertain=" + ispertain + ", mbrGuId=" + mbrGuId + ", genGuIdDate=" + genGuIdDate
				+ ", picUrl=" + picUrl + "]";
	}
}
