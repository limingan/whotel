package com.whotel.common.util;

public class ExceptionUtil {

	private ExceptionUtil() {}
	
	/**
	 * 将CheckedException转换为UnCheckedException.
	 */
	public static final RuntimeException unchecked(Exception e) {
		if (e instanceof RuntimeException) {
			return (RuntimeException) e;
		}
		return new RuntimeException(e.getMessage(), e);
	}

}
