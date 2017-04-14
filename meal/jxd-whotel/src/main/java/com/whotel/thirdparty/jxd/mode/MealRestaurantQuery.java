package com.whotel.thirdparty.jxd.mode;

import com.whotel.thirdparty.jxd.util.AbstractInputParam;

/**
 * 餐厅列表查询
 * @author 柯鹏程
 * 
 */
public class MealRestaurantQuery extends AbstractInputParam {
	
	private String opType = "餐饮餐厅查询";

	private String hotelCode;//酒店代码
	
	private String RefeNo;//分厅编号
	
	public String getOpType() {
		return opType;
	}

	public void setOpType(String opType) {
		this.opType = opType;
	}
	
	@Override
	public String getRoot() {
		return null;
	}

	public String getHotelCode() {
		return hotelCode;
	}

	public void setHotelCode(String hotelCode) {
		this.hotelCode = hotelCode;
	}

	public String getRefeNo() {
		return RefeNo;
	}

	public void setRefeNo(String refeNo) {
		RefeNo = refeNo;
	}
}
