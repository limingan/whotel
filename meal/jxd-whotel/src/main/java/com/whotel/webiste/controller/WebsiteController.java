package com.whotel.webiste.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.whotel.common.dao.mongo.Page;
import com.whotel.common.enums.FilterModel;
import com.whotel.common.util.BeanUtil;
import com.whotel.company.entity.Company;
import com.whotel.company.entity.PublicNo;
import com.whotel.front.controller.FanBaseController;
import com.whotel.front.entity.WeixinFan;
import com.whotel.hotel.entity.Hotel;
import com.whotel.hotel.service.HotelService;
import com.whotel.thirdparty.jxd.mode.vo.HotelBranchVO;
import com.whotel.webiste.entity.Website;
import com.whotel.webiste.entity.WebsiteTemplate;
import com.whotel.webiste.service.WebsiteService;
import com.whotel.webiste.service.WebsiteTemplateService;
import com.whotel.weixin.bean.Location;
import com.whotel.weixin.service.LocationService;

/**
 * 微网站展示控制类
 * @author 冯勇
 *
 */
@Controller
public class WebsiteController extends FanBaseController {

	@Autowired
	private WebsiteTemplateService templateService;
	
	@Autowired
	private WebsiteService websiteService;
	
	@Autowired
	private HotelService hotelService;
	
	/**
	 * 进入微网站
	 * @param tempId
	 * @param siteId
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"/website", "/oauth/website"})
	public String website(String tempId, String siteId,String code, HttpServletRequest req) {
		Company company = getCurrentCompany(req);
		WeixinFan fan = getCurrentFan(req);
		Website website = null;
		WebsiteTemplate template = null;
		
		if(StringUtils.isNotBlank(tempId) || StringUtils.isNotBlank(siteId)) {
			website = websiteService.getWebsiteById(siteId);
			template = templateService.getWebsiteTemplateById(tempId);
			if((template != null && website == null) || (template != null && !StringUtils.equals(website.getTemplateId(), tempId))) {
				website = new Website();
				BeanUtil.copyProperties(website, template);
			} else if(website != null) {
				template = templateService.getWebsiteTemplateById(website.getTemplateId());
			}
		} else {
			website = websiteService.getEnableWebsite(company.getId());
			if(website != null) {
				template = templateService.getWebsiteTemplateById(website.getTemplateId());
			}
		}
		
		if(fan != null) {
			Location location = LocationService.getLocationService().getLocation(fan.getOpenId());
			req.setAttribute("location", location);
		}
		req.setAttribute("company", company);
		req.setAttribute("website", website);
		req.setAttribute("template", template);
		
		if(company == null){
			company = companyService.getCompanyByCode(code);
		}
		if(Boolean.TRUE.equals(company.getGroup())){
			List<Hotel> hotels = hotelService.findAllHotels(company.getId());
			req.setAttribute("hotels", hotels);
		}
		return "/front/website/template/"+template.getTemplate();
	}
	
	/**
	 * 预览微网站
	 * @param website
	 * @param req
	 * @return
	 */
	@RequestMapping("/previewWebsite")
	public String previewWebsite(Website website, HttpServletRequest req) {
		WebsiteTemplate template = templateService.getWebsiteTemplateById(website.getTemplateId());
		req.setAttribute("website", website);
		req.setAttribute("template", template);
		return "/front/website/template/"+template.getTemplate();
	}
	
	/**
	 * 微信联系
	 * @param req
	 * @return
	 */
	@RequestMapping("/wxContact")
	public String wxContact(HttpServletRequest req) {
		Company company = getCurrentCompany(req);
		PublicNo publicNo = getCurrentPublicNo(req);
		req.setAttribute("company", company);
		req.setAttribute("publicNo", publicNo);
		return "/front/website/wx_contact";
	}
	
	/**
	 * 导航
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("/oauth/navigation")
	public String navigation(HttpServletRequest req) throws UnsupportedEncodingException{
		WeixinFan fan = getCurrentFan(req);
		Company company = getCurrentCompany(req);
		
		if(StringUtils.equals(company.getId(), "57a2f38dd5e1ea160427e904")){//阳江佰利酒店
			req.setAttribute("mapUrl", "http://api.map.baidu.com/direction?region="+company.getRegion().getName()+"&mode=driving&src="+URLEncoder.encode(company.getName(), "UTF8")+"&output=html&destination=latlng:"+company.getCoords()[1]+","+company.getCoords()[0]+"|name:"+URLEncoder.encode(company.getName(), "UTF8")+"&origin=latlng:");
			return "/front/website/navigation";
		}
		
//		Page<Hotel> page = new Page<>();
//		page.addFilter("companyId", FilterModel.EQ, company.getId());
//		List<Hotel> list = hotelService.findHotels(page).getResult();
		
//		if(list.size()>0){
		Location location = LocationService.getLocationService().getLocation(fan.getOpenId());
		if(location == null){
			location = new Location();
		}
//			Hotel hotel = list.get(0);
//			HotelBranchVO hotelBranchVO = hotelService.getHotelBranchVO(company.getId(), hotel.getCode());
//			return "redirect:http://api.map.baidu.com/marker?location="+hotel.getCoords()[1]+","+hotel.getCoords()[0]+"&title="+URLEncoder.encode(hotelBranchVO.getCname(), "UTF8")+"&content="+URLEncoder.encode(hotelBranchVO.getAddress(), "UTF8")+"&output=html&src=jxd";
		return "redirect:http://api.map.baidu.com/direction?region="+company.getRegion().getName()+"&mode=driving&src="+URLEncoder.encode(company.getName(), "UTF8")+"&output=html&destination=latlng:"+company.getCoords()[1]+","+company.getCoords()[0]+"|name:"+URLEncoder.encode(company.getName(), "UTF8")+"&origin=latlng:"+location.getLat()+","+location.getLon()+"|name:你的位置";
//		}
		//return "";
	}
}
