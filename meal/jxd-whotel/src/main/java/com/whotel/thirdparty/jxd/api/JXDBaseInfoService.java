package com.whotel.thirdparty.jxd.api;

import java.util.List;

import org.apache.log4j.Logger;

import com.whotel.common.http.HttpHelper;
import com.whotel.common.http.HttpHelper.Response;
import com.whotel.thirdparty.jxd.JXDConstants;
import com.whotel.thirdparty.jxd.mode.HotelBranchQuery;
import com.whotel.thirdparty.jxd.mode.HotelQuery;
import com.whotel.thirdparty.jxd.mode.vo.HotelBranchVO;
import com.whotel.thirdparty.jxd.mode.vo.HotelVO;
import com.whotel.thirdparty.jxd.util.ApiXmlVoParser;
import com.whotel.thirdparty.jxd.util.JxdXmlUtils;

public class JXDBaseInfoService {
	
	private static final Logger log = Logger.getLogger(JXDBaseInfoService.class);
	
	/**
	 * 酒店列表
	 * @return
	 * @throws Exception
	 */
	public List<HotelVO> loadHotels(String hotelUrl) throws Exception {
		
		 HotelQuery query = new HotelQuery();
		
		 String param = JxdXmlUtils.toXml(query);
		 if(log.isDebugEnabled()) {
			log.debug("param: \n" + param);
		 }
		 // 请求接口并获得响应
		 List<HotelVO> hotels = null;
		 Response res = HttpHelper.connect(hotelUrl).header("Content-Type",
				 "text/xml").timeout(JXDConstants.TIMEOUT).post(param);
		 ApiXmlVoParser.checkReturnContent(res);
		 hotels = ApiXmlVoParser.parseHotelVOs(res.html(), res.charset());
		 return hotels;
	}
	
	/**
	 * 可预订酒店列表
	 * @return
	 * @throws Exception
	 */
	public List<HotelBranchVO> loadHotelBranchs(HotelBranchQuery query,String hotelUrl) throws Exception {
		
		 String param = JxdXmlUtils.toXml(query);
		 if(log.isDebugEnabled()) {
			log.debug("param: \n" + param);
		 }
		 // 请求接口并获得响应
		 List<HotelBranchVO> hotels = null;
		 Response res = HttpHelper.connect(hotelUrl).header("Content-Type",
		 "text/xml").timeout(JXDConstants.TIMEOUT).post(param);
		 ApiXmlVoParser.checkReturnContent(res);
		 hotels = ApiXmlVoParser.parseHotelBranchVOs(res.html(), res.charset());
		 return hotels;
	}
	
	
}
