package com.whotel.hotel.dao;

import org.springframework.stereotype.Repository;

import com.whotel.common.dao.mongo.MongoDao;
import com.whotel.hotel.entity.HotelComment;

@Repository
public class HotelCommentDao extends MongoDao<HotelComment, String> {

}
