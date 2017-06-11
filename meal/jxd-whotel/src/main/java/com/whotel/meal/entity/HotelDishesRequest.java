package com.whotel.meal.entity;

import com.whotel.common.entity.BaseEntity;
import org.mongodb.morphia.annotations.Entity;

import java.util.List;

/**
 * Created by limingan on 2017/6/10.
 */
@Entity(noClassnameStored=true)
public class HotelDishesRequest extends BaseEntity {

    private String companyId;

    private String hotelId;

    private String hotelCode;

    private List<DishesRequest> list;

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getHotelId() {
        return hotelId;
    }

    public void setHotelId(String hotelId) {
        this.hotelId = hotelId;
    }

    public String getHotelCode() {
        return hotelCode;
    }

    public void setHotelCode(String hotelCode) {
        this.hotelCode = hotelCode;
    }

    public List<DishesRequest> getList() {
        return list;
    }

    public void setList(List<DishesRequest> list) {
        this.list = list;
    }
}
