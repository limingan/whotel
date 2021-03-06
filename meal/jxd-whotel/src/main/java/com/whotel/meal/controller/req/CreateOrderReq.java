package com.whotel.meal.controller.req;

import com.whotel.meal.entity.MealOrderItem;
import com.whotel.meal.enums.MealOrderType;

import java.util.List;

/**
 * Created by limingan on 2017/5/4.
 */
public class CreateOrderReq {

    private String companyId;

    private String openId;

    private String addressId;

    private List<MealOrderItem> list;

    private String remark;

    private Integer guestNum;

    private MealOrderType mealOrderType;

    private String prizeId;//优惠券ID

    private Integer payAfter;//是否后付费 0-否 1-是

    private String tabId;

    public String getTabId() {
        return tabId;
    }

    public void setTabId(String tabId) {
        this.tabId = tabId;
    }

    public Integer getPayAfter() {
        return payAfter;
    }

    public void setPayAfter(Integer payAfter) {
        this.payAfter = payAfter;
    }

    public MealOrderType getMealOrderType() {
        return mealOrderType;
    }

    public void setMealOrderType(MealOrderType mealOrderType) {
        this.mealOrderType = mealOrderType;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public List<MealOrderItem> getList() {
        return list;
    }

    public void setList(List<MealOrderItem> list) {
        this.list = list;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getGuestNum() {
        return guestNum;
    }

    public void setGuestNum(Integer guestNum) {
        this.guestNum = guestNum;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getPrizeId() {
        return prizeId;
    }

    public void setPrizeId(String prizeId) {
        this.prizeId = prizeId;
    }
}
