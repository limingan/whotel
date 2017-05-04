package com.whotel.meal.controller.req;

import com.whotel.meal.entity.MealOrderItem;

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
}
