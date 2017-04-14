package com.weixin.core.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.weixin.core.bean.Menu;
import com.weixin.core.common.BaseToken;
import com.whotel.common.http.HttpHelper;
import com.whotel.ext.json.JSONConvertFactory;
import com.whotel.ext.json.JSONDataUtil;

/**
 * 微信自定义菜单创建类
 * @author 冯勇
 *
 */
public class MenuCreator {
	private static Logger log = LoggerFactory.getLogger(MenuCreator.class);

	/** 菜单创建（POST） 限100（次/天） */
	public static final String MENU_CREATE_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token={$ACCESS_TOKEN}";
	/** 菜单删除 */
	public static final String MENU_DELETE_URL = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token={$ACCESS_TOKEN}";
	/** 菜单查询 */
	public static final String MENU_GET_URL = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token={$ACCESS_TOKEN}";
	public static final int TIME_OUT = 30 * 1000;

	/**
	 * 创建菜单
	 * 
	 * @param menu
	 * @param appId
	 * @param appSecret
	 * @return
	 * @throws Exception
	 */
	public static BaseToken createMenu(Menu menu, String appId, String appSecret)
			throws Exception {
		return createMenu(menu, appId, appSecret, TIME_OUT);
	}
	
	/**
	 * 创建菜单
	 * 
	 * @param menu
	 * @param token
	 * @param timeout
	 * @return
	 * @throws Exception
	 */
	public static BaseToken createMenu(Menu menu, BaseToken token) throws Exception {
		return createMenu(menu,token,TIME_OUT);
	}
	/**
	 * 创建菜单
	 * 
	 * @param menu
	 * @param token
	 * @param timeout
	 * @return
	 * @throws Exception
	 */
	public static BaseToken createMenu(Menu menu,  BaseToken token,int timeout) throws Exception {
		if (token.getErrcode() != null && !token.getErrcode().equals("0")) {
				return null;
		}
		String url = MENU_CREATE_URL.replace("{$ACCESS_TOKEN}",
				token.getAccess_token());
		// 将菜单对象转换成json字符串
		JSONDataUtil jacksonConverter = JSONConvertFactory.getJacksonConverter();
		String jsonMenu = jacksonConverter.jsonfromObject(menu);
		// 调用接口创建菜单
		log.info("send menu json->" + jsonMenu);
		String resp = HttpHelper.connect(url).timeout(timeout).post(jsonMenu)
				.html();
		log.info(resp);
		if (resp != null && !resp.isEmpty()) {
			BaseToken respToken = jacksonConverter.objectFromString(resp, BaseToken.class);
			return respToken;
		}
		return null;
	}
	/**
	 * 创建菜单
	 * 
	 * @param menu
	 * @param appId
	 * @param appSecret
	 * @param timeout
	 * @return
	 * @throws Exception
	 */
	public static BaseToken createMenu(Menu menu, String appId, String appSecret,
			int timeout) throws Exception {
		BaseToken token = TokenManager.getBaseToken(appId, appSecret, timeout);
	    log.info("token:"+token);
		return createMenu(menu,token,timeout);
	}

	/**
	 * 删除菜单
	 * 
	 * @return
	 * @throws Exception
	 */
	public static BaseToken deleteMenu(BaseToken token,int timeout)
			throws Exception {
		if (token.getErrcode() != null && !token.getErrcode().equals("0")&&!token.isExceed()) {
			return null;
		}
		String url = MENU_DELETE_URL.replace("{$ACCESS_TOKEN}",
				token.getAccess_token());
		String resp = HttpHelper.connect(url).timeout(timeout).get().html();
		log.info(resp);
		if (resp != null && !resp.isEmpty()) {
			JSONDataUtil jacksonConverter = JSONConvertFactory.getJacksonConverter();
			BaseToken respToken = jacksonConverter.objectFromString(resp, BaseToken.class);
			return respToken;
		}
		return null;
	}
	
	public static BaseToken deleteMenu(BaseToken token)
			throws Exception {
		return deleteMenu(token,TIME_OUT);
	}
	/**
	 * 删除菜单
	 * 
	 * @param appId
	 * @param appSecret
	 * @return
	 * @throws Exception
	 */
	public static BaseToken deleteMenu(String appId, String appSecret)
			throws Exception {
		return deleteMenu(appId, appSecret, TIME_OUT);
	}

	/**
	 * 删除菜单
	 * 
	 * @param appId
	 * @param appSecret
	 * @param timeout
	 * @return
	 * @throws Exception
	 */
	public static BaseToken deleteMenu(String appId, String appSecret, int timeout)
			throws Exception {
		BaseToken token = TokenManager.getBaseToken(appId, appSecret, timeout);
		return deleteMenu(token,timeout);
	}

	/**
	 * 获取菜单
	 * 
	 * @param menu
	 * @param appId
	 * @param appSecret
	 * @param timeout
	 * @return
	 * @throws Exception
	 */
	public static String getMenu(Menu menu, String appId, String appSecret,
			int timeout) throws Exception {
		BaseToken token = TokenManager.getBaseToken(appId, appSecret, timeout);
		return getMenu(menu,token,timeout);
	}
	/**
	 * 获取菜单
	 * 
	 * @param menu
	 * @param appId
	 * @param appSecret
	 * @param timeout
	 * @return
	 * @throws Exception
	 */
	public static String getMenu(Menu menu, BaseToken token,
			int timeout) throws Exception {
		if (token.getErrcode() != null && !token.getErrcode().equals("0")&&!token.isExceed()) {
			return null;
		}
		String url = MENU_GET_URL.replace("{$ACCESS_TOKEN}",
				token.getAccess_token());
		String resp = HttpHelper.connect(url).timeout(timeout).get().html();
		if (log.isDebugEnabled()) {
			log.debug(resp);
		}
		return resp;
	}

}
