package com.weixin.core.exception;

/**
 * 访问网页异常
 * 
 */
@SuppressWarnings("serial")
public class AccessorException extends Exception {

	public AccessorException(String message) {
		super(message);
	}

	public AccessorException(Throwable cause) {
		super(cause);
	}

	public AccessorException(String message, Throwable cause) {
		super(message, cause);
	}

}
