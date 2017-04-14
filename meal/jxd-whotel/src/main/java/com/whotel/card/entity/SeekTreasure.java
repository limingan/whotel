package com.whotel.card.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Transient;

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
public class SeekTreasure extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String companyId;
	
	private String name;//活动名称
	
	private Date startTime;//开始时间
	
	private Date endTime;//结束时间
	
	private String remark;//备注
	
	@Transient
	private List<Prize> prizes = new ArrayList<Prize>();//礼品
	
	private String shareIcon;
	
	private String shareTitle;
	
	private String shareDescription;
	
	private Date createTime;
	
	private Long participationCounts;
	
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

	public Company getCompany() {
		return RepoUtil.getCompany(companyId);
	}

	public Long getParticipationCounts() {
		return participationCounts;
	}

	public void setParticipationCounts(Long participationCounts) {
		this.participationCounts = participationCounts;
	}
}
