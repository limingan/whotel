package com.whotel.meal.entity;

import org.mongodb.morphia.annotations.Embedded;

import java.util.List;

/**
 * Created by limingan on 2017/5/3.
 */
@Embedded
public class SuiteItem {

    private String suiteNo;
    private String suiteName;
    private String suiteUnit;
    private Integer grade;
    private Integer isAuto;
    private String dishNo;
    private String dishName;
    private Float quan;//数量
    private String unit;//单位
    private Float price;//价格
    private Float amount;//金额
    private String remark;//备注

    @Embedded
    private List<DishProp> propList;//菜品做法、规格信息

    private String propData;//菜品做法、规格信息json字符串

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

    public String getSuiteNo() {
        return suiteNo;
    }

    public void setSuiteNo(String suiteNo) {
        this.suiteNo = suiteNo;
    }

    public String getSuiteName() {
        return suiteName;
    }

    public void setSuiteName(String suiteName) {
        this.suiteName = suiteName;
    }

    public String getSuiteUnit() {
        return suiteUnit;
    }

    public void setSuiteUnit(String suiteUnit) {
        this.suiteUnit = suiteUnit;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public Integer getIsAuto() {
        return isAuto;
    }

    public void setIsAuto(Integer isAuto) {
        this.isAuto = isAuto;
    }

    public String getDishNo() {
        return dishNo;
    }

    public void setDishNo(String dishNo) {
        this.dishNo = dishNo;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public Float getQuan() {
        return quan;
    }

    public void setQuan(Float quan) {
        this.quan = quan;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
