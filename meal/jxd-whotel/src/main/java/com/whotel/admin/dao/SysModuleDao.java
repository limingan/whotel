package com.whotel.admin.dao;

import org.springframework.stereotype.Repository;

import com.whotel.admin.entity.SysModule;
import com.whotel.common.dao.mongo.MongoDao;

@Repository
public class SysModuleDao extends MongoDao<SysModule, String> {

}
