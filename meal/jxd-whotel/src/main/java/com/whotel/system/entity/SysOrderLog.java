package com.whotel.system.entity;

import java.util.Date;

import org.mongodb.morphia.annotations.Entity;

import com.whotel.common.entity.BaseEntity;

/**
 * 系统订单日志实体
 * @author 柯鹏程
 *
 */
@Entity(noClassnameStored=true)
public class SysOrderLog extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String paramXml;

	private String type;

	private String url;
	
	private String result;
	
	private Date createDate;
	
	public SysOrderLog(){
	}
	
	public SysOrderLog(String paramXml,String type,String url,String result){
		this.paramXml = paramXml;
		this.type = type;
		this.url = url;
		this.result = result;
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

	public String getParamXml() {
		return paramXml;
	}

	public void setParamXml(String paramXml) {
		this.paramXml = paramXml;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
