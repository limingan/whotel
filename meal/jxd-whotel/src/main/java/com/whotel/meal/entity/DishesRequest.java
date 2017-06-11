package com.whotel.meal.entity;

import com.whotel.thirdparty.jxd.util.AbstractInputParam;
import org.mongodb.morphia.annotations.Embedded;

/**
 * Created by limingan on 2017/6/10.
 */
@Embedded
public class DishesRequest {
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
