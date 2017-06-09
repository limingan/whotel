package com.whotel.thirdparty.jxd.mode;

import com.whotel.thirdparty.jxd.util.AbstractInputParam;

public class MealDishesUnitQuery extends AbstractInputParam {
	
	private String opType = "餐饮菜式单位价格";
	
	private String hotelCode;//酒店代码
	
	private String lastQueryTime;//

	private String dishNo;

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

	public String getDishNo() {
		return dishNo;
	}

	public void setDishNo(String dishNo) {
		this.dishNo = dishNo;
	}

	@Override
	public String getRoot() {
		// TODO Auto-generated method stub
		return null;
	}

}
