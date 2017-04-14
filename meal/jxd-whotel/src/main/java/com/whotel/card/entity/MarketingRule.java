package com.whotel.card.entity;

import org.apache.commons.lang3.StringUtils;
import org.mongodb.morphia.annotations.Entity;

import com.whotel.common.entity.BaseEntity;
import com.whotel.common.util.QiniuUtils;

@Entity(noClassnameStored=true)
public class MarketingRule extends BaseEntity {

	private static final long serialVersionUID = 3200633082514730512L;
	
	private String companyId;
	
	private String title;

	private Integer credit;
	
	private Integer commission;
	
	private String showText;
	
	private String backgroundImg;
	
	public String getBackgroundImgUrl() {
		return QiniuUtils.getResUrl(backgroundImg);
	}

	public String getShowText() {
		if(StringUtils.isBlank(showText)){
			return "欢迎扫码关注";
		}
		return showText;
	}

	public void setShowText(String showText) {
		this.showText = showText;
	}

	public String getBackgroundImg() {
		return backgroundImg;
	}

	public void setBackgroundImg(String backgroundImg) {
		this.backgroundImg = backgroundImg;
	}

	public String getCompanyId() {
		return companyId;
	}
	
	public String getTitle() {
		if(StringUtils.isBlank(title)){
			return "全员微营销";
		}
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public Integer getCredit() {
		return credit;
	}

	public void setCredit(Integer credit) {
		this.credit = credit;
	}

	public Integer getCommission() {
		return commission;
	}

	public void setCommission(Integer commission) {
		this.commission = commission;
	}
}
