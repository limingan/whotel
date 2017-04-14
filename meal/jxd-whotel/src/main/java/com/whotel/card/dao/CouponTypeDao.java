package com.whotel.card.dao;

import org.springframework.stereotype.Repository;

import com.whotel.card.entity.CouponType;
import com.whotel.common.dao.mongo.MongoDao;

@Repository
public class CouponTypeDao extends MongoDao<CouponType, String> {
	
}
