package com.whotel.card.dao;

import org.springframework.stereotype.Repository;

import com.whotel.card.entity.ContactAddress;
import com.whotel.common.dao.mongo.MongoDao;

@Repository
public class ContactAddressDao extends MongoDao<ContactAddress, String> {
	
}
