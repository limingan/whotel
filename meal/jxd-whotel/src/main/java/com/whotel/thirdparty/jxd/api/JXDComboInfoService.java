package com.whotel.thirdparty.jxd.api;

import java.util.List;

import org.apache.log4j.Logger;

import com.whotel.common.http.HttpHelper;
import com.whotel.common.http.HttpHelper.Response;
import com.whotel.thirdparty.jxd.JXDConstants;
import com.whotel.thirdparty.jxd.mode.HotelBranchQuery;
import com.whotel.thirdparty.jxd.mode.HotelCityQuery;
import com.whotel.thirdparty.jxd.mode.HotelQuery;
import com.whotel.thirdparty.jxd.mode.HotelServiceQuery;
import com.whotel.thirdparty.jxd.mode.InterfaceListQuery;
import com.whotel.thirdparty.jxd.mode.RoomInfoListQuery;
import com.whotel.thirdparty.jxd.mode.RoomPriceQuery;
import com.whotel.thirdparty.jxd.mode.vo.HotelBranchVO;
import com.whotel.thirdparty.jxd.mode.vo.HotelCityVO;
import com.whotel.thirdparty.jxd.mode.vo.HotelServiceVO;
import com.whotel.thirdparty.jxd.mode.vo.HotelVO;
import com.whotel.thirdparty.jxd.mode.vo.InterfaceListVO;
import com.whotel.thirdparty.jxd.mode.vo.RoomInfoVO;
import com.whotel.thirdparty.jxd.mode.vo.RoomPriceVO;
import com.whotel.thirdparty.jxd.util.ApiXmlVoParser;
import com.whotel.thirdparty.jxd.util.JxdXmlUtils;

/**
 * 套餐基础数据信息接口
 * @author 柯鹏程
 */
public class JXDComboInfoService extends JXDBaseInfoService{
	
	private static final Logger log = Logger.getLogger(JXDComboInfoService.class);
	
	/**
	 * 可预套餐列表
	 * @param query
	 * @return
	 * @throws Exception
	 */
	public List<RoomInfoVO> loadComboInfos(RoomInfoListQuery query,String hotelUrl) throws Exception {
		query.setOrderCategory("Combo");
		String param = JxdXmlUtils.toXml(query);
		if(log.isDebugEnabled()) {
			log.debug("param: \n" + param);
		}
		// 请求接口并获得响应
		List<RoomInfoVO> roomInfos = null;
		Response res = HttpHelper.connect(hotelUrl).header("Content-Type",
				"text/xml").timeout(JXDConstants.TIMEOUT).post(param);
		ApiXmlVoParser.checkReturnContent(res);
		roomInfos = ApiXmlVoParser.parseRoomInfoVOs(res.html(), res.charset());
		return roomInfos;
	}
	
	public List<HotelServiceVO> loadHotelServices(HotelServiceQuery query,String hotelUrl) throws Exception {
		String param = JxdXmlUtils.toXml(query);
		 if(log.isDebugEnabled()) {
			log.debug("param: \n" + param);
		 }
		 // 请求接口并获得响应
		 List<HotelServiceVO> hotelServices = null;
		 Response res = HttpHelper.connect(hotelUrl).header("Content-Type",
		 "text/xml").timeout(JXDConstants.TIMEOUT).post(param);
		 ApiXmlVoParser.checkReturnContent(res);
		 hotelServices = ApiXmlVoParser.parseHotelServiceVOs(res.html(), res.charset());
		 return hotelServices;
	}
	
	public List<RoomPriceVO> loadRoomPrices(RoomPriceQuery query,String hotelUrl) throws Exception {
		String param = JxdXmlUtils.toXml(query);
		 if(log.isDebugEnabled()) {
			log.debug("param: \n" + param);
		 }
		 // 请求接口并获得响应
		 List<RoomPriceVO> roomPriceVOs = null;
		 Response res = HttpHelper.connect(hotelUrl).header("Content-Type",
		 "text/xml").timeout(JXDConstants.TIMEOUT).post(param);
		 ApiXmlVoParser.checkReturnContent(res);
		 roomPriceVOs = ApiXmlVoParser.parseRoomPriceVOs(res.html(), res.charset());
		 return roomPriceVOs;
	}
	
	/**
	 * 酒店城市列表
	 * @return
	 * @throws Exception
	 */
	public List<HotelCityVO> loadHotelCityVOs(HotelCityQuery query,String hotelUrl) throws Exception {
		
		 String param = JxdXmlUtils.toXml(query);
		 if(log.isDebugEnabled()) {
			log.debug("param: \n" + param);
		 }
		 // 请求接口并获得响应
		 List<HotelCityVO> hotels = null;
		 Response res = HttpHelper.connect(hotelUrl).header("Content-Type",
		 "text/xml").timeout(JXDConstants.TIMEOUT).post(param);
		 ApiXmlVoParser.checkReturnContent(res);
		 hotels = ApiXmlVoParser.parseHotelCityVOs(res.html(), res.charset());
		 return hotels;
	}
	
	public List<InterfaceListVO> loadInterfaceListVOs(InterfaceListQuery query,String mbrUrl) throws Exception {
		String param = JxdXmlUtils.toXml(query);
		if(log.isDebugEnabled()) {
			log.debug("param: \n" + param);
		}
		// 请求接口并获得响应
		List<InterfaceListVO> interfaceLists = null;
		Response res = null;
		try {
			res = HttpHelper.connect(mbrUrl).header("Content-Type",
					"text/xml").timeout(JXDConstants.TIMEOUT).post(param);
			ApiXmlVoParser.checkReturnContent(res);
			interfaceLists = ApiXmlVoParser.parseInterfaceListVOs(res.html(), res.charset());
		} catch (Exception e) {
			log.error("param="+param+" url="+mbrUrl+" html="+(res==null?"":res.html()));
		}
		return interfaceLists;
	}
	
}
