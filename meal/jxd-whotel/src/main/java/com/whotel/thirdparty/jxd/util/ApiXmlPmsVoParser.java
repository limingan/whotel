package com.whotel.thirdparty.jxd.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.DocumentException;

import com.whotel.common.enums.Gender;
import com.whotel.common.util.DateUtil;
import com.whotel.common.util.Dom4jHelper;
import com.whotel.thirdparty.jxd.mode.vo.HotelOrderVO;
import com.whotel.thirdparty.jxd.mode.vo.HotelOrderVO.OrderDetailVO;
import com.whotel.thirdparty.jxd.mode.vo.MemberVO;
import com.whotel.thirdparty.jxd.mode.vo.RoomInfoVO;
import com.whotel.thirdparty.jxd.mode.vo.RoomPriceVO;

public class ApiXmlPmsVoParser {
	
	private static final Logger log = Logger.getLogger(ApiXmlPmsVoParser.class);
	
	/**
	 * 解析xml为Map
	 * @param xml
	 * @return
	 * @throws DocumentException
	 * @throws UnsupportedEncodingException
	 */
	public static Map<String, String> parseXml2Map(String xml, String node, String charset) throws DocumentException, UnsupportedEncodingException {
		InputStream ins = new ByteArrayInputStream(xml.getBytes(charset));
		Dom4jHelper dom4jHelper = new Dom4jHelper(ins, charset);

		Map<String, String> map = dom4jHelper.getAllElements(node);
		return map;
	}
	
	/////////////////////////////////////////////////客房//////////////////////////////////////////////
	/**
	 * 分店价格体系列表
	 * @param xml
	 * @param charset
	 * @return
	 * @throws DocumentException
	 * @throws UnsupportedEncodingException
	 */
	public static List<RoomInfoVO> parseRoomInfoVOs(String xml, String charset) throws DocumentException, UnsupportedEncodingException {
		InputStream ins = new ByteArrayInputStream(xml.getBytes(charset));
		Dom4jHelper dom4jHelper = new Dom4jHelper(ins, charset);
		List<Map<String, String>> list = dom4jHelper.getListElements("Row");
		return parseRoomInfoVOs(list);
	}

	private static List<RoomInfoVO> parseRoomInfoVOs(
			List<Map<String, String>> list) {
		if(list != null) {
			List<RoomInfoVO> roomInfos = new ArrayList<RoomInfoVO>();
			
			for(Map<String, String> map : list) {
				RoomInfoVO roomInfo = new RoomInfoVO();
				roomInfo.setOrderItemCode(map.get("OrderItemCode"));
				roomInfo.setOrderItemCName(map.get("OrderItemCName"));
				roomInfo.setcDescribe(map.get("CDescribe"));
				//roomInfo.setImageid2(map.get("ImageId2"));
				roomInfo.setPriceName(map.get("PriceName"));
				roomInfo.setAveprice(map.get("Aveprice"));
				roomInfo.setAvebreakfast(map.get("Avebreakfast"));
				roomInfo.setPaymethod(map.get("Paymethod"));
				roomInfo.setMode(map.get("Mode"));
				roomInfo.setRatecode(map.get("Ratecode"));
				roomInfo.setPricesystemid(map.get("Pricesystemid"));
				roomInfo.setSalepromotionid(map.get("SalePromotionId"));
				roomInfo.setHotelImageLoadUrl(map.get("HotelImageLoadUrl"));
				roomInfo.setBookingNoticeCdesc(map.get("BookingNoticeCdesc"));
				roomInfo.setImageNameList(map.get("ImageNameList"));
				roomInfo.setServiceList(map.get("ServiceList"));
				if(map.get("CanBooking")!=null&&map.get("CanBooking").equals("0")){//1为可预订，0为不可预订
					roomInfo.setCanBooking(false);
				}else{
					roomInfo.setCanBooking(true);
				}
				roomInfo.setNoBookingReason(map.get("NoBookingReason"));
				try {
					roomInfo.setMaxReturnMoneyPay(Float.valueOf(map.get("MaxReturnMoneyPay")));
				} catch (Exception ex) {
					roomInfo.setMaxReturnMoneyPay(0f);
				}
				try {
					roomInfo.setMaxScorePay(Float.valueOf(map.get("MaxScorePay")));
				} catch (Exception ex) {
					roomInfo.setMaxScorePay(0f);
				}
				try {
					roomInfo.setMaxCouponCountPay(Float.valueOf(map.get("MaxCouponCountPay")));
				} catch (Exception ex) {
					roomInfo.setMaxCouponCountPay(0f);
				}
				roomInfo.setBookingNoticeCdesc(map.get("BookingNoticeCdesc"));
				roomInfo.setOrderBy(map.get("OrderBy"));
				roomInfo.setIsWqCombine(map.get("IsWqCombine"));
				roomInfo.setAvaQty(map.get("AvaQty"));
				roomInfos.add(roomInfo);
			}
			return roomInfos;
		}
		return null;
	}
	/**
	 * 分店价格明细日历
	 * @param xml
	 * @param charset
	 * @return
	 * @throws DocumentException
	 * @throws UnsupportedEncodingException
	 */
	public static List<RoomPriceVO> parseRoomPriceVOs(String xml, String charset) throws DocumentException, UnsupportedEncodingException {
		InputStream ins = new ByteArrayInputStream(xml.getBytes(charset));
		Dom4jHelper dom4jHelper = new Dom4jHelper(ins, charset);
		List<Map<String, String>> list = dom4jHelper.getListElements("Row");
		return parseRoomPriceVOs(list);
	}

