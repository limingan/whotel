 package com.whotel.card.entity;

import java.util.Date;
import java.util.List;

import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;

import com.whotel.common.entity.BaseEntity;

/**
 * 抽奖会员
 * @author 柯鹏程
 *
 */
@Entity(noClassnameStored=true)
public class LuckDrawMember extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String openId;
	
	private String activityId;//活动id
	
	private Date date;//时间

	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}
}
