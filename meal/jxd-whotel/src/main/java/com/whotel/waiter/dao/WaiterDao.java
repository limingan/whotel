package com.whotel.waiter.dao;

import org.springframework.stereotype.Repository;

import com.whotel.common.dao.mongo.MongoDao;
import com.whotel.waiter.entity.Waiter;

@Repository
public class WaiterDao extends  MongoDao<Waiter,String>{
	
}
