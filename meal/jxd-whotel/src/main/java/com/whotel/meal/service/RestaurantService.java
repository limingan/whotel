package com.whotel.meal.service;

import com.whotel.common.util.DateUtil;
import com.whotel.meal.controller.req.ListRestaurantReq;
import com.whotel.meal.dao.MealOrderDao;
import com.whotel.meal.dao.RestaurantDao;
import com.whotel.meal.entity.MealOrder;
import com.whotel.meal.entity.Restaurant;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.mongodb.morphia.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by limingan on 2017/4/18.
 */
@Service
public class RestaurantService {

    private static final Logger logger = Logger.getLogger(RestaurantService.class);

    @Autowired
    private RestaurantDao restaurantDao;
    @Autowired
    private MealOrderDao mealOrderDao;

    public List<Restaurant> getByParam(ListRestaurantReq param){
        Map<String, Serializable> properties = new HashMap<>();
        properties.put("companyId", param.getCompanyId());
        if(StringUtils.isNotBlank(param.getHotelCode())) {
        	properties.put("hotelCode",param.getHotelCode());
        }
        if(StringUtils.isNotBlank(param.getRestaurantId())) {
        	properties.put("id", param.getRestaurantId());
        }
        if(param.getIsEnable() != null) {
        	properties.put("isEnable", param.getIsEnable());
        }
        return restaurantDao.findByProperties(properties);
    }

    public Restaurant getById(String id){
        Map<String, Serializable> properties = new HashMap<>();
        properties.put("id", id);
        return restaurantDao.getByProperties(properties);
    }

    /**
     * 统计餐厅月销量
     * @param restaurant
     * @return
     */
    public Long countMonthSale(Restaurant restaurant){
        Query query = mealOrderDao.createQuery();
        query.field("companyId").equal(restaurant.getCompanyId());
        query.field("hotelCode").equals(restaurant.getHotelCode());
        query.field("restaurantId").equal(restaurant.getId());
        query.field("createDate").greaterThanOrEq(DateUtil.getStartMonth(new Date()));
        return mealOrderDao.count(query);
    }


}
