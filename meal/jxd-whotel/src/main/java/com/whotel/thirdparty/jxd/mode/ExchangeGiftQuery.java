package com.whotel.thirdparty.jxd.mode;

import com.whotel.thirdparty.jxd.util.AbstractInputParam;

/**
 * 积分兑换
 * @author 冯勇
 *
 */
public class ExchangeGiftQuery extends AbstractInputParam {

	private String opType = "E云通兑换礼品";
	
	private String profileId;
	private String mbrCardNo;
	private String mbrSeqNo;
	private String itemId;
	private String itemCode;
	private String itemCName;
	private String itemEName;
	private Integer quantity;
	private String resortID;
	private String outletCode;
	private Float score;
	private String getMode;
	private String getterCName;
	private String getterPhone;
	private String getAddress;
	private String getZipCode;
	private String remark;
	private String openId;
	
	public String getOpType() {
		return opType;
	}

	public void setOpType(String opType) {
		this.opType = opType;
	}

	public String getProfileId() {
		return profileId;
	}

	public void setProfileId(String profileId) {
		this.profileId = profileId;
	}

	public String getMbrCardNo() {
		return mbrCardNo;
	}

	public void setMbrCardNo(String mbrCardNo) {
		this.mbrCardNo = mbrCardNo;
	}

	public String getMbrSeqNo() {
		return mbrSeqNo;
	}

	public void setMbrSeqNo(String mbrSeqNo) {
		this.mbrSeqNo = mbrSeqNo;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getItemCName() {
		return itemCName;
	}

	public void setItemCName(String itemCName) {
		this.itemCName = itemCName;
	}

	public String getItemEName() {
		return itemEName;
	}

	public void setItemEName(String itemEName) {
		this.itemEName = itemEName;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getResortID() {
		return resortID;
	}

	public void setResortID(String resortID) {
		this.resortID = resortID;
	}

	public String getOutletCode() {
		return outletCode;
	}

	public void setOutletCode(String outletCode) {
		this.outletCode = outletCode;
	}

	public Float getScore() {
		return score;
	}

	public void setScore(Float score) {
		this.score = score;
	}

	public String getGetMode() {
		return getMode;
	}

	public void setGetMode(String getMode) {
		this.getMode = getMode;
	}

	public String getGetterCName() {
		return getterCName;
	}

	public void setGetterCName(String getterCName) {
		this.getterCName = getterCName;
	}

	public String getGetterPhone() {
		return getterPhone;
	}

	public void setGetterPhone(String getterPhone) {
		this.getterPhone = getterPhone;
	}

	public String getGetAddress() {
		return getAddress;
	}

	public void setGetAddress(String getAddress) {
		this.getAddress = getAddress;
	}

	public String getGetZipCode() {
		return getZipCode;
	}

	public void setGetZipCode(String getZipCode) {
		this.getZipCode = getZipCode;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String getRoot() {
		return null;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	@Override
	public String toString() {
		return "ExchangeGiftQuery [opType=" + opType + ", profileId=" + profileId + ", mbrCardNo=" + mbrCardNo
				+ ", mbrSeqNo=" + mbrSeqNo + ", itemId=" + itemId + ", itemCode=" + itemCode + ", itemCName="
				+ itemCName + ", itemEName=" + itemEName + ", quantity=" + quantity + ", resortID=" + resortID
				+ ", outletCode=" + outletCode + ", score=" + score + ", getMode=" + getMode + ", getterCName="
				+ getterCName + ", getterPhone=" + getterPhone + ", getAddress=" + getAddress + ", getZipCode="
				+ getZipCode + ", remark=" + remark + ", openId=" + openId + "]";
	}
}
