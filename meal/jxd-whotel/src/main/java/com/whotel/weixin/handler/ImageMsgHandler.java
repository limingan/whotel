package com.whotel.weixin.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.weixin.core.bean.ImageMsg;
import com.weixin.core.bean.WeixinMsg;
import com.weixin.core.common.WeixinConstant;
import com.weixin.core.handler.AbstractWeixinHandler;
import com.whotel.common.util.SpringContextHolder;
import com.whotel.front.entity.WeixinFan;
import com.whotel.front.service.WeixinFanService;

/**
 * 图片消息处理器
 * @author 冯勇
 * 
 */
@Component
public class ImageMsgHandler extends AbstractWeixinHandler {
	private static Logger log = LoggerFactory.getLogger(ImageMsgHandler.class);

	@Override
	public WeixinMsg handleMsg(WeixinMsg msg) throws Exception {
		if (!WeixinConstant.MsgType.ImageMsg.equals(msg.getMsgType())) {
			return null;
		}
		log.info("〖收到图片消息〗: " + msg);
		
		ImageMsg imageMsg = (ImageMsg)msg;
		
//		imageMsg.setFromUserName(msg.getToUserName());
//		imageMsg.setToUserName(msg.getFromUserName());
		
		loadRequiredSpringBean();
		WeixinFan weixinFan = weixinFanService.getWeixinFanByOpenId(imageMsg.getFromUserName());
		return null;
	}
	
	/**
	 * 加载所需对象
	 */
	private void loadRequiredSpringBean() {
		if(weixinFanService == null) {
			weixinFanService = SpringContextHolder.getBean(WeixinFanService.class);
		}
	}
}
