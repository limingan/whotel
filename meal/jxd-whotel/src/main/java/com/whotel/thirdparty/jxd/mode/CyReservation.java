package com.whotel.thirdparty.jxd.mode;

import com.whotel.thirdparty.jxd.util.XmlBean;

import java.util.Date;
import java.util.List;

/**
 * Created by limingan on 2017/5/23.
 */
public class CyReservation implements XmlBean {

    private List<CyReservationItem> items;
    private String resStatus;
    private String hotelCode;
    private String confirmationID;
    private String createDate;
    private String refe;
    private String tabNo;
    private Float totalAmount;
    private String remark;
    private String guestCname;
    private Integer guestNum;
    private String mbrCardNo;
    private String mobile;
    private Float prepay;
    private String prepayMethod;
    private String prepayId;
    private String wxid;
    private String ticketName;
    private Float ticketAmount;
    private String operName;

    public List<CyReservationItem> getItems() {
        return items;
    }

    public void setItems(List<CyReservationItem> items) {
        this.items = items;
    }

    public String getResStatus() {
        return resStatus;
    }

    public void setResStatus(String resStatus) {
        this.resStatus = resStatus;
    }

    public String getHotelCode() {
        return hotelCode;
    }

    public void setHotelCode(String hotelCode) {
        this.hotelCode = hotelCode;
    }

    public String getConfirmationID() {
        return confirmationID;
    }

    public void setConfirmationID(String confirmationID) {
        this.confirmationID = confirmationID;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getRefe() {
        return refe;
    }

    public void setRefe(String refe) {
        this.refe = refe;
    }

    public String getTabNo() {
        return tabNo;
    }

    public void setTabNo(String tabNo) {
        this.tabNo = tabNo;
    }

    public Float getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Float totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getGuestCname() {
        return guestCname;
    }

    public void setGuestCname(String guestCname) {
        this.guestCname = guestCname;
    }

    public Integer getGuestNum() {
        return guestNum;
    }

    public void setGuestNum(Integer guestNum) {
        this.guestNum = guestNum;
    }

    public String getMbrCardNo() {
        return mbrCardNo;
    }

    public void setMbrCardNo(String mbrCardNo) {
        this.mbrCardNo = mbrCardNo;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Float getPrepay() {
        return prepay;
    }

    public void setPrepay(Float prepay) {
        this.prepay = prepay;
    }

    public String getPrepayMethod() {
        return prepayMethod;
    }

    public void setPrepayMethod(String prepayMethod) {
        this.prepayMethod = prepayMethod;
    }

    public String getPrepayId() {
        return prepayId;
    }

    public void setPrepayId(String prepayId) {
        this.prepayId = prepayId;
    }

    public String getWxid() {
        return wxid;
    }

    public void setWxid(String wxid) {
        this.wxid = wxid;
    }

    public String getTicketName() {
        return ticketName;
    }

    public void setTicketName(String ticketName) {
        this.ticketName = ticketName;
    }

    public Float getTicketAmount() {
        return ticketAmount;
    }

    public void setTicketAmount(Float ticketAmount) {
        this.ticketAmount = ticketAmount;
    }

    public String getOperName() {
        return operName;
    }

    public void setOperName(String operName) {
        this.operName = operName;
    }

    @Override
    public String getRoot() {
        return null;
    }
}
