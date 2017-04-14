package com.whotel.company.controller;

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
import com.whotel.company.entity.CompanyAdmin;
import com.whotel.company.entity.NewsResource;
import com.whotel.company.service.NewsResourceService;

/**
 * 图文资源管理控制器
 * @author 冯勇
 */
@Controller
@RequestMapping("/company/resource")
public class NewsResourceController extends BaseCompanyController {

	@Autowired
	private NewsResourceService newsResourceService;
	
	/**
	 * 加载图文素材
	 * @param req
	 * @return
	 */
	@RequestMapping("/listNewsResource")
	public String listNewsResource(Page<NewsResource> page, QueryParam queryParam, HttpServletRequest req) {
		
		if(queryParam != null && queryParam.getParams() != null) {
			
			Map<String, String> params = queryParam.getParams();
			
			String name = params.get("name");
			
			if(StringUtils.isNotBlank(name)) {
				page.addFilter("name", FilterModel.LIKE, name);
			}
		}
		loadNewsResource(page, req);
		return "/company/resource/resource_list";
	}
	
	/**
	 * ajax加载图文素材
	 * @param req
	 * @return
	 */
	@RequestMapping("/ajaxLoadNewsResource")
	@ResponseBody
	public Page<NewsResource> ajaxLoadNewsResource(Page<NewsResource> page, QueryParam queryParam, HttpServletRequest req) {
		
		if(queryParam != null && queryParam.getParams() != null) {
			
			Map<String, String> params = queryParam.getParams();
			
			String name = params.get("name");
			
			if(StringUtils.isNotBlank(name)) {
				page.addFilter("name", FilterModel.LIKE, name);
			}
		}
		
		loadNewsResource(page, req);
		return page;
	}
	
	private void loadNewsResource(Page<NewsResource> page,
			HttpServletRequest req) {
		CompanyAdmin companyAdmin = getCurrentCompanyAdmin(req);
		page.addFilter("companyId", FilterModel.EQ, companyAdmin.getCompanyId());
		page.addOrder(Order.desc("createTime"));
		newsResourceService.findNewsResources(page);
	}
	
	
	/**
	 * 编辑图文素材
	 * @param req
	 * @return
	 */
	@RequestMapping("/toEditNewsResource")
	public String toEditNewsResource(String id, HttpServletRequest req) {
		NewsResource newsResource = newsResourceService.getNewsResourceById(id);
		req.setAttribute("newsResource", newsResource);
		return "/company/resource/resource_edit";
	}
	
	/**
	 * 更新保存图文素材
	 * @param req
	 * @return
	 */
	@RequestMapping("/updateNewsResource")
	public String updateNewsResource(NewsResource newsResource, HttpServletRequest req) {
		CompanyAdmin companyAdmin = getCurrentCompanyAdmin(req);
		newsResource.setCompanyId(companyAdmin.getCompanyId());
		newsResourceService.saveNewsResource(newsResource);
		return "redirect:/company/resource/listNewsResource.do";
	}
	
	/**
	 * 删除图文素材
	 * @param req
	 * @return
	 */
	@RequestMapping("/deleteNewsResource")
	public String deleteNewsResource(String ids, HttpServletRequest req) {
		CompanyAdmin companyAdmin = getCurrentCompanyAdmin(req);
		newsResourceService.deleteMoreNewsResource(ids, companyAdmin.getCompanyId());
		return "redirect:/company/resource/listNewsResource.do";
	}
	
	@RequestMapping("/synchronizeMPResource")
	@ResponseBody
	public Boolean synchronizeMPResource(String type, HttpServletRequest req) {
		CompanyAdmin companyAdmin = getCurrentCompanyAdmin(req);
		return newsResourceService.synchronizeMPResource(companyAdmin.getCompanyId(), type);
	}
}
