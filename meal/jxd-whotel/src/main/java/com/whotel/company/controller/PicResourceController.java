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
import com.whotel.company.entity.PicResource;
import com.whotel.company.service.PicResourceService;

/**
 * 图片资源管理控制器
 * @author 冯勇
 */
@Controller
@RequestMapping("/company/resource")
public class PicResourceController extends BaseCompanyController {

	@Autowired
	private PicResourceService picResourceService;
	
	/**
	 * 加载图片素材
	 * @param req
	 * @return
	 */
	@RequestMapping("/listPicResource")
	public String listPicResource(Page<PicResource> page, QueryParam queryParam, HttpServletRequest req) {
		
		if(queryParam != null && queryParam.getParams() != null) {
			
			Map<String, String> params = queryParam.getParams();
			
			String name = params.get("name");
			
			if(StringUtils.isNotBlank(name)) {
				page.addFilter("name", FilterModel.LIKE, name);
			}
		}
		loadPicResource(page, req);
		return "/company/resource/pic_list";
	}
	
	/**
	 * ajax加载图片素材
	 * @param req
	 * @return
	 */
	@RequestMapping("/ajaxLoadPicResource")
	@ResponseBody
	public Page<PicResource> ajaxLoadPicResource(Page<PicResource> page, QueryParam queryParam, HttpServletRequest req) {
		
		if(queryParam != null && queryParam.getParams() != null) {
			
			Map<String, String> params = queryParam.getParams();
			
			String name = params.get("name");
			
			if(StringUtils.isNotBlank(name)) {
				page.addFilter("name", FilterModel.LIKE, name);
			}
		}
		loadPicResource(page, req);
		return page;
	}
	
	private void loadPicResource(Page<PicResource> page,
			HttpServletRequest req) {
		CompanyAdmin companyAdmin = getCurrentCompanyAdmin(req);
		page.addFilter("companyId", FilterModel.EQ, companyAdmin.getCompanyId());
		page.addOrder(Order.desc("createTime"));
		page.setPageSize(12);
		picResourceService.findPicResources(page);
	}
	
	
	/**
	 * 更新保存图片素材
	 * @param req
	 * @return
	 */
	@RequestMapping("/updatePicResource")
	public String updatePicResource(PicResource picResource, HttpServletRequest req) {
		CompanyAdmin companyAdmin = getCurrentCompanyAdmin(req);
		picResource.setCompanyId(companyAdmin.getCompanyId());
		picResourceService.savePicResource(picResource);
		return "redirect:/company/resource/listPicResource.do";
	}
	
	/**
	 * 删除图片素材
	 * @param req
	 * @return
	 */
	@RequestMapping("/deletePicResource")
	public String deletePicResource(String ids, HttpServletRequest req) {
		CompanyAdmin companyAdmin = getCurrentCompanyAdmin(req);
		picResourceService.deleteMorePicResource(ids, companyAdmin.getCompanyId());
		return "redirect:/company/resource/listPicResource.do";
	}
}
