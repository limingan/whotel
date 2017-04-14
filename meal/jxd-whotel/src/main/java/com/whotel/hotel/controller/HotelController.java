package com.whotel.hotel.controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.map.LinkedMap;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.whotel.card.entity.Guest;
import com.whotel.card.service.MemberService;
import com.whotel.card.service.MemberTradeService;
import com.whotel.common.base.Constants;
import com.whotel.common.dao.mongo.Page;
import com.whotel.common.enums.PayMent;
import com.whotel.common.enums.PayMode;
import com.whotel.common.enums.TradeStatus;
import com.whotel.common.util.DateUtil;
import com.whotel.common.util.MoneyUtil;
import com.whotel.company.entity.ArriveTime;
import com.whotel.company.entity.Company;
import com.whotel.company.entity.InterfaceConfig;
import com.whotel.company.entity.SysParamConfig;
import com.whotel.company.enums.ModuleType;
import com.whotel.company.service.ArriveTimeService;
import com.whotel.company.service.CompanyService;
import com.whotel.company.service.InterfaceConfigService;
import com.whotel.company.service.SysParamConfigService;
import com.whotel.ext.redis.RedisCache;
import com.whotel.front.controller.FanBaseController;
import com.whotel.front.entity.PayOrder;
import com.whotel.front.entity.WeixinFan;
import com.whotel.front.service.PayOrderService;
import com.whotel.hotel.entity.CommentConfig;
import com.whotel.hotel.entity.Hotel;
import com.whotel.hotel.entity.HotelOrder;
import com.whotel.hotel.entity.HotelOtherService;
import com.whotel.hotel.entity.RoomPrice;
import com.whotel.hotel.enums.HotelOrderStatus;
import com.whotel.hotel.service.HotelCommentService;
import com.whotel.hotel.service.HotelOrderService;
import com.whotel.hotel.service.HotelService;
import com.whotel.hotel.service.RoomTypeService;
import com.whotel.thirdparty.jxd.api.JXDOrderService;
import com.whotel.thirdparty.jxd.mode.HotelBranchQuery;
import com.whotel.thirdparty.jxd.mode.HotelCityQuery;
import com.whotel.thirdparty.jxd.mode.HotelServiceQuery;
import com.whotel.thirdparty.jxd.mode.RoomBookQuery;
import com.whotel.thirdparty.jxd.mode.RoomInfoListQuery;
import com.whotel.thirdparty.jxd.mode.RoomPriceQuery;
import com.whotel.thirdparty.jxd.mode.vo.CategoryCodeVO;
import com.whotel.thirdparty.jxd.mode.vo.HotelBranchVO;
import com.whotel.thirdparty.jxd.mode.vo.HotelCityVO;
import com.whotel.thirdparty.jxd.mode.vo.HotelServiceVO;
import com.whotel.thirdparty.jxd.mode.vo.HotelVO;
import com.whotel.thirdparty.jxd.mode.vo.MemberCouponVO;
import com.whotel.thirdparty.jxd.mode.vo.MemberVO;
import com.whotel.thirdparty.jxd.mode.vo.RoomInfoVO;
import com.whotel.thirdparty.jxd.mode.vo.RoomPriceVO;
import com.whotel.weixin.service.WeixinInterfaceService;
import com.whotel.weixin.service.WeixinMessageService;

@Controller
public class HotelController extends FanBaseController {

	@Autowired
	private HotelService hotelService;
	
	@Autowired
	private RoomTypeService roomTypeService;
	
	@Autowired
	private HotelOrderService hotelOrderService;
	
	@Autowired
	private MemberTradeService memberTradeService;
	
	@Autowired
	private ArriveTimeService arriveTimeService;
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	WeixinInterfaceService weixinInterfaceService;
	
	@Autowired
	CompanyService companyService;
	
	@Autowired
	WeixinMessageService weixinMessageService;
	
	@Autowired
	private RedisCache cache;
	
	@Autowired
	private SysParamConfigService sysParamConfigService;
	
	@Autowired
	private PayOrderService payOrderService;

	@Autowired
	private HotelCommentService hotelCommentService;
	
	@Autowired
	private InterfaceConfigService interfaceConfigService;
	
	@RequestMapping("/oauth/hotel/listHotelBranchs")
	public String listHotelBranchs(HotelBranchQuery query, HttpServletRequest req) {
		Company company = getCurrentCompany(req);
		if(company != null) {
			
			String beginDate = query.getBeginDate();
			String endDate = query.getEndDate();
			
			if(beginDate == null || endDate == null) {
				Calendar cal = Calendar.getInstance();
				query.setBeginDate(DateUtil.format(cal, "yyyy-MM-dd"));
				cal.add(Calendar.DATE, 1);
				query.setEndDate(DateUtil.format(cal, "yyyy-MM-dd"));
			}
			List<HotelBranchVO> hotels = hotelService.listHotelBranchVO(company.getId(), query);
			req.setAttribute("hotels", hotels);
		}
		return "/front/hotel/hotel_list";
	}
	
