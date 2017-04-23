package com.whotel.meal.service;

import com.whotel.meal.dao.DishesDao;
import com.whotel.meal.entity.Dishes;
import com.whotel.meal.entity.DishesCategory;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by limingan on 2017/4/21.
 */
@Service
public class DishesService {

    private static final Logger logger = Logger.getLogger(DishesCategoryService.class);

    @Autowired
    private DishesDao dishesDao;

    public List<Dishes> getByCate(DishesCategory cat){
        Map<String, Serializable> properties = new HashMap<>();
        properties.put("companyId", cat.getCompanyId());
        properties.put("hotelCode",cat.getHotelCode());
        properties.put("restaurantId",cat.getRestaurantId());
        properties.put("dishNo",cat.getDishNo());
        return dishesDao.findByProperties(properties);
    }
}
