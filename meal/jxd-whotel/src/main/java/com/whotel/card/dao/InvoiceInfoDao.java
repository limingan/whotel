package com.whotel.card.dao;

import org.springframework.stereotype.Repository;

import com.whotel.card.entity.InvoiceInfo;
import com.whotel.common.dao.mongo.MongoDao;

@Repository
public class InvoiceInfoDao extends MongoDao<InvoiceInfo, String> {
	
}
