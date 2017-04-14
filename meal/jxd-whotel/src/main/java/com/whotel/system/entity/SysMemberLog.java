package com.whotel.system.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;

import com.whotel.common.entity.BaseEntity;

/**
 * 会员日志实体
 * @author Administrator
 *
 */
@Entity(noClassnameStored=true)
public class SysMemberLog extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String hotelCode;

	private String methodName;
	
	@Embedded
	private List<String[]> param = new ArrayList<String[]>();
	
	private String result;
	
	private Date createDate;
	
	public SysMemberLog(){
	}
	
	public SysMemberLog(String hotelCode,String methodName,String result,String[]... parameters){
		this.setHotelCode(hotelCode);
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

	public List<String[]> getParam() {
		return param;
	}

	public void setParam(List<String[]> param) {
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

	public String getHotelCode() {
		return hotelCode;
	}

	public void setHotelCode(String hotelCode) {
		this.hotelCode = hotelCode;
	}
}
