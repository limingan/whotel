package com.whotel.weixin.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.weixin.core.api.TokenManager;
import com.weixin.core.common.BaseToken;
import com.whotel.card.entity.Member;
import com.whotel.common.http.HttpHelper;
import com.whotel.common.util.DateUtil;
import com.whotel.common.util.SystemConfig;
import com.whotel.company.entity.Company;
import com.whotel.company.entity.CompanyAdmin;
import com.whotel.company.entity.InterfaceConfig;
import com.whotel.company.entity.PublicNo;
import com.whotel.company.entity.TemplateMessage;
import com.whotel.company.enums.MessageType;
import com.whotel.company.service.CompanyService;
import com.whotel.company.service.InterfaceConfigService;
import com.whotel.company.service.PublicNoService;
import com.whotel.company.service.TemplateMessageService;
import com.whotel.front.entity.PayOrder;
import com.whotel.front.entity.WeixinFan;
import com.whotel.front.service.PayOrderService;
import com.whotel.hotel.entity.HotelComment;
import com.whotel.hotel.entity.HotelOrder;
import com.whotel.hotel.service.HotelOrderService;
import com.whotel.hotel.service.HotelService;
import com.whotel.meal.entity.MealOrder;
import com.whotel.meal.service.MealOrderService;
import com.whotel.system.entity.SysTemplateMsgLog;
import com.whotel.system.service.SystemLogService;
import com.whotel.thirdparty.jxd.api.JXDMemberService;
import com.whotel.thirdparty.jxd.mode.ExchangeGiftQuery;
import com.whotel.thirdparty.jxd.mode.InterfaceListQuery;
import com.whotel.thirdparty.jxd.mode.vo.FollowPolicyVO;
import com.whotel.thirdparty.jxd.mode.vo.HotelVO;
import com.whotel.thirdparty.jxd.mode.vo.InterfaceListVO;
import com.whotel.thirdparty.jxd.mode.vo.MemberVO;
import com.whotel.weixin.controller.WeixinInterfaceController;

import net.sf.json.JSONObject;

/**
 * @author 柯鹏程
 */
@Service
public class WeixinMessageService {

	private static final Logger log = LoggerFactory.getLogger(WeixinMessageService.class);

	@Autowired
	private SystemLogService systemLogService;

	@Autowired
	private WeixinInterfaceService weixinInterfaceService;

	@Autowired
	private TemplateMessageService templateMessageService;

	@Autowired
	private PublicNoService publicNoService;

	@Autowired
	private HotelOrderService hotelOrderService;

	@Autowired
	private CompanyService companyService;

	@Autowired
	private HotelService hotelService;

	@Autowired
	private InterfaceConfigService interfaceConfigService;


	@Autowired
	private MealOrderService mealOrderService;
	
	@Autowired
	protected PayOrderService payOrderService;
	
