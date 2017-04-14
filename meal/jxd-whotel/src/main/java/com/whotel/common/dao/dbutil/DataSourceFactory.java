package com.whotel.common.dao.dbutil;

import javax.sql.DataSource;

import com.whotel.common.util.SpringContextHolder;

/**
 * 数据源工厂
 * 
 * @author fy
 * 
 */
public class DataSourceFactory {

	private static DataSource dataSource = null;

	public static DataSource getDataSource() {
		if (dataSource == null)
			dataSource = SpringContextHolder.getBean("dataSource");
		return dataSource;
	}
}
