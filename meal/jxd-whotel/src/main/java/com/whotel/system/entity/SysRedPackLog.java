package com.whotel.system.entity;

import java.util.Date;
import java.util.Map;

import org.mongodb.morphia.annotations.Entity;

import com.whotel.common.entity.BaseEntity;

/**
 * 发红包日志
 */
@Entity(noClassnameStored=true)
public class SysRedPackLog extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Map<String, String> dataMap;

	private String msg;

	private String winningMemberId;
	
	private Boolean isOk;
	
	private Date createDate;
	
	public SysRedPackLog(){
	}
	
	public Map<String, String> getDataMap() {
		return dataMap;
	}

	public SysRedPackLog(Map<String, String> dataMap, String msg, String winningMemberId, Boolean isOk) {
		this.dataMap = dataMap;
		this.msg = msg;
		this.winningMemberId = winningMemberId;
		this.isOk = isOk;
	}

	public void setDataMap(Map<String, String> dataMap) {
		this.dataMap = dataMap;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getWinningMemberId() {
		return winningMemberId;
	}

	public void setWinningMemberId(String winningMemberId) {
		this.winningMemberId = winningMemberId;
	}

	public Boolean getIsOk() {
		return isOk;
	}

	public void setIsOk(Boolean isOk) {
		this.isOk = isOk;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

}
