package com.whotel.thirdparty.jxd.mode;

import com.whotel.thirdparty.jxd.util.AbstractInputParam;

/**
 * 餐台列表查询
 * @author 柯鹏程
 * 
 */
public class MealTabQuery extends AbstractInputParam {
	
	private String opType = "餐饮餐台查询";
	
	private String hotelCode;//酒店代码
	
	private String beginDate;//日期
	
	private String shuffle;//市别代码
	
	private String refeNo;//分厅的代码
	
	private String tabNo;//餐台代码
	
	private String orderDate;//预订时间
	
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

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getShuffle() {
		return shuffle;
	}

	public void setShuffle(String shuffle) {
		this.shuffle = shuffle;
	}

	public String getRefeNo() {
		return refeNo;
	}

	public void setRefeNo(String refeNo) {
		this.refeNo = refeNo;
	}

	public String getTabNo() {
		return tabNo;
	}

	public void setTabNo(String tabNo) {
		this.tabNo = tabNo;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}
}
