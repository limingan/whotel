package com.whotel.card.dao;

import org.springframework.stereotype.Repository;

import com.whotel.card.entity.Guest;
import com.whotel.common.dao.mongo.MongoDao;

@Repository
public class GuestDao extends MongoDao<Guest, String> {
	
}
