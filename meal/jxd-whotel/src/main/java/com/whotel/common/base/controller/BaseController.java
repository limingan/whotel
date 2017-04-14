package com.whotel.common.base.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import com.whotel.common.base.Constants;
import com.whotel.common.util.CustomTimestampEditor;

/**
 * <pre>
 * 基础控制器，提供一些常见操作
 *@author 冯勇
 * 
 */
@Controller
public class BaseController {
	
	private static final Logger log = Logger.getLogger(BaseController.class);

	/**
	 * 注册自定义数据绑定器
	 * 
	 * @param binder
	 */
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
		binder.registerCustomEditor(Timestamp.class, new CustomTimestampEditor(new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss"), true));
		binder.registerCustomEditor(Date.class, "startTime", new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));
		binder.registerCustomEditor(Date.class, "endTime", new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));
	}
	
	/**
	 * 清空Session值
	 * 
	 * @param req
	 */
	@SuppressWarnings("unchecked")
	public static void clearSession(HttpServletRequest req) {
		Enumeration<String> er = req.getSession().getAttributeNames();
		while (er.hasMoreElements()) {
			String attributeName = er.nextElement();
			req.getSession().removeAttribute(attributeName);
		}
		req.getSession().invalidate();
	}

	/**
	 * 统一处理JSON字符串写入Response的逻辑
	 * 
	 * @param res
	 * @param json
	 * @param charset
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	public static void responseJson(HttpServletResponse res, String json, String charset) {
		res.addHeader(Constants.HEADER_KEY_ACAO, Constants.HEADER_VAL_ACAO);
		res.setContentType(Constants.RESPONSE_CONTENT_TYPE_JSON);
		res.setCharacterEncoding(charset);
		try {
			res.getOutputStream().write(json.getBytes(charset));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 统一处理JSON字符串写入Response的逻辑，使用默认编码UTF-8
	 * 
	 * @param res
	 * @param json
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	public static void responseJson(HttpServletResponse res, String json) {
		responseJson(res, json, Constants.CHARSET);
	}

	public String getRealIp(HttpServletRequest req) {
		String ip = req.getHeader("X-Forwarded-For");
		if (StringUtils.isNotBlank(ip))
			return ip;
		return req.getRemoteAddr();
	}

	public static void printRequestBrief(final HttpServletRequest req) {
		log.info("Invoke: " + req.getRequestURL());
		log.info("Params: ");
		@SuppressWarnings("unchecked")
		Enumeration<String> names = req.getParameterNames();
		while (names.hasMoreElements()) {
			String name = names.nextElement();
			log.info("\t" + name + ": [" + req.getParameter(name) + "]");
		}
		log.info("From:   " + req.getRemoteAddr());
	}
}
