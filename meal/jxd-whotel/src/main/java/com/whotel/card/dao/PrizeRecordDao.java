package com.whotel.card.dao;

import org.springframework.stereotype.Repository;

import com.whotel.card.entity.PrizeRecord;
import com.whotel.common.dao.mongo.MongoDao;

@Repository
public class PrizeRecordDao extends MongoDao<PrizeRecord, String> {
	
}
