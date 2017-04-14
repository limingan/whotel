package com.whotel.hotel.dao;

import org.springframework.stereotype.Repository;

import com.whotel.common.dao.mongo.MongoDao;
import com.whotel.hotel.entity.ThumbUpRecord;

@Repository
public class ThumbUpRecordDao extends MongoDao<ThumbUpRecord, String>  {

}
