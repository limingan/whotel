package com.whotel.front.controller;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.weixin.core.api.JsapiTicket;
import com.weixin.core.api.TokenManager;
import com.weixin.core.api.UserInfoApi;
import com.weixin.core.bean.FanInfo;
import com.weixin.core.common.AccessToken;
import com.weixin.core.common.BaseToken;
import com.weixin.core.common.JsTicketToken;
import com.whotel.card.entity.Member;
import com.whotel.common.base.Constants;
import com.whotel.common.http.HttpHelper;
import com.whotel.common.http.HttpHelper.Response;
import com.whotel.common.util.DESUtil;
import com.whotel.common.util.SystemConfig;
import com.whotel.company.entity.Company;
import com.whotel.company.entity.OauthInterface;
import com.whotel.company.entity.PublicNo;
import com.whotel.company.service.OauthInterfaceService;
import com.whotel.company.service.PublicNoService;
import com.whotel.ext.json.JSONDataUtil;
import com.whotel.front.entity.WeixinFan;
import com.whotel.thirdparty.jxd.mode.vo.WeixinVO;
import com.whotel.weixin.service.TokenService;

import net.sf.json.JSONObject;

@Controller
public class OauthController extends FanBaseController {

	private static final Logger log = LoggerFactory.getLogger(OauthController.class);

	@Autowired
	private PublicNoService publicNoService;
	
	@Autowired
	private OauthInterfaceService oauthInterfaceService;
	
	@RequestMapping("/oauth")
	public String oauth(String state, String code, String toUrl, HttpServletRequest req) {
		
		String queryString = "";
		if(StringUtils.isNotBlank(state)) {
			queryString = "?" + state.replaceAll("@", "&");
		}
		Company company = getCurrentCompany(req);
		if(company != null) {
			PublicNo publicNo = publicNoService.getPublicNoByCompanyId(company.getId());
			if(publicNo != null) {
				AccessToken token = TokenManager.getAccessToken(publicNo.getAppId(),
						publicNo.getAppSecret(), code);
				log.info("appid:"+publicNo.getAppId()+", appsecret:"+publicNo.getAppSecret()+", token:"+token);
				if (token != null && StringUtils.isNotBlank(token.getOpenid())) {
					HttpSession session = req.getSession();
					session.setAttribute(Constants.Session.WEIXINFAN_LOGIN_OPENID, token.getOpenid());
					session.setAttribute(Constants.Session.WEIXINFAN_LOGIN_COMPANYID, company.getId());
					session.setAttribute(Constants.Session.DOMAIN, SystemConfig.getProperty("server.url"));
				}
			} else {
				log.error("publicNo info is null!");
			}
		} else {
			log.error("company info is null!");
		}
		return "redirect:" + toUrl + queryString;
	}

