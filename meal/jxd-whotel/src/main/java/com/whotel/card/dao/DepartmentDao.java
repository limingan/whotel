package com.whotel.card.dao;

import org.springframework.stereotype.Repository;

import com.whotel.card.entity.Department;
import com.whotel.common.dao.mongo.MongoDao;

@Repository
public class DepartmentDao extends MongoDao<Department, String> {
	
}
