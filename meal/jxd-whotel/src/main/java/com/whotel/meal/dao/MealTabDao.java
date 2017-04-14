package com.whotel.meal.dao;

import org.springframework.stereotype.Repository;

import com.whotel.common.dao.UnDeletedEntityMongoDao;
import com.whotel.meal.entity.MealTab;
/**
 * 餐台数据操作
 * @author Administrator
 *
 */
@Repository
public class MealTabDao extends UnDeletedEntityMongoDao<MealTab, String> {
	
}
