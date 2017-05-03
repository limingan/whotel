package com.whotel.meal.entity;

import com.whotel.common.entity.BaseEntity;
import org.mongodb.morphia.annotations.Entity;

/**
 * 菜的做法
 * Created by limingan on 2017/4/24.
 */
@Entity(noClassnameStored=true)
public class DishesUnit extends BaseEntity {

    private String companyId;//公司ID

    private String hotelCode;//hotelCode

    private String dishNo;//dishNo

    private String unit;//单位
    private Float price;//价格

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

    public String getDishNo() {
        return dishNo;
    }

    public void setDishNo(String dishNo) {
        this.dishNo = dishNo;
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
}
