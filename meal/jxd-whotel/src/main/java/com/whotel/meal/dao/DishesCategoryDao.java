package com.whotel.meal.dao;

import org.springframework.stereotype.Repository;

import com.whotel.common.dao.UnDeletedEntityMongoDao;
import com.whotel.meal.entity.DishesCategory;

/**
 * 菜式分类数据操作
 * @author Administrator
 *
 */
@Repository
public class DishesCategoryDao extends UnDeletedEntityMongoDao<DishesCategory, String> {

}
