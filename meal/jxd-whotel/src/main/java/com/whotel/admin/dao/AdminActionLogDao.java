package com.whotel.admin.dao;

import org.springframework.stereotype.Repository;

import com.whotel.admin.entity.AdminActionLog;
import com.whotel.common.dao.mongo.MongoDao;

@Repository
public class AdminActionLogDao extends MongoDao<AdminActionLog, String> {
}
