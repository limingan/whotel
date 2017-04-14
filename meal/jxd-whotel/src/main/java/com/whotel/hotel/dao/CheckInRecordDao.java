package com.whotel.hotel.dao;

import org.springframework.stereotype.Repository;

import com.whotel.common.dao.mongo.MongoDao;
import com.whotel.hotel.entity.CheckInRecord;

@Repository
public class CheckInRecordDao extends MongoDao<CheckInRecord, String> {
}
