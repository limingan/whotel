package com.whotel.common.base;

/**
 * 常量类
 * @author 冯勇
 *
 */
public  class Constants {
	/*
	 * 索引文件路径
	 */
	public static final String INDEX_PATH = "/index";
	
	public static final String MENU_MODULE_NAME = "MENU";
	
	public static final String MENU_MODULE_ID = "2";
	
	public static final String MENU_TYPE_CODE = "M";
	
	public static final String ACTION_TYPE_CODE = "A";
	
	public static final String MOBILE_VERIFY = "MOBILE_VERIFY_";

	public static final int DFT_CONSOLE_PAGE_SIZE = 20;
	
	public static final String BOOLEAN_TRUE = "TRUE";
	
	public static final String BOOLEAN_FLASH = "false";
	
	public static final String JXD_API_SOURCE = "weixin";
	
	// Response Constants
	public static final String  CHARSET = "UTF-8";
	public static final String  RESPONSE_CONTENT_TYPE_JSON = "application/json";
	public static final String  RESPONSE_CONTENT_TYPE_TXT = "text/plain";
	public static final String  HEADER_KEY_ACAO = "Access-Control-Allow-Origin";
	public static final String  HEADER_VAL_ACAO = "*";
	public static final String  STATUS_OK = "{\"status\":\"OK\"}";

	public static final int UNFREEZE_DAYS = 7;
	
	public static class UploadFileName {
		
		public static final String ORIGINAL = "ORIGINAL";    //原文件名称
		
		public static final String CUSTOMER = "CUSTOMER";     //自定义文件名称
	}
	
	public static class Session {
		public static final String ADMIN_LOGIN_KEY = "ADMIN_LOGIN_KEY";
		
		public static final String ADMIN_PRIVILEGE_KEY = "ADMIN_PRIVILEGE_KEY";
		
		public static final String COMPANY_PRIVILEGE_MAP = "COMPANY_PRIVILEGE_MAP";
		
		public static final String DOMAIN = "DOMAIN";
		
		public static final String COMPANY_ADMIN_LOGIN_KEY = "COMPANY_ADMIN_LOGIN_KEY";
		
		public static final String COMPANY_ADMIN_LOGIN_COMPANY = "COMPANY_ADMIN_LOGIN_COMPANY";
		
		public static final String COMPANY_ADMIN_LOGIN_CODE = "COMPANY_ADMIN_LOGIN_CODE";
		
		public static final String COMPANY_ADMIN_LOGIN_AVATAR = "COMPANY_ADMIN_LOGIN_AVATAR";
		
		public static final String WEIXINFAN_LOGIN_OPENID = "WEIXINFAN_LOGIN_OPENID";
		
		public static final String WEIXINFAN_LOGIN_COMPANYID = "WEIXINFAN_LOGIN_COMPANYID";
		
		public static final String WEIXINFAN_LOGIN_COMPANYNAME = "WEIXINFAN_LOGIN_COMPANYNAME";
		
		public static final String WEIXINFAN_LOGIN_IS_MEMBER = "WEIXINFAN_LOGIN_IS_MEMBER";
		
		public static final Integer SESSION_TIMEOUT = 864000;
		
		public static final String PAY_ORDER = "PAY_ORDER";
		
		public static final String GOODS_ORDER = "GOODS_ORDER";
		
		public static final String BOOK_ORDER = "BOOK_ORDER";
		
		public static final String TICKET_ORDER = "TICKET_ORDER";
		
		public static final String SCENIC_TICKET_ORDER = "SCENIC_TICKET_ORDER";
		
		public static final String COMBO_ORDER = "COMBO_ORDER";
		
		public static final String SKIP_BIND = "SKIP_BIND";
		
		public static final String SHOPPING_CART = "SHOPPING_CART";
		
		public static final String MEAL_ORDER = "MEAL_ORDER";
		
		public static final String MEAL_BOOK = "MEAL_BOOK";
		
		public static final String THEME = "THEME";
		
		public static final String COMPANY_THEME = "COMPANY_THEME";
	}
	
	public static class MessageCode {
		
		public static final String RESULT_SUCCESS = "200";
		
		public static final String RESULT_ERROR = "-1";
	}
	
	public static class OrderSnPre {
		public static final String PAY_SN = "01";
		public static final String MONEY_SN = "02";
		public static final String CREDIT_SN = "03";
		
		public static final int SN_LENGTH = 12;
	}
	
	public static class CacheKey {
	
		public static final String RESERVATION_BUY_PRE_KEY = "RESERVATION_BUY_PRE_KEY";
		
		public static final String SHOP_CART = "SHOP_CART";
		
		public static final Integer DAY_TIMIE_OUT = 86400;  //1天
		
		public static final String INVITECODE_KEY = "INVITECODE_KEY";
		
		public static final Integer INVITECODE_TIMIE_OUT = 2592000;  //30天
		
		public static final Integer HOUR_TIMIE_OUT = 3600;  //1小时
	}
	
}
