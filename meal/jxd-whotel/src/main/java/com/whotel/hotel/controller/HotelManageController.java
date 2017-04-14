package com.whotel.hotel.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.whotel.common.dao.mongo.Order;
import com.whotel.common.dao.mongo.Page;
import com.whotel.common.dto.QueryParam;
import com.whotel.common.enums.FilterModel;
import com.whotel.common.util.DateUtil;
import com.whotel.company.controller.BaseCompanyController;
import com.whotel.company.entity.Company;
import com.whotel.company.entity.CompanyAdmin;
import com.whotel.hotel.entity.CommentConfig;
import com.whotel.hotel.entity.Facilitys;
import com.whotel.hotel.entity.Hotel;
import com.whotel.hotel.entity.HotelComment;
import com.whotel.hotel.entity.HotelOrder;
import com.whotel.hotel.service.HotelCommentService;
import com.whotel.hotel.service.HotelOrderService;
import com.whotel.hotel.service.HotelService;
import com.whotel.thirdparty.jxd.mode.HotelBranchQuery;
import com.whotel.thirdparty.jxd.mode.vo.HotelBranchVO;

@Controller
@RequestMapping("/company/hotel")
public class HotelManageController extends BaseCompanyController {

	@Autowired
	private HotelService hotelService;
	
	@Autowired
	private HotelOrderService hotelOrderService;

	@Autowired
	private HotelCommentService hotelCommentService;
	
	@RequestMapping("/listHotels")
	public String listHotels(HttpServletRequest req) {
		CompanyAdmin currentCompanyAdmin = getCurrentCompanyAdmin(req);
		String companyId = currentCompanyAdmin.getCompanyId();
		List<Hotel> hotels = hotelService.findAllHotels(companyId);
//		Map<String, HotelBranchVO> hotelBranchMap = new HashMap<>();
//		List<HotelBranchVO> hotelBranchs = hotelService.listHotelBranchVO(companyId, new HotelBranchQuery());
//		if(hotelBranchs != null){
//			for (HotelBranchVO vo : hotelBranchs) {
//				hotelBranchMap.put(vo.getCode(), vo);
//			}
//		}
//		req.setAttribute("hotelBranchMap", hotelBranchMap);
		req.setAttribute("hotels", hotels);
		req.setAttribute("companyId", companyId);
		return "/company/hotel/hotel_list";
	}
	
	@RequestMapping("/synchronizeHotel")
	public String synchronizeHotel(HttpServletRequest req){
		CompanyAdmin companyAdmin = getCurrentCompanyAdmin(req);
		hotelService.synchronizeHotel(companyAdmin.getCompanyId());
		return "redirect:/company/hotel/listHotels.do";
	}
	
	@RequestMapping("/listOrderHotels")
	public String listOrderHotels(Page<HotelOrder> page, QueryParam queryParam,HttpServletRequest req) throws ParseException {
		CompanyAdmin currentCompanyAdmin = getCurrentCompanyAdmin(req);
		if(StringUtils.isNotBlank(currentCompanyAdmin.getCompanyId())){
			page.addFilter("companyId", FilterModel.EQ, currentCompanyAdmin.getCompanyId());
		}
		if(queryParam != null && queryParam.getParams() != null) {
			Map<String, String> params = queryParam.getParams();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String beginDate = params.get("beginDate");
			if(StringUtils.isNotBlank(beginDate)) {
				page.addFilter("createTime", FilterModel.GE, DateUtil.getStartTime(format.parse(beginDate)));
			}
			String endDate = params.get("endDate");
			if(StringUtils.isNotBlank(endDate)) {
				page.addFilter("createTime", FilterModel.LE, DateUtil.getEndTime(format.parse(endDate)));
			}
			String contactName = params.get("contactName");
			if(StringUtils.isNotBlank(contactName)) {
				page.addFilter("contactName", FilterModel.LIKE, contactName);
			}
		
			String orderSn = params.get("orderSn");
			if(StringUtils.isNotBlank(orderSn)) {
				page.addFilter("orderSn", FilterModel.LIKE, orderSn);
			}
		}
		page.addFilter("synchronizeState", FilterModel.EQ, false);
		page.addOrder(Order.desc("createTime"));
		hotelOrderService.findHotelOrders(page);
//		req.setAttribute("orderList", orderList);
		req.setAttribute("companyId", currentCompanyAdmin.getCompanyId());
		return "/company/hotel/hotel_order_nosync";
	}
	
	@RequestMapping("/hotelOrderSync")
	public String hotelOrderSync(String orderSn) throws UnsupportedEncodingException{
		HotelOrder hotelOrder = hotelOrderService.getHotelOrderByOrderSn(orderSn);
		hotelOrder.setOrderOperate("Add");
		boolean rs = hotelOrderService.synchronizeHotelOrderToJXD(hotelOrder);
		hotelOrder.setSynchronizeState(rs);
		hotelOrderService.saveHotelOrder(hotelOrder);
		if(!rs){
			return "redirect:/company/hotel/listOrderHotels.do?message="+URLEncoder.encode("同步失败", "UTF8");
		}
		return "redirect:/company/hotel/listOrderHotels.do?message="+URLEncoder.encode("同步成功！", "UTF8");
	}
	
	@RequestMapping("/toEditHotel")
	public String toEditHotel(String code, HttpServletRequest req) {
		CompanyAdmin currentCompanyAdmin = getCurrentCompanyAdmin(req);
		String companyId = currentCompanyAdmin.getCompanyId();
		req.setAttribute("companyId", companyId);
//		HotelBranchVO hotelBranchVO = hotelService.getHotelBranchVO(companyId, code);
		Hotel hotel = hotelService.getHotel(companyId, code);
//		req.setAttribute("hotelBranchVO", hotelBranchVO);
		req.setAttribute("hotel", hotel);
		return "/company/hotel/hotel_edit";
	}
	
