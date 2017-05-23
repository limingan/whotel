package com.whotel.thirdparty.jxd.mode;

import com.whotel.thirdparty.jxd.util.XmlBean;

/**
 * Created by limingan on 2017/5/23.
 */
public class CyReservationResult implements XmlBean {

    private String hotelCode;
    private String confirmationID;
    private String billno;
    private String errorMsg;

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

    public String getBillno() {
        return billno;
    }

    public void setBillno(String billno) {
        this.billno = billno;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Override
    public String getRoot() {
        return null;
    }
}
