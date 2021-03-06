package com.whotel.meal.entity;

import org.mongodb.morphia.annotations.Embedded;

/**
 *
 * Created by limingan on 2017/4/24.
 */
@Embedded
public class DishesUnit{

    private String id;//dishNo

    private String name;//单位

    private Float addPrice;//价格

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Float getAddPrice() {
        return addPrice;
    }

    public void setAddPrice(Float addPrice) {
        this.addPrice = addPrice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