	@RequestMapping("/oauth/hotel/listHotels")
	public String listHotels(HttpServletRequest req) {
		Company company = getCurrentCompany(req);
		if(company != null) {
			List<HotelVO> hotels = hotelService.listHotelVO(company.getId());
			req.setAttribute("hotels", hotels);
		}
		return "/front/hotel/hotel_list";
	}
	
	@RequestMapping("/oauth/hotel/showHotel")
	public String showHotel(String code, HttpServletRequest req) {
		Company company = getCurrentCompany(req);
		HotelBranchVO hotelBranchVO = hotelService.getHotelBranchVO(company.getId(), code);
		Hotel hotel = hotelService.getHotel(company.getId(), code);
		req.setAttribute("hotelBranchVO", hotelBranchVO);
		req.setAttribute("hotel", hotel);
		return "/front/hotel/hotel_info";
	}
	
	@RequestMapping("/oauth/hotel/hotelSearch")
	public String hotelSearch(HttpServletRequest req) {
		Company company = getCurrentCompany(req);
		if(company.getGroup()!=null && company.getGroup()) {
			Calendar cal = Calendar.getInstance();
			req.setAttribute("startDate", cal.getTime());
			cal.add(Calendar.DATE, 1);
			req.setAttribute("endDate", cal.getTime());
			
			List<HotelCityVO> hotelCitys = hotelService.listHotelCityVO(company.getId(), new HotelCityQuery());
			req.setAttribute("hotelCitys", hotelCitys);
			
			/*List<Hotel> hotels = hotelService.findHotelByCity(company.getId(), null);
			req.setAttribute("hotels", hotels);*/
			
			HotelBranchQuery branchQuery = new HotelBranchQuery();
			branchQuery.setBeginDate(DateUtil.format(new Date(), "yyyy-MM-dd"));
			branchQuery.setEndDate(DateUtil.getNextDate(new Date(), 1));
			List<HotelBranchVO> hotelBranchs = hotelService.listHotelBranchVO(company.getId(), branchQuery);
			req.setAttribute("hotels", hotelBranchs);
			return "/front/hotel/hotel_search"+getCurrentCompany(req).getTheme();
		} else {
			return "redirect:/oauth/hotel/listRooms.do";
		}
	}
	
	@RequestMapping("/oauth/hotel/listRooms")
	public String listRooms(RoomInfoListQuery query, HttpServletRequest req) {
		Company company = getCurrentCompany(req);
		
		String hotelCode = query.getHotelCode();
		
		Date beginDate = query.getBeginDate();
		Date endDate = query.getEndDate();
		
		if(beginDate == null || endDate == null) {
			Calendar cal = Calendar.getInstance();
			query.setBeginDate(cal.getTime());
			cal.add(Calendar.DATE, 1);
			query.setEndDate(cal.getTime());
		}
		
		Integer roomQty = query.getRoomQty();
		if(roomQty == null) {
			query.setRoomQty(1);
		}
		req.setAttribute("query", query);
		
		if(StringUtils.isBlank(hotelCode)) {
			
			String addr = query.getAddr();
			String name = query.getName();
			
			Page<HotelBranchVO> page = getHotelBranchVOs(1,DateUtil.formatDate(query.getBeginDate()),DateUtil.formatDate(query.getEndDate()), addr, name,company.getId());
			
			int size = page.getResult().size();
			
			if(size > 1) {
				List<HotelBranchVO> branchs = page.getResult();
				req.setAttribute("totalPages", page.getTotalPages());
				req.setAttribute("hotelBranchs", branchs);
				
				Map<String, String> hotelMap = hotelService.getHotelMiniatureUrlByCode(company.getId());
				req.setAttribute("hotelMap", hotelMap);
				return "/front/hotel/hotel_list"+company.getTheme();
			} else if(size > 0) {
				HotelBranchVO hotelBranchVO = page.getResult().get(0);
				hotelCode = hotelBranchVO.getCode();
				query.setHotelCode(hotelBranchVO.getCode());
				req.setAttribute("hotelName", hotelBranchVO.getCname());
			}
		} else {
			HotelVO hotelVO = hotelService.getHotelVO(company.getId(), hotelCode);
			if(hotelVO != null) {
				req.setAttribute("hotelName", hotelVO.getcName());
			}
		}
		
		if(StringUtils.isNotBlank(company.getTheme())){
			HotelBranchVO hotelBranchVO = hotelService.getHotelBranchVO(company.getId(), hotelCode);
			Hotel hotel = hotelService.getHotel(company.getId(), hotelCode);
			req.setAttribute("hotelBranchVO", hotelBranchVO);
			req.setAttribute("hotel", hotel);
		}
		
		SysParamConfig sysParamConfig = sysParamConfigService.getSysParamConfig(company.getId());
		if(sysParamConfig != null && Boolean.FALSE.equals(sysParamConfig.getIsHotelRegister())){
//			if(Boolean.FALSE.equals(sysParamConfig.getIsHotelRegister())){//禁止会员注册
				req.getSession().setAttribute(Constants.Session.SKIP_BIND, true);
//			}
		}else{
			MemberVO memberVO = getCurrentMemberVO(req);
			if(memberVO != null) {
				query.setProfileId(memberVO.getProfileId());
				query.setMbrCardTypeCode(memberVO.getMbrCardType());
				req.setAttribute("isMember", true);
			}
			req.getSession().setAttribute(Constants.Session.SKIP_BIND, null);
		}
		
		Map<RoomInfoVO, List<RoomInfoVO>> roomInfoMaps = new LinkedMap();
		int totalPageSize = getRoomInfoVOs(1,query,company.getId(),roomInfoMaps);
		req.setAttribute("totalPageSize", totalPageSize);
		req.setAttribute("roomInfoMaps", roomInfoMaps);
		
		CommentConfig commentConfig = hotelCommentService.getCommentConfig(company.getId());
		req.setAttribute("commentConfig", commentConfig);
		return "/front/hotel/room_list"+company.getTheme();
	}
	
