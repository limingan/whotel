package com.whotel.card.dao;

import org.springframework.stereotype.Repository;

import com.whotel.card.entity.MarketingGame;
import com.whotel.common.dao.mongo.MongoDao;

@Repository
public class MarketingGameDao extends MongoDao<MarketingGame, String> {

}
