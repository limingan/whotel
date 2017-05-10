package com.whotel.meal.controller.req;

/**
 * Created by limingan on 2017/5/2.
 */
public class LoginParam {

    private String tabId;

    private String code;


    public String getTabId() {
        return tabId;
    }

    public void setTabId(String tabId) {
        this.tabId = tabId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "LoginParam{" +
                "tabId='" + tabId + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
