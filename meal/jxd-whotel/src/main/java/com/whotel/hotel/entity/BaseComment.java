package com.whotel.hotel.entity;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.whotel.common.entity.BaseEntity;
import com.whotel.common.entity.OwnerCheck;
import com.whotel.common.util.QiniuUtils;
import com.whotel.common.util.RepoUtil;
import com.whotel.front.entity.WeixinFan;

public class BaseComment extends BaseEntity implements OwnerCheck {
	
	private static final long serialVersionUID = 120910933321946200L;

	private String openId;//点评人
	
	private String orderSn;//订单号
	
	private String companyId;//商户id
	
	private String buyName;//购买的商品
	
	private String content;//消息内容
	
	private String images;//图片
	
	private List<HotelComment> comments;//回复list
	
	private Date createTime;//创建(回复)时间
	
	private Boolean checkStatus;//审核状态：true通过，false未通过，null未审核
	
	private Integer commentStar;//评星
	
	private Integer thumbUpCount;//点赞数
	
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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getImages() {
		return images;
	}

	public void setImages(String images) {
		this.images = images;
	}

	public List<HotelComment> getComments() {
		return comments;
	}

	public void setComments(List<HotelComment> comments) {
		this.comments = comments;
	}

	public Boolean getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(Boolean checkStatus) {
		this.checkStatus = checkStatus;
	}

	public String[] getImage() {
		return StringUtils.split(images, ",");
	}
	
	public String[] getImagesUrls() {
		String[] attachs = getImage();
		if(attachs != null) {
			for(int i=0; i<attachs.length; i++) {
				attachs[i] = QiniuUtils.getResUrl(attachs[i]);
			}
		}
		return attachs;
	}
	
	public WeixinFan getWeixinFan(){
		return RepoUtil.getWeixinFan(openId);
	}

	public String getOrderSn() {
		return orderSn;
	}

	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}

	public Integer getCommentStar() {
		return commentStar;
	}

	public void setCommentStar(Integer commentStar) {
		this.commentStar = commentStar;
	}

	public String getBuyName() {
		return buyName;
	}

	public void setBuyName(String buyName) {
		this.buyName = buyName;
	}

	public Integer getThumbUpCount() {
		return thumbUpCount;
	}

	public void setThumbUpCount(Integer thumbUpCount) {
		this.thumbUpCount = thumbUpCount;
	}
}
