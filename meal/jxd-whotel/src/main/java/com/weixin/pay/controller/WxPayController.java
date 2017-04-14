package com.weixin.pay.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.weixin.pay.util.WxPayUtil;
import com.whotel.common.base.Constants;
import com.whotel.common.enums.PayMent;
import com.whotel.common.enums.PayMode;
import com.whotel.common.enums.TradeStatus;
import com.whotel.common.util.DataHandlerUtil;
import com.whotel.common.util.Dom4jHelper;
import com.whotel.common.util.MD5Util;
import com.whotel.company.entity.Company;
import com.whotel.company.entity.PayConfig;
import com.whotel.company.enums.PayType;
import com.whotel.company.service.PayConfigService;
import com.whotel.front.controller.FanBaseController;
import com.whotel.front.entity.PayOrder;
import com.whotel.front.entity.WeixinFan;
import com.whotel.front.service.PayOrderService;
import com.whotel.hotel.service.HotelService;
import com.whotel.weixin.service.WeixinMessageService;

/**
 * 微信支付相关处理
 * @author 冯勇
 *
 */
@Controller
public class WxPayController extends FanBaseController {
	
	private static Logger log = Logger.getLogger(WxPayUtil.class);
	
	@Autowired
	private PayOrderService payOrderService;
	
	@Autowired
	private PayConfigService payConfigService;
	
	@Autowired
	private DataHandlerUtil handler;
	
	@Autowired
	private HotelService hotelService;
	
	@Autowired
	private WeixinMessageService weixinMessageService;
	
