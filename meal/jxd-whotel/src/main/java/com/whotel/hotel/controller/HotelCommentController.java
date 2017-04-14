package com.whotel.hotel.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.whotel.company.entity.Company;
import com.whotel.front.controller.FanBaseController;
import com.whotel.front.entity.WeixinFan;
import com.whotel.hotel.entity.CommentConfig;
import com.whotel.hotel.entity.HotelComment;
import com.whotel.hotel.entity.HotelOrder;
import com.whotel.hotel.entity.ThumbUpRecord;
import com.whotel.hotel.service.HotelCommentService;
import com.whotel.hotel.service.HotelOrderService;
import com.whotel.weixin.service.WeixinMessageService;

@Controller
@RequestMapping("/oauth/hotelMsg")
public class HotelCommentController extends FanBaseController {
	
	@Autowired
	private HotelCommentService hotelCommentService;
	
	@Autowired
	private HotelOrderService hotelOrderService;
	
	@Autowired
	private WeixinMessageService weixinMessageService;
	
	/**
	 * 查询酒店下的点评
	 */
	@RequestMapping("/listHotelComment")
	public String listHotelComment(String hotelCode,String message,HttpServletRequest req){
		Company company = getCurrentCompany(req);
		WeixinFan fan = getCurrentFan(req);
		List<HotelComment> hotelComments = hotelCommentService.listHotelComment(company.getId(), hotelCode);
		req.setAttribute("hotelComments", hotelComments);
		req.setAttribute("hotelCode", hotelCode);
		req.setAttribute("company", company);
		req.setAttribute("message", message);
		
		Map<String,ThumbUpRecord> recordsMap = hotelCommentService.findThumbUpRecordMap(fan.getOpenId());
		req.setAttribute("recordsMap", recordsMap);
		
		CommentConfig commentConfig = hotelCommentService.getCommentConfig(company.getId());
		req.setAttribute("commentConfig", commentConfig);
		return "/front/hotel/hotelComment_list";
	}
	
	/**
	 * 点评
	 */
	@RequestMapping("/toHotelComment")
	public String toHotelComment(String replyId,String orderSn,HttpServletRequest req){
		WeixinFan fan = getCurrentFan(req);
		HotelOrder hotelOrder = hotelOrderService.getHotelOrderByOrderSn(orderSn);
		HotelComment hotelComment = hotelCommentService.getHotelCommentsByOrderSn(orderSn);
		req.setAttribute("hotelComment", hotelComment);
		req.setAttribute("hotelOrder", hotelOrder);
		req.setAttribute("weixinFan", fan);
		//req.setAttribute("replyId", replyId);
		return "/front/hotel/comments_on";
	}
	
	/**
	 * 点评
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("/saveHotelComment")
	public String saveHotelComment(HotelComment hotelComment,String replyId,HttpServletRequest req) throws UnsupportedEncodingException{
		HotelComment comment = hotelCommentService.getHotelCommentsByOrderSn(hotelComment.getOrderSn());
		HotelOrder hotelOrder = hotelOrderService.getHotelOrderByOrderSn(hotelComment.getOrderSn());
		if(comment==null || StringUtils.isNotBlank(hotelComment.getId())){//因为手机的返回键
			WeixinFan fan = getCurrentFan(req);
			Company company = getCurrentCompany(req);
			hotelComment.setOpenId(fan.getOpenId());
			hotelComment.setCompanyId(company.getId());
			hotelCommentService.saveHotelComment(hotelComment,replyId);
			
			weixinMessageService.sendCommentToCompany(hotelComment,hotelOrder);
			return "redirect:/oauth/hotelMsg/listHotelComment.do?hotelCode="+hotelComment.getHotelCode()+"&message="+URLEncoder.encode("评论提交成功，请您耐心等待酒店审批！", "UTF8");
		}
		return "redirect:/oauth/hotelMsg/toHotelComment.do?orderSn="+hotelComment.getOrderSn();
	}
	
	/**
	 * 点赞
	 */
	@RequestMapping("/ajaxThumbUp")
	@ResponseBody
	public Integer ajaxThumbUp(String id,HttpServletRequest req){
		WeixinFan fan = getCurrentFan(req);
		HotelComment hotelComment = hotelCommentService.getCommentDetails(id);
		if(hotelComment != null){
			Integer thumbUpCount = (hotelComment.getThumbUpCount()==null?0:hotelComment.getThumbUpCount())+1;
			hotelComment.setThumbUpCount(thumbUpCount);
			hotelCommentService.updateThumbUpCount(hotelComment);
			
			ThumbUpRecord thumbUpRecord = new ThumbUpRecord();
			thumbUpRecord.setCommentId(id);
			thumbUpRecord.setOpenId(fan.getOpenId());
			thumbUpRecord.setNickName(fan.getNickName());
			thumbUpRecord.setAvatar(fan.getAvatar());
			hotelCommentService.saveThumbUpRecordDao(thumbUpRecord);
			return thumbUpCount;
		}
		return 0;
	}
}
