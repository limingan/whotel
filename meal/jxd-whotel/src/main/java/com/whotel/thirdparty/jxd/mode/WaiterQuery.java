package com.whotel.thirdparty.jxd.mode;

import com.whotel.thirdparty.jxd.util.AbstractInputParam;


/** 
 * @ClassName: WaiterQuery 
 * @Description: 服务员查询实体封装
 * @author 李中辉 
 * @date 2017年5月6日 上午8:48:46  
 */
public class WaiterQuery extends AbstractInputParam{

	private String opType = "餐饮操作员查询";
	
	private String XType="Gemstar";
	
	private String hotelCode;//酒店代码
	
	private String beginDate;//日期

	public String getOpType() {
		return opType;
	}

	public void setOpType(String opType) {
		this.opType = opType;
	}

	public String getXType() {
		return XType;
	}

	public void setXType(String xType) {
		XType = xType;
	}

	public String getHotelCode() {
		return hotelCode;
	}

	public void setHotelCode(String hotelCode) {
		this.hotelCode = hotelCode;
	}

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	@Override
	public String getRoot() {
		return null;
	}
}
