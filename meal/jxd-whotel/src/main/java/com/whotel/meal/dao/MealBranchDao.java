package com.whotel.meal.dao;

import org.springframework.stereotype.Repository;

import com.whotel.common.dao.UnDeletedEntityMongoDao;
import com.whotel.meal.entity.MealBranch;
/**
 * 餐饮分店数据操作
 * @author Administrator
 *
 */
@Repository
public class MealBranchDao extends UnDeletedEntityMongoDao<MealBranch, String> {
	
}
