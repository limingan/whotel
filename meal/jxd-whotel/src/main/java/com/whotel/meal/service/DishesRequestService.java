package com.whotel.meal.service;

import com.whotel.meal.dao.DishesRequestDao;
import com.whotel.meal.entity.HotelDishesRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by limingan on 2017/6/10.
 */
@Service
public class DishesRequestService {

    @Autowired
    DishesRequestDao dishesRequestDao;

    public HotelDishesRequest getByHotelId(String hotelId) {
        if (StringUtils.isNotBlank(hotelId)) {
            HotelDishesRequest request = dishesRequestDao.getByProperty("hotelId", hotelId);
            return request;
        }
        return null;
    }

    public HotelDishesRequest getByHotelCode(String companyId,String hotelCode) {
        Map<String, Serializable> properties = new HashMap<>();
        properties.put("companyId", companyId);
        properties.put("hotelCode", hotelCode);
        return dishesRequestDao.getByProperties(properties);
    }

    public void save(HotelDishesRequest request) {
        dishesRequestDao.save(request);
    }

}