	@SuppressWarnings("unchecked")
	private int getRoomInfoVOs(Integer pageNo,RoomInfoListQuery query,String companyId,Map<RoomInfoVO, List<RoomInfoVO>> riMap){
		
		Map<RoomInfoVO, List<RoomInfoVO>> roomInfoMaps = null;
		String key = query.toString().replaceAll("\n|\t|\r", "").replaceAll(" ", "")+companyId;
		if(cache.get(key)==null){
			roomInfoMaps = roomTypeService.listRoomInfoVO(companyId, query);
			if(roomInfoMaps!=null&&roomInfoMaps.size()>0){
				cache.put(key, roomInfoMaps, 60*3);
			}
		}else{
			roomInfoMaps = (Map<RoomInfoVO, List<RoomInfoVO>>)cache.get(key);
		}
		
		int  totalCount = 0;
		int pageSize = 6;
		if(roomInfoMaps != null) {
			totalCount = roomInfoMaps.keySet().size();
			if(totalCount>pageSize){
				int beginIndex = (pageNo-1)*pageSize;
				long endIndex = (beginIndex+pageSize)>totalCount?totalCount:(beginIndex+pageSize);
				List<RoomInfoVO> list = new ArrayList<RoomInfoVO>(roomInfoMaps.keySet());
//				Collections.sort(list, new Comparator<RoomInfoVO>() {
//
//					@Override
//					public int compare(RoomInfoVO o1, RoomInfoVO o2) {
//						if(o1 != null && o2 != null) {
//							Float price1 = Float.valueOf(o1.getAveprice());
//							Float price2 = Float.valueOf(o2.getAveprice());
//							if(price1 != null && price2 != null) {
//								if(price1 > price2) {
//									return 1;
//								} else if(price1 < price2) {
//									return -1;
//								}
//							}
//						}
//						return 0;
//					}
//				});
				
				for (int i=beginIndex;i<endIndex;i++) {
					riMap.put(list.get(i), roomInfoMaps.get(list.get(i)));
				}
			}else{
				riMap.putAll(roomInfoMaps);
			}
		}
		
		return (totalCount%pageSize)==0?(totalCount/pageSize):(totalCount/pageSize+1);
	}
	
	@SuppressWarnings("unchecked")
	private Page<HotelBranchVO> getHotelBranchVOs(Integer pageNo, String beginDate,String endDate,String addr,String name,String companyId){
		HotelBranchQuery branchQuery = new HotelBranchQuery();
		branchQuery.setBeginDate(beginDate);
		branchQuery.setEndDate(endDate);
		List<HotelBranchVO> hotelBranchs = null;
		String key = branchQuery.getBeginDate()+branchQuery.getEndDate()+companyId;
		if(cache.get(key)==null){
			hotelBranchs = hotelService.listHotelBranchVO(companyId, branchQuery);
			if(hotelBranchs!=null&&hotelBranchs.size()>0){
				cache.put(key, hotelBranchs, 60*5);
			}
		}else{
			hotelBranchs = (List<HotelBranchVO>)cache.get(key);
		}
		
		List<HotelBranchVO> branchs = new ArrayList<HotelBranchVO>();
		Page<HotelBranchVO> page = new Page<>();
		int pageSize = 6;
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		page.setResult(branchs);
		
		if(hotelBranchs != null) {
			if(StringUtils.isNotBlank(addr) || StringUtils.isNotBlank(name)) {
				for(HotelBranchVO branchVO:hotelBranchs) {
					boolean meet = true;
					String address = branchVO.getCity();
					String cname = branchVO.getCname();
					
					if(StringUtils.isNotBlank(addr) && !StringUtils.contains(address, addr)) {
						meet = false;
					}
					
					if(StringUtils.isNotBlank(name) && !StringUtils.contains(cname, name)) {
						meet = false;
					}
					
					if(meet) {
						branchs.add(branchVO);
					}
				}
			}else{
				branchs = hotelBranchs;
			}
			page.setTotalCount(branchs.size());
			if(branchs.size()>pageSize){
				int beginIndex = (pageNo-1)*pageSize;
				int endIndex = (beginIndex+pageSize)>branchs.size()?branchs.size():(beginIndex+pageSize);
				page.setResult(branchs.subList(beginIndex,endIndex));
			}else{
				page.setResult(branchs);
			}
		}

		return page;
	}
	
