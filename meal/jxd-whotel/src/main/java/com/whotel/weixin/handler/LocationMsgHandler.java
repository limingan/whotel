package com.whotel.weixin.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.weixin.core.bean.WeixinMsg;
import com.weixin.core.common.WeixinConstant;
import com.weixin.core.handler.AbstractWeixinHandler;

/**
 * 位置消息处理器
 * @author 冯勇
 * 
 */
public class LocationMsgHandler extends AbstractWeixinHandler {
	private static Logger log = LoggerFactory.getLogger(TextMsgHandler.class);

	@Override
	public WeixinMsg handleMsg(WeixinMsg msg) throws Exception {
		if (!WeixinConstant.MsgType.LocationMsg.equals(msg.getMsgType())) {
			return null;
		}
		log.info("〖收到消息〗: " + msg);
		loadRequiredSpringBean();

		// TODO: 要实现根据地理坐标进行消息回复的逻辑，现在只是简单打印坐标
//		LocationMsg lm = (LocationMsg) msg;
//		TextMsg m = new TextMsg();
//		m.setFromUserName(msg.getToUserName());
//		m.setToUserName(msg.getFromUserName());
//		m.setContent(String.format("Label: %s \nlat:%s \nlng:%s \nScale:%s", lm.getLabel(), lm.getLocation_X(), lm.getLocation_Y(), lm.getScale()));
//		log.info("【返回消息】：" + m);
		return null;
	}

	/**
	 * 加载所需对象
	 */
	private void loadRequiredSpringBean() {
		
	}

}
