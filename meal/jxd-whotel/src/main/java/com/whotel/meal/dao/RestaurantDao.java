package com.whotel.meal.dao;

import org.springframework.stereotype.Repository;

import com.whotel.common.dao.UnDeletedEntityMongoDao;
import com.whotel.meal.entity.Restaurant;
/**
 * 餐厅数据操作
 * @author Administrator
 *
 */
@Repository
public class RestaurantDao extends UnDeletedEntityMongoDao<Restaurant, String> {
	
}