	@RequestMapping("/oauth/hotel/ajaxFindlistRoomContent")
	public String ajaxFindRoomInfoVOs(Integer index,RoomInfoListQuery query, HttpServletRequest req){
		Company company = getCurrentCompany(req);
		Map<RoomInfoVO, List<RoomInfoVO>> roomInfoMaps = new LinkedMap();//HashMap<RoomInfoVO, List<RoomInfoVO>>();
		getRoomInfoVOs(index,query,company.getId(),roomInfoMaps);
		req.setAttribute("roomInfoMaps", roomInfoMaps);
		return "/front/hotel/room_list_content";
	}
	
	@RequestMapping("/oauth/hotel/ajaxFindHotelBranchVOs")
	@ResponseBody
	public Page<HotelBranchVO> ajaxFindHotelBranchVOs(Integer index,String beginDate,String endDate,String companyId,String addr,String name){
		Page<HotelBranchVO> page = getHotelBranchVOs(index,beginDate,endDate, addr, name,companyId);
		return page;
	}
	
	@RequestMapping("/hotel/roomBooking")
	public String roomBooking(RoomBookQuery query, HttpServletRequest req) {
		Company company = getCurrentCompany(req);
		MemberVO memberVO = getCurrentMemberVO(req);
		String profileId = memberVO==null?null:memberVO.getProfileId();
		String mbrCardType = memberVO==null?null:memberVO.getMbrCardType();
		
		Date arriveDate = query.getArriveDate();
		Date leaveDate = query.getLeaveDate();
		
		if(arriveDate == null || leaveDate == null) {
			Calendar cal = Calendar.getInstance();
			arriveDate = cal.getTime();
			query.setArriveDate(arriveDate);
			cal.add(Calendar.DATE, 1);
			leaveDate = cal.getTime();
			query.setLeaveDate(leaveDate);
		}
		
		long intevalDays = DateUtil.getIntevalDays(arriveDate, leaveDate);
		req.setAttribute("days", intevalDays);
		
		Integer roomQty = query.getRoomQty();
		if(roomQty == null) {
			query.setRoomQty(1);
		}
		req.setAttribute("query", query);
//		float totalFee = MoneyUtil.round(query.getRoomQty() * intevalDays * query.getPrice());
//		req.setAttribute("totalFee", totalFee);
		
		List<RoomPriceVO> roomPrices = loadRoomPrice(query.getArriveDate(),query.getLeaveDate(), query.getHotelCode(),query.getRoomCode(),
				query.getRoomQty(),query.getRateCode(),profileId,mbrCardType,query.getSalesPromotionId(),company.getId());
		req.setAttribute("roomPrices", roomPrices);
		
		float totalFee = getTotlePrice(roomPrices,roomQty);
		req.setAttribute("totalFee", totalFee);
		
		if(totalFee==0||roomPrices==null){
			req.setAttribute("erryTitle", "客房预订失败！");
			req.setAttribute("erryInfo", "下单失败，请从新下单！");
			req.setAttribute("erryUrl", "/oauth/hotel/hotelSearch.do");
			return "/errorpage";
		}
		HotelServiceQuery hotelServiceQuery = new HotelServiceQuery();
		hotelServiceQuery.setHotelCode(query.getHotelCode());
		hotelServiceQuery.setRateCode(query.getRateCode());
		hotelServiceQuery.setSalePromotionId(query.getSalesPromotionId());
		
		List<HotelServiceVO> hotelServiceVOs = hotelService.listHotelService(company.getId(), hotelServiceQuery);
		req.setAttribute("hotelServiceVOs", hotelServiceVOs);
		
		String contactName = "";
		String contactMobile = "";
		
		if(memberVO != null) {
			contactName = memberVO.getGuestCName();
			contactMobile = memberVO.getMobile();
			List<MemberCouponVO> memberCoupons = memberTradeService.findMemberUseAbleCouponVO(company.getId(), totalFee, memberVO.getProfileId(),company.getCode(),ModuleType.HOTEL);
			req.setAttribute("memberCoupons", memberCoupons);
			
			Float incamount = memberVO.getIncamount();//会员返现
			if(incamount!=null && incamount>0){//返现
				Float maxIncamount = roomPrices.get(0).getMaxReturnMoneyPay();//可使用返现**************************
				if(maxIncamount<=1){
					maxIncamount = totalFee*maxIncamount;
				}
				incamount = incamount>maxIncamount?maxIncamount:incamount;
				incamount = incamount>totalFee?totalFee:incamount;
				req.setAttribute("incamount", incamount);
				req.setAttribute("maxIncamount", maxIncamount);
			}
			
			req.setAttribute("maxCouponCountPay", roomPrices.get(0).getMaxCouponCountPay());
		} else {
			WeixinFan fan = getCurrentFan(req);
			HotelOrder lastestHotelOrder = hotelOrderService.getLastestHotelOrderByOpenId(fan.getOpenId());
			if(lastestHotelOrder != null) {
				contactName = lastestHotelOrder.getContactName();
				contactMobile = lastestHotelOrder.getContactMobile();
			}
		}

		
		req.setAttribute("contactName", contactName);
		req.setAttribute("contactMobile", contactMobile);
		
		List<ArriveTime> arriveTimes = arriveTimeService.findArriveTimeAll(company.getId());
		req.setAttribute("arriveTimes", arriveTimes);
		
		WeixinFan fan = getCurrentFan(req);
		List<Guest> guests = memberService.findGuestByOpendId(fan.getOpenId());
		req.setAttribute("guests", guests);
		
		if(StringUtils.isNotBlank(query.getHotelCode())){
			HotelBranchVO hotelBranchVO = hotelService.getHotelBranchVO(company.getId(), query.getHotelCode());
			req.setAttribute("hotelBranchName", hotelBranchVO.getCname());
		}
		return "/front/hotel/room_book";
	}
	
