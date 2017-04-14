package com.whotel.card.dao;

import org.springframework.stereotype.Repository;

import com.whotel.card.entity.RedPackConfig;
import com.whotel.common.dao.mongo.MongoDao;

@Repository
public class RedPackConfigDao extends MongoDao<RedPackConfig, String> {
	
}
