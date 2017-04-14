package com.whotel.card.dao;

import org.springframework.stereotype.Repository;

import com.whotel.card.entity.MarketingRecord;
import com.whotel.common.dao.mongo.MongoDao;

@Repository
public class MarketingRecordDao extends MongoDao<MarketingRecord, String> {
	
}
