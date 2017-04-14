package com.whotel.card.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Transient;

import com.whotel.card.enums.ActivityType;
import com.whotel.common.entity.BaseEntity;
import com.whotel.common.util.QiniuUtils;
import com.whotel.common.util.RepoUtil;
import com.whotel.company.entity.Company;

/**
 * 活动
 * @author 柯鹏程
 *
 */
@Entity(noClassnameStored=true)
public class Activity extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String companyId;
	
	private String name;//活动名称
	
	private Date startTime;//开始时间
	
	private Date endTime;//结束时间
	
	private Integer everyDayNumber;//单天次数
	
	private Integer winningNumber;//中奖次数
	
	private Integer totalDrawPerPerson;//每人抽奖总数
	
	private String remark;//备注
	
	@Transient
	private List<Prize> prizes = new ArrayList<Prize>();//礼品
	
	private Integer totalNumber;//可抽奖总次数
	
	private Long participationCounts=0l;//参与抽奖次数
	
	private String shareIcon;
	
	private String shareTitle;
	
	private String shareDescription;
	
	private Date createTime;
	
	private Boolean isEnable;
	
	private String focusUrl;
	
	private Integer spendScore;//消耗积分
	
	private ActivityType activityType;//活动类型
	
	private String keyword;//语音关键字
	
	private Boolean isShow;//是否显示
	
	private Boolean isMemberCan; //是否只有会员才能抽奖  true：只有会员能抽奖
	
	private Integer totalWinningNumber;//每人总共可中奖次数
	
	private Integer mostShareCount;//最多分享次数
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Integer getEveryDayNumber() {
		return everyDayNumber;
	}

	public void setEveryDayNumber(Integer everyDayNumber) {
		this.everyDayNumber = everyDayNumber;
	}

	public Integer getWinningNumber() {
		return winningNumber;
	}

	public void setWinningNumber(Integer winningNumber) {
		this.winningNumber = winningNumber;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public List<Prize> getPrizes() {
		return prizes;
	}

	public void setPrizes(List<Prize> prizes) {
		this.prizes = prizes;
	}

	public Integer getTotalNumber() {
		return totalNumber;
	}

	public void setTotalNumber(Integer totalNumber) {
		this.totalNumber = totalNumber;
	}

	public Long getParticipationCounts() {
		return participationCounts;
	}

	public void setParticipationCounts(Long participationCounts) {
		this.participationCounts = participationCounts;
	}

	public String getShareIconUrl() {
		return QiniuUtils.getResUrl(shareIcon);
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

//	public Integer getState() {
//		return state;
//	}
//
//	public void setState(Integer state) {
//		this.state = state;
//	}

	public String getFocusUrl() {
		return focusUrl;
	}

	public void setFocusUrl(String focusUrl) {
		this.focusUrl = focusUrl;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public ActivityType getActivityType() {
		return activityType;
	}

	public void setActivityType(ActivityType activityType) {
		this.activityType = activityType;
	}

	public Boolean getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(Boolean isEnable) {
		this.isEnable = isEnable;
	}

	public Boolean getIsShow() {
		return isShow;
	}

	public void setIsShow(Boolean isShow) {
		this.isShow = isShow;
	}
	
	public String getIsOnGoing(){
		Date now = new Date();
		
		if(endTime.before(now)){//结束时间<当前时间...过期
			return "end";
		}else if(now.before(startTime)){//当前时间>开始时间...未开始
			return "star";
		}else{
			return "true";
		}
	}

	public Integer getSpendScore() {
		return spendScore;
	}

	public void setSpendScore(Integer spendScore) {
		this.spendScore = spendScore;
	}

	public Company getCompany() {
		return RepoUtil.getCompany(companyId);
	}

	public Integer getTotalDrawPerPerson() {
		return totalDrawPerPerson;
	}

	public void setTotalDrawPerPerson(Integer totalDrawPerPerson) {
		this.totalDrawPerPerson = totalDrawPerPerson;
	}

	public Boolean getIsMemberCan() {
		return isMemberCan;
	}

	public void setIsMemberCan(Boolean isMemberCan) {
		this.isMemberCan = isMemberCan;
	}

	public Integer getTotalWinningNumber() {
		return totalWinningNumber;
	}

	public void setTotalWinningNumber(Integer totalWinningNumber) {
		this.totalWinningNumber = totalWinningNumber;
	}

	public Integer getMostShareCount() {
		return mostShareCount;
	}

	public void setMostShareCount(Integer mostShareCount) {
		this.mostShareCount = mostShareCount;
	}
	
}
