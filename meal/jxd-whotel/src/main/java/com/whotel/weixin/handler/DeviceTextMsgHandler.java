package com.whotel.weixin.handler;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.weixin.core.bean.DeviceTextMsg;
import com.weixin.core.bean.WeixinMsg;
import com.weixin.core.common.WeixinConstant;
import com.weixin.core.handler.AbstractWeixinHandler;
import com.whotel.common.util.SpringContextHolder;
import com.whotel.company.entity.PublicNo;
import com.whotel.company.service.PublicNoService;
import com.whotel.hotel.service.UnlockService;

/**
 * 位置消息处理器
 * @author 冯勇
 * 
 */
public class DeviceTextMsgHandler extends AbstractWeixinHandler {
	private static Logger log = LoggerFactory.getLogger(TextMsgHandler.class);

	private PublicNoService publicNoService;
	
	private UnlockService unlockService;
	
	@Override
	public WeixinMsg handleMsg(WeixinMsg msg) throws Exception {
		if (!WeixinConstant.MsgType.DeviceText.equals(msg.getMsgType())) {
			return null;
		}
		log.info("〖收到消息〗: " + msg);
		
//		DeviceTextMsg deviceTextMsg = (DeviceTextMsg)msg;
//		String content = deviceTextMsg.getContent();
//		String msgText = "";
//		if(content.length()>20){
//			msgText = "门已开,欢迎光临！";
//		}
//		if(StringUtils.isNotBlank(msgText)){
//			loadRequiredSpringBean();
//			PublicNo publicNo = publicNoService.getPublicNoByDeveloperCode(deviceTextMsg.getToUserName());
//			unlockService.sendTextMessage(deviceTextMsg.getFromUserName(), msgText, publicNo.getAppId(), publicNo.getAppSecret());
//		}
		return null;
	}
	
	/**
	 * 加载所需对象
	 */
	private void loadRequiredSpringBean() {
		if(publicNoService == null) {
			publicNoService = SpringContextHolder.getBean(PublicNoService.class);
		}
		if(unlockService == null) {
			unlockService = SpringContextHolder.getBean(UnlockService.class);
		}
	}

}
