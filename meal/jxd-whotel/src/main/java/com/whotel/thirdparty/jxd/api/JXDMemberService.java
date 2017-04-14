package com.whotel.thirdparty.jxd.api;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.DocumentException;

import com.whotel.card.entity.Member;
import com.whotel.card.entity.SendCouponRecord;
import com.whotel.card.service.CouponTypeService;
import com.whotel.common.enums.Gender;
import com.whotel.common.http.HttpHelper;
import com.whotel.common.http.HttpHelper.Response;
import com.whotel.common.util.DateUtil;
import com.whotel.common.util.NameUtil;
import com.whotel.common.util.SpringContextHolder;
import com.whotel.company.entity.Company;
import com.whotel.company.entity.InterfaceConfig;
import com.whotel.company.entity.MbrInterfaceConfig;
import com.whotel.company.service.InterfaceConfigService;
import com.whotel.system.entity.SysMemberLog;
import com.whotel.system.service.SystemLogService;
import com.whotel.thirdparty.jxd.ApiException;
import com.whotel.thirdparty.jxd.JXDConstants;
import com.whotel.thirdparty.jxd.mode.BuyMbrCard;
import com.whotel.thirdparty.jxd.mode.CategoryCodeQuery;
import com.whotel.thirdparty.jxd.mode.ChargeMoneyQuery;
import com.whotel.thirdparty.jxd.mode.ExchangeGiftDetailQuery;
import com.whotel.thirdparty.jxd.mode.ExchangeGiftListQuery;
import com.whotel.thirdparty.jxd.mode.ExchangeGiftQuery;
import com.whotel.thirdparty.jxd.mode.FollowPolicyQuery;
import com.whotel.thirdparty.jxd.mode.InterfaceListQuery;
import com.whotel.thirdparty.jxd.mode.MbrCardBinding;
import com.whotel.thirdparty.jxd.mode.MbrCardQuery;
import com.whotel.thirdparty.jxd.mode.MbrCardTypeQuery;
import com.whotel.thirdparty.jxd.mode.MbrCardUpgrade;
import com.whotel.thirdparty.jxd.mode.MbrCardUpgradeQuery;
import com.whotel.thirdparty.jxd.mode.MemberCanUseScoreQuery;
import com.whotel.thirdparty.jxd.mode.MemberCouponQuery;
import com.whotel.thirdparty.jxd.mode.MemberPointsQuery;
import com.whotel.thirdparty.jxd.mode.MemberPointsQueryExchange;
import com.whotel.thirdparty.jxd.mode.MemberProfileUpdate;
import com.whotel.thirdparty.jxd.mode.MemberQuery;
import com.whotel.thirdparty.jxd.mode.MemberTrade;
import com.whotel.thirdparty.jxd.mode.MemberTradeQuery;
import com.whotel.thirdparty.jxd.mode.MemberTypeQuery;
import com.whotel.thirdparty.jxd.mode.MobileSmsQuery;
import com.whotel.thirdparty.jxd.mode.SendCouponQuery;
import com.whotel.thirdparty.jxd.mode.UseCouponQuery;
import com.whotel.thirdparty.jxd.mode.vo.CategoryCodeVO;
import com.whotel.thirdparty.jxd.mode.vo.ChargeMoneyVO;
import com.whotel.thirdparty.jxd.mode.vo.ExchangeGiftVO;
import com.whotel.thirdparty.jxd.mode.vo.FollowPolicyVO;
import com.whotel.thirdparty.jxd.mode.vo.GeneralMsg;
import com.whotel.thirdparty.jxd.mode.vo.InterfaceListVO;
import com.whotel.thirdparty.jxd.mode.vo.MbrCardTypeVO;
import com.whotel.thirdparty.jxd.mode.vo.MbrCardUpgradeResult;
import com.whotel.thirdparty.jxd.mode.vo.MbrCardUpgradeVO;
import com.whotel.thirdparty.jxd.mode.vo.MbrCardVO;
import com.whotel.thirdparty.jxd.mode.vo.MemberCouponVO;
import com.whotel.thirdparty.jxd.mode.vo.MemberTradeVO;
import com.whotel.thirdparty.jxd.mode.vo.MemberTypeVO;
import com.whotel.thirdparty.jxd.mode.vo.MemberVO;
import com.whotel.thirdparty.jxd.mode.vo.PointRecordVO;
import com.whotel.thirdparty.jxd.util.ApiXmlVoParser;
import com.whotel.thirdparty.jxd.util.JxdXmlUtils;

/**
 * 会员接口对接
 * @author 冯勇
 */
public class JXDMemberService {
	private static final Logger log = Logger.getLogger(JXDMemberService.class);
	
	private SystemLogService systemLogService = SpringContextHolder.getBean(SystemLogService.class);
	
	private CouponTypeService couponTypeService = SpringContextHolder.getBean(CouponTypeService.class);
	
	private InterfaceConfigService interfaceConfigService = SpringContextHolder.getBean(InterfaceConfigService.class);
	

