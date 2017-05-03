package com.whotel.meal.entity;

import com.whotel.common.entity.BaseEntity;
import com.whotel.common.entity.UnDeletedEntity;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;

import java.util.List;

/**
 * 菜的做法
 * Created by limingan on 2017/4/24.
 */
@Entity(noClassnameStored=true)
public class DishesAction extends BaseEntity {

    private String companyId;//公司ID

    private String hotelCode;//hotelCode

    private String dishNo;//dishNo

    private String actionNo;//做法编号
    private String name;//做法名称
    private Float addPrice;//加价

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

    public String getActionNo() {
        return actionNo;
    }

    public void setActionNo(String actionNo) {
        this.actionNo = actionNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getAddPrice() {
        return addPrice;
    }

    public void setAddPrice(Float addPrice) {
        this.addPrice = addPrice;
    }
}
