package com.whotel.meal.controller.req;

/**
 * Created by limingan on 2017/5/2.
 */
public class LoginParam {
    private String comid;

    private String wxid;

    private String tabId;

    private String code;

    private RedirectType type;

    public String getComid() {
        return comid;
    }

    public void setComid(String comid) {
        this.comid = comid;
    }

    public String getWxid() {
        return wxid;
    }

    public void setWxid(String wxid) {
        this.wxid = wxid;
    }

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

    public RedirectType getType() {
        return type;
    }

    public void setType(RedirectType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "LoginParam{" +
                "comid='" + comid + '\'' +
                ", wxid='" + wxid + '\'' +
                ", tabId='" + tabId + '\'' +
                ", code='" + code + '\'' +
                ", type=" + type +
                '}';
    }
}
