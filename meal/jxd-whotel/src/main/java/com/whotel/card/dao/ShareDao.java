package com.whotel.card.dao;

import org.springframework.stereotype.Repository;

import com.whotel.card.entity.Share;
import com.whotel.common.dao.mongo.MongoDao;

@Repository
public class ShareDao extends MongoDao<Share, String> {
}