	@RequestMapping("/updateHotel")
	public String updateHotel(Hotel hotel, HttpServletRequest req) {
		CompanyAdmin currentCompanyAdmin = getCurrentCompanyAdmin(req);
		String companyId = currentCompanyAdmin.getCompanyId();
		hotel.setCompanyId(companyId);
		List<Facilitys> facilitys = hotel.getFacilitys();
		List<Facilitys> updateFacilitys = null;
		if(facilitys != null) {
			updateFacilitys = new ArrayList<Facilitys>();
			for(Facilitys facility:facilitys) {
				if(facility != null && StringUtils.isNotBlank(facility.getHotelFacilitieNames())) {
					updateFacilitys.add(facility);
				}
			}
			hotel.setFacilitys(updateFacilitys);
		}
		hotelService.saveHotel(hotel);
		return "redirect:/company/hotel/listHotels.do";
	}
	
	@RequestMapping("/ajaxFindHConvert")
	@ResponseBody
	public Hotel ajaxFindHotelFacilityConvert(String hotelCode, HttpServletRequest req){
		CompanyAdmin companyAdmin = getCurrentCompanyAdmin(req);
		Hotel hotel = hotelService.getHotel(companyAdmin.getCompanyId(), hotelCode);
		return hotel;
	}
	
	////////////////////////////////////酒店点评////////////////////////////////////////////////////////
	/**
	 * 酒店点评参数配置
	 */
	@RequestMapping("/toCommentConfig")
	public String updateCommentConfig(HttpServletRequest req){
		CompanyAdmin companyAdmin = getCurrentCompanyAdmin(req);
		CommentConfig commentConfig = hotelCommentService.getCommentConfig(companyAdmin.getCompanyId());
		req.setAttribute("commentConfig", commentConfig);
		return "/company/hotel/comment_config";
	}
	
	@RequestMapping("/updateCommentConfig")
	public String updateCommentConfig(CommentConfig commentConfig,HttpServletRequest req){
		CompanyAdmin companyAdmin = getCurrentCompanyAdmin(req);
		commentConfig.setCompanyId(companyAdmin.getCompanyId());
		hotelCommentService.saveCommentConfig(commentConfig);
		return "redirect:/company/hotel/toCommentConfig.do";
	}
	
	/**
	 * 查询所有回复
	 */
	@RequestMapping("/listHotelComment")
	public String listHotelComment(Page<HotelComment> page,QueryParam queryParam,HttpServletRequest req){
		CompanyAdmin companyAdmin = getCurrentCompanyAdmin(req);
		page.addFilter("companyId", FilterModel.EQ, companyAdmin.getCompanyId());
		if(queryParam != null && queryParam.getParams() != null) {
			Map<String, String> params = queryParam.getParams();
			
			String hotelCode = params.get("hotelCode");
			if(StringUtils.isNotBlank(hotelCode)){
				page.addFilter("hotelCode", FilterModel.EQ, hotelCode);
			}
			
			String checkStatus = params.get("checkStatus");
			if(StringUtils.equals(checkStatus,"0")){//未审核
				page.addFilter("checkStatus", FilterModel.EQ, null);
			}else if(StringUtils.equals(checkStatus,"1")){//未通过
				page.addFilter("checkStatus", FilterModel.EQ, false);
			}else if(StringUtils.equals(checkStatus,"2")){//已通过
				page.addFilter("checkStatus", FilterModel.EQ, true);
			}
		}
		page.addOrder(Order.desc("createTime"));
		hotelCommentService.findHotelComments(page);;
		return "/company/hotel/hotelComment_list";
	}
	
	/**
	 * 查看点评
	 */
	@RequestMapping("/showHotelComment")
	public String showHotelComment(String id,HttpServletRequest req){
		HotelComment hotelComment = hotelCommentService.getCommentDetails(id);
		req.setAttribute("hotelComment", hotelComment);
		return "/company/hotel/hotelComment_details";
	}
	
	/**
	 * 酒店回复
	 */
	@RequestMapping("/toHotelComment")
	public String toHotelComment(String id,HttpServletRequest req){
		HotelComment hotelComment = hotelCommentService.getCommentDetails(id);
		req.setAttribute("hotelComment", hotelComment);
		return "/company/hotel/comments_on";
	}
	
	/**
	 * 酒店回复
	 */
	@RequestMapping("/saveHotelComment")
	public String saveHotelComment(HotelComment hotelComment,String replyId,HttpServletRequest req){
		CompanyAdmin companyAdmin = getCurrentCompanyAdmin(req);
		hotelComment.setCompanyId(companyAdmin.getCompanyId());
		hotelCommentService.saveHotelComment(hotelComment,replyId);
		return "redirect:/company/hotel/listHotelComment.do";
	}
	
	/**
	 * 审核点评
	 */
	@RequestMapping("/auditHotelComment")
	public String auditHotelComment(String id,Boolean checkStatus,HttpServletRequest req){
		HotelComment hotelComment = hotelCommentService.getCommentDetails(id);
		hotelComment.setCheckStatus(checkStatus);
		hotelCommentService.saveHotelComment(hotelComment,null);
		return "redirect:/company/hotel/listHotelComment.do";
	}
	
	@RequestMapping("/deleteHotelComment")
	public String deleteHotelComment(String ids,HttpServletRequest req){
		hotelCommentService.deleteHotelCommentById(ids);
		return "redirect:/company/hotel/listHotelComment.do";
	}
}