	private static List<RoomPriceVO> parseRoomPriceVOs(
			List<Map<String, String>> list) {
		if(list != null) {
			List<RoomPriceVO> roomPriceVOs = new ArrayList<RoomPriceVO>();
			
			for(Map<String, String> map : list) {
				RoomPriceVO roomPrice = new RoomPriceVO();
				roomPrice.setPriceDate(map.get("PriceDate"));
				roomPrice.setPriceName(map.get("PriceName"));
				roomPrice.setOrderItemCode(map.get("orderItemCode"));
				roomPrice.setItemCname(map.get("ItemCname"));
				roomPrice.setPrice(map.get("Price"));
				roomPrice.setBreakfast(map.get("breakfast"));
				roomPrice.setServicerate(map.get("servicerate"));
				roomPrice.setBookingNoticeCdesc(map.get("BookingNoticeCdesc"));
				roomPrice.setCancelNoticeCdesc(map.get("CancelNoticeCdesc"));
				roomPrice.setWeeknum(map.get("weeknum"));
				try {
					roomPrice.setMaxReturnMoneyPay(Float.valueOf(map.get("MaxReturnMoneyPay")));
				} catch (Exception ex) {
					roomPrice.setMaxReturnMoneyPay(0f);
				}
				try {
					roomPrice.setMaxScorePay(Float.valueOf(map.get("MaxScorePay")));
				} catch (Exception ex) {
					roomPrice.setMaxScorePay(0f);
				}
				try {
					roomPrice.setMaxCouponCountPay(Float.valueOf(map.get("MaxCouponCountPay")));
				} catch (Exception ex) {
					roomPrice.setMaxCouponCountPay(0f);
				}
				if(map.get("CanBooking")!=null&&map.get("CanBooking").equals("0")){//1为可预订，0为不可预订
					roomPrice.setCanBooking(false);
				}else{
					roomPrice.setCanBooking(true);
				}
				roomPrice.setNoBookingReason(map.get("NoBookingReason"));
				roomPriceVOs.add(roomPrice);
			}
			return roomPriceVOs;
		}
		return null;
	}
	
