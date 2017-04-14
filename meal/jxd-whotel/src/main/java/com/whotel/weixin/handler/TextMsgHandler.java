package com.whotel.weixin.handler;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.weixin.core.bean.TextMsg;
import com.weixin.core.bean.WeixinMsg;
import com.weixin.core.common.WeixinConstant;
import com.weixin.core.handler.AbstractWeixinHandler;
import com.whotel.common.util.SpringContextHolder;
import com.whotel.common.util.SystemConfig;
import com.whotel.company.entity.MatchKeyword;
import com.whotel.company.entity.MatchKeywordRule;
import com.whotel.company.entity.PublicNo;
import com.whotel.company.entity.ResponseMsg;
import com.whotel.company.service.MatchKeywordRuleService;
import com.whotel.company.service.PublicNoService;
import com.whotel.front.entity.WeixinFan;
import com.whotel.front.service.WeixinFanService;
import com.whotel.weixin.service.ResponseMsgService;
import com.whotel.weixin.service.WeixinMessageService;

/**
 * 文本消息处理器
 * @author 冯勇
 * 
 */
public class TextMsgHandler extends AbstractWeixinHandler {
	
	private static Logger log = LoggerFactory.getLogger(TextMsgHandler.class);
	
	private MatchKeywordRuleService matchKeywordRuleService;
	
	private PublicNoService publicNoService;
	
	private ResponseMsgService responseMsgService;
	
	private WeixinMessageService weixinMessageService;
	
	/**
	 * 处理消息入口
	 */
	@Override
	public WeixinMsg handleMsg(WeixinMsg msg) throws Exception {
		long beginTime = System.currentTimeMillis();
		// 如果不属于自己的类型，则返回null，下一个处理器处理
		if (!WeixinConstant.MsgType.TextMsg.equals(msg.getMsgType())) {
			return null;
		}
		loadRequiredSpringBean();
		log.info("〖收到消息〗: " + msg);
		
		TextMsg textMsg = (TextMsg)msg;
		String content = textMsg.getContent();
		
		WeixinFan weixinFan = weixinFanService.getWeixinFanByOpenId(msg.getFromUserName());
		
		WeixinMsg replyMsg = getReplyByKeyword(textMsg);
		
		if(StringUtils.contains(content, "人工客服") || StringUtils.contains(content, "人工服务")) {
			replyMsg = new WeixinMsg();
			replyMsg.setCreateTime(msg.getCreateTime());
			replyMsg.setMsgType("transfer_customer_service");
			log.info("【多客服转接】：" + replyMsg.toXml());
		}
		
		if(replyMsg != null) {
			replyMsg.setFromUserName(msg.getToUserName());
			replyMsg.setToUserName(msg.getFromUserName());
			log.info("【返回消息】：" + replyMsg.toXml());
		}
		long endTime = System.currentTimeMillis();
		System.out.println("handleMsg--------------"+(endTime-beginTime));
		return replyMsg;
	}

	/**
	 * 匹配自动回复关键字
	 * @param textMsg
	 * @return
	 */
	private WeixinMsg getReplyByKeyword(TextMsg textMsg) {
		long beginTime = System.currentTimeMillis();
		String developerCode = textMsg.getToUserName();
		final String openId = textMsg.getFromUserName();
		final String content = textMsg.getContent();
		ResponseMsg responseMsg = null;
		ResponseMsg defaultResponseMsg = null;
		PublicNo publicNo = publicNoService.getPublicNoByDeveloperCode(developerCode);
		long endTime = 0l;
		if(publicNo != null) {
			
			WeixinFan fan = weixinFanService.updateFocus(openId, developerCode, true);
			readUserMsg(publicNo, fan);
			endTime = System.currentTimeMillis();
			System.out.println("更新微信粉信息用时："+(endTime-beginTime));
			beginTime = endTime;
			List<MatchKeywordRule> keywordRules = matchKeywordRuleService.findMatchKeywordRules(publicNo.getCompanyId());
			if(keywordRules != null) {
				for(MatchKeywordRule rule:keywordRules) {
					if (rule.getDef() != null && rule.getDef()) {
						defaultResponseMsg = rule.getResponseMsg(); // 记住默认回复消息
					} else {
						List<MatchKeyword> keywords = rule.getKeywords();
						MatchKeyword keyword = null;
						// 用传来的消息内容逐个比对规则中的每个关键词，如果发现则中断循环
						for (MatchKeyword matchKeyword : keywords) {
							if (matchKeyword.isMatch(content)) {
								responseMsg = rule.getResponseMsg();
								keyword = matchKeyword;
								break;
							}
						}
						
						if(keyword != null) {
							keyword.setCount(keyword.getCount() + 1);         //使用次数统计
							matchKeywordRuleService.saveMatchKeywordRule(rule);
							break;
						}
					}
				}
			}
			endTime = System.currentTimeMillis();
			System.out.println("匹配关键字用时："+(endTime-beginTime));
			beginTime = endTime;
		}
		
		// 1.关键词命中，返回相应的消息
		if (responseMsg != null) {
			return responseMsgService.toWeixinMsg(responseMsg, openId);
		}
		
		Thread thread = new Thread(new Runnable() {
			public void run() {
				WeixinFan fan = weixinFanService.getWeixinFanByOpenId(openId);
				weixinMessageService.sendWeiXinCustomerServiceMsg(fan, content);//bo为false》》不会发送模板消息
			}
		});
		thread.start();
		
		endTime = System.currentTimeMillis();
		System.out.println("发送模板消息用时："+(endTime-beginTime));
		beginTime = endTime;
		
		endTime = System.currentTimeMillis();
		System.out.println("保存客服消息用时："+(endTime-beginTime));
		beginTime = endTime;
		
//		if(defaultResponseMsg != null){//不在客服时间内》》默认消息
//			return responseMsgService.toWeixinMsg(defaultResponseMsg, openId);
//		}
		return null;
	}

	/**
	 * 加载所需对象
	 */
	private void loadRequiredSpringBean() {
		if (matchKeywordRuleService == null) {
			matchKeywordRuleService = SpringContextHolder.getBean(MatchKeywordRuleService.class);
		}
		
		if(publicNoService == null) {
			publicNoService = SpringContextHolder.getBean(PublicNoService.class);
		}
		
		if(responseMsgService == null) {
			responseMsgService = SpringContextHolder.getBean(ResponseMsgService.class);
		}
		
		if(weixinFanService == null) {
			weixinFanService = SpringContextHolder.getBean(WeixinFanService.class);
		}
		
		if(weixinMessageService == null) {
			weixinMessageService = SpringContextHolder.getBean(WeixinMessageService.class);
		}
	}
}