	private Float getTotlePrice(List<RoomPriceVO> roomPrices,Integer roomQty){
		float totlePrice = 0.0f;
		if(roomPrices!=null){
			for (RoomPriceVO roomPriceVO : roomPrices) {
				totlePrice = totlePrice+NumberUtils.toFloat(roomPriceVO.getPrice());
			}
			totlePrice = MoneyUtil.round(totlePrice*roomQty);
		}
		return totlePrice;
	}

	private List<RoomPriceVO> loadRoomPrice(Date arriveDate, Date leaveDate, String hotelCode,
											String roomCode, Integer roomQty, String rateCode,
											String profileId,String mbrCardTypeCode, String salesPromotionId, String companyId) {
		RoomPriceQuery roomPriceQuery = new RoomPriceQuery();
		roomPriceQuery.setBeginDate(arriveDate);
		roomPriceQuery.setEndDate(leaveDate);
		roomPriceQuery.setHotelCode(hotelCode);
		roomPriceQuery.setOrderItemCode(roomCode);
		roomPriceQuery.setRoomQty(roomQty);
		roomPriceQuery.setRateCode(rateCode);
		roomPriceQuery.setProfileId(profileId);               //会员id
		roomPriceQuery.setMbrCardTypeCode(mbrCardTypeCode);
		//roomPriceQuery.setSource(Constants.JXD_API_SOURCE);
		roomPriceQuery.setSalePromotionId(salesPromotionId);
		List<RoomPriceVO> roomPrices = roomTypeService.findRoomPriceVOs(companyId, roomPriceQuery);
		return roomPrices;
	}
	