	/**
	 * 注册会员
	 * @param member
	 * @return
	 */
	public boolean sendSms(String mobile, String content, String time) {
		MobileSmsQuery query = new MobileSmsQuery();
		query.setColumn1(content);
		query.setColumn2(time);
		query.setColumn3(mobile);
		
		String param = JxdXmlUtils.toXml(query);
		if(log.isDebugEnabled()) {
			log.debug("param: \n" + param);
		}

		// 请求接口并获得响应
		Response res;
		try {
			res = HttpHelper.connect(JXDConstants.API_GATEWAY).header("Content-Type", "text/xml")
					.timeout(JXDConstants.TIMEOUT).post(param);
			ApiXmlVoParser.checkReturnContent(res);
			return true;
		} catch (ApiException e) {
			log.error("发送短信信息失败");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 注册会员
	 * @param member
	 * @return
	 */
	public MemberVO registerMember(MemberVO member,Company company,String action) {
		Map<String, String> params = new HashMap<String, String>();
		if(Boolean.TRUE.equals(company.getMultipleMbr()) && !StringUtils.equals("save", action)){
			params.put("Action", "New");
		}else if(StringUtils.isNotBlank(member.getProfileId())) {
			params.put("Action", "MODIFY");
		} else {
			params.put("Action", "Add");
		}
		
		params.put("ProfileId", member.getProfileId());
		params.put("GuestCName", member.getGuestCName());
		String[] pinyinName = NameUtil.convertPinyinName(member.getGuestCName());
		params.put("GuestEName", pinyinName[0] + " " + pinyinName[1]);
		params.put("CertificateNo", member.getCertificateNo());
		if (Gender.MALE.equals(member.getGender())) {
			params.put("Gender", "M");
		} else if (Gender.FEMALE.equals(member.getGender())) {
			params.put("Gender", "F");
		}
		params.put("Birthday", member.getBirthday());
		params.put("Address", member.getAddress());
		// params.put("ZipCode", "");
		// params.put("Tel", "");
		// params.put("OfficeTel", "");
		params.put("Mobile", member.getMobile());
		// params.put("Fax", "");
		// params.put("Email", "");
		// params.put("Remark", "");
		params.put("CardPassword", member.getPwd());
		params.put("OtherKeyWord", member.getWeixinId());
		params.put("OtherName", "微信");
		params.put("Introducer", member.getIntroducer());
		params.put("NotSendMsg", (member.getIsSendMsg()==null||member.getIsSendMsg()==true)?"0":"1");//0发送1不发送
		return registerMember(params,company.getCode());
	}
	
	public static void main(String[] args) {
		Boolean isSend = null;
		System.out.println(Boolean.TRUE.equals(isSend));
	}
	
	/**
	 * 注册会员
	 * 
	 * @param params
	 * @throws Exception
	 */
	private MemberVO registerMember(Map<String, String> params,String hotelCode) {
		log.info("Invoke JXD api registerMember...");

		MemberProfileUpdate update = new MemberProfileUpdate();
		update.setProfileUpdate(params);

		String param = JxdXmlUtils.toXml(update);
		if(log.isDebugEnabled()) {
			log.debug("param: \n" + param);
		}

		// 请求接口并获得响应
		Response res = null;
		MemberVO memberVO = null;
		String html = "";
		String url = getUrl(hotelCode, update.getOpType());
		try {
			
			res = HttpHelper.connect(url).header("Content-Type", "text/xml")
					.timeout(JXDConstants.TIMEOUT*6).post(param);
			ApiXmlVoParser.checkReturnContent(res);
			html += res.html();
			memberVO = ApiXmlVoParser.parseMemberVO(res.html(), res.charset());
		} catch (ApiException e) {
			if(e.getCause()==null){
				html += e.getMessage();
			}else{
				html += e.getCause().getMessage();
			}
			log.error("注册会员信息失败");
		} catch (Exception e) {
			if(e.getCause()==null){
				html += e.getMessage();
			}else{
				html += e.getCause().getMessage();
			}
			e.printStackTrace();
		}finally{
			try {
				//记录会员注册
				systemLogService.saveMemberLog(new SysMemberLog(hotelCode,"registerMember",(memberVO==null?"失败":"成功"),new String[]{"url",url},new String[]{"param",param},new String[]{"html",html}));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return memberVO;
	}
	
	/**
	 * 查询会员卡类型查询
	 * @param company
	 * @return
	 */
	public List<MbrCardTypeVO> findMbrCardTypeVO(MbrCardTypeQuery query,String hotelCode){
		log.info("Invoke JXD api findMbrCardTypeVO...");

		String param = JxdXmlUtils.toXml(query);
		if(log.isDebugEnabled()) {
			log.debug("param: \n" + param);
		}

		List<MbrCardTypeVO> mctVos = null;
		// 请求接口并获得响应
		Response res = null;
		try {
			String url = getUrl(hotelCode, query.getOpType());
			
			res = HttpHelper.connect(url).header("Content-Type", "text/xml")
					.timeout(JXDConstants.TIMEOUT).post(param);
			ApiXmlVoParser.checkReturnContent(res);
			
			mctVos = ApiXmlVoParser.parseMbrCardTypeVOs(res.html(), res.charset());
		} catch (ApiException e) {
			log.error("会员卡类型查询失败");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mctVos;
	}
	
	/**
	 * 购买会员卡
	 */
	public GeneralMsg buyMbrCard(BuyMbrCard buyMbrCard,String hotelCode) throws Exception{
		log.info("Invoke JXD api buyMbrCard...");
		
		String param = JxdXmlUtils.toXml(buyMbrCard);
		if(log.isDebugEnabled()) {
			log.debug("param: \n" + param);
		}
		
		String url = getUrl(hotelCode, buyMbrCard.getOpType());
		
		Response res = HttpHelper.connect(url).header("Content-Type", "text/xml")
				.timeout(JXDConstants.TIMEOUT).post(param);
		return ApiXmlVoParser.parseGeneralMsg(res.html(), res.charset());
	}
	
	/**
	 * 会员卡绑定
	 * @throws Exception 
	 */
	public GeneralMsg mbrCardBinding(MbrCardBinding mbrCardBinding,String hotelCode) {
		log.info("Invoke JXD api MbrCardBinding...");
		
		String param = JxdXmlUtils.toXml(mbrCardBinding);
		if(log.isDebugEnabled()) {
			log.debug("param: \n" + param);
		}
		
		Response res = null;
		String url = getUrl(hotelCode, mbrCardBinding.getOpType());
		
		try {
			
			res = HttpHelper.connect(url).header("Content-Type", "text/xml")
				.timeout(JXDConstants.TIMEOUT).post(param);
			ApiXmlVoParser.checkReturnContent(res);
		
			return ApiXmlVoParser.parseGeneralMsg(res.html(), res.charset());
		} catch (Exception e) {
			e.printStackTrace();
			try {
				log.error(param+"  res.html()"+res==null?"":res.html());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		return null;
	}
	
	/**
	 * 会员卡查询
	 * @param query
	 * @param hotelCode
	 * @return code=Profileid cname=Mobile extraValue=MbrCardNo
	 * @throws Exception
	 */
	public List<CategoryCodeVO> getMbrCardByNo(CategoryCodeQuery query,String hotelCode) throws Exception{
		log.info("Invoke JXD api MbrCardBinding...");
		
		String param = JxdXmlUtils.toXml(query);
		if(log.isDebugEnabled()) {
			log.debug("param: \n" + param);
		}
		
		String url = getUrl(hotelCode, query.getOpType());
		
		Response res = HttpHelper.connect(url).header("Content-Type", "text/xml")
				.timeout(JXDConstants.TIMEOUT).post(param);
		ApiXmlVoParser.checkReturnContent(res);
		 
		return ApiXmlVoParser.parseCategoryCodeVOs(res.html(), res.charset());
	}
	
	/**
	 * 会员储值卡查询
	 */
	public List<MbrCardVO> findMbrCardQuery(MbrCardQuery query,String hotelCode){
		log.info("Invoke JXD api findMbrCardQuery...");

		String param = JxdXmlUtils.toXml(query);
		if(log.isDebugEnabled()) {
			log.debug("param: \n" + param);
		}

		List<MbrCardVO> mcVos = null;
		// 请求接口并获得响应
		Response res = null;
		try {
			String url = getUrl(hotelCode, query.getOpType());
			
			res = HttpHelper.connect(url).header("Content-Type", "text/xml")
					.timeout(JXDConstants.TIMEOUT).post(param);
			ApiXmlVoParser.checkReturnContent(res);
			
			mcVos = ApiXmlVoParser.parseMbrCardVOs(res.html(), res.charset());
		} catch (ApiException e) {
			log.error("会员卡类型查询失败");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mcVos;
	}
	
	/**
	 * 会员卡升级
	 * @throws Exception 
	 */
	public MbrCardUpgradeResult mbrCardUpgrade(MbrCardUpgrade query,String hotelCode) throws Exception {
		log.info("Invoke JXD api mbrCardUpgrade...");
		
		String param = JxdXmlUtils.toXml(query);
		if(log.isDebugEnabled()) {
			log.debug("param: \n" + param);
		}
		
		MbrCardUpgradeResult result = null;
		String url = getUrl(hotelCode, query.getOpType());
		String html = "";
		try{
			Response res = HttpHelper.connect(url).header("Content-Type", "text/xml")
					.timeout(JXDConstants.TIMEOUT).post(param);
			ApiXmlVoParser.checkReturnContent(res);
			html += res.html();
			result = ApiXmlVoParser.parseMbrCardUpgradeResult(html, res.charset());
		}catch(Exception e){
			if(e.getCause()==null){
				html += e.getMessage();
			}else{
				html += e.getCause().getMessage();
			}
			e.printStackTrace();
		}finally{
			try {
				//记录会员卡升级
				systemLogService.saveMemberLog(new SysMemberLog(hotelCode,"mbrCardUpgrade",(result==null?"失败":"成功"),new String[]{"url",url},new String[]{"param",param},new String[]{"html",html}));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	/**
	 * 会员卡升级列表
	 * @throws Exception 
	 */
	public List<MbrCardUpgradeVO> listMbrCardUpgrade(MbrCardUpgradeQuery query,String hotelCode) throws Exception{
		log.info("Invoke JXD api listMbrCardUpgrade...");
		
		String param = JxdXmlUtils.toXml(query);
		if(log.isDebugEnabled()) {
			log.debug("param: \n" + param);
		}
		
		String url = getUrl(hotelCode, query.getOpType());
		
		Response res = HttpHelper.connect(url).header("Content-Type", "text/xml")
				.timeout(JXDConstants.TIMEOUT).post(param);
		ApiXmlVoParser.checkReturnContent(res);
		
		List<MbrCardUpgradeVO> cards = ApiXmlVoParser.parseMbrCardUpgradeVOs(res.html(), res.charset());;
		return cards;
	}
	/**
	 * 获取会员
	 * @param keyword 登录关键字
	 * @param pwd 登录密码，可以为空
	 * @return
	 * @throws Exception
	 */
	public MemberVO getMemberByMobile(String mobile, String pwd,String hotelCode) {
		log.info("Invoke JXD api getMember...");

		MemberQuery mbrQry = new MemberQuery();
		Map<String, String> map = new HashMap<String, String>();
		mbrQry.setMbrQuery(map);
		map.put("Mobile", mobile);
		map.put("Pwd", pwd);
		String param = JxdXmlUtils.toXml(mbrQry);
		if(log.isDebugEnabled()) {
			log.debug("param: \n" + param);
		}

		// 请求接口并获得响应
		MemberVO memberVO = null;
		try {
			String url = getUrl(hotelCode, mbrQry.getOpType());
			
			Response res = HttpHelper.connect(url).header("Content-Type", "text/xml")
					.timeout(JXDConstants.TIMEOUT).post(param);
			ApiXmlVoParser.checkReturnContent(res);

			memberVO = ApiXmlVoParser.parseMemberVO(res.html(), res.charset());
		} catch (ApiException e) {
			log.error("查询会员信息失败");
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return memberVO;
	}
	
	/**
	 * 获取会员
	 * @param keyword 微信ID
	 * @return
	 */
	public MemberVO getMemberByWeixinId(String weixinId,String hotelCode) {
		InterfaceConfig interfaceConfig = interfaceConfigService.getInterfaceConfigByCode(hotelCode);
		if(interfaceConfig!=null && StringUtils.equals(interfaceConfig.getChannel(), "pms")){
			JXDPmsMemberService jxdPmsMemberService = new JXDPmsMemberService();
			return jxdPmsMemberService.getMemberByWeixinId(weixinId, hotelCode,interfaceConfig.getHost(),interfaceConfig.getSign());
		}
		
		log.info("Invoke JXD api getMember...");
		MemberQuery mbrQry = new MemberQuery();
		Map<String, String> map = new HashMap<String, String>();
		mbrQry.setMbrQuery(map);
		map.put("OtherKeyWord", weixinId);
		map.put("OtherName", "微信");
		String param = JxdXmlUtils.toXml(mbrQry);
		if(log.isDebugEnabled()) {
			log.debug("param: \n" + param);
		}

		// 请求接口并获得响应
		MemberVO memberVO = null;
		Response res = null;
		String url = getUrl(hotelCode, mbrQry.getOpType());
		try {
			
			res = HttpHelper.connect(url).header("Content-Type", "text/xml")
					.timeout(JXDConstants.TIMEOUT*3).post(param);
			ApiXmlVoParser.checkReturnContent(res);

			memberVO = ApiXmlVoParser.parseMemberVO(res.html(), res.charset());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return memberVO;
	}
	
	/**
	 * 获取会员
	 * @param keyword 微信ID
	 * @return
	 */
	public MemberVO getQuickMemberByWeixinId(String weixinId,String hotelCode) {
//		InterfaceConfig interfaceConfig = interfaceConfigService.getInterfaceConfigByCode(hotelCode);
//		if(interfaceConfig!=null && StringUtils.equals(interfaceConfig.getChannel(), "pms")){
//			JXDPmsMemberService jxdPmsMemberService = new JXDPmsMemberService();
//			return jxdPmsMemberService.getMemberByWeixinId(weixinId, hotelCode);
//		}
		log.info("Invoke JXD api getQuickMember...");
		MemberQuery mbrQry = new MemberQuery();
		Map<String, String> map = new HashMap<String, String>();
		mbrQry.setMbrQuery(map);
		mbrQry.setOpType("会员快速查询");
		map.put("OtherKeyWord", weixinId);
		map.put("OtherName", "微信");
		String param = JxdXmlUtils.toXml(mbrQry);
		if(log.isDebugEnabled()) {
			log.debug("param: \n" + param);
		}

		// 请求接口并获得响应
		MemberVO memberVO = null;
		Response res = null;
		String url = getUrl(hotelCode, mbrQry.getOpType());
		try {
			res = HttpHelper.connect(url).header("Content-Type", "text/xml")
					.timeout(JXDConstants.TIMEOUT*3).post(param);
			ApiXmlVoParser.checkReturnContent(res);

			memberVO = ApiXmlVoParser.parseMemberVO(res.html(), res.charset());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return memberVO;
	}
	
	/**
	 * 获取会员
	 * @param ProfileId 会员ID
	 * @return
	 */
	public MemberVO getMemberByProfileId(String profileId,String hotelCode) {
		InterfaceConfig interfaceConfig = interfaceConfigService.getInterfaceConfigByCode(hotelCode);
		if(interfaceConfig!=null && StringUtils.equals(interfaceConfig.getChannel(), "pms")){
			JXDPmsMemberService jxdPmsMemberService = new JXDPmsMemberService();
			return jxdPmsMemberService.getMemberByProfileId(profileId, hotelCode,interfaceConfig.getHost(),interfaceConfig.getSign());
		}
		
		log.info("Invoke JXD api getMember...");
		MemberQuery mbrQry = new MemberQuery();
		Map<String, String> map = new HashMap<String, String>();
		mbrQry.setMbrQuery(map);
		map.put("ProfileId", profileId);
		String param = JxdXmlUtils.toXml(mbrQry);
		if(log.isDebugEnabled()) {
			log.debug("param: \n" + param);
		}

		// 请求接口并获得响应
		MemberVO memberVO = null;
		Response res = null;
		String url = getUrl(hotelCode, mbrQry.getOpType());
		try {
			res = HttpHelper.connect(url).header("Content-Type", "text/xml")
					.timeout(JXDConstants.TIMEOUT*3).post(param);
			ApiXmlVoParser.checkReturnContent(res);

			memberVO = ApiXmlVoParser.parseMemberVO(res.html(), res.charset());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return memberVO;
	}
	
	public Float getCanUseScoreByProfileId(String profileId,String hotelCode){
		log.info("Invoke JXD api getCanUseScoreByProfileId...");
		MemberCanUseScoreQuery query = new MemberCanUseScoreQuery();
		query.setProfileId(profileId);
		String param = JxdXmlUtils.toXml(query);
		if(log.isDebugEnabled()) {
			log.debug("param: \n" + param);
		}

		// 请求接口并获得响应
		try {
			String url = getUrl(hotelCode, query.getOpType());
			
			Response res = HttpHelper.connect(url).header("Content-Type", "text/xml")
					.timeout(JXDConstants.TIMEOUT*6).post(param);
			ApiXmlVoParser.checkReturnContent(res);
			
			String name = "SearchResult";
			if(StringUtils.contains(res.html(), "<Row>")) {
				name = "Row";
			}
			Map<String, String> map = ApiXmlVoParser.parseXml2Map(res.html(), name, res.charset());//SearchResult
			try {
				return Float.valueOf(map.get("CanUseScore"));
			} catch (Exception e) {
				return 0f;
			}
		} catch (ApiException e) {
			log.error("查询会员信息失败");
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0f;
	}
	

	public MemberVO getMemberByCardNo(String cardNo, String pwd,String hotelCode) {
		return getMemberByMobile(cardNo, pwd,hotelCode);
	}

	public MemberVO getMemberByEmail(String email, String pwd,String hotelCode){
		return getMemberByMobile(email, pwd,hotelCode);
	}
	
	public List<PointRecordVO> queryMemberPoints(String profileId, Date begin, Date end,String hotelCode,String remark) {
		log.info("Invoke JXD api queryMemberPoints...");

		MemberPointsQuery query = new MemberPointsQuery();
		Map<String, String> map = new HashMap<String, String>();
		map.put("ProfileId", profileId);
		map.put("BeginDate", DateUtil.formatDatetime(begin));
		map.put("EndDate", DateUtil.formatDatetime(end));
		if(StringUtils.isNotBlank(remark)) {
			map.put("remark", remark);
		}
		query.setMbrScoreQuery(map);
		String param = JxdXmlUtils.toXml(query);
		if(log.isDebugEnabled()) {
			log.debug("param: \n" + param);
		}

		List<PointRecordVO> pvos = null;
		// 请求接口并获得响应
		Response res = null;
		try {
			String url = getUrl(hotelCode, query.getOpType());
			
			res = HttpHelper.connect(url).header("Content-Type", "text/xml")
					.timeout(JXDConstants.TIMEOUT).post(param);
			ApiXmlVoParser.checkReturnContent(res);
			
			pvos = ApiXmlVoParser.parsePointRecordVOs(res.html(), res.charset());
		} catch (ApiException e) {
			log.error("查询会员消费记录失败");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pvos;
	}

	public List<PointRecordVO> queryMemberPointExchanges(String profileId, Date begin, Date end,String hotelCode) {
		log.info("Invoke JXD api queryMemberPointExchanges...");

		MemberPointsQueryExchange query = new MemberPointsQueryExchange();
		query.setProfileId(profileId);
		query.setBeginDate(DateUtil.formatDatetime(begin));
		query.setEndDate(DateUtil.formatDatetime(end));
		
		String param = JxdXmlUtils.toXml(query);
		if(log.isDebugEnabled()) {
			log.debug("param: \n" + param);
		}

		List<PointRecordVO> pvos = null;
		// 请求接口并获得响应
		Response res = null;
		try {
			String url = getUrl(hotelCode, query.getOpType());
			
			res = HttpHelper.connect(url).header("Content-Type", "text/xml")
					.timeout(JXDConstants.TIMEOUT).post(param);
			ApiXmlVoParser.checkReturnContent(res);
			
			pvos = ApiXmlVoParser.parsePointRecordExchangesVOs(res.html(), res.charset());
		} catch (ApiException e) {
			log.error("查询积分兑换记录查询失败");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pvos;
	}
	

	public boolean memberTrade(String profileId, String outletCode, String paymentDesc, Float amount, String refNo, String remark, String creator, String hotelCode) {
		log.info("Invoke JXD api memberTrade...");
		MemberTrade trade = new MemberTrade();
		Map<String, String> map = new HashMap<String, String>();
		map.put("ProfileId", profileId);
		map.put("OutletCode", outletCode);
		map.put("PaymentDesc", paymentDesc);
		if(StringUtils.equals(paymentDesc, "23") || StringUtils.equals(paymentDesc, "24")) {
			map.put("Score", String.valueOf(amount.longValue()));
		} else if(StringUtils.equals(paymentDesc, "11") || StringUtils.equals(paymentDesc, "12") || StringUtils.equals(paymentDesc, "18")) {
			map.put("Amount", String.valueOf(amount));
		}
		map.put("RefNo", refNo);
		map.put("Remark", remark);
		map.put("Creator", creator);
		map.put("HotelCode", hotelCode);
		
		trade.setProfileCa(map);

		String param = JxdXmlUtils.toXml(trade);
		if(log.isDebugEnabled()) {
			log.debug("param: \n" + param);
		}

		//请求接口并获得响应
		Response res = null;
		boolean isOk = false;
		String url = getUrl(hotelCode, trade.getOpType());
		String html = "";
		try {
			
			res = HttpHelper.connect(url).header("Content-Type", "text/xml")
					.timeout(JXDConstants.TIMEOUT*18).post(param);
			ApiXmlVoParser.checkReturnContent(res);
			isOk = true;
			html = res.html();
		} catch (ApiException e) {
			if(e.getCause()==null){
				html += e.getMessage();
			}else{
				html += e.getCause().getMessage();
			}
			log.error("会员交易失败");
		} catch (Exception e) {
			if(e.getCause()==null){
				html += e.getMessage();
			}else{
				html += e.getCause().getMessage();
			}
			e.printStackTrace();
		}finally {
			//记录会员交易
			try {
				systemLogService.saveMemberLog(new SysMemberLog(hotelCode,"memberTrade",(isOk?"成功":"失败")
					,new String[]{"param",param},new String[]{"url",url},new String[]{"html",html}));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return isOk;
	}
	
	public List<MemberTypeVO> queryMemberTypes(String hotelCode) {
		log.info("Invoke JXD api queryMemberTypes...");

		MemberTypeQuery query = new MemberTypeQuery();
		
		String param = JxdXmlUtils.toXml(query);
		if(log.isDebugEnabled()) {
			log.debug("param: \n" + param);
		}

		List<MemberTypeVO> mtVos = null;
		// 请求接口并获得响应
		Response res = null;
		try {
			String url = getUrl(hotelCode, query.getOpType());
			
			res = HttpHelper.connect(url).header("Content-Type", "text/xml")
					.timeout(JXDConstants.TIMEOUT).post(param);
			ApiXmlVoParser.checkReturnContent(res);
			
			mtVos = ApiXmlVoParser.parseMemberTypeVOs(res.html(), res.charset());
		} catch (ApiException e) {
			log.error("查询会员类型失败");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mtVos;
	}
	
	/**
	 * 查询会员储值记录
	 */
	public List<MemberTradeVO> queryMemberTrades(String profileId, Date begin, Date end, String type,String hotelCode,String remark) {
		log.info("Invoke JXD api queryMemberTrades...");

		MemberTradeQuery query = new MemberTradeQuery();
		Map<String, String> map = new HashMap<String, String>();
		map.put("ProfileId", profileId);
		map.put("BeginDate", DateUtil.formatDatetime(begin));
		map.put("EndDate", DateUtil.formatDatetime(end));
		if(StringUtils.isNotBlank(type)) {
			map.put("paymentdesc", type);
		}
		if(StringUtils.isNotBlank(remark)) {
			map.put("remark", remark);
		}
		query.setMbrAmountQuery(map);

		String param = JxdXmlUtils.toXml(query);
		if(log.isDebugEnabled()) {
			log.debug("param: \n" + param);
		}

		// 请求接口并获得响应
		Response res = null;
		List<MemberTradeVO> mtVOs = null;
		try {
			String url = getUrl(hotelCode, query.getOpType());
			
			res = HttpHelper.connect(url).header("Content-Type", "text/xml")
					.timeout(JXDConstants.TIMEOUT).post(param);
			ApiXmlVoParser.checkReturnContent(res);
			mtVOs = ApiXmlVoParser.parseMemberTradeVOs(res.html(), res.charset());
		} catch (ApiException e) {
			log.error("查询会员消费记录失败");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mtVOs;
	}
	
	/**
	 * 发券
	 * @param profileId
	 * @param type
	 * @param qty
	 * @param operator
	 * @param remark
	 * @return
	 */
	public Map<String,String> sendMemberCoupon1(Member member, String type, Integer qty, String operator, String remark,String hotelCode){
		Map<String,String> returnMap = new HashMap<>();
		SendCouponQuery query = new SendCouponQuery();
		query.setProfileid(member.getProfileId());
		query.setTickettype(type);
		query.setQuantity(qty);
		query.setOperator(operator);
		query.setRemark(remark);
		
		String param = JxdXmlUtils.toXml(query);
		if(log.isDebugEnabled()) {
			log.debug("param: \n" + param);
		}
		returnMap.put("param", param);

		// 请求接口并获得响应
		Response res = null;
		Boolean isOk = false;
		String url = getUrl(hotelCode, query.getOpType());
		String html = "";
		String errorMsg = "发放会员券失败";
		try {
			res = HttpHelper.connect(url).header("Content-Type", "text/xml")
					.timeout(JXDConstants.TIMEOUT*6).post(param);
			ApiXmlVoParser.checkReturnContent(res);
			
			html = res.html();
			String[] rs = ApiXmlVoParser.parseSendCouponRs(html, res.charset());
			if(rs != null){
				if(StringUtils.equals(rs[0], "0")) {
					isOk = true;
				}
				errorMsg = rs[1];
			}
		} catch (ApiException e) {
			if(e.getCause()==null){
				html += e.getMessage();
			}else{
				html += e.getCause().getMessage();
			}
			log.error("发放会员券失败");
		} catch (Exception e) {
			if(e.getCause()==null){
				html += e.getMessage();
			}else{
				html += e.getCause().getMessage();
			}
			e.printStackTrace();
		}finally{
			returnMap.put("html", html);
			returnMap.put("errorMsg", errorMsg);
			returnMap.put("url", url);
			returnMap.put("isOk", isOk.toString());
		}
		//return new String[]{(isOk?"true":"false"),sendCouponRecord.getId(),errorMsg};
		return returnMap;
	}
	public String[] sendMemberCoupon(Member member, String type, Integer qty, String operator, String remark,String hotelCode,Integer chargeamtmodel) {
		log.info("Invoke JXD api sendMemberCoupon...");
		SendCouponRecord sendCouponRecord = null;
		Map<String,String> map = new HashMap<>();
		try {
			InterfaceConfig interfaceConfig = interfaceConfigService.getInterfaceConfigByCode(hotelCode);
			if(interfaceConfig!=null && StringUtils.equals(interfaceConfig.getChannel(), "pms")){
				JXDPmsMemberService jxdPmsMemberService = new JXDPmsMemberService();
				map = jxdPmsMemberService.memberTrade(member.getProfileId(), member.getCompany().getOutletCode(), (chargeamtmodel==1?"31":"32"), Float.valueOf(qty), null, remark, "WX", hotelCode, "30",interfaceConfig.getHost(),interfaceConfig.getSign());
			}else{
				map = sendMemberCoupon1(member, type, qty, operator, remark, hotelCode);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			sendCouponRecord = new SendCouponRecord(hotelCode, member.getName(),member.getMobile(), type,qty, remark,(map.get("isOk")=="true"?"成功":"失败"),
					map.get("errorMsg"), map.get("html"), map.get("url"), map.get("param"));
			couponTypeService.saveSendCouponRecord(sendCouponRecord);
		}
		return new String[]{map.get("isOk"),sendCouponRecord.getId(),map.get("errorMsg")};
	}
	
	/**
	 * 派劵记录中点补发
	 */
	public Boolean replaceSendMemberCoupon(MemberVO memberVO, SendCouponRecord record) {
		log.info("Invoke JXD api sendMemberCoupon...");

		SendCouponQuery query = new SendCouponQuery();
		query.setProfileid(memberVO.getProfileId());
		query.setTickettype(record.getCouponCode());
		query.setQuantity(record.getQuantity());
		query.setRemark(record.getReason()+"(补发)");
		
		String param = JxdXmlUtils.toXml(query);
		if(log.isDebugEnabled()) {
			log.debug("param: \n" + param);
		}

		// 请求接口并获得响应
		Response res = null;
		boolean isOk = false;
		String url = getUrl(record.getHotelCode(), query.getOpType());
		String html = "";
		String errorMsg = "发放会员券失败";
		try {
			
			res = HttpHelper.connect(url).header("Content-Type", "text/xml")
					.timeout(JXDConstants.TIMEOUT).post(param);
			ApiXmlVoParser.checkReturnContent(res);
			
			html = res.html();
			String[] rs = ApiXmlVoParser.parseSendCouponRs(html, res.charset());
			if(rs != null){
				if(StringUtils.equals(rs[0], "0")) {
					isOk = true;
				}
				errorMsg = rs[1];
			}
		} catch (ApiException e) {
			if(e.getCause()==null){
				html += e.getMessage();
			}else{
				html += e.getCause().getMessage();
			}
			log.error("发放会员券失败");
		} catch (Exception e) {
			if(e.getCause()==null){
				html += e.getMessage();
			}else{
				html += e.getCause().getMessage();
			}
			e.printStackTrace();
		}finally{
			try {
				record.setSynchronizeState((isOk?"成功":"失败"));
				record.setErrorMsg(errorMsg);
				couponTypeService.saveSendCouponRecord(record);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return isOk;
	}
	
	/**
	 * 核销会员券
	 * @param profileId
	 * @param type
	 * @param code
	 * @param operator
	 * @param remark
	 * @return
	 */
	public Boolean useMemberCoupon(String profileId, String code, String operator, String remark,String hotelCode) {
		log.info("Invoke JXD api useMemberCoupon...");

		UseCouponQuery query = new UseCouponQuery();
		query.setProfileId(profileId);
		query.setTicketcode(code);
		query.setOperator(operator);
		query.setRemark(remark);
		
		String param = JxdXmlUtils.toXml(query);
		if(log.isDebugEnabled()) {
			log.debug("param: \n" + param);
		}

		// 请求接口并获得响应
		Response res = null;
		boolean isOk = false;
		String url = getUrl(hotelCode, query.getOpType());
		String html = "";
		try {
			
			res = HttpHelper.connect(url).header("Content-Type", "text/xml")
					.timeout(JXDConstants.TIMEOUT).post(param);
			ApiXmlVoParser.checkReturnContent(res);
			
			html = res.html();
			String[] rs = ApiXmlVoParser.parseUseCouponRs(html, res.charset());
			if(rs != null && StringUtils.equals(rs[0], "0")) {
				isOk = true;
			}
		} catch (ApiException e) {
			if(e.getCause()==null){
				html += e.getMessage();
			}else{
				html += e.getCause().getMessage();
			}
			log.error("核销会员券失败");
		} catch (Exception e) {
			if(e.getCause()==null){
				html += e.getMessage();
			}else{
				html += e.getCause().getMessage();
			}
			e.printStackTrace();
		}finally{
			try {
				systemLogService.saveMemberLog(new SysMemberLog(hotelCode,"核销会员券:useMemberCoupon",(isOk?"成功":"失败"),new String[]{"url",url},new String[]{"param",param},new String[]{"html",html}));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return isOk;
	}
	
	public List<MemberCouponVO> queryMemberCoupons(String profileId,String hotelCode) {
		log.info("Invoke JXD api queryMemberCoupons...");

		MemberCouponQuery query = new MemberCouponQuery();
		query.setProfileId(profileId);
		
		String param = JxdXmlUtils.toXml(query);
		if(log.isDebugEnabled()) {
			log.debug("param: \n" + param);
		}

		List<MemberCouponVO> mtVos = null;
		// 请求接口并获得响应
		Response res = null;
		try {
			String url = getUrl(hotelCode, query.getOpType());
			
			res = HttpHelper.connect(url).header("Content-Type", "text/xml")
					.timeout(JXDConstants.TIMEOUT).post(param);
			ApiXmlVoParser.checkReturnContent(res);
			
			mtVos = ApiXmlVoParser.parseMemberCouponVOs(res.html(), res.charset());
		} catch (ApiException e) {
			log.error("查询会员券失败");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mtVos;
	}
	
	public List<CategoryCodeVO> queryCouponType(String hotelCode) {
		log.info("Invoke JXD api queryCouponType...");

		CategoryCodeQuery query = new CategoryCodeQuery();
		query.setCategoryCode("electickettype");
		
		String param = JxdXmlUtils.toXml(query);
		if(log.isDebugEnabled()) {
			log.debug("param: \n" + param);
		}

		List<CategoryCodeVO> ctVOs = new ArrayList<>();
		// 请求接口并获得响应
		Response res = null;
		try {
			String url = getUrl(hotelCode, query.getOpType());
			
			res = HttpHelper.connect(url).header("Content-Type", "text/xml")
					.timeout(JXDConstants.TIMEOUT).post(param);
			ApiXmlVoParser.checkReturnContent(res);
			
			ctVOs = ApiXmlVoParser.parseCategoryCodeVOs(res.html(), res.charset());
		} catch (ApiException e) {
			log.error("查询券类型失败");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ctVOs;
	}
	
	public List<ExchangeGiftVO> queryExchangeGiftVOs(Date beginTime, Date endTime,String hotelCode) {
		log.info("Invoke JXD api queryExchangeGiftVOs...");

		ExchangeGiftListQuery query = new ExchangeGiftListQuery();
		query.setBeginDate(DateUtil.formatDatetime(beginTime));
		query.setEndDate(DateUtil.formatDatetime(endTime));
		
		String param = JxdXmlUtils.toXml(query);
		if(log.isDebugEnabled()) {
			log.debug("param: \n" + param);
		}

		List<ExchangeGiftVO> giftVOs = null;
		// 请求接口并获得响应
		Response res = null;
		try {
			String url = getUrl(hotelCode, query.getOpType());
			
			res = HttpHelper.connect(url).header("Content-Type", "text/xml")
					.timeout(JXDConstants.TIMEOUT).post(param);
			ApiXmlVoParser.checkReturnContent(res);
			
			giftVOs = ApiXmlVoParser.parseExchangeGiftVOs(res.html(), res.charset());
		} catch (ApiException e) {
			log.error("查询积分兑换列表");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return giftVOs;
	}
	
	public ExchangeGiftVO getExchangeGiftVO(ExchangeGiftDetailQuery query,String hotelCode) {
		log.info("Invoke JXD api exchangeGift...");

		String param = JxdXmlUtils.toXml(query);
		if(log.isDebugEnabled()) {
			log.debug("param: \n" + param);
		}

		// 请求接口并获得响应
		Response res = null;
		ExchangeGiftVO giftVO = null;
		try {
			String url = getUrl(hotelCode, query.getOpType());
			
			res = HttpHelper.connect(url).header("Content-Type", "text/xml")
					.timeout(JXDConstants.TIMEOUT).post(param);
			ApiXmlVoParser.checkReturnContent(res);
			giftVO = ApiXmlVoParser.parseExchangeGiftVO(res.html(), res.charset());
		} catch (ApiException e) {
			log.error("查询积分兑换详情");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return giftVO;
	}
	
	public boolean exchangeGift(ExchangeGiftQuery query,String hotelCode) {
		log.info("Invoke JXD api exchangeGift...");

		String param = JxdXmlUtils.toXml(query);
		if(log.isDebugEnabled()) {
			log.debug("param: \n" + param);
		}

		// 请求接口并获得响应
		Response res = null;
		try {
			String url = getUrl(hotelCode, query.getOpType());
			
			res = HttpHelper.connect(url).header("Content-Type", "text/xml")
					.timeout(JXDConstants.TIMEOUT).post(param);
			ApiXmlVoParser.checkReturnContent(res);
			return true;
		} catch (ApiException e) {
			log.error("积分兑换");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public List<ChargeMoneyVO> getChargeMoney(ChargeMoneyQuery query,String hotelCode) {
		log.info("Invoke JXD api queryChargeMoney...");

		String param = JxdXmlUtils.toXml(query);
		if(log.isDebugEnabled()) {
			log.debug("param: \n" + param);
		}

		// 请求接口并获得响应
		Response res = null;
		
		List<ChargeMoneyVO> moneys = null;
		try {
			String url = getUrl(hotelCode, query.getOpType());
			
			res = HttpHelper.connect(url).header("Content-Type", "text/xml")
					.timeout(JXDConstants.TIMEOUT).post(param);
			ApiXmlVoParser.checkReturnContent(res);
			moneys = ApiXmlVoParser.parseChargeMoneyVO(res.html(), res.charset());
		} catch (ApiException e) {
			log.error("充值档次");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return moneys;
	}
	
	private Map<String,String> getHotelUrlMap(String hotelCode) throws ApiException{
		Map<String,String> urlMap = new HashMap<String,String>();
		InterfaceListQuery query = new InterfaceListQuery();
		query.setCode(hotelCode);
		try {
			List<InterfaceListVO> interfaceLists = loadInterfaceListVOs(query);
			if(interfaceLists!=null && interfaceLists.size()>0){
				interfaceConfigService.deleteMbrInterfaceConfigAll(hotelCode);
			}
			for (InterfaceListVO vo : interfaceLists) {
				urlMap.put(vo.getInterfaceName(), vo.getInterfaceUrl());
				MbrInterfaceConfig mbrInterfaceConfig = new MbrInterfaceConfig();
				mbrInterfaceConfig.setHotelCode(hotelCode);
				mbrInterfaceConfig.setOpType(vo.getInterfaceName());
				mbrInterfaceConfig.setUrl(vo.getInterfaceUrl());
				mbrInterfaceConfig.setCreateDate(new Date());
				interfaceConfigService.saveMbrInterfaceConfig(mbrInterfaceConfig);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(urlMap.isEmpty()){
			throw new ApiException("接口列表为空！");
		}
		return urlMap;
	}
	
	public String getUrl(String hotelCode,String opType){
		String url = "";
		MbrInterfaceConfig config = interfaceConfigService.getMbrInterfaceConfig(hotelCode, opType);
		if(config!=null){
			url = config.getUrl();
		}else{
			Map<String, String> urlMap;
			try {
				urlMap = getHotelUrlMap(hotelCode);
				if(urlMap.get(opType)!=null){
					url = urlMap.get(opType);
				}
			} catch (ApiException e) {
				e.printStackTrace();
			}
			if(StringUtils.isBlank(url)){
				url = interfaceConfigService.getHotelUrlByHotelCode(hotelCode);
			}
		}
		return url;
	}
	
	public List<InterfaceListVO> loadInterfaceListVOs(InterfaceListQuery query) throws Exception {
		
		 String param = JxdXmlUtils.toXml(query);
		 if(log.isDebugEnabled()) {
			log.debug("param: \n" + param);
		 }
		 // 请求接口并获得响应
		 List<InterfaceListVO> interfaceLists = null;
		 Response res = null;
		 try {
			 res = HttpHelper.connect(JXDConstants.API_URL).header("Content-Type",
					 "text/xml").timeout(JXDConstants.TIMEOUT).post(param);
			 ApiXmlVoParser.checkReturnContent(res);
			 interfaceLists = ApiXmlVoParser.parseInterfaceListVOs(res.html(), res.charset());
		} catch (Exception e) {
			e.printStackTrace();
			log.error("param="+param+" url="+JXDConstants.API_URL+" html="+(res==null?"":res.html()));
		}
		 return interfaceLists;
	}
	
	/**
	 * 调用额云通的关注政策
	 * @param followPolicy
	 * @param hotelCode
	 */
	public List<FollowPolicyVO> followPolicyQyery(String url){
		log.info("Invoke JXD api followPolicyQyery...");
		FollowPolicyQuery followPolicyQuery = new FollowPolicyQuery();
		String param = JxdXmlUtils.toXml(followPolicyQuery);
		if(log.isDebugEnabled()) {
			log.debug("param: \n" + param);
		}

		// 请求接口并获得响应
		Response res = null;
		List<FollowPolicyVO> vos = null;
		try {
			res = HttpHelper.connect(url).header("Content-Type", "text/xml")
					.timeout(JXDConstants.TIMEOUT).post(param);
			ApiXmlVoParser.checkReturnContent(res);
			vos = ApiXmlVoParser.parseFollowPolicyVOs(res.html(), res.charset());
		} catch (ApiException e) {
			log.error("E云通营销政策查询");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return vos;
	}
}
