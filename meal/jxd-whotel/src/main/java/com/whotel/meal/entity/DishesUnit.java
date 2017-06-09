package com.whotel.meal.entity;

import com.whotel.common.entity.BaseEntity;
import com.whotel.thirdparty.jxd.util.AbstractInputParam;
import org.mongodb.morphia.annotations.Entity;

/**
 *
 * Created by limingan on 2017/4/24.
 */
public class DishesUnit extends AbstractInputParam {

    private Integer id;//dishNo

    private String unit;//单位

    private Float price;//价格

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    @Override
    public String getRoot() {
        return "unit";
    }
}
