package com.whotel.card.entity;

import org.mongodb.morphia.annotations.Entity;

import com.whotel.common.entity.BaseEntity;

@Entity(noClassnameStored=true)
public class Share extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 389754414921741661L;
	
	private String activityId;
	
	private String openId;
	
	private int count;    //分享次数
	
	private int useCount; // 使用次数

	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getUseCount() {
		return useCount;
	}

	public void setUseCount(int useCount) {
		this.useCount = useCount;
	}
	
}
