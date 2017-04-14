package com.whotel.hotel.dao;

import org.springframework.stereotype.Repository;

import com.whotel.common.dao.UnDeletedEntityMongoDao;
import com.whotel.hotel.entity.Hotel;

/**
 * 酒店信息数据操作
 * @author 冯勇
 */
@Repository
public class HotelDao extends UnDeletedEntityMongoDao<Hotel, String> {
}
