package com.whotel.card.dao;

import org.springframework.stereotype.Repository;

import com.whotel.card.entity.MbrCardType;
import com.whotel.common.dao.mongo.MongoDao;

@Repository
public class MbrCardTypeDao extends MongoDao<MbrCardType, String> {
	
}
