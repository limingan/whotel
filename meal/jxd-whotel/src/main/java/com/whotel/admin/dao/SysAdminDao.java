package com.whotel.admin.dao;

import org.springframework.stereotype.Repository;

import com.whotel.admin.entity.SysAdmin;
import com.whotel.common.dao.mongo.MongoDao;

@Repository
public class SysAdminDao extends MongoDao<SysAdmin, String> {

}
