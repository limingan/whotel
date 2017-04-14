package com.whotel.weixin.handler;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.weixin.core.handler.TokenAuthHandler;
import com.whotel.common.util.SpringContextHolder;
import com.whotel.company.entity.PublicNo;
import com.whotel.company.service.PublicNoService;

/**
 * 开发者模式授权Token处理类
 * 
 * @author 冯勇
 */
public class TokenAuthHandlerImpl implements TokenAuthHandler {

	private static final Logger log = LoggerFactory.getLogger(TokenAuthHandlerImpl.class);

	private PublicNoService publicNoService;

	@Override
	public String auth(String cid) {
		log.info("TokenAuthHandlerImpl companyId=" + cid);

		if (StringUtils.isBlank(cid)) {
			return null;
		}
		loadRequiredSpringBean();
		PublicNo publicNo = publicNoService.getPublicNoByCompanyId(cid);
		if (publicNo != null) {
			String authToken = publicNo.getAuthToken();
			return authToken;
		} else {
			log.warn("read publicNo not set authToken!");
			return null;
		}
	}

	/**
	 * 加载所需对象
	 */
	private void loadRequiredSpringBean() {
		if (publicNoService == null)
			publicNoService = SpringContextHolder.getBean(PublicNoService.class);
	}
}
