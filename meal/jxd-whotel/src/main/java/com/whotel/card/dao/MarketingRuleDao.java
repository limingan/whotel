package com.whotel.card.dao;

import org.springframework.stereotype.Repository;

import com.whotel.card.entity.MarketingRule;
import com.whotel.common.dao.mongo.MongoDao;

@Repository
public class MarketingRuleDao extends MongoDao<MarketingRule, String> {
	
}