	/**
	 * 微信支付参数签名
	 * @param payOrder 支付订单
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/pay/wxpay", method = RequestMethod.POST)
	@ResponseBody
	public String wxpay(PayOrder payOrder, HttpServletRequest req) {
		
		if(payOrder == null || !payOrderService.checkPayOrder(payOrder)) {
			return null;
		}
		
		String wxApi = genWxApi(payOrder, req);
		return wxApi;
	}
	
	/**
	 * 微信支付参数签名
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/pay/wxSessionpay", method = RequestMethod.GET)
	@ResponseBody
	public String wxSessionpay(HttpServletRequest req) {
		
		PayOrder payOrder = (PayOrder) req.getSession().getAttribute(Constants.Session.PAY_ORDER);
		if(payOrder == null || !payOrderService.checkPayOrder(payOrder)) {
			return null;
		}
		String wxApi = genWxApi(payOrder, req);
		return wxApi;
	}

	private String genWxApi(PayOrder payOrder, HttpServletRequest req) {
		WeixinFan fan = getCurrentFan(req);
		Company company = getCurrentCompany(req);
		
		payOrder.setOpenId(fan.getOpenId());
		payOrder.setCompanyId(company.getId());
		
		payOrder.setPayMent(PayMent.WXPAY);
		payOrderService.savePayOrder(payOrder);
		
		PayConfig payConfig = payConfigService.getPayConfigByType(payOrder.getCompanyId(),PayType.WX_PROVIDER);
		if(payConfig==null||Boolean.FALSE.equals(payConfig.getValid())){
			payConfig = payConfigService.getPayConfigByType(payOrder.getCompanyId(),PayType.WX);
		}
		String payApi = WxPayUtil.genJsApi(payConfig, payOrder, getRealIp(req), null);
		return payApi;
	}

	/**
	 * 微信支付异步通知
	 * @param req
	 * @param resp
	 * @throws IOException
	 */
	@RequestMapping(value = "/pay/wxnotify", method = RequestMethod.POST)
	public void wxnotify(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setContentType("text/html;charset=UTF-8");
		PrintWriter out = resp.getWriter();
		String return_code = req.getParameter("return_code");
		String result_code = req.getParameter("result_code");
		
		Dom4jHelper dom4jDealor = null;
		try {
			dom4jDealor = new Dom4jHelper(req.getInputStream());
		} catch (DocumentException e) {
			log.error("READ XML ERROR", e);
		}
		if(dom4jDealor == null) {
			return;
		}
		String reqContent = dom4jDealor.getXML();
		log.info("respon:"+reqContent);
		Document reqData;
		try {
			reqData = DocumentHelper.parseText(reqContent);
			
			return_code = reqData.selectSingleNode("//return_code").getText();
			if(StringUtils.equals(return_code, "SUCCESS")) {
				result_code = reqData.selectSingleNode("//result_code").getText();
			}
			
			boolean rs = false;
			log.info("notify return_code:" + return_code + ",*********** result_code:" + result_code);
			if (StringUtils.equals(return_code, "SUCCESS") && StringUtils.equals(result_code, "SUCCESS")) {
				String out_trade_no = reqData.selectSingleNode("//out_trade_no").getText();
				String transaction_id = reqData.selectSingleNode("//transaction_id").getText();
				
				rs = payOrderService.handlePayOrder(out_trade_no, transaction_id, PayMent.WXPAY);
			} 
			if(rs) {
				if (log.isInfoEnabled())
					log.info("通知支付成功!");
				out.println("<xml><return_code>SUCCESS</return_code></xml>");
			} else {
				if (log.isInfoEnabled())
					log.info("通知支付失败!");
				out.println("<xml><return_code>FAIL</return_code></xml>");
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/pay/toWxScanCodePay")
	public String toWxPay(String hotelCode,HttpServletRequest req){
		WeixinFan weixinFan = getCurrentFan(req);
		Company company = getCurrentCompany(req);
		if(weixinFan!=null&&company!=null){
			req.setAttribute("company", company);
			req.setAttribute("hotelCode", hotelCode);
			return "/pay/wxpay";
		}
		return "";
	}
	
	@RequestMapping("/oauth/pay/scanCodePay")
	public String toScanCodePay(String hotelCode,HttpServletRequest req){
		Company company = getCurrentCompany(req);
		WeixinFan weixinFan = getCurrentFan(req);
//		PublicNo publicNo = getCurrentPublicNo(req);
		if(company!=null&&weixinFan!=null){
//			if(Boolean.FALSE.equals(weixinFan.isFocus())){
//				return "redirect:"+publicNo.getAttentionUrl();
//			}else{
			if(StringUtils.isNotBlank(hotelCode)){
				return "redirect:/pay/toWxScanCodePay.do?comid="+company.getId()+"&hotelCode="+hotelCode;
			}else{
				return "redirect:/pay/toWxScanCodePay.do?comid="+company.getId();
			}
//			}
		}
		return "";
	}
	
	@RequestMapping("/oauth/pay/ajaxSavePayOrder")
	@ResponseBody
	public String ajaxSavePayOrder(PayOrder payOrder,HttpServletRequest req){
		WeixinFan weixinFan = getCurrentFan(req);
		Company company = getCurrentCompany(req);
		if(weixinFan!=null&&company!=null&&payOrder!=null){
			payOrder.setPayMode(PayMode.PAY);
			payOrder.setOpenId(weixinFan.getOpenId());
			payOrder.setName("微信扫码支付");
			payOrder.setBusinessId("wxpay");
			payOrder.setCompanyId(company.getId());
			payOrder.setStatus(TradeStatus.WAIT_PAY);
			payOrder.setRemark("微信扫码支付");
			if(StringUtils.isNotBlank(payOrder.getHotelCode())){
				payOrder.setHotelNmae(hotelService.getHotel(company.getId(), payOrder.getHotelCode()).getName());
			}
			req.getSession().setAttribute(Constants.Session.PAY_ORDER,payOrder);
			return "";
		}
		return "-1";
	}
	
	@RequestMapping("/oauth/pay/sendPaySuccessMessage")
	@ResponseBody
	public void sendPaySuccessMessage(String orderSn, HttpServletRequest req){
		weixinMessageService.sendPaySuccessToCompany(orderSn);
	}
	
	@RequestMapping(value = "/pay/orderRefund", method = RequestMethod.POST)
	@ResponseBody
	public String toOrderRefund(String orderNo,String sign,Long totalAmount,HttpServletRequest req){
		PayOrder payOrder = payOrderService.getPayOrderByOrderSn(orderNo);
		payOrder.setRefundFee(totalAmount);
		if(!StringUtils.equals(sign, MD5Util.MD5(orderNo+payOrder.getOpenId()).toUpperCase())){
			return "秘钥错误";
		}
		PayConfig payConfig = payConfigService.getPayConfigByType(payOrder.getCompanyId(),PayType.WX_PROVIDER);
		if(payConfig==null||Boolean.FALSE.equals(payConfig.getValid())){
			payConfig = payConfigService.getPayConfigByType(payOrder.getCompanyId(),PayType.WX);
		}
		String msg = WxPayUtil.orderRefund(payConfig, payOrder);
		payOrder.setRemark(payOrder.getRemark()+"退款结果"+msg);
		payOrderService.savePayOrder(payOrder);
		return msg;
	}
}
