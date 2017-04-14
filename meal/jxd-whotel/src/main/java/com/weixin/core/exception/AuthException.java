package com.weixin.core.exception;

/**
 * 认证异常
 * 
 */
@SuppressWarnings("serial")
public class AuthException extends Exception {

	public AuthException(String message) {
		super(message);
	}

	public AuthException(Throwable cause) {
		super(cause);
	}

	public AuthException(String message, Throwable cause) {
		super(message, cause);
	}

}
