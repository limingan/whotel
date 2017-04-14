package com.whotel.common.dao.dbutil;

public class DBException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public DBException() {
		super();
	}

	/**
	 * @param message
	 * @param cause
	 */
	public DBException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 */
	public DBException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public DBException(Throwable cause) {
		super(cause);
	}
}
