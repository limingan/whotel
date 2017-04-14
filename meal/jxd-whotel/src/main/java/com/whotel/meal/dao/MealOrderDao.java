package com.whotel.meal.dao;

import org.springframework.stereotype.Repository;

import com.whotel.common.dao.UnDeletedEntityMongoDao;
import com.whotel.meal.entity.MealOrder;
/**
 * 餐饮数据操作
 * @author Administrator
 *
 */
@Repository
public class MealOrderDao extends UnDeletedEntityMongoDao<MealOrder, String> {
	
}