	//////////////////////////////////////////////////////会员/////////////////////////////////////////////////////
	public static MemberVO parseMemberVO(String xml, String charset) throws DocumentException, UnsupportedEncodingException {
		Map<String, String> map = parseXml2Map(xml, "Row", charset);

		MemberVO memberVO = new MemberVO();
		memberVO.setProfileId(map.get("ProfileId"));
		memberVO.setGuestType(map.get("GuestType"));
		memberVO.setIsMember(map.get("IsMember"));
		//memberVO.setVipType(map.get("VIPType"));
		memberVO.setMbrCardType(map.get("MbrCardType"));
		memberVO.setMbrCardTypeName(map.get("MbrCardTypeName"));
		memberVO.setBlackList(map.get("BlackList"));
		memberVO.setMbrCardNo(map.get("MbrCardNo"));
		memberVO.setRfCardNo(map.get("RFCardNo"));
		memberVO.setCreditLevel(map.get("CreditLevel"));
		memberVO.setMbrExpired(map.get("MbrExpired"));
		memberVO.setGuestCName(map.get("GuestCName"));
		memberVO.setGuestEName(map.get("GuestEName"));
		memberVO.setCttNo(map.get("CttNo"));
		memberVO.setContactor(map.get("Contactor"));
		memberVO.setRateCode(map.get("RateCode"));
		//memberVO.setHalfTimeBegin(map.get("HalfTimeBegin"));
		//memberVO.setDayRentBegin(map.get("DayRentBegin"));
		//memberVO.setSalutation(map.get("Salutation"));
		memberVO.setCertificateType(map.get("CertificateType"));
		memberVO.setCertificateNo(map.get("CertificateNo"));
		String gender = map.get("Gender");
		if (StringUtils.equals(gender, "男")) {
			memberVO.setGender(Gender.MALE);
		} else {
			memberVO.setGender(Gender.FEMALE);
		}
		memberVO.setBirthday(map.get("Birthday"));
		//memberVO.setNationality(map.get("Nationality"));
		//memberVO.setVisaExpired(map.get("VisaExpired"));
		memberVO.setAddress(map.get("Address"));
		//memberVO.setZipCode(map.get("ZipCode"));
		//memberVO.setTel(map.get("Tel"));
		//memberVO.setOfficeTel(map.get("OfficeTel"));
		memberVO.setMobile(map.get("Mobile"));
		//memberVO.setFax(map.get("Fax"));
		memberVO.setEmail(map.get("Email"));
		try {
			memberVO.setBalance(Float.valueOf(map.get("Balance")));
		} catch (Exception e) {
			memberVO.setBalance(0f);
		}
		try {
			memberVO.setDebtAmount(Float.valueOf(map.get("DebtAmount")));
		} catch (Exception e) {
			memberVO.setDebtAmount(0f);
		}
		
		try {
			memberVO.setValidScore(Float.valueOf(map.get("ValidScore")));
		} catch (Exception e) {
			memberVO.setValidScore(0f);
		}
		memberVO.setCreateDate(DateUtil.parseDatetime(map.get("CreateDate")));
		memberVO.setRemark(map.get("Remark"));
		
		try {
			memberVO.setTicketqty(Integer.valueOf(map.get("ticketqty")));
		} catch (Exception e) {
			memberVO.setTicketqty(0);
		}
		
		try {
			memberVO.setTicketamt(Float.valueOf(map.get("ticketamt")));
		} catch (Exception e) {
			memberVO.setTicketamt(0f);
		}
		
		try {
			memberVO.setTotalUsedBalance(Float.valueOf(map.get("TotalUsedBalance")));
		} catch (Exception e) {
			memberVO.setTotalUsedBalance(0f);
		}
		
		try {
			memberVO.setSubCardCount(Integer.valueOf(map.get("SubCardCount")));
		} catch (Exception e) {
			memberVO.setSubCardCount(0);
		}
		
		try {
			memberVO.setBaseAmtBalance(Float.valueOf(map.get("BaseAmtBalance")));
		} catch (Exception e) {
			memberVO.setBaseAmtBalance(0f);
		}
		try {
			memberVO.setIncamount(Float.valueOf(map.get("incamount")));
		} catch (Exception e) {
			memberVO.setIncamount(0f);
		}
		try {
			memberVO.setIncamt(Float.valueOf(map.get("incamt")));
		} catch (Exception e) {
			memberVO.setIncamt(0f);
		}
		try {
			memberVO.setDeductamt(Float.valueOf(map.get("deductamt")));
		} catch (Exception e) {
			memberVO.setDeductamt(0f);
		}
//		try {
//			memberVO.setChargeamt(Float.valueOf(map.get("chargeamt")));
//		} catch (Exception e) {
//			memberVO.setChargeamt(0f);
//		}
		try {
			memberVO.setUsedScore(Float.valueOf(map.get("UsedScore")));
		} catch (Exception e) {
			memberVO.setUsedScore(0f);
		}
		memberVO.setSaleName(map.get("SaleName"));
		memberVO.setSales(map.get("Sales"));
		memberVO.setEmail(map.get("SaleMobile"));
		if(memberVO.getBalance()!=null && memberVO.getIncamount()!=null){
			memberVO.setBalance(memberVO.getBalance()-memberVO.getIncamount());
		}
		
		try {
			memberVO.setGainedScore(Float.valueOf(map.get("GainedScore")));
		} catch (Exception e) {
			memberVO.setGainedScore(0f);
		}
		memberVO.setResortID(map.get("ResortID"));
		memberVO.setRoomNo(map.get("RoomNo"));
		memberVO.setStatus(map.get("Status"));
		memberVO.setPortraitID(map.get("PortraitID"));
		memberVO.setPortraitURL(map.get("PortraitURL"));
		return memberVO;
	}
}
