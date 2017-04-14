package com.whotel.weixin.controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.weixin.core.common.BaseToken;
import com.whotel.card.service.MemberService;
import com.whotel.common.base.Constants;
import com.whotel.common.base.controller.BaseController;
import com.whotel.common.http.HttpHelper;
import com.whotel.common.http.HttpHelper.Response;
import com.whotel.common.util.SystemConfig;
import com.whotel.company.entity.Company;
import com.whotel.company.entity.CompanyAdmin;
import com.whotel.company.entity.PublicNo;
import com.whotel.company.entity.TemplateMessage;
import com.whotel.company.enums.MessageType;
import com.whotel.company.enums.ModuleType;
import com.whotel.company.service.CompanyService;
import com.whotel.company.service.PublicNoService;
import com.whotel.company.service.TemplateMessageService;
import com.whotel.system.entity.SysTemplateMsgLog;
import com.whotel.system.service.SystemLogService;
import com.whotel.thirdparty.jxd.mode.vo.MemberVO;
import com.whotel.weixin.service.TokenService;
import com.whotel.weixin.service.WeixinInterfaceService;
import com.whotel.weixin.service.WeixinMessageService;

import net.sf.json.JSONObject;

/**
 * @author 柯鹏程
 *
 */
@Controller
@RequestMapping("/weixinInterface")
public class WeixinInterfaceController extends BaseController {
	
	/** 发送模板消息 */
	public static final String SEND_TEMPLATE_MESSAGE = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token={$ACCESS_TOKEN}";
	
	private static final Logger log = Logger.getLogger(WeixinInterfaceController.class);
	
	@Autowired
	private SystemLogService systemLogService;
	
	@Autowired
	private PublicNoService publicNoService;
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private CompanyService companyService;
	
	@Autowired
	private WeixinMessageService weixinMessageService;
	
	@Autowired
	WeixinInterfaceService weixinInterfaceService;
	
	@Autowired
	TemplateMessageService templateMessageService;
	
