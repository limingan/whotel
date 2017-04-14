package com.whotel.card.dao;

import org.springframework.stereotype.Repository;

import com.whotel.card.entity.SendCouponRecord;
import com.whotel.common.dao.mongo.MongoDao;

@Repository
public class SendCouponRecordDao extends MongoDao<SendCouponRecord, String>  {

}
