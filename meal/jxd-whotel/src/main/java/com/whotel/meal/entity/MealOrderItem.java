package com.whotel.meal.entity;

import com.whotel.thirdparty.jxd.util.AbstractInputParam;
import org.mongodb.morphia.annotations.Embedded;

import java.util.List;

public class MealOrderItem extends AbstractInputParam {
	
	private String dishesId;
	
	private String itemCode;//菜式代码
	
	private String name;//菜式名称
	
	private Integer itemQuantity;//数量
	
	private String unit;//单位
	
	private Float itemPrice;//价格
	
	private Float itemAmount;//消费项目金额

	@Embedded
	private List<SuiteItem> itemList;//已选套餐信息

	private DishesAction dishesAction;//做法

	private String suiteData;//存储的套餐

	private Integer isSuite;//是否套餐

	private String actionId;//做法Id


	@Embedded
	private List<DishProp> propList;//菜品做法、规格信息

	private String propData;//菜品做法、规格信息json字符串

	public Integer getIsSuite() {
		return isSuite;
	}

	public void setIsSuite(Integer isSuite) {
		this.isSuite = isSuite;
	}

	public List<SuiteItem> getItemList() {
		return itemList;
	}

	public void setItemList(List<SuiteItem> itemList) {
		this.itemList = itemList;
	}

	public DishesAction getDishesAction() {
		return dishesAction;
	}

	public void setDishesAction(DishesAction dishesAction) {
		this.dishesAction = dishesAction;
	}

	public String getItemCode() {
		return itemCode;
	}

	public String getSuiteData() {
		return suiteData;
	}

	public void setSuiteData(String suiteData) {
		this.suiteData = suiteData;
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

	public String getActionId() {
		return actionId;
	}

	public void setActionId(String actionId) {
		this.actionId = actionId;
	}

	public List<DishProp> getPropList() {
		return propList;
	}

	public void setPropList(List<DishProp> propList) {
		this.propList = propList;
	}

	public String getPropData() {
		return propData;
	}

	public void setPropData(String propData) {
		this.propData = propData;
	}

	@Override
	public String getRoot() {
		return "Item";
	}
}
