package com.whotel.meal.service;

import com.google.common.collect.Lists;
import com.whotel.hotel.entity.Hotel;
import com.whotel.meal.dao.DishesActionDao;
import com.whotel.meal.entity.Dishes;
import com.whotel.meal.entity.DishesAction;
import com.whotel.thirdparty.jxd.api.JXDMealService;
import com.whotel.thirdparty.jxd.mode.MealDishesActionQuery;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by limingan on 2017/4/24.
 */
@Service
public class DishesActionService {
    private static final Logger logger = Logger.getLogger(DishesCategoryService.class);

    @Autowired
    private DishesActionDao dishesActionDao;

    public DishesAction getById(String id){
        return dishesActionDao.get(id);
    }

    public List<DishesAction> getDishesAction(Hotel hotel){
        List<DishesAction> result = Lists.newArrayList();
        try {
            JXDMealService jxdMealService = new JXDMealService();
            MealDishesActionQuery query = new MealDishesActionQuery();
            query.setHotelCode(hotel.getCode());
            query.setLastQueryTime("2010-01-01");
            result = jxdMealService.loadDishesAction(query, hotel);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("DishesActionService getDishesAction" ,e);
        }
        return result;
    }

    public void saveDishesAction(List<DishesAction> list){
        for(DishesAction dishesAction:list){
            dishesActionDao.save(dishesAction);
        }
    }

    public List<DishesAction> getByDishes(Dishes dishes){
        Map<String, Serializable> properties = new HashMap<>();
        properties.put("companyId", dishes.getCompanyId());
        properties.put("hotelCode", dishes.getHotelCode());
        properties.put("dishNo", dishes.getDishNo());
        return dishesActionDao.findByProperties(properties);
    }

    public void deleteByHotel(Hotel hotel){
        Map<String, Serializable> properties = new HashMap<>();
        properties.put("companyId", hotel.getCompanyId());
        properties.put("hotelCode", hotel.getCode());
        dishesActionDao.delete(properties);
    }
}
