package com.whotel.thirdparty.jxd.mode;

import com.whotel.thirdparty.jxd.util.XmlBean;

/**
 * Created by limingan on 2017/5/23.
 */
public class CyReservationItem implements XmlBean {

    private String itemCode;
    private String itemName;
    private Integer itemQuantity;
    private String unit;
    private Float itemPrice;
    private Integer itemAmount;
    private String actionName;
    private String request;
    private Integer rowid;
    private Integer upRowid;

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Integer getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(Integer itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Float getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(Float itemPrice) {
        this.itemPrice = itemPrice;
    }

    public Integer getItemAmount() {
        return itemAmount;
    }

    public void setItemAmount(Integer itemAmount) {
        this.itemAmount = itemAmount;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public Integer getRowid() {
        return rowid;
    }

    public void setRowid(Integer rowid) {
        this.rowid = rowid;
    }

    public Integer getUpRowid() {
        return upRowid;
    }

    public void setUpRowid(Integer upRowid) {
        this.upRowid = upRowid;
    }

    @Override
    public String getRoot() {
        return null;
    }


}
