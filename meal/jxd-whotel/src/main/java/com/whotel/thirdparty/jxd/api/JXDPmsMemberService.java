package com.whotel.thirdparty.jxd.api;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.DocumentException;

import com.whotel.common.enums.Gender;
import com.whotel.common.http.HttpHelper;
import com.whotel.common.http.HttpHelper.Response;
import com.whotel.common.util.MD5Util;
import com.whotel.common.util.NameUtil;
import com.whotel.common.util.SpringContextHolder;
import com.whotel.company.entity.Company;
import com.whotel.company.entity.InterfaceConfig;
import com.whotel.company.service.InterfaceConfigService;
import com.whotel.system.entity.SysMemberLog;
import com.whotel.system.service.SystemLogService;
import com.whotel.thirdparty.jxd.ApiException;
import com.whotel.thirdparty.jxd.JXDConstants;
import com.whotel.thirdparty.jxd.mode.MemberProfileUpdate;
import com.whotel.thirdparty.jxd.mode.MemberQuery;
import com.whotel.thirdparty.jxd.mode.MemberTrade;
import com.whotel.thirdparty.jxd.mode.vo.GeneralMsg;
import com.whotel.thirdparty.jxd.mode.vo.MemberVO;
import com.whotel.thirdparty.jxd.util.ApiXmlPmsVoParser;
import com.whotel.thirdparty.jxd.util.ApiXmlVoParser;
import com.whotel.thirdparty.jxd.util.JxdXmlUtils;

public class JXDPmsMemberService {
	private static final Logger log = Logger.getLogger(JXDPmsMemberService.class);
	
	private SystemLogService systemLogService = SpringContextHolder.getBean(SystemLogService.class);
	
	public String getUrl(String code,String param,String url,String key){
			url = url+"?grpid="+code+"&channel=C01&sign=";
			url += MD5Util.MD5(key+code+"C01"+param).toLowerCase();
		return url;
	}
	
