package com.whotel.front.entity;

import java.util.Date;
import java.util.List;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Indexed;

import com.whotel.common.entity.UnDeletedEntity;
import com.whotel.common.enums.PayMent;
import com.whotel.common.enums.PayMode;
import com.whotel.common.enums.TradeStatus;
import com.whotel.common.util.RepoUtil;
import com.whotel.company.entity.Company;

/**
 * 支付订单
 * 
 * @author fy
 * 
 */
@Entity(noClassnameStored=true)
public class PayOrder extends UnDeletedEntity {

	private static final long serialVersionUID = 4583963349534789697L;

	private String name;

	private Long totalFee;
	
	private Long refundFee;
	
	@Indexed(unique = true)
	private String orderSn;
	
	private String tradeSn;
	
	private PayMent payMent;
	
	private PayMode payMode = PayMode.CHARGE;
	
	private Integer nums;
	
	private String businessId;

	private TradeStatus status;

	private String openId;
	
	private String companyId;
	
	private String remark;

	private Date createTime;

	private Date updateTime;
	
	private Boolean wxnotifyState;//通知状态，true为通知e运通成功，false为通知e运通失败
	
	private String mbrCardTypeCode;
	
	private String hotelNmae;
	
	private String hotelCode;
	
	private String mbrCardNo;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(Long totalFee) {
		this.totalFee = totalFee;
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

	public TradeStatus getStatus() {
		return status;
	}

	public void setStatus(TradeStatus status) {
		this.status = status;
	}
	
	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public PayMent getPayMent() {
		return payMent;
	}

	public void setPayMent(PayMent payMent) {
		this.payMent = payMent;
	}

	public PayMode getPayMode() {
		return payMode;
	}

	public void setPayMode(PayMode payMode) {
		this.payMode = payMode;
	}

	public Integer getNums() {
		return nums;
	}

	public void setNums(Integer nums) {
		this.nums = nums;
	}

	public String getBusinessId() {
		return businessId;
	}

	public void setBusinessId(String businessId) {
		this.businessId = businessId;
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
	
	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	
	public Company getCompany() {
		return RepoUtil.getCompany(companyId);
	}

	public Boolean getWxnotifyState() {
		return wxnotifyState;
	}

	public void setWxnotifyState(Boolean wxnotifyState) {
		this.wxnotifyState = wxnotifyState;
	}

	public String getMbrCardTypeCode() {
		return mbrCardTypeCode;
	}

	public void setMbrCardTypeCode(String mbrCardTypeCode) {
		this.mbrCardTypeCode = mbrCardTypeCode;
	}

	public String getHotelNmae() {
		return hotelNmae;
	}

	public void setHotelNmae(String hotelNmae) {
		this.hotelNmae = hotelNmae;
	}

	public String getHotelCode() {
		return hotelCode;
	}

	public void setHotelCode(String hotelCode) {
		this.hotelCode = hotelCode;
	}

	public String getMbrCardNo() {
		return mbrCardNo;
	}

	public void setMbrCardNo(String mbrCardNo) {
		this.mbrCardNo = mbrCardNo;
	}

	@Override
	public String toString() {
		return "PayOrder [name=" + name + ", totalFee=" + totalFee + ", orderSn=" + orderSn + ", tradeSn=" + tradeSn
				+ ", payMent=" + payMent + ", payMode=" + payMode + ", nums=" + nums + ", businessId=" + businessId
				+ ", status=" + status + ", openId=" + openId + ", companyId=" + companyId + ", remark=" + remark + ", createTime=" + createTime + ", updateTime=" + updateTime
				+ ", wxnotifyState=" + wxnotifyState + ", mbrCardTypeCode=" + mbrCardTypeCode + ", hotelNmae="
				+ hotelNmae + ", hotelCode=" + hotelCode + ", mbrCardNo=" + mbrCardNo + "]";
	}

	public Long getRefundFee() {
		return refundFee;
	}

	public void setRefundFee(Long refundFee) {
		this.refundFee = refundFee;
	}
}