	@RequestMapping("/oauth2")
	public String oauth2(String state, String code, String toUrl, HttpServletRequest req) {
		
		String queryString = "";
		if(StringUtils.isNotBlank(state)) {
			queryString = "?" + state.replaceAll("@", "&");
		}
		
		Company company = getCurrentCompany(req);
		if(company != null) {
			PublicNo publicNo = publicNoService.getPublicNoByCompanyId(company.getId());
			if(publicNo != null) {
				AccessToken token = TokenManager.getAccessToken(publicNo.getAppId(),
						publicNo.getAppSecret(), code);
				log.info("appid:"+publicNo.getAppId()+", appsecret:"+publicNo.getAppSecret()+", token:"+token);
				if (token != null && StringUtils.isNotBlank(token.getOpenid())) {
					HttpSession session = req.getSession();
					session.setAttribute(Constants.Session.WEIXINFAN_LOGIN_OPENID, token.getOpenid());
					session.setAttribute(Constants.Session.WEIXINFAN_LOGIN_COMPANYID, company.getId());
					session.setAttribute(Constants.Session.DOMAIN, SystemConfig.getProperty("server.url"));
					try {
						FanInfo info = UserInfoApi.getSnsUserInfo(token, token.getOpenid());
						if (info.getErrcode() == null || info.getErrcode() == 0) {
							weixinFanService.updateWeixinFan(info, publicNo.getDeveloperCode());
						} else {
							log.warn("采集用户信息失败，info.errcode" + info.getErrcode());
							// 重试一次
							info = UserInfoApi.getSnsUserInfo(token, token.getOpenid());
							if (info.getErrcode() == null || info.getErrcode() == 0) {
								weixinFanService.updateWeixinFan(info, publicNo.getDeveloperCode());
							} else {
								log.warn("再次采集用户信息失败，info.errcode" + info.getErrcode());
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} else {
				log.error("publicNo info is null!");
			}
		} else {
			log.error("company info is null!");
		}
		return "redirect:" + toUrl + queryString;
	}
	
	@RequestMapping("/oauth/toOauthInterface")
	public String toOauthInterface(String code,String arg, HttpServletRequest req) throws Exception{
		String companyId = getCurrentCompany(req).getId();
		OauthInterface oauthInterface = oauthInterfaceService.getEnableOauthInterface(companyId, code);
		WeixinFan weixinFan = getCurrentFan(req);
		
		if(StringUtils.isBlank(weixinFan.getNickName())){
			PublicNo publicNo = publicNoService.getPublicNoByCompanyId(companyId);
			BaseToken baseToken = TokenService.getTokenService().getAccessToken(publicNo.getAppId(), publicNo.getAppSecret());
			FanInfo info = UserInfoApi.getUserInfo(baseToken, weixinFan.getOpenId());
			if (info.getErrcode() == null || info.getErrcode() == 0) {
				weixinFanService.updateWeixinFan(info, publicNo.getDeveloperCode());
				weixinFan = getCurrentFan(req);
			}
		}
		
		StringBuffer url = new StringBuffer(oauthInterface.getUrl());
		if(url.indexOf("?")>0){
			url.append("&weixinFan=");
		}else{
			url.append("?weixinFan=");
		}
		String json = getUserByWxid(weixinFan);
		url.append(encrypt(json, oauthInterface.getSecretKey()));
		url.append("&arg=");
		url.append(arg);
		
		return "redirect:" + url.toString();
	}
	
	@RequestMapping("/ticket/getTicket")
	@ResponseBody
	public String getTicket(String appId,String appSecret, HttpServletRequest req){
		try {
			if(StringUtils.isNotBlank(appId) && StringUtils.isNotBlank(appSecret)){
				JsTicketToken jsTicketToken = TokenService.getTokenService().getJsTicketToken(appId, appSecret,JsapiTicket.type_jsapi);
				return jsTicketToken.getTicket();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	/*@RequestMapping("/oauthInterface/getUserByWxid")
	@ResponseBody*/
	private String getUserByWxid(WeixinFan weixinFan){
		WeixinVO vo = new WeixinVO();
		if(weixinFan!=null){
			vo.setOpenId(weixinFan.getOpenId());
			vo.setAvatar(weixinFan.getAvatar());
			vo.setFocus(weixinFan.isFocus());
			vo.setNickName(weixinFan.getNickName());
			vo.setSex(weixinFan.getSex());
			
			Member member = memberService.getMemberByOpendId(weixinFan.getOpenId());
			if(member!=null){
				vo.setAddr(member.getAddr());
				vo.setEmail(member.getEmail());
				vo.setMobile(member.getMobile());
			}
		}
		return JSONObject.fromObject(vo).toString();
	}
	
	private String encrypt(String source,String key){
		try {
			String originalText = Base64.encodeBase64String(source.getBytes("UTF-8"));
			originalText += Base64.encodeBase64String(key.getBytes("UTF-8"));
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < originalText.length(); i++) {
				sb.append(originalText.charAt(i));
				sb.append(originalText.charAt(originalText.length()-i-1));
			}
			String ciphertext = sb.substring(sb.length()/2);
			System.out.println(ciphertext);
			log.info("密文="+ciphertext);
			if(ciphertext.indexOf("+")>0){
				ciphertext = ciphertext.replace("+", "*");
			}
			if(ciphertext.indexOf("=")>0){
				ciphertext = ciphertext.replace("=", "_");
			}
			return ciphertext;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	
	public static void main(String[] args) {
//		try {
//			String url = "http://localhost:8080/oauthInterface/getUserByWxid.do";
//			String wxid= "oLI_KjkrrRtd8RPJkIiLz17M1XTI";
//			Map<String,String> param = new HashMap<>();
//			param.put("wxid", wxid);
//			Response res = HttpHelper.connect(url).data(param).post();
//			String result = res.html();
//			System.out.println(result);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}
}
