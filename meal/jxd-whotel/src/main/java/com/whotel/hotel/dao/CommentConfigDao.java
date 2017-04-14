package com.whotel.hotel.dao;

import org.springframework.stereotype.Repository;

import com.whotel.common.dao.mongo.MongoDao;
import com.whotel.hotel.entity.CommentConfig;

@Repository
public class CommentConfigDao extends MongoDao<CommentConfig, String> {

}
