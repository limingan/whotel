package com.weixin.core.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.weixin.core.bean.WeixinMsg;
import com.weixin.core.common.WeixinConstant;
import com.weixin.core.handler.TokenAuthHandler;
import com.weixin.core.handler.WeixinMsgHandler;
import com.whotel.common.util.Dom4jHelper;
import com.whotel.common.util.ReflectionUtil;
import com.whotel.common.util.Sha1Util;
import com.whotel.common.util.TextUtil;

/**
 * 微信消息，事件处理服务Servlet
 * 
 * @author 冯勇
 */
@SuppressWarnings("serial")
public class WeixinHandleServlet extends HttpServlet {
	private static Logger log = LoggerFactory.getLogger(WeixinHandleServlet.class);
	private WeixinMsgHandler headHandler;
	private TokenAuthHandler authHandler;
	private static String beanPre = "com.weixin.core.bean.";

	/**
	 * 初始化，主要是1.加载消息处理器; 2.获取访问令牌
	 */
	@Override
	@SuppressWarnings("unchecked")
	public void init(ServletConfig config) throws ServletException {
		if (log.isInfoEnabled()) {
			log.info("init");
		}
		String msgHandlerClass = config.getInitParameter("msgHandlerClass");
		String authHandlerClass = config.getInitParameter("authHandlerClass");
		if (log.isDebugEnabled()) {
			log.debug("msg handler: " + msgHandlerClass);
			log.debug("auth handler: " + authHandlerClass);
		}
		// parse handler from list string
		String[] handers = msgHandlerClass.split(",");
		WeixinMsgHandler preHandler = null;

		for (int i = 0; i < handers.length; i++) {
			try {
				Class<WeixinMsgHandler> handlerCls = (Class<WeixinMsgHandler>) Class
						.forName(handers[i]);
				WeixinMsgHandler currentHandler = handlerCls.newInstance();
				currentHandler.setServletContext(config.getServletContext());
				if (i == 0) {
					headHandler = preHandler = currentHandler;
				} else {
					preHandler.setHandler(currentHandler);
					preHandler = currentHandler;
				}
			} catch (Exception e) {
				throw new ServletException("Class.forName for [ "
						+ msgHandlerClass + "] not found", e);
			}
		}
		
		try {
			Class<TokenAuthHandler> authClass = (Class<TokenAuthHandler>) Class
					.forName(authHandlerClass);
			authHandler = authClass.newInstance();
		} catch (Exception e) {
			if (authHandlerClass == null)
				authHandlerClass = "NULL";
			throw new ServletException("Class.forName for [ "
					+ authHandlerClass + "] not found", e);
		}
	}

	/**
	 * 微信消息交互， 当普通微信用户向公众账号发消息时，微信服务器将POST该消息到填写的URL上 ，服务程序将根据消息内容，返回不同的回复消息
	 */
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (log.isDebugEnabled()) {
			log.info("doPost");
		}
		// read XML content from post request
		Dom4jHelper dom4jDealor = null;
		try {
			dom4jDealor = new Dom4jHelper(request.getInputStream());
		} catch (DocumentException e) {
			log.error("READ XML ERROR", e);
			throw new IOException("READ XML ERROR");
		}
		// 解析消息Bean，并且交给handler处理，获取对应的响应消息Bean给Response
		try {
			WeixinMsg msg = buildMsg(dom4jDealor.getElementValue("MsgType"),
					dom4jDealor);
			if (log.isInfoEnabled()) {
				log.info("revice xml: " + msg.toXml());
			}
			WeixinMsg respMsg = headHandler.handle(msg);
			response.setCharacterEncoding(WeixinConstant.DEFAULT_CHARSET);
			PrintWriter wt = response.getWriter();
			if (respMsg != null) {
				wt.print(respMsg.toXml());
				log.info("respMsg xml(): " + respMsg.toXml());
			}else{
				response.setContentType("text/xml;charset=UTF-8");
				wt.println("success");
			}
			wt.flush();
			log.warn("回复消息内容："+msg.toString());
		} catch (Exception e) {
			log.error("Error in doPost!", e);
		}
	}

	/**
	 * 根据消息类型创建消息

	 */
	private WeixinMsg buildMsg(String msgType, Dom4jHelper dom4jDealor) throws Exception {
		Class<?> clazz = Class.forName(beanPre + TextUtil.capitalize(msgType) + "Msg");
		WeixinMsg msg = (WeixinMsg) clazz.newInstance();
		List<Field> fs = ReflectionUtil.findAllField(clazz);
		if (fs != null && fs.size() != 0) {
			for (Field f : fs) {
				f.setAccessible(true);
				String value = dom4jDealor.getElementValue(TextUtil.capitalize(f.getName()));
				if(StringUtils.isNotBlank(value)){
					ReflectionUtil.setFieldVlaue(msg, f, value);
				}
			}
		}
		return msg;
	}

	/**
	 * 网址接入
	 * 
	 * <p>
	 * 公众平台用户提交信息后，微信服务器将发送GET请求到填写的URL上，并且带上四个参数:
	 * <li>signature 微信加密签名
	 * <li>timestamp 时间戳
	 * <li>nonce 随机数
	 * <li>echostr 随机字符串
	 * <p>
	 * 开发者通过检验signature对请求进行校验（下面有校验方式）。若确认此次GET请求来自微信服务器，请原样返回echostr参数内容，则接入生效
	 * ，否则接入失败。
	 * 
	 */
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String signature = request.getParameter("signature");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		String echostr = request.getParameter("echostr");
		String cid = request.getParameter("cid");
		log.info("cid->" + cid);
		String token = authHandler.auth(cid);
		
		log.info("doGet: {signature: " + signature + ", timestamp: " + timestamp + ", nonce: " + nonce + ", echostr: "
				+ echostr + ",token=" + token);
		if (token == null || token.trim().equals("")) {
			log.warn("认证失败 token is null, cid->" + cid);
			return;
		}
		
		if (timestamp == null || nonce == null) {
			log.error("timestamp or nonce is null! valid failure!");
			return;
		}
		String[] array = { token, timestamp, nonce };
		Arrays.sort(array);// 将token、timestamp、nonce三个参数进行字典序排序
		// 将排序后的三个字符串，拼成一个字符串
		StringBuffer sb = new StringBuffer();
		for (String str : array) {
			sb.append(str);
		}
		// 对拼成的字符串进行sha1加密
		String ret = Sha1Util.getSha1(sb.toString());
		// 将加密之后的字符串，与signature进行比对，如果一致的话，表示你的这个接口就是微信里设置的那个接口
		// 双方就会认为对方是可信的
		// 这里，signature其实是一段加密过的字符，它是微信用token,timestamp,nonce这三个参数进行sha1加密后的字符串，和我们上面进行的过程是一样的
		// 所以，只要我们自己加密后的密文，和signature是一样的，就表示双方是可信的。
		log.info("ret: " + ret + ", signature: " + signature);
		if (ret.equalsIgnoreCase(signature)) {
			log.info("Auth success!");
			PrintWriter wt = response.getWriter();
			// 若确认此次GET请求来自微信服务器，请原样返回echostr参数内容
			wt.print(echostr);
			wt.close();
		} else {
			log.info("Auth failed!");
		}
	}
}
