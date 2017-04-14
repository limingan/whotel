package com.whotel.hotel.entity;

import java.util.Date;

import org.mongodb.morphia.annotations.Indexed;

import com.whotel.common.entity.OwnerCheck;
import com.whotel.common.entity.UnDeletedEntity;
import com.whotel.common.enums.PayMent;
import com.whotel.common.enums.TradeStatus;
import com.whotel.hotel.enums.SynStatus;

public class BaseOrder extends UnDeletedEntity implements OwnerCheck {

	private static final long serialVersionUID = 526669210640587263L;

	private String companyId;
	
	private String openId;
	
	private String name;
	
	private String orderSn;
	
	private String tradeSn;
	
	private Float totalFee;
	
	private Float payFee;
	
	private Float promotionFee;
	
	private String couponCode;//券代码
	
	private String couponSeqid;//券id
	
	private Integer chargeamtmodel;//券类型
	
	private Float chargeamt;//券面值
	
	private TradeStatus tradeStatus;
	
	private PayMent payMent;
	
	private String contactName;
	
	private String contactMobile;
	
	private String contactTel;
	
	private String contactEmai;
	
	private String remark;
	
	private String cancelReason;
	
	private Date createTime;
	
	private Date updateTime;
	
	private Boolean synchronizeState;
	
	private String errorMsg;//错误消息提示
	
	private Float incamount;//返现
	
	private String profileId;//会员id
	
	private String contactEmail;//联系邮箱
	
	private String bookChannel;//预订渠道，hotel(酒店)shop(商城)
	
	private SynStatus synStatus;//商城MQ同步
	
	private Integer consumePoint;//消耗积分
	
	public String getProfileId() {
		return profileId;
	}

	public void setProfileId(String profileId) {
		this.profileId = profileId;
	}
	
	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOrderSn() {
		return orderSn;
	}

	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}
	
	public String getTradeSn() {
		return tradeSn;
	}

	public void setTradeSn(String tradeSn) {
		this.tradeSn = tradeSn;
	}

	public Float getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(Float totalFee) {
		this.totalFee = totalFee;
	}
	
	public Float getPayFee() {
		return payFee;
	}

	public void setPayFee(Float payFee) {
		this.payFee = payFee;
	}
	
	public Float getPromotionFee() {
		return promotionFee;
	}

	public void setPromotionFee(Float promotionFee) {
		this.promotionFee = promotionFee;
	}
	
	public Float getOrderTotalFee() {
		if(promotionFee == null) {
			promotionFee = 0f;
		}
		if(totalFee == null) {
			totalFee = 0f;
		}
		return promotionFee + totalFee;
	}

	public TradeStatus getTradeStatus() {
		return tradeStatus;
	}

	public void setTradeStatus(TradeStatus tradeStatus) {
		this.tradeStatus = tradeStatus;
	}

	public PayMent getPayMent() {
		return payMent;
	}

	public void setPayMent(PayMent payMent) {
		this.payMent = payMent;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactMobile() {
		return contactMobile;
	}

	public void setContactMobile(String contactMobile) {
		this.contactMobile = contactMobile;
	}

	public String getContactTel() {
		return contactTel;
	}

	public void setContactTel(String contactTel) {
		this.contactTel = contactTel;
	}

	public String getContactEmai() {
		return contactEmai;
	}

	public void setContactEmai(String contactEmai) {
		this.contactEmai = contactEmai;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getCouponCode() {
		return couponCode;
	}

	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}
	
	public String getCancelReason() {
		return cancelReason;
	}

	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Float getChargeamt() {
		return chargeamt;
	}

	public void setChargeamt(Float chargeamt) {
		this.chargeamt = chargeamt;
	}

	public Integer getChargeamtmodel() {
		return chargeamtmodel;
	}

	public void setChargeamtmodel(Integer chargeamtmodel) {
		this.chargeamtmodel = chargeamtmodel;
	}

	public String getCouponSeqid() {
		return couponSeqid;
	}

	public void setCouponSeqid(String couponSeqid) {
		this.couponSeqid = couponSeqid;
	}

	public Boolean getSynchronizeState() {
		return synchronizeState;
	}

	public void setSynchronizeState(Boolean synchronizeState) {
		this.synchronizeState = synchronizeState;
	}
	
	public Float getIncamount() {
		return incamount;
	}

	public void setIncamount(Float incamount) {
		this.incamount = incamount;
	}

	public String getBookChannel() {
		return bookChannel;
	}

	public void setBookChannel(String bookChannel) {
		this.bookChannel = bookChannel;
	}

	public SynStatus getSynStatus() {
		return synStatus;
	}

	public void setSynStatus(SynStatus synStatus) {
		this.synStatus = synStatus;
	}

	public String getContactEmail() {
		return contactEmail;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}
	
	public Integer getConsumePoint() {
		return consumePoint;
	}

	public void setConsumePoint(Integer consumePoint) {
		this.consumePoint = consumePoint;
	}
}
