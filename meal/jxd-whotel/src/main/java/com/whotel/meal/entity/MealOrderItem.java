package com.whotel.meal.entity;

import com.whotel.thirdparty.jxd.util.AbstractInputParam;

public class MealOrderItem extends AbstractInputParam {
	
	private String dishesId;
	
	private String itemCode;//菜式代码
	
	private String name;//菜式名称
	
	private Integer itemQuantity;//数量
	
	private String unit;//单位
	
	private Float itemPrice;//价格
	
	private Float itemAmount;//消费项目金额

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public Integer getItemQuantity() {
		return itemQuantity;
	}

	public void setItemQuantity(Integer itemQuantity) {
		this.itemQuantity = itemQuantity;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Float getItemPrice() {
		return itemPrice;
	}

	public void setItemPrice(Float itemPrice) {
		this.itemPrice = itemPrice;
	}

	public Float getItemAmount() {
		return itemAmount;
	}

	public void setItemAmount(Float itemAmount) {
		this.itemAmount = itemAmount;
	}

	public String getDishesId() {
		return dishesId;
	}

	public void setDishesId(String dishesId) {
		this.dishesId = dishesId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getRoot() {
		return "Item";
	}
}
