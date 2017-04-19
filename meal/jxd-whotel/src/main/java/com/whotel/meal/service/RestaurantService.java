package com.whotel.meal.service;

import com.whotel.meal.controller.req.ListRestaurantReq;
import com.whotel.meal.dao.RestaurantDao;
import com.whotel.meal.entity.Restaurant;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by limingan on 2017/4/18.
 */
@Service
public class RestaurantService {

    private static final Logger logger = Logger.getLogger(MealOrderService.class);

    @Autowired
    private RestaurantDao restaurantDao;

    public List<Restaurant> getByParam(ListRestaurantReq param){
        Map<String, Serializable> properties = new HashMap<>();
        properties.put("companyId", param.getCompanyId());
        properties.put("hotelCode",param.getHotelCode());
        return restaurantDao.findByProperties(properties);
    }


}
