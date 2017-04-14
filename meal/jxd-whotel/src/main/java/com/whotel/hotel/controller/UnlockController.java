package com.whotel.hotel.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.whotel.company.entity.Company;
import com.whotel.company.service.CompanyService;
import com.whotel.front.controller.FanBaseController;
import com.whotel.hotel.entity.CheckInRecord;
import com.whotel.hotel.service.UnlockService;
import com.whotel.thirdparty.jxd.mode.vo.OccupancyManVO;

@Controller
public class UnlockController extends FanBaseController {
	
	@Autowired
	private UnlockService unlockService;
	
	@Autowired
	private CompanyService companyService;
	
	/**
	 * 微信开门
	 */
	@RequestMapping("/oauth/unlock/toUnlock")
	public String toUnlock(HttpServletRequest req) {
		String openId = getCurrentOpenId(req);
		req.setAttribute("openId", openId);
		
		List<CheckInRecord> checkInRecords = unlockService.findCheckInRecord(getCurrentCompanyId(req), null, null,openId,null);
		if(checkInRecords.size()>0){
			CheckInRecord checkInRecord = checkInRecords.get(0);
			
			Map<String,OccupancyManVO> map = unlockService.queryOccupancyManMap(checkInRecord.getCompanyId(), checkInRecord.getIdNo(), checkInRecord.getRoomNo());
			OccupancyManVO occupancyManVO = map.get(checkInRecord.getIdNo()+checkInRecord.getRoomNo());
			if(occupancyManVO!=null){
				req.setAttribute("checkInRecord", checkInRecord);
			}
		}
		return "/front/hotel/unlock";
	}
	
	/**
	 * 验证是否在住
	 * @param idNo
	 * @param roomNo
	 */
	@RequestMapping("/oauth/unlock/validateCheckInRecord")
	@ResponseBody
	public CheckInRecord validateCheckInRecord(String idNo,String roomNo,HttpServletRequest req) {
		String openId = getCurrentOpenId(req);
		CheckInRecord checkInRecord = unlockService.validateCheckInRecord(getCurrentCompanyId(req), idNo, roomNo,openId);
		return checkInRecord;
	}
	
	/**
	 * 后台绑定门锁
	 * @param idNo
	 * @param roomNo
	 */
	@RequestMapping("/oauth/unlock/ajaxBindDevice")
	@ResponseBody
	public String ajaxBindDevice(String id,String ticket,HttpServletRequest req){
		String openId = getCurrentOpenId(req);
		unlockService.compelBind(id, openId,ticket);
		return "ok";
	}
	
	/**
	 * 生成门锁二维码
	 * @param hotelCode 酒店代码
	 * @param name 入住人姓名
	 * @param mobile 手机
	 * @param regId 登记号
	 * @param idNo 身份证号码
	 * @param roomNo 客房号码
	 */
	@RequestMapping("unlock/getGuestCheckInQrcode")
	@ResponseBody
	public String getGuestCheckInQrcode(String hotelCode,String name,String mobile,String regId,String idNo,String roomNo,HttpServletResponse res){
		Company company = companyService.getCompanyByCode(hotelCode);
		String qrUrl = unlockService.guestCheckInQrcode(company.getId(), name, mobile, hotelCode+regId,idNo,roomNo);
		return qrUrl;
	}
}
