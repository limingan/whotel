package com.whotel.meal.entity;

import org.mongodb.morphia.annotations.Embedded;

import java.util.List;

/**
 * Created by limingan on 2017/5/8.
 */
@Embedded
public class DishProp {

    private String propId;//规格ID

    private List<String> valueList;//规格值Id;

    public List<String> getValueList() {
        return valueList;
    }

    public void setValueList(List<String> valueList) {
        this.valueList = valueList;
    }

    public String getPropId() {
        return propId;
    }

    public void setPropId(String propId) {
        this.propId = propId;
    }
}
