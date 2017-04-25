package com.whotel.meal.enums;

import com.whotel.common.base.Labeled;

/**
 * Created by limingan on 2017/4/25.
 */
public enum  MealOrderType implements Labeled {

    IN("堂食"),OUT("外卖");

    private String label;

    private MealOrderType(String label) {
        this.label = label;
    }

    @Override
    public String getLabel() {
        return null;
    }
}