	/**
	 * 发送会员消费消息
	 * @param openId 
	 * @param productType 消费项目
	 * @param name 消费名称
	 * @param accountType 消费类型
	 * @param account 消费值
	 */
	@RequestMapping("/sendMemberConsumerMsg")
	public void sendMemberConsumerMsg(String roomMessage,String openId,HttpServletResponse res){
		String commpanyId = "";
		String html = "";
		//roomMessage = "{'data':{'productType':{'color':'#000000','value':'消费类型'},'name':{'color':'#000000','value':'【消费类型】'},'accountType':{'color':'#000000','value':'金额'},'account':{'color':'#000000','value':'【消费金额】'},'time':{'color':'#000000','value':'【消费时间】'},'remark':{'color':'#000000','value':'剩余金额：【剩余金额】'}},'template_id':'templateId','touser':'"+openId+"','url':''}";
		try {
			PublicNo publicNo = publicNoService.getPublicNoByOpenId(openId);
			commpanyId = publicNo.getCompanyId();
			TemplateMessage templateMessage = templateMessageService.findTemplateMessageByType(MessageType.MEMBER_CONSUMER,commpanyId);
			html = weixinInterfaceService.sendTemplateMessage(roomMessage.replace("templateId", templateMessage.getTemplateId()), openId, publicNo.getAppId(),publicNo.getAppSecret());
			res.getWriter().write(html);
		} catch (Exception e) {
			e.printStackTrace();
			try {
				log.error("roomMessage="+roomMessage+" openId="+openId);
				if(e.getCause()==null){
					res.getWriter().write(e.getMessage());
				}else{
					res.getWriter().write(e.getCause().getMessage());
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}finally {
			systemLogService.saveTemplateMsgLog(new SysTemplateMsgLog(commpanyId,"发送会员消费消息（线下）",html,new Object[]{"openId",openId},new Object[]{"roomMessage",roomMessage}));
		}
	}
	
	/**
	 * 客房预定消息
	 * @param roomMessage
	 * @param openId
	 * @param res
	 */
	@RequestMapping("/sendRoomMessage")
	public void sendRoomMessage(String roomMessage,String openId,Boolean isSuccess,HttpServletResponse res) {
		String html = "";
		String comid = "";
		try {
			PublicNo publicNo = publicNoService.getPublicNoByOpenId(openId);
			comid = publicNo.getCompanyId();
			MessageType messageType = isSuccess?MessageType.ROOM_BOOK_SUCCESS:MessageType.ROOM_BOOK_FAIL;
			TemplateMessage templateMessage = templateMessageService.findTemplateMessageByType(messageType,comid);
			html = weixinInterfaceService.sendTemplateMessage(roomMessage.replace("templateId", templateMessage.getTemplateId()),openId,publicNo.getAppId(),publicNo.getAppSecret());
			res.getWriter().write(html);
		} catch (Exception e) {
			e.printStackTrace();
			try {
				log.error("roomMessage="+roomMessage+" openId="+openId);
				if(e.getCause()==null){
					res.getWriter().write(e.getMessage());
				}else{
					res.getWriter().write(e.getCause().getMessage());
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}finally {
			systemLogService.saveTemplateMsgLog(new SysTemplateMsgLog(comid,"客房预定消息",html,new Object[]{"roomMessage",roomMessage},new Object[]{"openId",openId},
					new Object[]{"isSuccess",isSuccess}));
		}
	}
	
	/**
	 * 客房取消订单
	 */
	@RequestMapping("/sendCancelRoomMessage")
	public void sendCancelRoomMessage(String roomMessage,String openId,HttpServletResponse res) {
		String html = "";
		String comid = "";
		try {
			PublicNo publicNo = publicNoService.getPublicNoByOpenId(openId);
			comid = publicNo.getCompanyId();
			MessageType messageType = MessageType.ROOM_BOOK_CANCEL;
			TemplateMessage templateMessage = templateMessageService.findTemplateMessageByType(messageType,comid);
			html = weixinInterfaceService.sendTemplateMessage(roomMessage.replace("templateId", templateMessage.getTemplateId()),openId,publicNo.getAppId(),publicNo.getAppSecret());
			res.getWriter().write(html);
		} catch (Exception e) {
			e.printStackTrace();
			try {
				log.error("roomMessage="+roomMessage+" openId="+openId);
				if(e.getCause()==null){
					res.getWriter().write(e.getMessage());
				}else{
					res.getWriter().write(e.getCause().getMessage());
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}finally {
			systemLogService.saveTemplateMsgLog(new SysTemplateMsgLog(comid,"客房预定消息",html,new Object[]{"roomMessage",roomMessage},new Object[]{"openId",openId}));
		}
	}
	
	/**
	 * 积分兑换消息
	 * @param roomMessage
	 * @param openId
	 * @param res
	 */
	@RequestMapping("/sendCreditMessage")
	public void sendCreditMessage(String creditMessage,String openId,Boolean isSuccess,HttpServletResponse res) {
		String html = "";
		String comid = "";
		try {
			PublicNo publicNo = publicNoService.getPublicNoByOpenId(openId);
			MessageType messageType = isSuccess?MessageType.CREDIT_EXCHANGE_SUCCESS:MessageType.CREDIT_EXCHANGE_FAIL;
			comid = publicNo.getCompanyId();
			TemplateMessage templateMessage = templateMessageService.findTemplateMessageByType(messageType,comid);
			html = weixinInterfaceService.sendTemplateMessage(creditMessage.replace("templateId", templateMessage.getTemplateId()),openId,publicNo.getAppId(),publicNo.getAppSecret());
			res.getWriter().write(html);
		} catch (Exception e) {
			e.printStackTrace();
			try {
				if(e.getCause()==null){
					res.getWriter().write(e.getMessage());
				}else{
					res.getWriter().write(e.getCause().getMessage());
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}finally {
			systemLogService.saveTemplateMsgLog(new SysTemplateMsgLog(comid,"积分兑换消息",html,new Object[]{"creditMessage",creditMessage},new Object[]{"openId",openId},
					new Object[]{"isSuccess",isSuccess}));
		}
	}
	/**
	 * 门票预定消息
	 * @param roomMessage
	 * @param openId
	 * @param res
	 */
	@RequestMapping("/sendTicketMessage")
	public void sendTicketMessage(String roomMessage,String openId,Boolean isSuccess,HttpServletResponse res) {
		String html = "";
		String comid = "";
		try {
			PublicNo publicNo = publicNoService.getPublicNoByOpenId(openId);
			comid = publicNo.getCompanyId();
			MessageType messageType = isSuccess?MessageType.TICKET_BOOK_SUCCESS:MessageType.TICKET_BOOK_FAIL;
			TemplateMessage templateMessage = templateMessageService.findTemplateMessageByType(messageType,comid);
			html = weixinInterfaceService.sendTemplateMessage(roomMessage.replace("templateId", templateMessage.getTemplateId()),openId,publicNo.getAppId(),publicNo.getAppSecret());
			res.getWriter().write(html);
		} catch (Exception e) {
			e.printStackTrace();
			try {
				log.error("roomMessage="+roomMessage+" openId="+openId);
				if(e.getCause()==null){
					res.getWriter().write(e.getMessage());
				}else{
					res.getWriter().write(e.getCause().getMessage());
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}finally {
			systemLogService.saveTemplateMsgLog(new SysTemplateMsgLog(comid,"门票预定消息",html,new Object[]{"roomMessage",roomMessage},new Object[]{"openId",openId},
					new Object[]{"isSuccess",isSuccess}));
		}
	}
	/**
	 * 到店提醒
	 * @throws IOException 
	 * @throws ServletException 
	 */
	@RequestMapping(value="/sendToRemindMsg",produces="application/json; charset=utf-8")
	@ResponseBody
	public String sendToRemindMsg(String comid,HttpServletRequest req,HttpServletResponse res) throws IOException{
		String openId = (String) req.getSession().getAttribute(Constants.Session.WEIXINFAN_LOGIN_OPENID);
		Company company = companyService.getCompanyById(comid);
		MemberVO memberVo = memberService.getMemberVOByOpenId(company.getId(), openId, company.getCode());
		if(memberVo != null){
			memberVo.setWeixinId(openId);
			weixinMessageService.sendToRemindMsg(memberVo);
		}else{
			res.sendRedirect("/oauth/member/toBindMember.do?toRemindMsg="+URLEncoder.encode("到店提醒必须注册会员才能使用", "UTF8")); 
		}
		return "发送成功";
	}
	
	/**
	 * 离店发送优惠劵
	 */
	@RequestMapping("/sendCheckOutCouponMsg")
	public void sendCheckOutCouponMsg(String roomMessage,String openId,HttpServletResponse res){
		String commpanyId = "";
		String html = "";
		//roomMessage = "{'data':{'productType':{'color':'#000000','value':'消费类型'},'name':{'color':'#000000','value':'【消费类型】'},'accountType':{'color':'#000000','value':'金额'},'account':{'color':'#000000','value':'【消费金额】'},'time':{'color':'#000000','value':'【消费时间】'},'remark':{'color':'#000000','value':'剩余金额：【剩余金额】'}},'template_id':'templateId','touser':'"+openId+"','url':''}";
		try {
			PublicNo publicNo = publicNoService.getPublicNoByOpenId(openId);
			commpanyId = publicNo.getCompanyId();
			TemplateMessage templateMessage = templateMessageService.findTemplateMessageByType(MessageType.CHECK_OUT,commpanyId);
			html = weixinInterfaceService.sendTemplateMessage(roomMessage.replace("templateId", templateMessage.getTemplateId()), openId, publicNo.getAppId(),publicNo.getAppSecret());
			res.getWriter().write("【【【【【【【【【【【【【【【【【"+html+"】】】】】】】】】】】】】】】】】】");
		} catch (Exception e) {
			e.printStackTrace();
			try {
				log.error("roomMessage="+roomMessage+" openId="+openId);
				if(e.getCause()==null){
					res.getWriter().write(e.getMessage());
				}else{
					res.getWriter().write(e.getCause().getMessage());
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}finally {
			systemLogService.saveTemplateMsgLog(new SysTemplateMsgLog(commpanyId,"离店发送优惠劵",html,new Object[]{"openId",openId},new Object[]{"roomMessage",roomMessage}));
		}
	}
	
	/**
	 * 单据信息通知
	 */
	@RequestMapping("/sendBillsInfoMsg")
	public void sendBillsInfoMsg(String roomMessage,String openId,HttpServletResponse res){
		String commpanyId = "";
		String html = "";
		try {
			PublicNo publicNo = publicNoService.getPublicNoByOpenId(openId);
			commpanyId = publicNo.getCompanyId();
			String url = SystemConfig.getProperty("server.url")+"/oauth/toOauthInterface.do?comid="+commpanyId+"&code=sendBillsInfoMsg&";
			html = weixinInterfaceService.sendTemplateMessage(roomMessage.replace("URL", url), openId, publicNo.getAppId(),publicNo.getAppSecret());
			res.getWriter().write(html);
		} catch (Exception e) {
			e.printStackTrace();
			try {
				log.error("roomMessage="+roomMessage+" openId="+openId);
				if(e.getCause()==null){
					res.getWriter().write(e.getMessage());
				}else{
					res.getWriter().write(e.getCause().getMessage());
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}finally {
			systemLogService.saveTemplateMsgLog(new SysTemplateMsgLog(commpanyId,"单据信息通知",html,new Object[]{"openId",openId},new Object[]{"roomMessage",roomMessage}));
		}
	}
	
	/**
	 * 套餐成功预定消息
	 * @param roomMessage
	 * @param openId
	 * @param res
	 */
	@RequestMapping("/sendComboMessage")
	public void sendComboMessage(String roomMessage,String openId,HttpServletResponse res) {
		String html = "";
		String comid = "";
		try {
			PublicNo publicNo = publicNoService.getPublicNoByOpenId(openId);
			comid = publicNo.getCompanyId();
			TemplateMessage templateMessage = templateMessageService.findTemplateMessageByType(MessageType.COMBO_BOOK_SUCCESS,comid);
			html = weixinInterfaceService.sendTemplateMessage(roomMessage.replace("templateId", templateMessage.getTemplateId()),openId,publicNo.getAppId(),publicNo.getAppSecret());
			res.getWriter().write(html);
		} catch (Exception e) {
			e.printStackTrace();
			try {
				log.error("roomMessage="+roomMessage+" openId="+openId);
				if(e.getCause()==null){
					res.getWriter().write(e.getMessage());
				}else{
					res.getWriter().write(e.getCause().getMessage());
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}finally {
			systemLogService.saveTemplateMsgLog(new SysTemplateMsgLog(comid,"套餐成功预定消息",html,new Object[]{"roomMessage",roomMessage},new Object[]{"openId",openId}));
		}
	}
	
	public static void main(String[] args) {
		
	}
}
