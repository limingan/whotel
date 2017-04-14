package com.whotel.meal.dao;

import org.springframework.stereotype.Repository;

import com.whotel.common.dao.UnDeletedEntityMongoDao;
import com.whotel.meal.entity.Dishes;
/**
 * 菜式数据操作
 * @author Administrator
 *
 */
@Repository
public class DishesDao extends UnDeletedEntityMongoDao<Dishes, String> {

}
