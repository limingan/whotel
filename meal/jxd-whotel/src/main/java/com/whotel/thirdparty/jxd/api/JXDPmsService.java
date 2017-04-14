package com.whotel.thirdparty.jxd.api;

import java.util.List;

import org.apache.log4j.Logger;

import com.whotel.common.http.HttpHelper;
import com.whotel.common.http.HttpHelper.Response;
import com.whotel.common.util.MD5Util;
import com.whotel.thirdparty.jxd.JXDConstants;
import com.whotel.thirdparty.jxd.mode.HotelBranchQuery;
import com.whotel.thirdparty.jxd.mode.HotelCityQuery;
import com.whotel.thirdparty.jxd.mode.RoomInfoListQuery;
import com.whotel.thirdparty.jxd.mode.RoomPriceQuery;
import com.whotel.thirdparty.jxd.mode.vo.HotelBranchVO;
import com.whotel.thirdparty.jxd.mode.vo.HotelCityVO;
import com.whotel.thirdparty.jxd.mode.vo.RoomInfoVO;
import com.whotel.thirdparty.jxd.mode.vo.RoomPriceVO;
import com.whotel.thirdparty.jxd.util.ApiXmlVoParser;
import com.whotel.thirdparty.jxd.util.JxdXmlUtils;
import com.whotel.thirdparty.jxd.util.ApiXmlPmsVoParser;

public class JXDPmsService {
	
	private static final Logger log = Logger.getLogger(JXDPmsService.class);
	
	///////////////////////////////////客房////////////////////////////////////////////
	/**
	 * 集团分店列表
	 * @param query
	 * @param hotelUrl
	 * @return
	 * @throws Exception
	 */
	public List<HotelBranchVO> loadHotelBranchs(HotelBranchQuery query,String code,String key,String url) throws Exception {
		query.setOpType("集团分店列表");
		query.setxType("JxdBSPms");
		
		String param = JxdXmlUtils.toXml(query);
		if(log.isDebugEnabled()) {
			log.debug("param: \n" + param);
		}
		 
		url += "?grpid="+code+"&channel=C01&sign="+MD5Util.MD5(key+code+"C01"+param).toLowerCase();
		 
		 // 请求接口并获得响应
		 List<HotelBranchVO> hotels = null;
		 Response res = HttpHelper.connect(url).header("Content-Type","text/xml").timeout(JXDConstants.TIMEOUT).post(param);
		 ApiXmlVoParser.checkReturnContent(res);
		 hotels = ApiXmlVoParser.parseHotelBranchVOs(res.html(), res.charset());
		 return hotels;
	}
	
	/**
	 * 分店价格体系列表
	 * @param query
	 * @return
	 * @throws Exception
	 */
	public List<RoomInfoVO> loadRoomInfos(RoomInfoListQuery query,String code,String key,String url) throws Exception {
		query.setOpType("分店价格体系列表");
		query.setxType("JxdBSPms");
		query.setOrderCategory("Room");
		
		String param = JxdXmlUtils.toXml(query);
		if(log.isDebugEnabled()) {
			log.debug("param: \n" + param);
		}
		
		url += "?grpid="+code+"&channel=C01&sign="+MD5Util.MD5(key+code+"C01"+param).toLowerCase();
		
		 // 请求接口并获得响应
		 List<RoomInfoVO> roomInfos = null;
		 Response res = HttpHelper.connect(url).header("Content-Type","text/xml").timeout(JXDConstants.TIMEOUT).post(param);
		 ApiXmlVoParser.checkReturnContent(res);
		 roomInfos = ApiXmlPmsVoParser.parseRoomInfoVOs(res.html(), res.charset());
		 return roomInfos;
	}
	/**
	 * 分店价格明细日历
	 */
	public List<RoomPriceVO> loadRoomPrices(RoomPriceQuery query,String code,String key,String url) throws Exception {
		query.setOpType("分店价格明细日历");
		query.setxType("JxdBSPms");
		query.setSource(null);
		query.setSaleCode(null);
		
		String param = JxdXmlUtils.toXml(query);
		if(log.isDebugEnabled()) {
			log.debug("param: \n" + param);
		}
		
		url += "?grpid="+code+"&channel=C01&sign="+MD5Util.MD5(key+code+"C01"+param).toLowerCase();
		
		// 请求接口并获得响应
		List<RoomPriceVO> roomPriceVOs = null;
		Response res = HttpHelper.connect(url).header("Content-Type","text/xml").timeout(JXDConstants.TIMEOUT).post(param);
		ApiXmlVoParser.checkReturnContent(res);
		roomPriceVOs = ApiXmlPmsVoParser.parseRoomPriceVOs(res.html(), res.charset());
		return roomPriceVOs;
	}
	
	/**
	 * 酒店城市列表
	 * @return
	 * @throws Exception
	 */
	public List<HotelCityVO> loadHotelCityVOs(HotelCityQuery query,String code,String key,String url) throws Exception {
		query.setxType("JxdBSPms");
		 String param = JxdXmlUtils.toXml(query);
		 if(log.isDebugEnabled()) {
			log.debug("param: \n" + param);
		 }
		 url += "?grpid="+code+"&channel=C01&sign="+MD5Util.MD5(key+code+"C01"+param).toLowerCase();
		 // 请求接口并获得响应
		 List<HotelCityVO> hotels = null;
		 Response res = HttpHelper.connect(url).header("Content-Type",
		 "text/xml").timeout(JXDConstants.TIMEOUT).post(param);
		 ApiXmlVoParser.checkReturnContent(res);
		 hotels = ApiXmlVoParser.parseHotelCityVOs(res.html(), res.charset());
		 return hotels;
	}
}