	/**
	 * 关注礼品通知
	 * @param memberVO
	 * @param vos
	 */
	public void sendFocusGiftsMessage(String openId,String mame){
		String html = "";
		String comid = "";
		try {
			JXDMemberService memberService = new JXDMemberService();
			PublicNo publicNo = publicNoService.getPublicNoByOpenId(openId);
			comid = publicNo.getCompanyId();
			InterfaceConfig interfaceConfig = interfaceConfigService.getEnableInterfaceConfig(comid);
			if(interfaceConfig != null) {
				List<FollowPolicyVO> vos = memberService.followPolicyQyery(interfaceConfig.getHost());
				if(vos==null||vos.size()==0){
					return;
				}
				StringBuffer sb = new StringBuffer("");
				for (FollowPolicyVO vo : vos) {
					sb.append("恭喜您获得:").append(vo.getName()).append("\\n");
				}
				TemplateMessage templateMessage = templateMessageService.findTemplateMessageByType(MessageType.FOCUS_GIFTS,comid);
				if(templateMessage!=null){
					String roomMessage = "{'data':{'first':{'color':'#000000','value':'恭喜您获得了关注礼品'},"
							+ "'keyword1':{'color':'#000000','value':'"+mame+"'},"
							+ "'keyword2':{'color':'#000000','value':'"+sb.toString()+"'},"
							+ "'keyword3':{'color':'#000000','value':'"+DateUtil.getCurrDateStr()+"'},"
							+ "'remark':{'color':'#000000','value':'请注意查收'}},"
							+ "'template_id':'"+templateMessage.getTemplateId()+"',"
							+ "'touser':'"+openId+"','url':'"+SystemConfig.getProperty("server.url")+"/oauth/member/memberCoupon.do?comid="+comid+"'}";
					html = weixinInterfaceService.sendTemplateMessage(roomMessage, openId, publicNo.getAppId(),publicNo.getAppSecret());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			systemLogService.saveTemplateMsgLog(new SysTemplateMsgLog(comid,"关注礼品通知",html,new Object[]{"openId",openId},new Object[]{"mame",mame}));
		}
	}

	/**
	 * 发送客房预订消息
	 * @param orderSn
	 */
	public void sendRoomBookMessage(String orderSn){
		String commpanyId = "";
		String html = "";
		try {
			HotelOrder hotelOrder = hotelOrderService.getHotelOrderByOrderSn(orderSn);
			PublicNo publicNo = publicNoService.getPublicNoByOpenId(hotelOrder.getOpenId());
			TemplateMessage templateMessage = templateMessageService.findTemplateMessageByType(MessageType.ROOM_BOOK,hotelOrder.getCompanyId());
			List<CompanyAdmin> companyAdmins = companyService.findCompanyAdminAll(hotelOrder.getCompanyId(),hotelOrder.getHotelCode());
			InterfaceListQuery query = new InterfaceListQuery();
			Company company = companyService.getCompanyById(publicNo.getCompanyId());
			commpanyId = company.getId();
			query.setCode(company.getCode());
			List<InterfaceListVO> interfaceListVOs = hotelService.interfaceListVO(hotelOrder.getCompanyId(), query);
			String url = "";
			if(interfaceListVOs!=null&&interfaceListVOs.size()>0){
				url = interfaceListVOs.get(0).getMobileAuditUrl()+"?hotelCode="+company.getCode()+"&roomOrderNo="+hotelOrder.getHotelOrderNo();
				System.out.println(url);
			}

			for (CompanyAdmin companyAdmin : companyAdmins) {
				List<MessageType> messageType = companyAdmin.getMessageTypes();
				if(messageType!=null&&messageType.contains(MessageType.ROOM_BOOK)){
					String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
					String orderNo = StringUtils.isNotBlank(hotelOrder.getHotelOrderNo())?hotelOrder.getHotelOrderNo():orderSn;
					String mobile = hotelOrder.getContactMobile();
					String name = hotelOrder.getContactName();
					HotelVO hotelVO = hotelService.getHotelVO(hotelOrder.getCompanyId(),hotelOrder.getHotelCode());
					String roomMessage = "{'data':{'first':{'color':'#000000','value':'您好，您有新的客房订单'},'keyword1':{'color':'#000000','value':'"+orderNo+"'},'keyword2':{'color':'#000000','value':'"+date+"'},'remark':{'color':'#000000','value':'酒店名称："+hotelVO.getcName()+"\\n房型："+hotelOrder.getName()+"\\n总价："+hotelOrder.getTotalFee()+"\\n入住时间："+DateUtil.formatDate(hotelOrder.getCheckInTime()) + " " + hotelOrder.getArriveTime()+"\\n预离时间："+DateUtil.formatDate(hotelOrder.getCheckOutTime()) + " 12:00" +"\\n姓名："+name+"\\n手机："+mobile+"\\n请及时处理'}},'template_id':'"+templateMessage.getTemplateId()+"','touser':'"+companyAdmin.getOpenId()+"','url':'"+url+"'}";
					html += weixinInterfaceService.sendTemplateMessage(roomMessage, companyAdmin.getOpenId(), publicNo.getAppId(),publicNo.getAppSecret());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			systemLogService.saveTemplateMsgLog(new SysTemplateMsgLog(commpanyId,"发送客房预订消息",html,new Object[]{"orderSn",orderSn}));
		}
	}

	/**
	 * 发送门票预订消息
	 * 新订单提醒（门票）
	 */
	
	/**
	 * 发送积分兑换消息
	 * @param company
	 * @param fan
	 * @param score
	 */
	public void sendCreditExchangeMsg(Company company,WeixinFan fan,ExchangeGiftQuery query){
		String commpanyId = company.getId();
		String html = "";
		try {
			PublicNo publicNo = publicNoService.getPublicNoByOpenId(fan.getOpenId());
			List<CompanyAdmin> companyAdmins = companyService.findCompanyAdminAll(commpanyId,query.getResortID());
			TemplateMessage templateMessage = templateMessageService.findTemplateMessageByType(MessageType.EXCHANGE_GIFT,commpanyId);
			for (CompanyAdmin companyAdmin : companyAdmins) {
				List<MessageType> messageType = companyAdmin.getMessageTypes();
				if(messageType!=null&&messageType.contains(MessageType.EXCHANGE_GIFT)){
					String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
					String creditMessage = "{'data':{'first':{'color':'#000000','value':'您好，你有新的积分兑换订单'},"
							+ "'keyword1':{'color':'#000000','value':'"+date+"'},"
							+ "'keyword2':{'color':'#000000','value':'未审核'},"
							+ "'keyword3':{'color':'#000000','value':'"+query.getScore()+"'},"
							+ "'remark':{'color':'#000000','value':'兑换商品："+query.getItemCName()+"\\n兑换数量："+query.getQuantity()
								+"\\n会员卡号："+query.getMbrCardNo()+"\\n联系人："+query.getGetterCName()+"\\n联系方式："+query.getGetterPhone()
								+"\\n请您及时审核'}},'template_id':'"+templateMessage.getTemplateId()+"','touser':'"+companyAdmin.getOpenId()+"','url':''}";
					html += weixinInterfaceService.sendTemplateMessage(creditMessage, companyAdmin.getOpenId(), publicNo.getAppId(),publicNo.getAppSecret());
					systemLogService.saveTemplateMsgLog(new SysTemplateMsgLog(company.getId(),"发送积分兑换消息",html,new Object[]{"fan",fan.toString()},new Object[]{"query",query.toString()}));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			systemLogService.saveTemplateMsgLog(new SysTemplateMsgLog(commpanyId,"发送积分兑换消息",html,new Object[]{"fan",fan.toString()},new Object[]{"query",query.toString()}));
		}
	}

	/**
	 * 取消订单消息 用户
	 * @param weixinFan
	 * @param hotelOrder
	 * @param orderSn
	 */
	public void sendCancelHotelOrderMsgToUser(WeixinFan weixinFan,HotelOrder hotelOrder,String orderSn){
		String commpanyId = "";
		String html = "";
		try {
			PublicNo publicNo = publicNoService.getPublicNoByOpenId(weixinFan.getOpenId());
			commpanyId = hotelOrder.getCompanyId();
			TemplateMessage templateMessage = templateMessageService.findTemplateMessageByType(MessageType.ROOM_BOOK_CANCEL,commpanyId);
			String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
			orderSn = StringUtils.isNotBlank(hotelOrder.getHotelOrderNo())?hotelOrder.getHotelOrderNo():orderSn;
			String roomMessage = "{'data':{'first':{'color':'#000000','value':'您好，您已成功取消了客房订单'},"
					+ "'keyword1':{'color':'#000000','value':'"+orderSn+"'},"
					+ "'keyword2':{'color':'#000000','value':'"+date+"'},"
					+ "'keyword3':{'color':'#000000','value':'"+hotelOrder.getName()+"'},"
					+ "'keyword4':{'color':'#000000','value':'"+hotelOrder.getOrderTotalFee()+"'},"
					+ "'remark':{'color':'#000000','value':'感谢您的预订'}},"
					+ "'template_id':'"+templateMessage.getTemplateId()+"','touser':'"+hotelOrder.getOpenId()+"','url':''}";
			html = weixinInterfaceService.sendTemplateMessage(roomMessage, hotelOrder.getOpenId(), publicNo.getAppId(),publicNo.getAppSecret());
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			systemLogService.saveTemplateMsgLog(new SysTemplateMsgLog(commpanyId,"取消订单消息 用户",html,new Object[]{"weixinFan",weixinFan.toString()},new Object[]{"orderSn",orderSn}));
		}
	}

	/**
	 * 新订单提醒（酒店）  用户
	 */
	public void sendRoomBookMessageByUser(HotelOrder hotelOrder){
		String commpanyId = "";
		String html = "";
		String openId = hotelOrder.getOpenId();
		String orderSn = hotelOrder.getOrderSn();
		try {
			PublicNo publicNo = publicNoService.getPublicNoByOpenId(openId);		
			commpanyId = hotelOrder.getCompanyId();
			TemplateMessage templateMessage = templateMessageService.findTemplateMessageByType(MessageType.ROOM_BOOK,commpanyId);
			String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
			orderSn = StringUtils.isNotBlank(hotelOrder.getHotelOrderNo())?hotelOrder.getHotelOrderNo():orderSn;
			String roomMessage = "{'data':{'first':{'color':'#000000','value':'酒店订单信息'},"
					+ "'keyword1':{'color':'#000000','value':'"+orderSn+"'},"
					+ "'keyword2':{'color':'#000000','value':'"+date+"'},"
					+ "'remark':{'color':'#000000','value':'房型："+hotelOrder.getName()+"\\n总价："+hotelOrder.getTotalFee()
					+"\\n预抵时间："+DateUtil.formatDate(hotelOrder.getCheckInTime())+"之前\\n预离时间："+DateUtil.formatDate(hotelOrder.getCheckOutTime())+"'}},"
					+ "'template_id':'"+templateMessage.getTemplateId()+"','touser':'"+openId+"','url':''}";
			html = weixinInterfaceService.sendTemplateMessage(roomMessage, openId, publicNo.getAppId(),publicNo.getAppSecret());
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			systemLogService.saveTemplateMsgLog(new SysTemplateMsgLog(commpanyId,"新订单提醒 用户",html,new Object[]{"openId",openId},new Object[]{"orderSn",orderSn}));
		}
	}

	/**
	 * 新订单提醒（门票）
	 */

	/**
	 * 酒店订单操作失败
	 */
	public void sendHotelOrderOperationErrorMsg(HotelOrder hotelOrder,String orderSn){
		String commpanyId = "";
		String html = "";
		try {
			PublicNo publicNo = publicNoService.getPublicNoByOpenId(hotelOrder.getOpenId());
			commpanyId = hotelOrder.getCompanyId();
			TemplateMessage templateMessage = templateMessageService.findTemplateMessageByType(MessageType.OPERATION_FAILED,commpanyId);
			orderSn = StringUtils.isNotBlank(hotelOrder.getHotelOrderNo())?hotelOrder.getHotelOrderNo():orderSn;
			List<CompanyAdmin> companyAdmins = companyService.findCompanyAdminAll(hotelOrder.getCompanyId(),hotelOrder.getHotelCode());
			for (CompanyAdmin admin : companyAdmins) {
				List<MessageType> messageType = admin.getMessageTypes();
				if(messageType!=null && messageType.contains(MessageType.OPERATION_FAILED)){
					String roomMessage = "{'data':{'first':{'color':'#000000','value':'酒店订单操作失败'},"
							+ "'keyword1':{'color':'#000000','value':'"+orderSn+"'},"//订单号
							+ "'keyword2':{'color':'#000000','value':'"+hotelOrder.getErrorMsg()+"'}},"//失败原因
							+ "'template_id':'"+templateMessage.getTemplateId()+"','touser':'"+admin.getOpenId()+"','url':''}";
//					html = weixinInterfaceService.sendTemplateMessage(roomMessage, openId, "wx3f43e51f631c3e2b","1cb2b455990805f2905afaeefea476a4");
					html+= weixinInterfaceService.sendTemplateMessage(roomMessage, admin.getOpenId(), publicNo.getAppId(),publicNo.getAppSecret());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			systemLogService.saveTemplateMsgLog(new SysTemplateMsgLog(commpanyId,"酒店订单操作失败",html,new Object[]{"orderSn",orderSn},new Object[]{"errorMsg",hotelOrder.getErrorMsg()}));
		}
	}
	
	/**
	 * 门票订单操作失败
	 */
	
	/**
	 * 商城订单操作失败
	 */

	/**
	 * 取消订单消息  商户
	 * @param weixinFan
	 * @param hotelOrder
	 * @param orderSn
	 */
	public void sendCancelHotelOrderMsgToCompany(WeixinFan weixinFan,HotelOrder hotelOrder,String orderSn){
		String commpanyId = "";
		String html = "";
		try {
			PublicNo publicNo = publicNoService.getPublicNoByOpenId(weixinFan.getOpenId());
			commpanyId = hotelOrder.getCompanyId();
			TemplateMessage templateMessage = templateMessageService.findTemplateMessageByType(MessageType.ROOM_BOOK_CANCEL,commpanyId);
			String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
			List<CompanyAdmin> companyAdmins = companyService.findCompanyAdminAll(hotelOrder.getCompanyId(),hotelOrder.getHotelCode());
			for (CompanyAdmin companyAdmin : companyAdmins) {
				List<MessageType> messageTypes = companyAdmin.getMessageTypes();
				if(messageTypes!=null&&messageTypes.contains(MessageType.ROOM_BOOK)){
					orderSn = StringUtils.isNotBlank(hotelOrder.getHotelOrderNo())?hotelOrder.getHotelOrderNo():orderSn;
					String mobile = hotelOrder.getContactMobile();
					String name = hotelOrder.getContactName();
					HotelVO hotelVO = hotelService.getHotelVO(hotelOrder.getCompanyId(),hotelOrder.getHotelCode());
					String roomMessage = "{'data':{'first':{'color':'#000000','value':'您好，客房取消了订单'},"
							+ "'keyword1':{'color':'#000000','value':'"+orderSn+"'},"
							+ "'keyword2':{'color':'#000000','value':'"+date+"'},"
							+ "'keyword3':{'color':'#000000','value':'"+hotelOrder.getName()+"'},"
							+ "'keyword4':{'color':'#000000','value':'"+hotelOrder.getOrderTotalFee()+"'},"
							+ "'remark':{'color':'#000000','value':'酒店名称："+hotelVO.getcName()+"\\n姓名："+name+"\\n手机："+mobile+"\\n'}},"
							+ "'template_id':'"+templateMessage.getTemplateId()+"','touser':'"+companyAdmin.getOpenId()+"','url':''}";
					html += weixinInterfaceService.sendTemplateMessage(roomMessage, companyAdmin.getOpenId(), publicNo.getAppId(),publicNo.getAppSecret());
					System.out.println(html);
					log.info("sendCancelHotelOrderMsgToCompany="+html);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			systemLogService.saveTemplateMsgLog(new SysTemplateMsgLog(commpanyId,"取消订单消息  商户",html,new Object[]{"weixinFan",weixinFan.toString()},new Object[]{"orderSn",orderSn}));
		}
	}

	/**
	 * 发送会员消费消息
	 * @param openId 
	 * @param productType 消费项目
	 * @param name 消费名称
	 * @param accountType 消费类型
	 * @param account 消费值
	 */
	public void sendMemberConsumerMsg(String openId,String productType,String name,String accountType,Float account,String remark,String url){
		String commpanyId = "";
		String html = "";
		try {
			PublicNo publicNo = publicNoService.getPublicNoByOpenId(openId);
			commpanyId = publicNo.getCompanyId();
			TemplateMessage templateMessage = templateMessageService.findTemplateMessageByType(MessageType.MEMBER_CONSUMER,commpanyId);
			String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
			String roomMessage = "{'data':{'productType':{'color':'#000000','value':'"+productType+"'},"
					+ "'name':{'color':'#000000','value':'"+name+"'},"
					+ "'accountType':{'color':'#000000','value':'"+accountType+"'},"
					+ "'account':{'color':'#000000','value':'"+account+"'},"
					+ "'time':{'color':'#000000','value':'"+date+"'},"
					+ "'remark':{'color':'#000000','value':'"+remark+"'}},"
					+ "'template_id':'"+templateMessage.getTemplateId()+"','touser':'"+openId+"','url':'"+url+"'}";
			html = weixinInterfaceService.sendTemplateMessage(roomMessage, openId, publicNo.getAppId(),publicNo.getAppSecret());
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			systemLogService.saveTemplateMsgLog(new SysTemplateMsgLog(commpanyId,"发送会员消费消息",html,
					new Object[]{"openId",openId},new Object[]{"productType",productType},new Object[]{"name",name},
					new Object[]{"accountType",accountType},new Object[]{"account",account},new Object[]{"remark",remark},new Object[]{"url",url}));
		}
	}

	/**
	 * 取消门票订单  用户
	 */

	/**
	 * 取消门票订单  商户
	 */
	
	/**
	 * 微信客服  商户
	 */
	public Boolean sendWeiXinCustomerServiceMsg(WeixinFan weixinFan,String textMsg){
		boolean bo = false;
		String commpanyId = "";
		String html = "";
		try {
			PublicNo publicNo = publicNoService.getPublicNoByOpenId(weixinFan.getOpenId());
			TemplateMessage templateMessage = templateMessageService.findTemplateMessageByType(MessageType.CUSTOMERSERVICE,publicNo.getCompanyId());
			List<CompanyAdmin> companyAdmins = companyService.findCompanyAdminAll(publicNo.getCompanyId(),null);
			//InterfaceListQuery query = new InterfaceListQuery();
			Company company = companyService.getCompanyById(publicNo.getCompanyId());
			commpanyId = company.getId();
			//query.setCode(company.getCode());
			//String url = "http://www.gshis.net/oauth/cusmsg/cusmsglist.do?comid="+publicNo.getCompanyId();
			String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
			Integer now = Integer.valueOf(date.substring(11, 16).replace(":", ""));
			for (CompanyAdmin companyAdmin : companyAdmins) {
				List<MessageType> messageType = companyAdmin.getMessageTypes();
				if(messageType!=null && messageType.contains(MessageType.CUSTOMERSERVICE) && companyAdmin.getServiceTime(now)){
					bo = true;//在客服时间段内
					String url = SystemConfig.getProperty("server.url")+"/oauth/cusmsg/loadCustomerMessageList.do?comid="+commpanyId;
					String roomMessage = "{'data':{'first':{'color':'#000000','value':'您好，您收到了一个新消息'},"
							+ "'keyword1':{'color':'#000000','value':'"+weixinFan.getNickName()+"'},"
							+ "'keyword2':{'color':'#000000','value':'"+textMsg+"'},"
							+ "'remark':{'color':'#000000','value':'发送时间："+date+"\\n请尽快处理'}},"
							+ "'template_id':'"+templateMessage.getTemplateId()+"','touser':'"+companyAdmin.getOpenId()+"','url':'"+url+"'}";
					html += weixinInterfaceService.sendTemplateMessage(roomMessage, companyAdmin.getOpenId(), publicNo.getAppId(),publicNo.getAppSecret());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			systemLogService.saveTemplateMsgLog(new SysTemplateMsgLog(commpanyId,"发送微信客服消息",html,new Object[]{"openId",weixinFan.getOpenId()},new Object[]{"textMsg",textMsg}));
		}
		return bo;
	}
	
	/**
	 * redis报错通知
	 */
	public void sendRedisError(){
		String commpanyId = "55ed0b51f19aa1122cbd1a7e";
		String openId = "oLI_KjsR4DUKt9gp-m8jYrJyggZQ";
		String html = "";
		boolean isOk = true;
		
		SysTemplateMsgLog sysTemplateMsgLog = systemLogService.getLastSysTemplateMsgLog();
		try {
			long time = new Date().getTime()-sysTemplateMsgLog.getCreateDate().getTime();
			if(time<(1000*60)){
				isOk = false;
				return;
			}
			TemplateMessage templateMessage = templateMessageService.findTemplateMessageByType(MessageType.TICKET_BOOK_CANCEL,commpanyId);
			String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
			String roomMessage = "{'data':{'first':{'color':'#000000','value':'您好，您已成功取消了门票订单'},"
					+ "'keyword1':{'color':'#000000','value':'redis'},"//编号
					+ "'keyword2':{'color':'#000000','value':'"+date+"'},"//时间
					+ "'remark':{'color':'#000000','value':'redis'}},"
					+ "'template_id':'"+templateMessage.getTemplateId()+"','touser':'"+openId+"','url':''}";
			BaseToken baseToken = TokenManager.getBaseToken("wx3f43e51f631c3e2b", "1cb2b455990805f2905afaeefea476a4");
			String url = WeixinInterfaceController.SEND_TEMPLATE_MESSAGE.replace("{$ACCESS_TOKEN}", baseToken.getAccess_token());
			html = HttpHelper.connect(url).post(JSONObject.fromObject(roomMessage).toString()).html();
			System.out.println(html);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(isOk){
				systemLogService.saveTemplateMsgLog(new SysTemplateMsgLog(commpanyId,"redis报错通知",html));
				log.debug("redis报错通知==="+html);
			}
		}
	}
	////////////////////////////////////////////商城模板消息/////////////////////////////////////////////////////////

	/**
	 * 取消商城订单成功  用户
	 */
	
	/**
	 * 取消商城订单  商户
	 */
	
	/**
	 * 商城  新订单提醒（商户）
	 */
	
	/**
	 * 商城  新订单提醒（用户）
	 */
	
	/**
	 * 商城  订单提醒（用户）：提醒用户,商家已发货
	 */
	/**
	 * 到店提醒
	 */
	public void sendToRemindMsg(MemberVO memberVo){
		String html = "";
		String commpanyId = "";
		String openId = memberVo.getWeixinId();
		try {
			PublicNo publicNo = publicNoService.getPublicNoByOpenId(openId);
			commpanyId = publicNo.getCompanyId();
			TemplateMessage templateMessage = templateMessageService.findTemplateMessageByType(MessageType.TO_REMIND,commpanyId);
			List<CompanyAdmin> companyAdmins = companyService.findCompanyAdminAll(commpanyId,null);
			for (CompanyAdmin companyAdmin : companyAdmins) {
				List<MessageType> messageTypes = companyAdmin.getMessageTypes();
				if(messageTypes!=null&&messageTypes.contains(MessageType.TO_REMIND)){
					String roomMessage = "{'data':{'first':{'color':'#000000','value':'您好，您收到一个到店提醒'},"
							+ "'keyword1':{'color':'#000000','value':'"+memberVo.getGuestCName()+"'},"
							+ "'keyword2':{'color':'#000000','value':'"+memberVo.getMobile()+"'},"
							+ "'keyword3':{'color':'#000000','value':'"+memberVo.getAddress()+"'},"
							+ "'keyword4':{'color':'#000000','value':'会员即将到达，请做好准备'},"
							+ "'remark':{'color':'#000000','value':''}},"
							+ "'template_id':'"+templateMessage.getTemplateId()+"','touser':'"+companyAdmin.getOpenId()+"','url':''}";
//					html = weixinInterfaceService.sendTemplateMessage(roomMessage, companyAdmin.getOpenId(), "wx3f43e51f631c3e2b","1cb2b455990805f2905afaeefea476a4");
					html = weixinInterfaceService.sendTemplateMessage(roomMessage,companyAdmin.getOpenId(),publicNo.getAppId(),publicNo.getAppSecret());
					System.out.println(html);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			systemLogService.saveTemplateMsgLog(new SysTemplateMsgLog(commpanyId,"到店提醒",html,new Object[]{"openId",openId}));
		}
	}
	

	/**
	 * 积分消息模板
	 */
	public void sendIntegralSuccessMsg(String openId,String name,String [] args, MemberVO memberVO,String signInId){
		String html = "";
		String comid = "";
		try {
			PublicNo publicNo = publicNoService.getPublicNoByOpenId(openId);
			comid = publicNo.getCompanyId();
			TemplateMessage templateMessage = templateMessageService.findTemplateMessageByType(MessageType.GET_QIANDAO,comid);
			if(templateMessage!=null){
				String remark = "";
				int signInNumber = args[6] == null ? 0 : Integer.parseInt(args[6]);
				if(signInNumber > 0) {
					remark = "连续签到赠送积分： " + signInNumber + "\\n";
				}
				boolean fullBool = Boolean.parseBoolean(args[3]);//是否满签
				if(fullBool) {
					remark += "满签到次数： " + args[5] + "\\n";
					if(Integer.parseInt(args[7]) > 0) {
						remark += "满签到赠送积分： " + args[7] + "\\n";
					}
				}
				
				remark = remark + "连续签到可获得更多积分，不要断签哦！详情请看活动说明";
				
				String roomMessage = "{'data':{'first':{'color':'#000000','value':'您今天已成功签到,获得"+args[8]+"积分。'},"
						+ "'keyword1':{'color':'#000000','value':'成功'},"
						+ "'keyword2':{'color':'#000000','value':'"+args[4]+"'},"
						+ "'keyword3':{'color':'#000000','value':'"+args[9]+"'},"
						+ "'keyword4':{'color':'#000000','value':'"+ (memberVO !=null && memberVO.getValidScore() != null ? memberVO.getValidScore().intValue() : 0 )+"'},"
						+ "'remark':{'color':'#000000','value':'" + remark + "'}},"
						+ "'template_id':'"+templateMessage.getTemplateId()+"','touser':'"+openId+"','url':'"+SystemConfig.getProperty("server.url")+"/oauth/member/toMarketingSignIn.do?signInId="+signInId+"&comid="+comid+"'}";
				html = weixinInterfaceService.sendTemplateMessage(roomMessage, openId, publicNo.getAppId(),publicNo.getAppSecret());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			systemLogService.saveTemplateMsgLog(new SysTemplateMsgLog(comid,"连续签到奖励积分的模板",html,new Object[]{"openId",openId},new Object[]{"name",name}));
		}
	}

	/**
	 * 派劵活动》》优惠劵领取成功
	 */
	public void sendCouponGetSuccessMsg(String openId,String name,String priceName){
		String html = "";
		String comid = "";
//		comid = "55ed0b51f19aa1122cbd1a7e";
//		openId = "oLI_KjkrrRtd8RPJkIiLz17M1XTI";
		try {
			PublicNo publicNo = publicNoService.getPublicNoByOpenId(openId);
			comid = publicNo.getCompanyId();
			TemplateMessage templateMessage = templateMessageService.findTemplateMessageByType(MessageType.GET_COUPON,comid);
			if(templateMessage!=null){
				String roomMessage = "{'data':{'first':{'color':'#000000','value':'优惠劵领取成功通知'},"
						+ "'keyword1':{'color':'#000000','value':'"+name+"'},"
						+ "'keyword2':{'color':'#000000','value':'"+priceName+"'},"
						+ "'keyword3':{'color':'#000000','value':'"+DateUtil.getCurrDateStr()+"'},"
						+ "'remark':{'color':'#000000','value':'请注意查收'}},"
						+ "'template_id':'"+templateMessage.getTemplateId()+"',"
						+ "'touser':'"+openId+"','url':'"+SystemConfig.getProperty("server.url")+"/oauth/member/memberCoupon.do?comid="+comid+"'}";
//				html = weixinInterfaceService.sendTemplateMessage(roomMessage, openId, "wx3f43e51f631c3e2b","1cb2b455990805f2905afaeefea476a4");
//				System.out.println(html);
				html = weixinInterfaceService.sendTemplateMessage(roomMessage, openId, publicNo.getAppId(),publicNo.getAppSecret());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			systemLogService.saveTemplateMsgLog(new SysTemplateMsgLog(comid,"优惠劵领取成功",html,new Object[]{"openId",openId},new Object[]{"name",name}));
		}
	}
	
	////////////////////////////////////////////////餐饮模板消息//////////////////////////////////////////////////////////
	/**
	 * 餐饮预订提醒（商户）
	 */
	public void sendMealOrderMsgToCompany(String orderSn){
		MealOrder mealOrder = mealOrderService.getMealOrderByOrderSn(orderSn);
		String commpanyId = "";
		String html = "";
		String openId = mealOrder.getOpenId();
		//String openId = "oLI_KjkrrRtd8RPJkIiLz17M1XTI";
		try {
			PublicNo publicNo = publicNoService.getPublicNoByOpenId(openId);
			commpanyId = mealOrder.getCompanyId();
			TemplateMessage templateMessage = templateMessageService.findTemplateMessageByType(MessageType.MEAL_BOOK,commpanyId);
			String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
			String arrDate = new SimpleDateFormat("yyyy-MM-dd").format(mealOrder.getArrDate())+" "+mealOrder.getArriveTime();
			String num = mealOrder.getGuestNum()==null?"0":mealOrder.getGuestNum().toString();
			String url = SystemConfig.getProperty("server.url")+"/oauth/meal/showMealOrder.do?comid="+commpanyId+"&orderSn="+orderSn;
			List<CompanyAdmin> companyAdmins = companyService.findCompanyAdminAll(mealOrder.getCompanyId(),mealOrder.getHotelCode());
			for (CompanyAdmin companyAdmin : companyAdmins) {
				List<MessageType> messageTypes = companyAdmin.getMessageTypes();
				if(messageTypes!=null&&messageTypes.contains(MessageType.MEAL_BOOK)){
					String roomMessage = "{'data':{'first':{'color':'#000000','value':'您好，您有新的餐饮订单需要处理：'},"
							+ "'keyword1':{'color':'#000000','value':'"+mealOrder.getContactName()+"'},"//会员名称
							+ "'keyword2':{'color':'#000000','value':'"+mealOrder.getName()+"'},"//餐厅
							+ "'keyword3':{'color':'#000000','value':'"+num+"'},"//人数
							+ "'keyword4':{'color':'#000000','value':'"+date+"'},"//时间
							+ "'keyword5':{'color':'#000000','value':'"+mealOrder.getRemark()+"'},"//特殊要求
							+ "'remark':{'color':'#000000','value':'联系电话："+mealOrder.getContactMobile()+"\\n所属分店："+mealOrder.getRestaurant().getHotelName()+"\\n到店日期："+arrDate+"\\n订单编号："+mealOrder.getOrderSn();
					if(StringUtils.isNotBlank(mealOrder.getMealTabId())){
						roomMessage += "\\n包间名称："+mealOrder.getMealTab().getTabName()+"\\n包间编码："+mealOrder.getMealTab().getTabNo();
					}
					roomMessage += "\\n总价："+mealOrder.getTotalFee()+"\\n请尽快处理'}},"
							+ "'template_id':'"+templateMessage.getTemplateId()+"','touser':'"+companyAdmin.getOpenId()+"','url':'"+url+"'}";					
					//html = weixinInterfaceService.sendTemplateMessage(roomMessage, companyAdmin.getOpenId(), "wx3f43e51f631c3e2b","1cb2b455990805f2905afaeefea476a4");
					html += weixinInterfaceService.sendTemplateMessage(roomMessage, companyAdmin.getOpenId(), publicNo.getAppId(),publicNo.getAppSecret());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			systemLogService.saveTemplateMsgLog(new SysTemplateMsgLog(commpanyId,"餐饮预订提醒（商户）",html,new Object[]{"openId",openId},new Object[]{"orderSn",mealOrder.getOrderSn()}));
		}
	}
	
	/**
	 * 餐饮订单取消通知（商户）
	 * @param orderSn
	 */
	public void sendCancelMealOrderMsgToCompany(String orderSn){
		MealOrder mealOrder = mealOrderService.getMealOrderByOrderSn(orderSn);
		String commpanyId = "";
		String html = "";
		String openId = mealOrder.getOpenId();
		try {
			PublicNo publicNo = publicNoService.getPublicNoByOpenId(openId);
			commpanyId = mealOrder.getCompanyId();
			TemplateMessage templateMessage = templateMessageService.findTemplateMessageByType(MessageType.MEAL_ORDER_CANCEL,commpanyId);
			String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
			String arrDate = new SimpleDateFormat("yyyy-MM-dd").format(mealOrder.getArrDate())+" "+mealOrder.getArriveTime();
			String num = mealOrder.getGuestNum()==null?"0":mealOrder.getGuestNum().toString();
			List<CompanyAdmin> companyAdmins = companyService.findCompanyAdminAll(mealOrder.getCompanyId(),mealOrder.getHotelCode());
			for (CompanyAdmin companyAdmin : companyAdmins) {
				List<MessageType> messageTypes = companyAdmin.getMessageTypes();
				if(messageTypes!=null&&messageTypes.contains(MessageType.MEAL_BOOK)){
					String roomMessage = "{'data':{'first':{'color':'#000000','value':'您好，您已成功的取消了"+mealOrder.getRestaurant().getHotelName()+"的餐饮订单：'},"
							+ "'keyword1':{'color':'#000000','value':'"+mealOrder.getContactName()+"'},"//会员名称
							+ "'keyword2':{'color':'#000000','value':'"+mealOrder.getName()+"'},"//餐厅
							+ "'keyword3':{'color':'#000000','value':'"+num+"'},"//人数
							+ "'keyword4':{'color':'#000000','value':'"+date+"'},"//时间
							+ "'keyword5':{'color':'#000000','value':'"+mealOrder.getRemark()+"'},"//特殊要求
							+ "'remark':{'color':'#000000','value':'到店日期："+arrDate+"\\n订单编号："+mealOrder.getOrderSn();
					if(StringUtils.isNotBlank(mealOrder.getMealTabId())){
						roomMessage += "\\n包间名称："+mealOrder.getMealTab().getTabName();
					}
					roomMessage += "\\n总价："+mealOrder.getTotalFee()+"\\n取消原因："+mealOrder.getCancelReason()+"'}},"
							+ "'template_id':'"+templateMessage.getTemplateId()+"','touser':'"+openId+"','url':''}";
					html += weixinInterfaceService.sendTemplateMessage(roomMessage, companyAdmin.getOpenId(), publicNo.getAppId(),publicNo.getAppSecret());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			systemLogService.saveTemplateMsgLog(new SysTemplateMsgLog(commpanyId,"餐饮订单取消提醒（商户）",html,new Object[]{"openId",openId},new Object[]{"orderSn",mealOrder.getOrderSn()}));
		}
	}
	
	/**
	 * 餐饮订单取消成功（用户）
	 */
	public void sendCancelMealOrderMsgToUser(String orderSn){
		MealOrder mealOrder = mealOrderService.getMealOrderByOrderSn(orderSn);
		String commpanyId = "";
		String html = "";
		String openId = mealOrder.getOpenId();
		//String openId = "oLI_KjkrrRtd8RPJkIiLz17M1XTI";
		try {
			PublicNo publicNo = publicNoService.getPublicNoByOpenId(openId);
			commpanyId = mealOrder.getCompanyId();
			TemplateMessage templateMessage = templateMessageService.findTemplateMessageByType(MessageType.MEAL_ORDER_CANCEL,commpanyId);
			String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
			String arrDate = new SimpleDateFormat("yyyy-MM-dd").format(mealOrder.getArrDate())+" "+mealOrder.getArriveTime();
			String num = mealOrder.getGuestNum()==null?"0":mealOrder.getGuestNum().toString();
			String url = SystemConfig.getProperty("server.url")+"/oauth/meal/showMealOrder.do?comid="+commpanyId+"&orderSn="+orderSn;
			String roomMessage = "{'data':{'first':{'color':'#000000','value':'您好，您已成功的取消了"+mealOrder.getRestaurant().getHotelName()+"的餐饮订单：'},"
					+ "'keyword1':{'color':'#000000','value':'"+mealOrder.getContactName()+"'},"//会员名称
					+ "'keyword2':{'color':'#000000','value':'"+mealOrder.getName()+"'},"//餐厅
					+ "'keyword3':{'color':'#000000','value':'"+num+"'},"//人数
					+ "'keyword4':{'color':'#000000','value':'"+date+"'},"//时间
					+ "'keyword5':{'color':'#000000','value':'"+mealOrder.getRemark()+"'},"//特殊要求
					+ "'remark':{'color':'#000000','value':'到店日期："+arrDate+"\\n订单编号："+mealOrder.getOrderSn();
			if(StringUtils.isNotBlank(mealOrder.getMealTabId())){
				roomMessage += "\\n包间名称："+mealOrder.getMealTab().getTabName();
			}
			roomMessage += "\\n总价："+mealOrder.getTotalFee()+"\\n感谢您的预订'}},"
					+ "'template_id':'"+templateMessage.getTemplateId()+"','touser':'"+openId+"','url':'"+url+"'}";
			
			//html = weixinInterfaceService.sendTemplateMessage(roomMessage, openId, "wx3f43e51f631c3e2b","1cb2b455990805f2905afaeefea476a4");
			html += weixinInterfaceService.sendTemplateMessage(roomMessage, openId, publicNo.getAppId(),publicNo.getAppSecret());
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			systemLogService.saveTemplateMsgLog(new SysTemplateMsgLog(commpanyId,"餐饮订单取消成功（用户）",html,new Object[]{"openId",openId},new Object[]{"orderSn",mealOrder.getOrderSn()}));
		}
	}
	
	/**
	 * 餐饮预订消息(用户)
	 */
	public void sendMealMessageToUser(MealOrder mealOrder,Boolean isSuccess) {
		String html = "";
		String comid = "";
		String openId = mealOrder.getOpenId();
		String success = "";
		try {
			MessageType messageType = null;
			if(Boolean.FALSE.equals(isSuccess)){//失败
				messageType = MessageType.MEAL_BOOK_FAIL;
				success = "失败";
			}else{
				messageType = MessageType.MEAL_BOOK_SUCCESS;
				success = "成功";
			}
			
			PublicNo publicNo = publicNoService.getPublicNoByOpenId(openId);
			comid = publicNo.getCompanyId();
			TemplateMessage templateMessage = templateMessageService.findTemplateMessageByType(messageType,comid);
			String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
			String arrDate = new SimpleDateFormat("yyyy-MM-dd").format(mealOrder.getArrDate())+" "+mealOrder.getArriveTime();
			String num = mealOrder.getGuestNum()==null?"0":mealOrder.getGuestNum().toString();
			String url = SystemConfig.getProperty("server.url")+"/oauth/meal/showMealOrder.do?comid="+comid+"&orderSn="+mealOrder.getOrderSn();
			
			String roomMessage = "{'data':{'first':{'color':'#000000','value':'您好，您的"+mealOrder.getRestaurant().getHotelName()+"的餐饮预订"+success+"！'},"
					+ "'keyword1':{'color':'#000000','value':'"+mealOrder.getContactName()+"'},"//会员名称
					+ "'keyword2':{'color':'#000000','value':'"+mealOrder.getName()+"'},"//餐厅
					+ "'keyword3':{'color':'#000000','value':'"+num+"'},"//人数
					+ "'keyword4':{'color':'#000000','value':'"+date+"'},"//时间
					+ "'keyword5':{'color':'#000000','value':'"+mealOrder.getRemark()+"'},"//特殊要求
					+ "'remark':{'color':'#000000','value':'总价："+mealOrder.getTotalFee()+"\\n到店日期："+arrDate+"\\n订单编号："+mealOrder.getOrderSn();
			if(StringUtils.isNotBlank(mealOrder.getMealTabId())){
				roomMessage += "\\n包间名称："+mealOrder.getMealTab().getTabName();//+"\\n包间编码："+mealOrder.getMealTab().getTabNo();
			}
			if(Boolean.FALSE.equals(isSuccess)){//失败
				roomMessage += "\\n失败原因："+mealOrder.getErrorMsg();
			}
			roomMessage += "\\n感谢您的预订'}},"
					+ "'template_id':'"+templateMessage.getTemplateId()+"','touser':'"+openId+"','url':'"+url+"'}";					
			html = weixinInterfaceService.sendTemplateMessage(roomMessage.replace("templateId", templateMessage.getTemplateId()),openId,publicNo.getAppId(),publicNo.getAppSecret());
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			systemLogService.saveTemplateMsgLog(new SysTemplateMsgLog(comid,"餐饮预定消息(用户)",html,new Object[]{"orderSn",mealOrder.getOrderSn()},new Object[]{"openId",openId},new Object[]{"isSuccess",success}));
		}
	}
	
	/**
	 * 卡券兑换提醒（商户）
	 */
	public void sendConsumeCardMsgToCompany(String code,String mobile,String openId){
		String commpanyId = "";
		String html = "";
//		String openId = "oLI_KjkrrRtd8RPJkIiLz17M1XTI";
		try {
			PublicNo publicNo = publicNoService.getPublicNoByOpenId(openId);
			commpanyId = publicNo.getCompanyId();
			TemplateMessage templateMessage = templateMessageService.findTemplateMessageByType(MessageType.CONSUME_CARD,commpanyId);
			String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
			List<CompanyAdmin> companyAdmins = companyService.findCompanyAdminAll(commpanyId,null);
			for (CompanyAdmin companyAdmin : companyAdmins) {
				List<MessageType> messageTypes = companyAdmin.getMessageTypes();
				if(messageTypes!=null&&messageTypes.contains(MessageType.CONSUME_CARD)){
					String roomMessage = "{'data':{'first':{'color':'#000000','value':'您好，您有新的卡券兑换需要处理：'},"
							+ "'keyword1':{'color':'#000000','value':'"+code+"'},"//编号
							+ "'keyword2':{'color':'#000000','value':'"+date+"'},"//时间
							+ "'remark':{'color':'#000000','value':'手机："+mobile+"\\n请尽快处理'}},"
							+ "'template_id':'"+templateMessage.getTemplateId()+"','touser':'"+companyAdmin.getOpenId()+"','url':''}";					
//					html = weixinInterfaceService.sendTemplateMessage(roomMessage, companyAdmin.getOpenId(), "wx3f43e51f631c3e2b","1cb2b455990805f2905afaeefea476a4");
					html += weixinInterfaceService.sendTemplateMessage(roomMessage, companyAdmin.getOpenId(), publicNo.getAppId(),publicNo.getAppSecret());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			systemLogService.saveTemplateMsgLog(new SysTemplateMsgLog(commpanyId,"卡券兑换提醒（商户）",html,new Object[]{"openId",openId},new Object[]{"code",code},new Object[]{"mobile",mobile}));
		}
	}
	
	/**
	 * 预订订单使用提醒
	 */
	public void sendUseRemind(HotelOrder hotelOrder){
		String commpanyId = "";
		String html = "";
		String openId = hotelOrder.getOpenId();
//		openId = "oLI_KjkrrRtd8RPJkIiLz17M1XTI";
		try {
			PublicNo publicNo = publicNoService.getPublicNoByOpenId(openId);
			commpanyId = hotelOrder.getCompanyId();
			TemplateMessage templateMessage = templateMessageService.findTemplateMessageByType(MessageType.USE_REMIND,commpanyId);
			String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + " " + hotelOrder.getArriveTime();
			String roomMessage = "{'data':{'first':{'color':'#000000','value':'您好，您预定了今天"+hotelOrder.getArriveTime()+"的客房：'},"
					+ "'keyword1':{'color':'#000000','value':'"+hotelOrder.getOrderSn()+"'},"//订单号
					+ "'keyword2':{'color':'#000000','value':'"+date+"'},"//日期
					+ "'remark':{'color':'#000000','value':'请注意时间安排入住'}},"
					+ "'template_id':'"+templateMessage.getTemplateId()+"','touser':'"+openId+"','url':''}";					
			//html = weixinInterfaceService.sendTemplateMessage(roomMessage, openId, "wx3f43e51f631c3e2b","1cb2b455990805f2905afaeefea476a4");
			html += weixinInterfaceService.sendTemplateMessage(roomMessage, openId, publicNo.getAppId(),publicNo.getAppSecret());
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			systemLogService.saveTemplateMsgLog(new SysTemplateMsgLog(commpanyId,"预订订单使用提醒（用户）",html,new Object[]{"openId",openId},new Object[]{"orderSn",hotelOrder.getOrderSn()}));
		}
	}
	
	/**
	 * 微信会员注册成功提醒
	 */
	public void sendUseRemindLoginSuccess(MemberVO vo){
		String commpanyId = "";
		String html = "";
		String openId = vo.getWeixinId();
//		openId = "oLI_KjkrrRtd8RPJkIiLz17M1XTI";
		try {
			PublicNo publicNo = publicNoService.getPublicNoByOpenId(openId);
			commpanyId = publicNo.getCompanyId();
			TemplateMessage templateMessage = templateMessageService.findTemplateMessageByType(MessageType.MEMBER_REGISTERED,commpanyId);
			String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
			String roomMessage = "{'data':{'first':{'color':'#000000','value':'您好，您已注册成为"+vo.getMbrCardTypeName()+"会员'},"
					+ "'keyword1':{'color':'#000000','value':'"+vo.getMbrCardNo()+"'},"//会员号
					+ "'keyword2':{'color':'#000000','value':'"+vo.getGuestCName()+"'},"//会员名
					+ "'keyword3':{'color':'#000000','value':'"+vo.getMobile()+"'},"//手机号
					+ "'remark':{'color':'#000000','value':'注册时间:"+date+"\\n会员卡有效期:"+vo.getMbrExpired()+"\\n请尽快登录'}},"
					+ "'template_id':'"+templateMessage.getTemplateId()+"','touser':'"+openId+"','url':''}";				
			//html = weixinInterfaceService.sendTemplateMessage(roomMessage, openId, "wx3f43e51f631c3e2b","1cb2b455990805f2905afaeefea476a4");
			html += weixinInterfaceService.sendTemplateMessage(roomMessage, openId, publicNo.getAppId(),publicNo.getAppSecret());
			System.out.println(html);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			systemLogService.saveTemplateMsgLog(new SysTemplateMsgLog(commpanyId," 微信会员注册成功提醒(会员)",html,new Object[]{"openId",openId},new Object[]{"mobile",vo.getMobile()}));
		}
	}
	
	/**
	 * 微信会员注册失败提醒
	 */
//	public void sendUseRemindLoginFail(MemberVO vo){
//		String commpanyId = "";
//		String html = "";
//		String openId = vo.getWeixinId();
////		openId = "oLI_KjkrrRtd8RPJkIiLz17M1XTI";
//		try {
//			PublicNo publicNo = publicNoService.getPublicNoByOpenId(openId);
//			commpanyId = publicNo.getCompanyId();
//			TemplateMessage templateMessage = templateMessageService.findTemplateMessageByType(MessageType.MEMBER_REGISTERED_FAIL,commpanyId);
//			String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
//			String roomMessage = "{'data':{'first':{'color':'#000000','value':'您好，您注册失败'},"
//					+ "'keyword1':{'color':'#000000','value':'"+vo.getGuestCName()+"'},"//会员名
//					+ "'keyword2':{'color':'#000000','value':'"+vo.getMobile()+"'},"//手机号
//					+ "'keyword3':{'color':'#000000','value':'信息有误'},"//手机号
//					+ "'remark':{'color':'#000000','value':'请重新提交审核，谢谢！'}},"
//					+ "'template_id':'"+templateMessage.getTemplateId()+"','touser':'"+openId+"','url':''}";				
//			//html = weixinInterfaceService.sendTemplateMessage(roomMessage, openId, "wx3f43e51f631c3e2b","1cb2b455990805f2905afaeefea476a4");
//			html += weixinInterfaceService.sendTemplateMessage(roomMessage, openId, publicNo.getAppId(),publicNo.getAppSecret());
//			System.out.println(html);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}finally {
//			systemLogService.saveTemplateMsgLog(new SysTemplateMsgLog(commpanyId," 微信会员注册失败提醒(用户注册)",html,new Object[]{"openId",openId},new Object[]{"mobile",vo.getMobile()}));
//		}
//	}
	
	 /*
	  *  点评模板消息（商户）
	  */
	public void sendCommentToCompany(HotelComment hotelComment,HotelOrder hotelOrder){
		String commpanyId = "";
		String html = "";
		String openId = hotelComment.getOpenId();
//		String openId = "oLI_KjkrrRtd8RPJkIiLz17M1XTI";
		try {
			PublicNo publicNo = publicNoService.getPublicNoByOpenId(openId);
			commpanyId = hotelComment.getCompanyId();
			TemplateMessage templateMessage = templateMessageService.findTemplateMessageByType(MessageType.COMMENT_MESSAGE,commpanyId);
			String date = new SimpleDateFormat("yyyy-MM-dd").format(hotelOrder.getCreateTime());
			List<CompanyAdmin> companyAdmins = companyService.findCompanyAdminAll(commpanyId,null);
			for (CompanyAdmin companyAdmin : companyAdmins) {
				List<MessageType> messageTypes = companyAdmin.getMessageTypes();
				if (messageTypes != null && messageTypes.contains(MessageType.ROOM_BOOK)) {
					String roomMessage = "{'data':{'first':{'color':'#000000','value':'您有一条新的点评'},"
							+ "'keyword1':{'color':'#000000','value':'" + hotelOrder.getOrderSn() + "'},"// 订单编号
							+ "'keyword2':{'color':'#000000','value':'" + date + "'},"// 订单时间
							+ "'remark':{'color':'#000000','value':'用户姓名:"+hotelOrder.getGuestName()+"\\n消费类型:"+hotelOrder.getName()+"\\n点评内容:"+hotelComment.getContent()+"\\n请尽快处理'}}," 
							+ "'template_id':'"
							+ templateMessage.getTemplateId() + "','touser':'" + companyAdmin.getOpenId()
							+ "','url':''}";
					html += weixinInterfaceService.sendTemplateMessage(roomMessage, companyAdmin.getOpenId(),
							publicNo.getAppId(), publicNo.getAppSecret());
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			systemLogService.saveTemplateMsgLog(new SysTemplateMsgLog(commpanyId,"点评消息提醒（商户）",html,new Object[]{"openId",openId},new Object[]{"orderSn", hotelComment.getOrderSn()}));
		}
	}
	
	/**
	 * 用户支付成功通知（商户）
	 */
	public void sendPaySuccessToCompany(String orderSn){
		PayOrder order = payOrderService.getPayOrderByOrderSn(orderSn);
		String commpanyId = "";
		String html = "";
		if(order != null){
			String openId = order.getOpenId();
			try {
				PublicNo publicNo = publicNoService.getPublicNoByCompanyId(order.getCompanyId());
				commpanyId = publicNo.getCompanyId();
				TemplateMessage templateMessage = templateMessageService.findTemplateMessageByType(MessageType.PAY_SUCCESS,commpanyId);
				String date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
				List<CompanyAdmin> companyAdmins = companyService.findCompanyAdminAll(commpanyId,null);
				for (CompanyAdmin companyAdmin : companyAdmins) {
					List<MessageType> messageTypes = companyAdmin.getMessageTypes();
					if(messageTypes!=null&&messageTypes.contains(MessageType.PAY_SUCCESS)){
						String roomMessage = "{'data':{'first':{'color':'#000000','value':'你好，顾客已支付成功：'},"
								+ "'keyword1':{'color':'#000000','value':'"+(order.getTotalFee()*1.0/100)+"'},"//付款金额
								+ "'keyword2':{'color':'#000000','value':'"+order.getOrderSn()+"'},"//交易单号
								+ "'remark':{'color':'#000000','value':'时间："+date+"\\n请尽快处理'}},"
								+ "'template_id':'"+templateMessage.getTemplateId()+"','touser':'"+companyAdmin.getOpenId()+"','url':''}";					
//						html = weixinInterfaceService.sendTemplateMessage(roomMessage, companyAdmin.getOpenId(), "wx3f43e51f631c3e2b","1cb2b455990805f2905afaeefea476a4");
						html += weixinInterfaceService.sendTemplateMessage(roomMessage, companyAdmin.getOpenId(), publicNo.getAppId(),publicNo.getAppSecret());
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				systemLogService.saveTemplateMsgLog(new SysTemplateMsgLog(commpanyId,"用户支付成功通知（商户）",html,new Object[]{"openId",openId},new Object[]{"orderSn",order.getOrderSn()}));
			}
		}
	}
}
