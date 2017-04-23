package com.whotel.meal.service;

import com.whotel.meal.dao.DishesCategoryDao;
import com.whotel.meal.entity.DishesCategory;
import com.whotel.meal.entity.Restaurant;
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
public class DishesCategoryService {

    private static final Logger logger = Logger.getLogger(DishesCategoryService.class);

    @Autowired
    private DishesCategoryDao dishesCategoryDao;

    public List<DishesCategory> getByRestaurant(Restaurant restaurant){
        Map<String, Serializable> properties = new HashMap<>();
        properties.put("companyId", restaurant.getCompanyId());
        properties.put("hotelCode",restaurant.getHotelCode());
        properties.put("restaurantId",restaurant.getId());
        return dishesCategoryDao.findByProperties(properties);
    }

    public DishesCategory getById(String id){
        Map<String, Serializable> properties = new HashMap<>();
        properties.put("id", id);
        return dishesCategoryDao.getByProperties(properties);
    }

}
