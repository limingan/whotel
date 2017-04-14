package com.whotel.common.util;

/**
 * 金额工具
 * 
 */
public class MoneyUtil {
	
	private MoneyUtil() {}
	
	/**
	 * 四舍五入双精度浮点数
	 * 
	 * @param money
	 * @return
	 */
	public static final double round(double money) {
		return Math.round(money * 100) / 100D;
	}

	/**
	 * 四舍五入单精度浮点数
	 * 
	 * @param money
	 * @return
	 */
	public static final float round(float money) {
		return Math.round(money * 100) /100F;
	}

}
