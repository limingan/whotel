package com.whotel.weixin.service;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.weixin.core.api.JsapiTicket;
import com.weixin.core.api.TokenManager;
import com.weixin.core.common.BaseToken;
import com.weixin.core.common.JsTicketToken;
import com.whotel.common.base.Constants;
import com.whotel.common.util.SpringContextHolder;
import com.whotel.ext.redis.RedisCache;

/**
 * access token生成，2小时内多次利用处理类
 * @author 冯勇
 *
 */
public class TokenService {

	private RedisCache cache;

	private static final Logger log = LoggerFactory.getLogger(TokenService.class);

	private TokenService() {
		cache = SpringContextHolder.getBean(RedisCache.class);
	}

	// 基础支持的access_token
	private final static String CACHE_PRE = "AccessToken_";
	
	private final static String JSAPI_CACHEPRE = "JsApiTicket_";

	private volatile static TokenService service;

	public static TokenService getTokenService() {
		if (service == null) {
			synchronized (TokenService.class) {
				if (service == null) {
					service = new TokenService();
				}
			}
		}
		return service;
	}

	
	public BaseToken refreshAccessToken(String appId, String appSecret, BaseToken oldToken) throws Exception {
		synchronized (TokenService.class) {
			log.warn("刷新TOKEN NOW!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
//			BaseToken token=getAccessToken(appId,appSecret);
//			//createTime的比较是为了防止多线程下多次刷新
//			if(oldToken==null||(token!=null&&token.getCreateTime()!=oldToken.getCreateTime())){
//				//已被刷新过
//				return token;
//			}else{
				String key = buildKey(appId);
				cache.evict(key);
				return getAccessToken(appId,appSecret);
//			}
		}
	}
	
	/**
	 * 获取accesstoken
	 * @param appId
	 * @param appSecret
	 * @return
	 * @throws Exception
	 */
	public BaseToken getAccessToken(String appId, String appSecret) throws Exception {
		String key = buildKey(appId);
		Object value = cache.get(key);//  null;//
		if (value == null || ((BaseToken) value).isExceed()) {
			log.info("从微信API获取TOKEN FOR APPID->" + appId);
			
			synchronized (TokenService.class) {
				
				value = cache.get(key);//  null;//
				if (value == null || ((BaseToken) value).isExceed()) {
					BaseToken token = null;
					int reTry = 3;
					while (!TokenManager.isValidToken(token) && reTry-- > 0) {
						token = TokenManager.getBaseToken(appId, appSecret);
					}
					log.info("获取TOKEN尝试次数->" + (3 - reTry));
					if (TokenManager.isValidToken(token)) {
						cache.put(key, token, Constants.CacheKey.HOUR_TIMIE_OUT * 1);
						return token;
					} else {
						log.warn("获取TOKEN失败,token-->" + token);
						return null;
					}
				}
			}

		}
		log.info("从缓存获取TOKEN FOR APPID->" + appId);
		return (BaseToken) value;
	}
	
	public JsTicketToken refreshJsToken(String appId, String appSecret,JsTicketToken oldToken,String type) throws Exception {
		synchronized (TokenService.class) {
//			log.warn("刷新TOKEN NOW!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
			JsTicketToken token=getJsTicketToken(appId,appSecret,type);
			//createTime的比较是为了防止多线程下多次刷新
			if(oldToken==null||(token!=null&&token.getCreateTime()!=oldToken.getCreateTime())){
				//已被刷新过
				return token;
			}else{
				String key = buildKey(appId);
				cache.evict(key);
				return getJsTicketToken(appId,appSecret,type);
			}
		}
	}
	
	public JsTicketToken getJsTicketToken(String appId, String appSecret,String type) throws Exception {
		String key = JSAPI_CACHEPRE + appId;
		JsTicketToken token=(JsTicketToken) cache.get(key);
		if (token == null || token.isExceed()||!StringUtils.equals(token.getErrcode(), "0")) {
			synchronized (TokenService.class) {
				 token = (JsTicketToken) cache.get(key);
				 if (token == null || token.isExceed()||!StringUtils.equals(token.getErrcode(), "0")) {
					int reTry = 3;
					BaseToken bt=getAccessToken(appId, appSecret);
					token = JsapiTicket.getJsApiTicket(bt,type);
					while (!TokenManager.isValidToken(token) && reTry-- > 0) {
						token = JsapiTicket.getJsApiTicket(bt,type);
						if(token!=null&&token.getErrcode()!=null&&token.getErrcode().equals("42001")){
							bt=this.refreshAccessToken(appId, appSecret, bt);
						}
					}
					if (TokenManager.isValidToken(token)) {
						cache.put(key, token, Constants.CacheKey.HOUR_TIMIE_OUT * 2);
						return token;
					} else {
						//42001 access_token expired
						log.warn("获取JSTicketTOKEN失败,JSTicketTOKEN-->" + token);
						return token;
					}
				}
			}
		}
		return token;
	}
	

	private String buildKey(String appId) {
		return CACHE_PRE + appId;
	}

}
