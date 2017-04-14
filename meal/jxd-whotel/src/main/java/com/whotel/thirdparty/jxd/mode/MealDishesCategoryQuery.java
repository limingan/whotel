package com.whotel.thirdparty.jxd.mode;

import com.whotel.thirdparty.jxd.util.AbstractInputParam;

public class MealDishesCategoryQuery extends AbstractInputParam {
	
	private String opType = "餐饮菜式分类查询";
	
	private String hotelCode;//酒店代码
	
	private String refeNo;//分厅编码

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
