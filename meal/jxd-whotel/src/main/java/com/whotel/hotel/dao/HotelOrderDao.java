package com.whotel.hotel.dao;

import org.springframework.stereotype.Repository;

import com.whotel.common.dao.UnDeletedEntityMongoDao;
import com.whotel.hotel.entity.HotelOrder;

/**
 * 酒店订单信息数据操作
 * @author 冯勇
 */
@Repository
public class HotelOrderDao extends UnDeletedEntityMongoDao<HotelOrder, String> {
}
