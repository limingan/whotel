package com.whotel.thirdparty.jxd.mode;

import java.util.Map;

import com.whotel.thirdparty.jxd.util.AbstractInputParam;

/**
 * 餐饮预订
 *
 * @author 柯鹏程
 */
public class MealBook extends AbstractInputParam {

    private String opType = "餐饮预订";

    private Map<String, Object> cyReservation;

    public String getOpType() {
        return opType;
    }

    public void setOpType(String opType) {
        this.opType = opType;
    }

    @Override
    public String getRoot() {
        return null;
    }

    public Map<String, Object> getCyReservation() {
        return cyReservation;
    }

    public void setCyReservation(Map<String, Object> cyReservation) {
        this.cyReservation = cyReservation;
    }

}
