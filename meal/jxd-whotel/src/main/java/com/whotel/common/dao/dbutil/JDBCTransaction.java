package com.whotel.common.dao.dbutil;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 事务处理
 * @author fy
 *
 */
public class JDBCTransaction implements Transaction {
	private static Log log = LogFactory.getFactory().getInstance(JDBCTransaction.class);
	private final DataSource dataSource;
	private Connection conn;

	/**
	 * construct  method
	 */
	public JDBCTransaction() {
		this.dataSource = DataSourceFactory.getDataSource();
	}

	public JDBCTransaction(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public void begin() {
		try {
			if (dataSource != null) {
				conn = dataSource.getConnection();
				if (conn != null && conn.getAutoCommit()) {
					conn.setAutoCommit(false);
				}
			}
		} catch (SQLException e) {
			log.error("begin transaction ", e);
		}
	}

	/**
	 * get connection
	 * @return
	 */
	public Connection getConnection() {
		return conn;
	}

	
	@Override
	public void commit() {
		try {
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void rollback() {
		try {
			conn.rollback();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
