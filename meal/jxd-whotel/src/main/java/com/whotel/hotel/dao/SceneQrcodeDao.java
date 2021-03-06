package com.whotel.hotel.dao;

import org.springframework.stereotype.Repository;

import com.whotel.common.dao.mongo.MongoDao;
import com.whotel.hotel.entity.SceneQrcode;

@Repository
public class SceneQrcodeDao extends MongoDao<SceneQrcode, String> {
}
