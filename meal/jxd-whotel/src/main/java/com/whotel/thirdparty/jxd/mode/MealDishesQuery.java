package com.whotel.thirdparty.jxd.mode;

import com.whotel.thirdparty.jxd.util.AbstractInputParam;

/**
 * 菜式列表查询
 * @author 柯鹏程
 * 
 */
public class MealDishesQuery extends AbstractInputParam {
	
	private String opType = "餐饮菜式查询";
	
	private String hotelCode;//酒店代码
	
	private String lastQueryTime;//上次查询时间 格式为:	YYYY-MM-DD hh:mm 
	
	private String refeNo;//分厅编码
	
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
}