	@RequestMapping("/hotel/toCashierDesk")
	public String toCashierDesk(HotelOrder hotelOrder, HttpServletRequest req) {
		MemberVO memberVO = getCurrentMemberVO(req);
		Company company = getCurrentCompany(req);
		WeixinFan fan = getCurrentFan(req);
		
		RoomBookQuery query = new RoomBookQuery();
		query.setArriveDate(hotelOrder.getCheckInTime());
		query.setLeaveDate(hotelOrder.getCheckOutTime());
		query.setHotelCode(hotelOrder.getHotelCode());
		query.setRoomCode(hotelOrder.getRoomCode());
		query.setRoomQty(hotelOrder.getRoomQty());
		query.setRateCode(hotelOrder.getRateCode());
		query.setSalesPromotionId(hotelOrder.getSalesPromotionId());
		
		String profileId = "";
		String mbrCardType = "";
		String mbrCardNo = "";
		if(memberVO!=null){
			profileId = memberVO.getProfileId();
			mbrCardType = memberVO.getMbrCardType();
		}
		
		List<RoomPriceVO> roomPrices = loadRoomPrice(query.getArriveDate(),query.getLeaveDate(), query.getHotelCode(),query.getRoomCode(),
				query.getRoomQty(),query.getRateCode(),profileId,mbrCardType,query.getSalesPromotionId(),company.getId());
		
		Float totalFee = getTotlePrice(roomPrices,hotelOrder.getRoomQty());
		if(totalFee==0||roomPrices==null||roomPrices.size()==0){
			req.setAttribute("erryTitle", "客房预订失败！");
			req.setAttribute("erryInfo", "下单失败，请从新下单！");
			req.setAttribute("erryUrl", "/oauth/hotel/hotelSearch.do");
			System.out.println("toCashierDesk getTotlePrice 查询失败========================================================");
			return "/errorpage";
		}
		
		List<HotelOtherService> otherServices = hotelOrder.getOtherServices();
		if(otherServices != null) {//客制服务
			List<HotelOtherService> validServices = new ArrayList<HotelOtherService>();
			for(HotelOtherService service:otherServices) {
				if(service.getChecked() != null && service.getChecked()) {
					totalFee += service.getPrice()*service.getNumber();
					validServices.add(service);
				}
			}
			hotelOrder.setOtherServices(validServices);
		}
		
		if(memberVO != null){
			Float promotionFee = 0f;
			if(hotelOrder.getIncamount()>0){//勾选了返现
				Float incamount = memberVO.getIncamount();
				if(incamount!=null && incamount>0){//返现
					Float maxIncamount = roomPrices.get(0).getMaxReturnMoneyPay();//**************************
					if(maxIncamount<=1){
						maxIncamount = totalFee*maxIncamount;
					}
					incamount = incamount>maxIncamount?maxIncamount:incamount;
					incamount = incamount>totalFee?totalFee:incamount;
				}
				totalFee -= incamount;
				hotelOrder.setIncamount(incamount);
				promotionFee += incamount;
			}
			if(StringUtils.isNotBlank(hotelOrder.getCouponSeqid())){//优惠劵
				MemberCouponVO memberCouponVO = memberTradeService.getMemberCouponVOBySeqId(company.getId(), memberVO.getProfileId(), company.getCode(), hotelOrder.getCouponSeqid());
				hotelOrder.setCouponCode(memberCouponVO.getCode());
				hotelOrder.setChargeamt(memberCouponVO.getChargeamt());
				hotelOrder.setChargeamtmodel(memberCouponVO.getChargeamtmodel());
				promotionFee += hotelOrder.getChargeamt();
				totalFee -= promotionFee;
			}
			hotelOrder.setPromotionFee(promotionFee);
			hotelOrder.setProfileId(profileId);
		}
		
		String formatTotalFee = new DecimalFormat("#.00").format(totalFee);
		hotelOrder.setTotalFee(Float.valueOf(formatTotalFee));
		req.getSession().setAttribute(Constants.Session.BOOK_ORDER, hotelOrder);
		
		if(totalFee<=0){
			String orderSn = saveHotelOrder(PayMent.BALANCEPAY, req);
			if(!StringUtils.equals("-1", orderSn)){
				PayOrder payOrder = (PayOrder) req.getSession().getAttribute(Constants.Session.PAY_ORDER);
				payOrder.setOpenId(fan.getOpenId());
				payOrder.setCompanyId(company.getId());
				payOrder.setMbrCardNo(mbrCardNo);
				payOrder.setPayMent(PayMent.BALANCEPAY);
				payOrderService.savePayOrder(payOrder);
				
				boolean rs = payOrderService.handlePayOrder(orderSn, orderSn, PayMent.BALANCEPAY);
				if(rs){
					//sendRoomBookMessage(orderSn, req);
					return "redirect:/hotel/showRoomBookRs.do?orderSn="+orderSn;
				}
			}
		}
		return "redirect:/pay/toCashierDesk.do?showwxpaytitle=1";
	}
	
	@RequestMapping("/hotel/toOrderPayment")
	public String toOrderPayment(String orderSn, HttpServletRequest req){
		HotelOrder hotelOrder = hotelOrderService.getHotelOrderByOrderSn(orderSn);
		return toCashierDesk(hotelOrder,req);
	}
	
