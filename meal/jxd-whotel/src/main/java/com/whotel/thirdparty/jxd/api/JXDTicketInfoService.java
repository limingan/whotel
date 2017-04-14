package com.whotel.thirdparty.jxd.api;

import java.util.List;

import org.apache.log4j.Logger;

import com.whotel.common.http.HttpHelper;
import com.whotel.common.http.HttpHelper.Response;
import com.whotel.thirdparty.jxd.JXDConstants;
import com.whotel.thirdparty.jxd.mode.TicketAccessoryQuery;
import com.whotel.thirdparty.jxd.mode.TicketBranchQuery;
import com.whotel.thirdparty.jxd.mode.TicketCityQuery;
import com.whotel.thirdparty.jxd.mode.TicketInfoListQuery;
import com.whotel.thirdparty.jxd.mode.TicketPriceDateQuery;
import com.whotel.thirdparty.jxd.mode.TicketPriceQuery;
import com.whotel.thirdparty.jxd.mode.TicketServiceQuery;
import com.whotel.thirdparty.jxd.mode.vo.TicketAccessoryVO;
import com.whotel.thirdparty.jxd.mode.vo.TicketBranchVO;
import com.whotel.thirdparty.jxd.mode.vo.TicketCityVO;
import com.whotel.thirdparty.jxd.mode.vo.TicketInfoVO;
import com.whotel.thirdparty.jxd.mode.vo.TicketPriceDateVO;
import com.whotel.thirdparty.jxd.mode.vo.TicketPriceVO;
import com.whotel.thirdparty.jxd.mode.vo.TicketServiceVO;
import com.whotel.thirdparty.jxd.util.ApiXmlVoParser;
import com.whotel.thirdparty.jxd.util.JxdXmlUtils;

/**
 * 基础数据信息接口
 * @author 冯勇
 */
public class JXDTicketInfoService {
	
	private static final Logger log = Logger.getLogger(JXDTicketInfoService.class);

	/**
	 * 门票城市列表
	 * @return
	 * @throws Exception
	 */
	public List<TicketCityVO> loadTicketCityVOs(TicketCityQuery query,String ticketUrl) throws Exception {
		
		 String param = JxdXmlUtils.toXml(query);
		 if(log.isDebugEnabled()) {
			log.debug("param: \n" + param);
		 }
		 // 请求接口并获得响应
		 List<TicketCityVO> ticketCitys = null;
		 Response res = HttpHelper.connect(ticketUrl).header("Content-Type",
		 "text/xml").timeout(JXDConstants.TIMEOUT).post(param);
		 ApiXmlVoParser.checkReturnContent(res);
		 ticketCitys = ApiXmlVoParser.parseTicketCitysVOs(res.html(), res.charset());
		 return ticketCitys;
	}
	
	/**
	 * 可预订门票列表
	 * @return
	 * @throws Exception
	 */
	public List<TicketBranchVO> loadTicketBranchs(TicketBranchQuery query,String ticketUrl) throws Exception {
		
		 String param = JxdXmlUtils.toXml(query);
		 if(log.isDebugEnabled()) {
			log.debug("param: \n" + param);
		 }
		 // 请求接口并获得响应
		 List<TicketBranchVO> tickets = null;
		 Response res = HttpHelper.connect(ticketUrl).header("Content-Type",
		 "text/xml").timeout(JXDConstants.TIMEOUT).post(param);
		 ApiXmlVoParser.checkReturnContent(res);
		 tickets = ApiXmlVoParser.parseTicketBranchVOs(res.html(), res.charset());
		 return tickets;
	}
	
	/**
	 * 可预订门票价格列表
	 * @param query
	 * @return
	 * @throws Exception
	 */
	public List<TicketInfoVO> loadTicketInfos(TicketInfoListQuery query,String ticketUrl) throws Exception {
		 String param = JxdXmlUtils.toXml(query);
		 if(log.isDebugEnabled()) {
			log.debug("param: \n" + param);
		 }
		 // 请求接口并获得响应
		 List<TicketInfoVO> ticketInfos = null;
		 Response res = HttpHelper.connect(ticketUrl).header("Content-Type",
		 "text/xml").timeout(JXDConstants.TIMEOUT).post(param);
		 ApiXmlVoParser.checkReturnContent(res);
		 ticketInfos = ApiXmlVoParser.parseTicketInfoVOs(res.html(), res.charset());
		 return ticketInfos;
	}
	
	public List<TicketServiceVO> loadTicketServices(TicketServiceQuery query,String ticketUrl) throws Exception {
		 String param = JxdXmlUtils.toXml(query);
		 if(log.isDebugEnabled()) {
			log.debug("param: \n" + param);
		 }
		 // 请求接口并获得响应
		 List<TicketServiceVO> ticketServices = null;
		 Response res = HttpHelper.connect(ticketUrl).header("Content-Type",
		 "text/xml").timeout(JXDConstants.TIMEOUT).post(param);
		 ApiXmlVoParser.checkReturnContent(res);
		 ticketServices = ApiXmlVoParser.parseTicketServiceVOs(res.html(), res.charset());
		 return ticketServices;
	}
	
	public List<TicketPriceVO> loadTicketPrices(TicketPriceQuery query,String ticketUrl) throws Exception {
		 String param = JxdXmlUtils.toXml(query);
		 if(log.isDebugEnabled()) {
			log.debug("param: \n" + param);
		 }
		 // 请求接口并获得响应
		 List<TicketPriceVO> ticketPriceVOs = null;
		 Response res = HttpHelper.connect(ticketUrl).header("Content-Type",
		 "text/xml").timeout(JXDConstants.TIMEOUT).post(param);
		 ApiXmlVoParser.checkReturnContent(res);
		 ticketPriceVOs = ApiXmlVoParser.parseTicketPriceVOs(res.html(), res.charset());
		 return ticketPriceVOs;
	}
	
	public List<TicketPriceDateVO> loadTicketPriceDateVOs(TicketPriceDateQuery query,String ticketUrl) throws Exception {
		 String param = JxdXmlUtils.toXml(query);
		 if(log.isDebugEnabled()) {
			log.debug("param: \n" + param);
		 }
		 // 请求接口并获得响应
		 List<TicketPriceDateVO> ticketPriceDateVOs = null;
		 Response res = HttpHelper.connect(ticketUrl).header("Content-Type",
		 "text/xml").timeout(JXDConstants.TIMEOUT).post(param);
		 ApiXmlVoParser.checkReturnContent(res);
		 ticketPriceDateVOs = ApiXmlVoParser.parseTicketPriceDateVOs(res.html(), res.charset());
		 return ticketPriceDateVOs;
	}
	
	public List<TicketAccessoryVO> loadTicketAccessoryVOs(TicketAccessoryQuery query,String ticketUrl) throws Exception {
		 String param = JxdXmlUtils.toXml(query);
		 if(log.isDebugEnabled()) {
			log.debug("param: \n" + param);
		 }
		 // 请求接口并获得响应
		 List<TicketAccessoryVO> ticketAccessorys = null;
		 Response res = HttpHelper.connect(ticketUrl).header("Content-Type",
		 "text/xml").timeout(JXDConstants.TIMEOUT).post(param);
		 ApiXmlVoParser.checkReturnContent(res);
		 ticketAccessorys = ApiXmlVoParser.parseTicketAccessoryVOs(res.html(), res.charset());
		 return ticketAccessorys;
	}
}
