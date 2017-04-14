package com.whotel.card.dao;

import org.springframework.stereotype.Repository;

import com.whotel.card.entity.SignInRecord;
import com.whotel.common.dao.mongo.MongoDao;

@Repository
public class SignInRecordDao extends MongoDao<SignInRecord, String> {
	
}
