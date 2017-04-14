package com.whotel.system.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;

import com.whotel.common.entity.BaseEntity;

/**
 * 模板消息发送日志实体
 * @author Administrator
 *
 */
@Entity(noClassnameStored=true)
public class SysTemplateMsgLog extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String commpanyId;

	private String methodName;
	
	@Embedded
	private List param = new ArrayList<>();
	
	private String result;
	
	private Date createDate;
	
	public SysTemplateMsgLog(){
	}
	
	public SysTemplateMsgLog(String commpanyId,String methodName,String result,Object... parameters){
		this.commpanyId = commpanyId;
		this.methodName = methodName;
		if(parameters!=null){
			for (int i = 0; i < parameters.length; i++) {
				this.param.add(parameters[i]);
			}
		}
		this.result = result;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public List getParam() {
		return param;
	}

	public void setParam(List param) {
		this.param = param;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getCommpanyId() {
		return commpanyId;
	}

	public void setCommpanyId(String commpanyId) {
		this.commpanyId = commpanyId;
	}
}
