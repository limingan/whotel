package com.whotel.card.dao;

import org.springframework.stereotype.Repository;

import com.whotel.card.entity.ActivityGoods;
import com.whotel.common.dao.mongo.MongoDao;

@Repository
public class ActivityGoodsDao extends MongoDao<ActivityGoods, String> {
	
}
