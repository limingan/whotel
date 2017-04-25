package com.whotel.meal.entity;

import com.whotel.common.entity.UnDeletedEntity;
import org.mongodb.morphia.annotations.Entity;

/**
 * 菜的做法
 * Created by limingan on 2017/4/24.
 */
@Entity(noClassnameStored=true)
public class DishesPractice extends UnDeletedEntity {

    private String actionNo;//作法编码

    private String actionName;//作法名称

    private Float addprice;//作法加价

    public String getActionNo() {
        return actionNo;
    }

    public void setActionNo(String actionNo) {
        this.actionNo = actionNo;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public Float getAddprice() {
        return addprice;
    }

    public void setAddprice(Float addprice) {
        this.addprice = addprice;
    }
}
