package com.whotel.admin.dao;

import org.springframework.stereotype.Repository;

import com.whotel.admin.entity.SysRole;
import com.whotel.common.dao.mongo.MongoDao;

@Repository
public class SysRoleDao extends MongoDao<SysRole, String> {

}
