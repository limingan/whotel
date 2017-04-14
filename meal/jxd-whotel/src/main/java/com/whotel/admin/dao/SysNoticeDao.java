package com.whotel.admin.dao;

import org.springframework.stereotype.Repository;

import com.whotel.admin.entity.SysNotice;
import com.whotel.common.dao.mongo.MongoDao;

@Repository
public class SysNoticeDao extends MongoDao<SysNotice, String> {

}
