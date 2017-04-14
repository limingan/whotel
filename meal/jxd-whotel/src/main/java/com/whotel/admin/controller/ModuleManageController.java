package com.whotel.admin.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.whotel.admin.entity.SysModule;
import com.whotel.admin.service.SysModuleService;
import com.whotel.common.dao.mongo.Page;
import com.whotel.common.dto.QueryParam;
import com.whotel.common.enums.FilterModel;
import com.whotel.company.entity.CompanyModule;
import com.whotel.company.service.CompanyModuleService;

/**
 * 后台模块管理
 * @author 冯勇
 *
 */
@Controller
@RequestMapping("/admin/sys")
public class ModuleManageController extends BaseAdminController {

	@Autowired
	private SysModuleService moduleService;
	
	@Autowired
	private CompanyModuleService companyModuleService;
	
	/**
	 * 加载系统模块
	 * @param req
	 * @return
	 */
	@RequestMapping("/listSysModule")
	public String listSysModule(Page<SysModule> page, HttpServletRequest req) {
		moduleService.findSysModules(page);
		req.setAttribute("page", page);
		return "/admin/sys/module_list";
	}
	
	/**
	 * 编辑系统模块
	 * @param req
	 * @return
	 */
	@RequestMapping("/toEditSysModule")
	public String toEditSysModule(String id, HttpServletRequest req) {
		SysModule module = moduleService.getSysModuleById(id);
		req.setAttribute("module", module);
		return "/admin/sys/module_edit";
	}
	
	/**
	 * 更新保存系统模块
	 * @param req
	 * @return
	 */
	@RequestMapping("/updateSysModule")
	public String updateSysModule(SysModule module, HttpServletRequest req) {
		moduleService.saveSysModule(module);
		return "redirect:/admin/sys/listSysModule.do";
	}

	/**
	 * 删除系统模块
	 * @param id
	 * @param req
	 * @return
	 */
	@RequestMapping("/deleteSysModule")
	public String deleteModule(String id, HttpServletRequest req) {
		moduleService.deleteSysModule(id);
		return "redirect:/admin/sys/listSysModule.do";
	}
	
	
/////////////////////////////////////////商户模块管理///////////////////////////////////////////////////
	/**
	 * 加载商户系统模块
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping("/listCompanyModule")
	public String listCompanyModule(Page<CompanyModule> page,QueryParam queryParam, HttpServletRequest req) {
		if(queryParam != null && queryParam.getParams() != null) {
			Map<String, String> params = queryParam.getParams();
			String name = params.get("name");
			if(StringUtils.isNotBlank(name)) {
				page.addFilter("name", FilterModel.LIKE, name);
			}
		}
		companyModuleService.findCompanyModules(page);
		req.setAttribute("page", page);
		return "/admin/sys/companyModule_list";
	}

	/**
	 * 编辑商户系统模块
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping("/toEditCompanyModule")
	public String toEditCompanyModule(String id, HttpServletRequest req) {
		CompanyModule module = companyModuleService.getCompanyModuleById(id);
		req.setAttribute("module", module);
		return "/admin/sys/companyModule_edit";
	}

	/**
	 * 更新保存商户系统模块
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping("/updateCompanyModule")
	public String updateCompanySysModule(CompanyModule module, HttpServletRequest req) {
		companyModuleService.saveCompanyModule(module);
		return "redirect:/admin/sys/listCompanyModule.do";
	}

	/**
	 * 删除商户系统模块
	 * 
	 * @param id
	 * @param req
	 * @return
	 */
	@RequestMapping("/deleteCompanyModule")
	public String deleteCompanySysModule(String id, HttpServletRequest req) {
		companyModuleService.deleteCompanyModule(id);
		return "redirect:/admin/sys/listCompanyModule.do";
	}
}