	@RequestMapping("/hotel/saveHotelOrder")
	@ResponseBody
	public String saveHotelOrder(PayMent payMent, HttpServletRequest req) {
		Company company = getCurrentCompany(req);
		WeixinFan fan = getCurrentFan(req);
		HotelOrder hotelOrder = (HotelOrder) req.getSession().getAttribute(Constants.Session.BOOK_ORDER);
		System.out.println(hotelOrder+"------------------");
		if(hotelOrder != null) {
			String companyId = company.getId();
			String openId = fan.getOpenId();
			hotelOrder.setTradeStatus(TradeStatus.WAIT_PAY);
			hotelOrder.setCompanyId(companyId);
			hotelOrder.setOpenId(fan.getOpenId());
			hotelOrder.setPayFee(0f);
			
			//预处理订单
			InterfaceConfig interfaceConfig = interfaceConfigService.getEnableInterfaceConfig(companyId);
			if(!StringUtils.equals("pms", interfaceConfig.getChannel())){//pms暂时不做处理
				hotelOrder.setOrderOperate("Prepay");
				hotelOrderService.saveHotelOrder(hotelOrder);
				boolean isOk = hotelOrderService.synchronizeHotelOrderToJXD(hotelOrder);
				if(!isOk){
					return "-2";
				}
			}
			
			if(PayMent.OFFLINEPAY.equals(payMent)) {
				hotelOrder.setStatus(HotelOrderStatus.NEW);
				String couponSeqid = hotelOrder.getCouponSeqid();
				boolean rs = true;
				if(StringUtils.isNotBlank(couponSeqid)) {//优惠劵
					rs = memberTradeService.useMemberCoupon(hotelOrder.getCompanyId(), couponSeqid, "预订房型使用券", hotelOrder.getOpenId(),company.getCode());  //核销券
				}
				if(hotelOrder.getIncamount()>0){//返现
					Hotel hotel = hotelService.getHotel(companyId, hotelOrder.getHotelCode());
					String remark = hotel.getName()+hotelOrder.getName()+"使用返现";
					rs = memberTradeService.useMemberIncamount(company.getId(),hotelOrder.getProfileId(), company.getOutletCode(),hotelOrder.getIncamount(),company.getCode(),hotelOrder.getTradeSn(),remark);
				}
				if(Boolean.TRUE.equals(rs)){
					hotelOrderService.saveHotelOrder(hotelOrder);
					req.getSession().setAttribute(Constants.Session.BOOK_ORDER, hotelOrder);
					
					hotelOrder.setOrderOperate("Add");
					hotelOrderService.synchronizeHotelOrderToJXD(hotelOrder);
					if(!Boolean.FALSE.equals(hotelOrder.getSynchronizeState())){
						sendRoomBookMessage(hotelOrder.getOrderSn(), req);
					}
				}
				return hotelOrder.getOrderSn();
			} else {
				hotelOrder.setStatus(HotelOrderStatus.WAIT_PAY);
				hotelOrderService.saveHotelOrder(hotelOrder);
				req.getSession().setAttribute(Constants.Session.BOOK_ORDER, hotelOrder);
				
				PayOrder payOrder = new PayOrder();
				payOrder.setPayMode(PayMode.BOOKHOTEL);
				payOrder.setOpenId(openId);
				payOrder.setName(hotelOrder.getName());
				payOrder.setBusinessId(hotelOrder.getId());
				payOrder.setTotalFee((long)(hotelOrder.getTotalFee() * 100));
				payOrder.setCompanyId(companyId);
				payOrder.setOrderSn(hotelOrder.getOrderSn());
				
				Hotel hotel = hotelService.getHotel(payOrder.getCompanyId(), hotelOrder.getHotelCode());
				payOrder.setRemark(hotel.getName()+hotelOrder.getName()+"使用余额");
				
				HttpSession session = req.getSession();
				session.setAttribute(Constants.Session.PAY_ORDER, payOrder);
			}
			
			hotelOrder = hotelOrderService.getHotelOrderByOrderSn(hotelOrder.getOrderSn());
			if(HotelOrderStatus.NEW.equals(hotelOrder.getStatus()) || HotelOrderStatus.WAIT_PAY.equals(hotelOrder.getStatus())) {
				return hotelOrder.getOrderSn();
			}
		}
		return "-1";
	}
	
	/**
	 * 显示客房支付结果
	 * @param orderSn
	 * @param req
	 * @return
	 */
	@RequestMapping("/hotel/showRoomBookRs")
	public String showHousePayRs(String orderSn, HttpServletRequest req) {
		HotelOrder order = hotelOrderService.getHotelOrderByOrderSn(orderSn);
		if(order != null) {
			req.setAttribute("order", order);
		}
		req.getSession().removeAttribute(Constants.Session.BOOK_ORDER);
		return "/front/hotel/room_book_rs";
	}
	
	@RequestMapping("/hotel/sendRoomBookMessage")
	public void sendRoomBookMessage(String orderSn, HttpServletRequest req){
//		HotelOrder hotelOrder = hotelOrderService.getHotelOrderByOrderSn(orderSn);
//		weixinMessageService.sendRoomBookMessageByUser(hotelOrder);
		weixinMessageService.sendRoomBookMessage(orderSn);
	}
	
