package com.whotel.front.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.whotel.company.entity.NewsItem;
import com.whotel.company.entity.NewsResource;
import com.whotel.company.entity.PublicNo;
import com.whotel.company.service.NewsResourceService;
import com.whotel.company.service.PublicNoService;

/**
 * 前端页面展示
 * @author 冯勇
 *
 */
@Controller
@RequestMapping("/front")
public class FrontPageController extends FanBaseController {

	@Autowired
	private NewsResourceService newsResourceService;
	
	@Autowired
	private PublicNoService publicNoService;
	
	/**
	 * 显示图文信息页面
	 * @param idKey
	 * @param req
	 * @return
	 */
	@RequestMapping("/showNewsItem")
	public String showNewsItem(String idKey, HttpServletRequest req) {
		if(StringUtils.isNotBlank(idKey)) {
			String[] idKeys = idKey.split(",");
			if(idKeys.length == 2) {
				NewsResource newsResource = newsResourceService.getNewsResourceById(idKeys[0]);
				NewsItem newsItem = newsResourceService.getNewsItem(newsResource, Integer.parseInt(idKeys[1]));
				
				String companyId = newsResource.getCompanyId();
				PublicNo publicNo = publicNoService.getPublicNoByCompanyId(companyId);
				
				req.setAttribute("newsItem", newsItem);
				req.setAttribute("publicNo", publicNo);
				//req.setAttribute("createTime", newsResource.getCreateTime());
				req.setAttribute("createTime", new Date());
			}
		}
		return "/front/news_item";
	}
}
