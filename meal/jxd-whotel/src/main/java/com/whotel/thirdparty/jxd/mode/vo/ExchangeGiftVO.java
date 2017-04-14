package com.whotel.thirdparty.jxd.mode.vo;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;

public class ExchangeGiftVO {

	private String itemId;
	private String itemCode;
	private String itemCName;
	private String itemEName;
	private Float score;
	private String pictureID;
	private String pictureUrl;
	
	private Integer quantity; 
	private String outletCode;
	private String isGoods;
	private String isApply;
	private String remark;
	private Date beginDate;
	private Date endDate;
	private String errorMsg;
	private String getMode;
	
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
	public Float getScore() {
		return score;
	}
	public void setScore(Float score) {
		this.score = score;
	}
	public String getPictureID() {
		return pictureID;
	}
	public void setPictureID(String pictureID) {
		this.pictureID = pictureID;
	}
	public String getPictureUrl() {
		return pictureUrl;
	}
	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}
	
	public String getGiftPic() {
		if(StringUtils.isNotBlank(pictureUrl) && StringUtils.isNotBlank(pictureID)) {
			return pictureUrl.replace("{0}", pictureID);
		}
		return null;
	}
	
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public String getOutletCode() {
		return outletCode;
	}
	public void setOutletCode(String outletCode) {
		this.outletCode = outletCode;
	}
	public String getIsGoods() {
		return isGoods;
	}
	public void setIsGoods(String isGoods) {
		this.isGoods = isGoods;
	}
	public String getIsApply() {
		return isApply;
	}
	public void setIsApply(String isApply) {
		this.isApply = isApply;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Date getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	@Override
	public String toString() {
		return "ExchangeGiftVO [itemId=" + itemId + ", itemCode=" + itemCode
				+ ", itemCName=" + itemCName + ", itemEName=" + itemEName
				+ ", score=" + score + ", pictureID=" + pictureID
				+ ", pictureUrl=" + pictureUrl + ", quantity=" + quantity
				+ ", outletCode=" + outletCode + ", isGoods=" + isGoods
				+ ", isApply=" + isApply + ", remark=" + remark
				+ ", beginDate=" + beginDate + ", endDate=" + endDate
				+ ", errorMsg=" + errorMsg + "]";
	}
	public String getGetMode() {
		return getMode;
	}
	public void setGetMode(String getMode) {
		this.getMode = getMode;
	}
	
}
