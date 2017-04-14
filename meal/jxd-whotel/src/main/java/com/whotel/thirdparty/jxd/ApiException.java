package com.whotel.thirdparty.jxd;

/**
 * 调用第三方接口发生业务逻辑相关的异常
 * @author 冯勇
 * 
 */
@SuppressWarnings("serial")
public class ApiException extends Exception {

	public ApiException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ApiException(String message, Throwable cause) {
		super(message, cause);
	}

	public ApiException(String message) {
		super(message);
	}

	public ApiException(Throwable cause) {
		super(cause);
	}

}
