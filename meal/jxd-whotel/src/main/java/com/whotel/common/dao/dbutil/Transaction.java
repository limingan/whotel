package com.whotel.common.dao.dbutil;

/**
 * Transaction
 */
public interface Transaction {

	public void begin();

	public void commit();

	public void rollback();

}