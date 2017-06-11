package com.whotel.thirdparty.jxd.mode;

import com.whotel.thirdparty.jxd.util.AbstractInputParam;

public class MealDishesRequestQuery extends AbstractInputParam {
	
	private String opType = "餐饮要求查询";
	
	private String hotelCode;//酒店代码

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

	@Override
	public String getRoot() {
		// TODO Auto-generated method stub
		return null;
	}

}
