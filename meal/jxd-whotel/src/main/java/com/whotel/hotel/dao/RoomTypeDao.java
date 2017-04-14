package com.whotel.hotel.dao;

import org.springframework.stereotype.Repository;

import com.whotel.common.dao.UnDeletedEntityMongoDao;
import com.whotel.hotel.entity.RoomType;

/**
 * 酒店房型信息数据操作
 * @author 冯勇
 */
@Repository
public class RoomTypeDao extends UnDeletedEntityMongoDao<RoomType, String> {
}