	@RequestMapping("/oauth/hotel/listHotelOrder")
	public String listHotelOrder(TradeStatus tradeStatus, HttpServletRequest req) {
		Company company = getCurrentCompany(req);
		WeixinFan fan = getCurrentFan(req);
		req.setAttribute("tradeStatus", tradeStatus);
		
		List<ModuleType> moduleTypes = new ArrayList<ModuleType>();
		if(company.getModuleTypes()!=null){
			int conut = 0;
			String url = "";
			for (ModuleType moduleType : company.getModuleTypes()) {
				if(moduleType != null){
					conut++;
					url = moduleType.getUrl();
					moduleTypes.add(moduleType);
				}
			}
			req.setAttribute("moduleTypes", moduleTypes);
			
			if(conut==1 && !StringUtils.equals("/oauth/hotel/listHotelOrder.do", url)){
				return "redirect:"+url+(tradeStatus==null?"":("?tradeStatus="+tradeStatus));
			}
			
			List<HotelOrder> hotelOrders = hotelOrderService.loadHotelOrders(company.getId(), fan.getOpenId(), tradeStatus);
			req.setAttribute("hotelOrders", hotelOrders);
		}
		return "/front/hotel/hotel_order_list";
	}
	
	@RequestMapping("/hotel/showHotelOrder")
	public String showHotelOrder(String orderSn, HttpServletRequest req) {
//		WeixinFan fan = getCurrentFan(req);
		HotelOrder hotelOrder = hotelOrderService.getHotelOrderByOrderSn(orderSn);
		
		if(hotelOrder != null) {
			HotelBranchVO hotelBranchVO = hotelService.getHotelBranchVO(hotelOrder.getCompanyId(), hotelOrder.getHotelCode());
			Hotel hotel = hotelService.getHotel(hotelOrder.getCompanyId(), hotelOrder.getHotelCode());
			req.setAttribute("hotelBranchVO", hotelBranchVO);
			req.setAttribute("hotel", hotel);
			
			HotelServiceQuery hotelServiceQuery = new HotelServiceQuery();
			hotelServiceQuery.setHotelCode(hotelOrder.getHotelCode());
//			hotelServiceQuery.setRateCode(query.getRateCode());
			hotelServiceQuery.setSalePromotionId(hotelOrder.getSalesPromotionId());
			List<HotelServiceVO> hotelServiceVOs = hotelService.listHotelService(hotelOrder.getCompanyId(), hotelServiceQuery);
			req.setAttribute("hotelServiceVOs", hotelServiceVOs);
		}
		float roomFee = 0.0f;
		if(hotelOrder.getRoomPrices()!=null){
			for (RoomPrice roomPrice : hotelOrder.getRoomPrices()) {
				roomFee = roomFee+roomPrice.getPrice();
			}
			roomFee = MoneyUtil.round(roomFee*hotelOrder.getRoomQty());
		}
		req.setAttribute("roomFee", roomFee);
		
		req.setAttribute("hotelOrder", hotelOrder);
		
//		if(fan != null) {
//			Location location = LocationService.getLocationService().getLocation(fan.getOpenId());
//			req.setAttribute("location", location);
//		}
		CommentConfig commentConfig = hotelCommentService.getCommentConfig(hotelOrder.getCompanyId());
		req.setAttribute("commentConfig", commentConfig);
		return "/front/hotel/hotel_order_info";
	}
	
	@RequestMapping("/hotel/cancelHotelOrder")
	@ResponseBody
	public Boolean cancelHotelOrder(String orderSn, String cancelReason, HttpServletRequest req) {
		WeixinFan weixinFan = getCurrentFan(req);
		Company company = getCurrentCompany(req);
		HotelOrder hotelOrder = hotelOrderService.getHotelOrderByOrderSn(orderSn);
		JXDOrderService orderService = new JXDOrderService();
		orderService.sendMsg(company.getCode(),hotelOrder.getOrderSn(),"hoteCancel");
		
		boolean isOk = hotelOrderService.cancelHotelOrder(hotelOrder.getCompanyId(), weixinFan.getOpenId(), orderSn, cancelReason);
		if(isOk){
			orderSn = StringUtils.isBlank(hotelOrder.getHotelOrderNo())?orderSn:hotelOrder.getHotelOrderNo();
			weixinMessageService.sendCancelHotelOrderMsgToUser(weixinFan,hotelOrder,orderSn);
			weixinMessageService.sendCancelHotelOrderMsgToCompany(weixinFan,hotelOrder,orderSn);
		}
		return isOk;
	}
	
	@RequestMapping("/hotel/updateGuest")
	public String updateGuest(Guest guest, HttpServletRequest req) {
		WeixinFan fan = getCurrentFan(req);
		if(fan!=null&&StringUtils.isNotBlank(fan.getOpenId())){
			guest.setOpenId(fan.getOpenId());
		}
		memberService.saveGuest(guest);
		return "redirect:/oauth/member/listGuests.do";
	}

	/**
	 *  仅天沐使用
	 */
	@RequestMapping("/oauth/hotel/toLinkWiFi")
	public String toLinkWiFi(HttpServletRequest req){
		WeixinFan weixinFan = getCurrentFan(req);
		return "http://www.baidu.com/?parameter=sign:"+weixinFan.getOpenId()+"|url:http://o2o.i-liaoning.com.cn/wsb/index.php?g=User&m=Yulan&a=index&token=1007453100";
	}
}
