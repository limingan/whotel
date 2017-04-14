package com.whotel.front.dao;

import java.util.Date;

import org.springframework.stereotype.Repository;

import com.whotel.common.dao.dbutil.DBExecutor;

/**
 * 生成订单号
 * @author fy
 */
@Repository
public class OrderSnDao {
	
	private DBExecutor dbExecutor = new DBExecutor();
	
	private static final String SQL_INSERT_ORDERSN = "insert into order_sn(create_time) values(?)";
	
	public long getOrderSn() {
		return dbExecutor.insertWithAutoKey(SQL_INSERT_ORDERSN, new Date());
	}
}
