package com.whotel.card.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Transient;

import com.whotel.common.entity.BaseEntity;
import com.whotel.common.util.QiniuUtils;

@Entity(noClassnameStored=true)
public class MarketingGame extends BaseEntity {
	
	private static final long serialVersionUID = 3668660726437438193L;
	
	private String companyId;
	
	private String name;//游戏名称
	
	private Integer gameTime;//游戏时长
	
	private String focusUrl;//关注链接
	
	@Embedded
	private List<GameLevel> levels = new ArrayList<GameLevel>();//关卡
	
	private String remark;//备注
	
	private String shareIcon;
	
	private String shareTitle;
	
	private String shareDescription;
	
	private Date createTime;
	
	@Embedded
	private Prize passPrize;//通关奖品

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getGameTime() {
		return gameTime;
	}

	public void setGameTime(Integer gameTime) {
		this.gameTime = gameTime;
	}

	public List<GameLevel> getLevels() {
		return levels;
	}

	public void setLevels(List<GameLevel> levels) {
		this.levels = levels;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getShareIcon() {
		return shareIcon;
	}

	public void setShareIcon(String shareIcon) {
		this.shareIcon = shareIcon;
	}

	public String getShareTitle() {
		return shareTitle;
	}

	public void setShareTitle(String shareTitle) {
		this.shareTitle = shareTitle;
	}

	public String getShareDescription() {
		return shareDescription;
	}

	public void setShareDescription(String shareDescription) {
		this.shareDescription = shareDescription;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getFocusUrl() {
		return focusUrl;
	}

	public void setFocusUrl(String focusUrl) {
		this.focusUrl = focusUrl;
	}
	
	public String getShareIconUrl() {
		return QiniuUtils.getResUrl(shareIcon);
	}

	public Prize getPassPrize() {
		return passPrize;
	}

	public void setPassPrize(Prize passPrize) {
		this.passPrize = passPrize;
	}
}
