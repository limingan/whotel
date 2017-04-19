package com.whotel.meal.controller.req;

import java.math.BigDecimal;

/**
 * Created by limingan on 2017/4/16.
 */
public class ListHotelReq {

    private String companyId;

    private Integer type;//门店类型 2-堂食 1-外卖 0-两者

    private String city;//城市

    private Integer sortType;//排序方式 1-正在营业 2-距离优先

    private Double lat;//纬度

    private Double lng;//经度

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getSortType() {
        return sortType;
    }

    public void setSortType(Integer sortType) {
        this.sortType = sortType;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }
}
