package com.whotel.meal.controller.req;

import com.whotel.meal.enums.MealOrderType;

/**
 * Created by limingan on 2017/4/18.
 */
public class ListRestaurantReq {

    private String companyId;//公司ID

    private String hotelCode;//分店Code

    private Integer payAfter;//是否后付费

    private MealOrderType mealOrderType;//订单类型 IN——OUT

    public MealOrderType getMealOrderType() {
        return mealOrderType;
    }

    public void setMealOrderType(MealOrderType mealOrderType) {
        this.mealOrderType = mealOrderType;
    }

    public Integer getPayAfter() {
        return payAfter;
    }

    public void setPayAfter(Integer payAfter) {
        this.payAfter = payAfter;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getHotelCode() {
        return hotelCode;
    }

    public void setHotelCode(String hotelCode) {
        this.hotelCode = hotelCode;
    }
}
