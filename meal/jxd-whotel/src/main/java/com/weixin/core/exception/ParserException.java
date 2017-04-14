package com.weixin.core.exception;

/**
 * 解析异常
 * 
 */
@SuppressWarnings("serial")
public class ParserException extends Exception {

	public ParserException(String message) {
		super(message);
	}

	public ParserException(Throwable cause) {
		super(cause);
	}

	public ParserException(String message, Throwable cause) {
		super(message, cause);
	}

}
