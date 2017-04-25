package com.whotel.meal.service;

import com.google.common.collect.Lists;
import com.whotel.hotel.entity.Hotel;
import com.whotel.meal.entity.DishesPractice;
import com.whotel.thirdparty.jxd.api.JXDMealService;
import com.whotel.thirdparty.jxd.mode.MealDishesPracticeQuery;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by limingan on 2017/4/24.
 */
@Service
public class DishesPracticeService{
    private static final Logger logger = Logger.getLogger(DishesCategoryService.class);



    public List<DishesPractice> getDishesPractices(Hotel hotel){
        List<DishesPractice> result = Lists.newArrayList();
        try {
            JXDMealService jxdMealService = new JXDMealService();
            MealDishesPracticeQuery query = new MealDishesPracticeQuery();
            query.setHotelCode(hotel.getCode());
            result = jxdMealService.loadDishesPractice(query, hotel.getMealUrl());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("DishesPracticeService getDishesPractices" ,e);
        }
        return result;
    }
}
