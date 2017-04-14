package com.whotel.hotel.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.whotel.company.entity.Company;
import com.whotel.front.controller.FanBaseController;
import com.whotel.hotel.service.BanquetReservationService;
import com.whotel.thirdparty.jxd.mode.BanquetReservation;

@Controller
/**
 * 会议宴会预订
 * @author 柯鹏程
 *
 */
public class BanquetReservationController extends FanBaseController {

	@Autowired
	BanquetReservationService banquetReservationService;
	
	@RequestMapping("/oauth/hotel/toEditBanquetReservation")
	public String toEditBanquetReservation(HttpServletRequest req) {
		return "/front/hotel/banquet_reservation_edit";
	}
	
	@RequestMapping("/oauth/hotel/saveBanquetReservation")
	@ResponseBody
	public Boolean saveBanquetReservation(BanquetReservation banquetReservation,HttpServletRequest req) {
		Company company = getCurrentCompany(req);
		return banquetReservationService.saveBanquetReservation(banquetReservation, company.getId());
	}
}
