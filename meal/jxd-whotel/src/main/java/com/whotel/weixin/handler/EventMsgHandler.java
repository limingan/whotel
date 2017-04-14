package com.whotel.weixin.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.weixin.core.bean.EventMsg;
import com.weixin.core.bean.TextMsg;
import com.weixin.core.bean.WeixinMsg;
import com.weixin.core.common.WeixinConstant;
import com.weixin.core.common.WeixinConstant.EventType;
import com.weixin.core.handler.AbstractWeixinHandler;
import com.whotel.common.util.SpringContextHolder;
import com.whotel.company.entity.MenuItem;
import com.whotel.company.entity.PublicNo;
import com.whotel.company.entity.ResponseMsg;
import com.whotel.company.entity.WelcomeMsg;
import com.whotel.company.service.MenuItemService;
import com.whotel.company.service.PublicNoService;
import com.whotel.front.entity.WeixinFan;
import com.whotel.front.service.WeixinFanService;
import com.whotel.system.entity.SysSubscribeLog;
import com.whotel.system.service.SystemLogService;
import com.whotel.weixin.service.LocationService;
import com.whotel.weixin.service.ResponseMsgService;
import com.whotel.weixin.service.SceneService;

/**
 * 事件消息处理器
 * @author 冯勇
 * 
 */
public class EventMsgHandler extends AbstractWeixinHandler {
	
	private static Logger log = LoggerFactory.getLogger(EventMsgHandler.class);
	
	private MenuItemService menuItemservice;
	
	private PublicNoService publicNoService;
	
	private ResponseMsgService responseMsgService;
	
	private SceneService sceneService;
	
	private SystemLogService systemLogService;
	
	@Override
	public WeixinMsg handleMsg(WeixinMsg msg) throws Exception {
		if (!WeixinConstant.MsgType.EventMsg.equals(msg.getMsgType())) {
			return null;
		}
		loadRequiredSpringBean();
		log.info("〖收到消息〗: " + msg);
		
		EventMsg eventMsg = (EventMsg)msg;
		WeixinMsg reponseMsg = null;
		if (eventMsg.getEvent().equals(EventType.Click)) {
			reponseMsg = handleClick(eventMsg);
		} else if (eventMsg.getEvent().equals(EventType.Subscribe) || eventMsg.getEvent().equals(EventType.SCAN)) {
			systemLogService.saveSubscribeLog(new SysSubscribeLog(eventMsg));
			reponseMsg = handleSub(eventMsg);
		} else if (eventMsg.getEvent().equals(EventType.Unsubscribe)) {
			handleUnsub(eventMsg);
			return null;
		} else if (eventMsg.getEvent().equals(EventType.LOCATION)) {
			// TODO: 待分析
			LocationService.getLocationService().recordFanLocation(eventMsg);
			return null;
		} else if(eventMsg.getEvent().equals(EventType.GETCARD)) {
			handleGetCard(eventMsg);
			return null;
		}  else if(eventMsg.getEvent().equals(EventType.DELCARD)) {
			return null;
		} else if(eventMsg.getEvent().equals(EventType.CARDPASS) || eventMsg.getEvent().equals(EventType.CARDNOTPASS)) {
			handleCardStatus(eventMsg);
			return null;
		}else if(eventMsg.getEvent().equals(EventType.SCANCODE_PUSH)){
			return null;
		}
		if (reponseMsg != null) {
			reponseMsg.setFromUserName(msg.getToUserName());
			reponseMsg.setToUserName(msg.getFromUserName());
			log.info("【返回消息】：" + reponseMsg);
		}
		return reponseMsg;
	}
	
	/**
	 * 处理自定义菜单点击事件
	 * @param e
	 * @return
	 */
	private WeixinMsg handleClick(EventMsg e) {
		String openId = e.getFromUserName();
		String devCode = e.getToUserName();
		MenuItem mi =menuItemservice.getMenuItemById(e.getEventKey());
		
		PublicNo publicNo = publicNoService.getPublicNoByDeveloperCode(devCode);
		WeixinFan fan = weixinFanService.updateFocus(openId, devCode, true);
		readUserMsg(publicNo, fan);
		
		if (mi != null) {
			ResponseMsg responseMsg = mi.getResponseMsg();
			if (responseMsg != null) {
				WeixinMsg msg = responseMsgService.toWeixinMsg(responseMsg, openId);
				return msg;
			}
		}
		log.error("点击处理失败，请检查自定义菜单及关联资源！");
		TextMsg m = new TextMsg();
		m.setContent("菜单响应出问题啦，请稍后重试！");
		return m;
	}

	/**
	 * <pre>
	 * 处理关注事件
	 */
	private WeixinMsg handleSub(EventMsg e) {
		String openId = e.getFromUserName();
		String devCode = e.getToUserName();
		PublicNo publicNo = publicNoService.getPublicNoByDeveloperCode(devCode);
		if (publicNo == null)
			return null;
		long bt = System.currentTimeMillis();
		WeixinFan fan = weixinFanService.updateFocus(openId, devCode, true);
		long et = System.currentTimeMillis();
		System.out.println("updateFocus"+(et-bt));
		bt = et;
		
		readUserMsg(publicNo, fan);
		et = System.currentTimeMillis();
		System.out.println("readUserMsg"+(et-bt));
		bt = et;
		
		WelcomeMsg welcomeMsg = publicNoService.getWelcomeMsg(publicNo.getCompanyId());
		et = System.currentTimeMillis();
		System.out.println("getWelcomeMsg"+(et-bt));
		bt = et;
		
		// 返回"被添加自动回复"消息
		WeixinMsg responseMsg=null;
		if(welcomeMsg != null){
			responseMsg = responseMsgService.toWeixinMsg(welcomeMsg.getResponseMsg(), openId);
		}
		et = System.currentTimeMillis();
		System.out.println("toWeixinMsg"+(et-bt));
		bt = et;
		
		//场景处理
		responseMsg = sceneService.handleScene(e, responseMsg);
		et = System.currentTimeMillis();
		System.out.println("handleScene"+(et-bt));
		bt = et;
		return responseMsg;
	}
	
	private void handleGetCard(EventMsg e) {
	
	}
	
	private void handleCardStatus(EventMsg e) {
		
	}
	
	/**
	 * <pre>
	 * 处理取消关注事件
	 * @param e
	 * @return
	 */
	private WeixinMsg handleUnsub(EventMsg e) {
		String openId = e.getFromUserName();
		String devCode = e.getToUserName();
		weixinFanService.updateFocus(openId, devCode, false);
		
		sceneService.handleScene(e, null);
		return null;
	}
	
	/**
	 * 加载所需对象
	 */
	private void loadRequiredSpringBean() {
		if (menuItemservice == null) {
			menuItemservice = SpringContextHolder.getBean(MenuItemService.class);
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
		
		if(sceneService == null) {
			sceneService = SpringContextHolder.getBean(SceneService.class);
		}
		if(systemLogService == null) {
			systemLogService = SpringContextHolder.getBean(SystemLogService.class);
		}
	}

}
