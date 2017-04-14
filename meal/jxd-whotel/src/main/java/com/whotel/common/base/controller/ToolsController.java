package com.whotel.common.base.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.whotel.common.base.Constants;
import com.whotel.common.base.SysCache;
import com.whotel.common.entity.Region;
import com.whotel.common.util.QiniuUtils;
import com.whotel.common.util.TextUtil;
import com.whotel.company.entity.Company;
import com.whotel.ext.redis.RedisCache;
import com.whotel.front.controller.FanBaseController;

/**
 * 通用工具
 * @author 冯勇
 *
 */
@Controller
public class ToolsController extends FanBaseController {

	private static final Logger logger = Logger.getLogger(ToolsController.class);

	@Autowired
	private RedisCache cache;
	
	@RequestMapping("/uploadToken")
	public void uploadToken(String dir, String resType, String fileName, HttpServletResponse res) throws IOException {
		String bucket = QiniuUtils.getBucket(resType);
		String key = QiniuUtils.genServerKey(bucket, dir, fileName);
		String token = QiniuUtils.getToken(bucket, key);
		String jsonContent = "\"res_url\":\"{0}\", \"token\":\"{1}\", \"key\":\"{2}\"";
		responseJson(res, "{"+MessageFormat.format(jsonContent, QiniuUtils.getResUrl(key), token, key)+"}");
	}
	
	@RequestMapping("/uploadBase64Token")
	public void uploadBase64Token( HttpServletResponse res) throws IOException {
		String token = QiniuUtils.getToken();
		String jsonContent = "\"res_url\":\"\", \"token\":\""+token+"\", \"key\":\"\"";
		responseJson(res, "{"+jsonContent+"}");
	}
	
	/**
	 * 发送短信验证码
	 */
	@RequestMapping("/sendSmsCode")
	@ResponseBody
	public Boolean sendSmsCode(String mobile, HttpServletRequest req) {
		Company company = getCurrentCompany(req);
		String ip = getRealIp(req);
		logger.info(ip + " 请求发送验证码");
		try {
			String key = Constants.MOBILE_VERIFY + mobile;
			if (cache.get(key) == null) {
				String verifyCode = TextUtil.genRandomNumString(4);
				memberService.sendJxdSms(company.getId(), verifyCode, "2", mobile);
				cache.put(key, verifyCode, 120);
				return true;
			} else {
				logger.warn("IP: " + ip + "Mobile:" + mobile + "，频繁请求生成短信验证码？");
			}
		} catch (Exception ex) {
			logger.error("ERROR", ex);
		}
		return false;
	}
	
	@RequestMapping("/checkSmsCode")
	@ResponseBody
	public Boolean checkSmsCode(String mobile, String verifyCode, HttpServletRequest req) throws UnsupportedEncodingException {
		// 手机验证码
		String key = Constants.MOBILE_VERIFY + mobile;
		String yzm = (String) cache.get(key);
		if (!StringUtils.equals(yzm, verifyCode)) {
			return false;
		} else {
			cache.evict(key);
		}
		return true;
	}
	
	/**
	 * 获取省份数据
	 */
	@RequestMapping("/ajaxGetProvince")
	@ResponseBody
	public List<Region> ajaxGetProvince() {
		List<Region> provinces = SysCache.getProvinces();
		return provinces;
	}
	
	/**
	 * 获取下级地区数据
	 */
	@RequestMapping("/ajaxGetSubRegion")
	@ResponseBody
	public List<Region> ajaxGetSubRegion(Integer parentId) {
		List<Region> regions = SysCache.getSubRegion(parentId);
		return regions;
	}
}