	/**
	 * 获取会员
	 * @param keyword 微信ID
	 * @return
	 */
	public MemberVO getMemberByWeixinId(String weixinId,String hotelCode,String url,String key) {
		
		log.info("Invoke JXD api getMember...");
		MemberQuery query = new MemberQuery();
		query.setxType("JxdBSPms");
		Map<String, String> map = new HashMap<String, String>();
		query.setMbrQuery(map);
		map.put("OtherKeyWord", weixinId);
		map.put("OtherName", "微信");
		String param = JxdXmlUtils.toXml(query);
		if(log.isDebugEnabled()) {
			log.debug("param: \n" + param);
		}

		// 请求接口并获得响应
		MemberVO memberVO = null;
		Response res = null;
		url = getUrl(hotelCode, param,url,key);
		try {
			res = HttpHelper.connect(url).header("Content-Type", "text/xml").timeout(JXDConstants.TIMEOUT).post(param);
			ApiXmlVoParser.checkReturnContent(res);
			memberVO = ApiXmlPmsVoParser.parseMemberVO(res.html(), res.charset());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return memberVO;
	}
	
	public MemberVO getMemberByMobile(String mobile,String hotelCode,String url,String key) {
		log.info("Invoke JXD api getMember...");

		MemberQuery mbrQry = new MemberQuery();
		mbrQry.setxType("JxdBSPms");
		Map<String, String> map = new HashMap<String, String>();
		mbrQry.setMbrQuery(map);
		map.put("Mobile", mobile);
		String param = JxdXmlUtils.toXml(mbrQry);
		if(log.isDebugEnabled()) {
			log.debug("param: \n" + param);
		}

		// 请求接口并获得响应
		MemberVO memberVO = null;
		try {
			url = getUrl(hotelCode, param,url,key);
			
			Response res = HttpHelper.connect(url).header("Content-Type", "text/xml")
					.timeout(JXDConstants.TIMEOUT).post(param);
			ApiXmlVoParser.checkReturnContent(res);

			memberVO = ApiXmlVoParser.parseMemberVO(res.html(), res.charset());
		} catch (Exception e) {
			System.out.println("url="+url+" param"+param);
			e.printStackTrace();
		}
		return memberVO;
	}
	
	/**
	 * 获取会员
	 * @param keyword 微信ID
	 * @return
	 */
	public MemberVO getMemberByProfileId(String profileId,String hotelCode,String url,String key) {
		
		log.info("Invoke JXD api getMember...");
		MemberQuery query = new MemberQuery();
		query.setxType("JxdBSPms");
		Map<String, String> map = new HashMap<String, String>();
		map.put("ProfileId", profileId);
		query.setMbrQuery(map);
		String param = JxdXmlUtils.toXml(query);
		if(log.isDebugEnabled()) {
			log.debug("param: \n" + param);
		}

		// 请求接口并获得响应
		MemberVO memberVO = null;
		Response res = null;
		url = getUrl(hotelCode, param,url,key);
		try {
			res = HttpHelper.connect(url).header("Content-Type", "text/xml").timeout(JXDConstants.TIMEOUT).post(param);
			ApiXmlVoParser.checkReturnContent(res);
			memberVO = ApiXmlPmsVoParser.parseMemberVO(res.html(), res.charset());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return memberVO;
	}
	
	/**
	 * 注册会员
	 */
	public MemberVO registerMember(MemberVO member,Company company,String action,String url,String key) {
		Map<String, String> params = new HashMap<String, String>();
		if(Boolean.TRUE.equals(company.getMultipleMbr()) && !StringUtils.equals("save", action)){
			params.put("Action", "New");
		}else if(StringUtils.isNotBlank(member.getProfileId())) {
			params.put("Action", "MODIFY");
		} else {
			params.put("Action", "Add");
		}
		
		params.put("ProfileId", member.getProfileId());
		
		//NetName NetPassword CertificateType
		
		params.put("GuestCName", member.getGuestCName());
		//String[] pinyinName = NameUtil.convertPinyinName(member.getGuestCName());
		//params.put("GuestEName", pinyinName[0] + " " + pinyinName[1]);
		// params.put("CertificateNo", "");
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
		//params.put("Sales", member.getSales());
		//params.put("NotSendMsg", (member.getIsSendMsg()==null||member.getIsSendMsg()==true)?"0":"1");//0发送1不发送
		return registerMember(params,company.getCode(),url,key);
	}
	
	private MemberVO registerMember(Map<String, String> params,String hotelCode,String url,String key) {
		log.info("Invoke JXD api registerMember...");

		MemberProfileUpdate query = new MemberProfileUpdate();
		query.setxType("JxdBSPms");
		query.setProfileUpdate(params);

		String param = JxdXmlUtils.toXml(query);
		if(log.isDebugEnabled()) {
			log.debug("param: \n" + param);
		}

		// 请求接口并获得响应
		Response res = null;
		MemberVO memberVO = null;
		String html = "";
		url = getUrl(hotelCode, param,url,key);
		try {
			
			res = HttpHelper.connect(url).header("Content-Type", "text/xml").timeout(JXDConstants.TIMEOUT).post(param);
			ApiXmlVoParser.checkReturnContent(res);
			html += res.html();
			memberVO = ApiXmlPmsVoParser.parseMemberVO(res.html(), res.charset());
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
	 * 会员交易
	 */
	public Map<String,String> memberTrade(String profileId, String outletCode, String paymentDesc, Float amount, String refNo, String remark, String creator, String hotelCode,String balanceType,String url,String key) {
		log.info("Invoke JXD api memberTrade...");
		MemberTrade trade = new MemberTrade();
		trade.setxType("JxdBSPms");
		Map<String, String> map = new HashMap<String, String>();
		map.put("ProfileId", profileId);
		map.put("OutletCode", outletCode);
		map.put("BalanceType", balanceType);
		map.put("PaymentDesc", paymentDesc);
		map.put("Amount", String.valueOf(amount));
		map.put("RefNo", refNo);
		map.put("Remark", remark);
		map.put("Creator", creator);
		trade.setProfileCa(map);
		
		Map<String,String> returnMap = new HashMap<>();

		String param = JxdXmlUtils.toXml(trade);
		if(log.isDebugEnabled()) {
			log.debug("param: \n" + param);
		}
		returnMap.put("param", param);
		
		//请求接口并获得响应
		Response res = null;
		Boolean isOk = false;
		url = getUrl(hotelCode, param,url,key);
		String html = "";
		try {
			res = HttpHelper.connect(url).header("Content-Type", "text/xml").timeout(JXDConstants.TIMEOUT).post(param);
			ApiXmlVoParser.checkReturnContent(res);
			GeneralMsg generalMsg = ApiXmlVoParser.parseGeneralMsg(res.html(), res.charset());
			returnMap.put("errorMsg", generalMsg.getMessage());
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
				returnMap.put("html", html);
				returnMap.put("url", url);
				returnMap.put("isOk", isOk.toString());
				systemLogService.saveMemberLog(new SysMemberLog(hotelCode,"memberTrade",(isOk?"成功":"失败"),new String[]{"param",param},new String[]{"url",url},new String[]{"html",html}));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return returnMap;
	}
}
