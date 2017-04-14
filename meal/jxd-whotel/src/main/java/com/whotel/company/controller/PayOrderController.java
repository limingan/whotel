package com.whotel.company.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.weixin.pay.util.WxPayUtil;
import com.whotel.common.dao.mongo.Order;
import com.whotel.common.dao.mongo.Page;
import com.whotel.common.enums.FilterModel;
import com.whotel.common.enums.PayMent;
import com.whotel.common.enums.TradeStatus;
import com.whotel.company.entity.CompanyAdmin;
import com.whotel.company.entity.PayConfig;
import com.whotel.company.enums.PayType;
import com.whotel.company.service.PayConfigService;
import com.whotel.front.entity.PayOrder;
import com.whotel.front.service.PayOrderService;

@Controller
@RequestMapping("/company/payOrder")
public class PayOrderController extends BaseCompanyController {
	
	@Autowired
	protected PayOrderService payOrderService;
	
	@Autowired
	private PayConfigService payConfigService;
	
	@RequestMapping("/toPayOrder")
	public String toPayOrder(Page<PayOrder> page, HttpServletRequest req){
		CompanyAdmin companyAdmin = getCurrentCompanyAdmin(req);
		page.addFilter("companyId", FilterModel.EQ, companyAdmin.getCompanyId());
		page.addFilter("wxnotifyState", FilterModel.EQ, false);
		page.addOrder(Order.desc("createTime"));
		payOrderService.findPayOrders(page);
		return "company/pay/payOrder_list";
	}
	
	@RequestMapping("/handlePayOrder")
	public String handlePayOrder(String orderSn) throws UnsupportedEncodingException{
		PayOrder payOrder = payOrderService.getPayOrderByOrderSn(orderSn);
		PayConfig payConfig = payConfigService.getPayConfigByType(payOrder.getCompanyId(),PayType.WX_PROVIDER);
		if(payConfig==null||Boolean.FALSE.equals(payConfig.getValid())){
			payConfig = payConfigService.getPayConfigByType(payOrder.getCompanyId(),PayType.WX);
		}
		String transaction_id = WxPayUtil.queryOrder(payConfig, orderSn);
		boolean rs = false;
		if(StringUtils.isNotBlank(transaction_id)){
			rs = payOrderService.handlePayOrder(orderSn, transaction_id, PayMent.WXPAY);
		}
		if(!rs){
			return "redirect:/company/payOrder/toPayOrder.do?message="+URLEncoder.encode("订单处理失败", "UTF8");
		}
		return "company/pay/payOrder_list";
	}
}
