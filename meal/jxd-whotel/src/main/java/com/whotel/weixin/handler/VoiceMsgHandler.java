package com.whotel.weixin.handler;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.weixin.core.bean.TextMsg;
import com.weixin.core.bean.VoiceMsg;
import com.weixin.core.bean.WeixinMsg;
import com.weixin.core.common.WeixinConstant;
import com.weixin.core.handler.AbstractWeixinHandler;
import com.whotel.card.service.MemberService;
import com.whotel.common.util.SpringContextHolder;
import com.whotel.company.entity.PublicNo;
import com.whotel.company.service.PublicNoService;
import com.whotel.front.service.WeixinFanService;

/**
 * 语音消息处理
 * @author 冯勇
 * 
 */
public class VoiceMsgHandler extends AbstractWeixinHandler {
	private static Logger log = LoggerFactory.getLogger(VoiceMsgHandler.class);
	private MemberService memberService;
	private PublicNoService publicNoService;

	@Override
	public WeixinMsg handleMsg(WeixinMsg msg) throws Exception {
		if (!WeixinConstant.MsgType.VoiceMsg.equals(msg.getMsgType())) {
			return null;
		}
		log.info("〖收到消息〗: " + msg);

		VoiceMsg voiceMsg = (VoiceMsg)msg;
		TextMsg textMsg = new TextMsg();
		textMsg.setFromUserName(msg.getToUserName());
		textMsg.setToUserName(msg.getFromUserName());
		String openId = msg.getFromUserName();
		
		loadRequiredSpringBean();
		PublicNo publicNo = publicNoService.getPublicNoByDeveloperCode(msg.getToUserName());
		
		if(StringUtils.isBlank(textMsg.getContent())){
			TextMsgHandler th = new TextMsgHandler();
			return th.handle(textMsg);
		}

		log.info("【返回消息】：" + textMsg);
		return textMsg;
	}
	
	/**
	 * 加载所需对象
	 */
	private void loadRequiredSpringBean() {
		
		if(memberService == null) {
			memberService = SpringContextHolder.getBean(MemberService.class);
		}
		
		if(weixinFanService == null) {
			weixinFanService = SpringContextHolder.getBean(WeixinFanService.class);
		}
		
		if(publicNoService == null) {
			publicNoService = SpringContextHolder.getBean(PublicNoService.class);
		}
		
	}
}
