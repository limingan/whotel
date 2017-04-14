package com.whotel.front.dao;

import org.springframework.stereotype.Repository;

import com.whotel.common.dao.UnDeletedEntityMongoDao;
import com.whotel.front.entity.PayOrder;

@Repository
public class PayOrderDao extends UnDeletedEntityMongoDao<PayOrder, String> {

}
