package com.whotel.system.entity;

import java.util.Date;

import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.weixin.core.bean.EventMsg;
import com.whotel.common.entity.BaseEntity;

/**
 * 会员日志实体
 * @author Administrator
 *
 */
@Entity(noClassnameStored=true)
public class SysSubscribeLog extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Embedded
	private EventMsg eventMsg;
	
	private Date createDate;
	
	public SysSubscribeLog(){
	}
	
	public SysSubscribeLog(EventMsg eventMsg){
		this.eventMsg = eventMsg;
	}

	public EventMsg getEventMsg() {
		return eventMsg;
	}

	public void setEventMsg(EventMsg eventMsg) {
		this.eventMsg = eventMsg;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
}
