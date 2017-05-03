package com.whotel.thirdparty.jxd.mode;

import com.whotel.thirdparty.jxd.util.AbstractInputParam;

public class MealDishesUnitQuery extends AbstractInputParam {
	
	private String opType = "餐饮食品作法查询";
	
	private String hotelCode;//酒店代码
	
	private String lastQueryTime;//

	private String refeNo;

	public String getOpType() {
		return opType;
	}

	public void setOpType(String opType) {
		this.opType = opType;
	}

	public String getHotelCode() {
		return hotelCode;
	}

	public void setHotelCode(String hotelCode) {
		this.hotelCode = hotelCode;
	}

	public String getLastQueryTime() {
		return lastQueryTime;
	}

	public void setLastQueryTime(String lastQueryTime) {
		this.lastQueryTime = lastQueryTime;
	}

	public String getRefeNo() {
		return refeNo;
	}

	public void setRefeNo(String refeNo) {
		this.refeNo = refeNo;
	}

	@Override
	public String getRoot() {
		// TODO Auto-generated method stub
		return null;
	}

}
