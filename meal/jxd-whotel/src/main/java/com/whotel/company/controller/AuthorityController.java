package com.whotel.company.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.whotel.common.dao.mongo.Page;
import com.whotel.common.enums.FilterModel;
import com.whotel.company.entity.CompanyAdmin;
import com.whotel.company.entity.CompanyModule;
import com.whotel.company.entity.CompanyRole;
import com.whotel.company.service.CompanyModuleService;
import com.whotel.company.service.CompanyRoleService;

/**
 * 管理员角色管理，角色权限管理
 * @author 冯勇
 *
 */
@Controller
@RequestMapping("/company/authority")
public class AuthorityController extends BaseCompanyController {

	@Autowired
	private CompanyRoleService roleService;
	
	@Autowired
	private CompanyModuleService moduleService;
	
	/**
	 * 无权限
	 * @param req
	 * @return
	 */
	@RequestMapping("/noPrivilege")
	public String noPrivilege(HttpServletRequest req) {
		return "/common/no_privilege";
	}
	
	/**
	 * 加载系统角色
	 * @param req
	 * @return
	 */
	@RequestMapping("/listCompanyRole")
	public String listCompanyRole(Page<CompanyRole> page, HttpServletRequest req) {
		CompanyAdmin companyAdmin = getCurrentCompanyAdmin(req);
		page.addFilter("companyId", FilterModel.EQ, companyAdmin.getCompanyId());
		roleService.findCompanyRoles(page);
		req.setAttribute("page", page);
		return "/company/authority/companyRole_list";
	}
	
	/**
	 * 编辑系统角色
	 * @param req
	 * @return
	 */
	@RequestMapping("/toEditCompanyRole")
	public String toEditCompanyRole(String id, HttpServletRequest req) {
		CompanyRole role = roleService.getCompanyRoleById(id);
		List<CompanyModule> roleModules = null;
		if(role != null) {
			roleModules = role.getModules();
		}
		List<CompanyModule> modules = getCurrentCompanyAdmin(req).getCompany().getModules();
		if(modules != null) {
			for(CompanyModule module:modules) {
				if(roleModules != null && roleModules.contains(module)) {
					module.setChecked(true);
				}
			}
		}
		req.setAttribute("role", role);
		req.setAttribute("modules", modules);
		return "/company/authority/companyRole_edit";
	}
	
	/**
	 * 更新保存系统角色
	 * @param req
	 * @return
	 */
	@RequestMapping("/updateCompanyRole")
	public String updateSysRole(CompanyRole role, HttpServletRequest req) {
		
		if(role != null) {
			List<CompanyModule> modules = role.getModules();
			List<CompanyModule> roleModules = null;
			if(modules != null) {
				roleModules = new ArrayList<CompanyModule>();
				for(CompanyModule module:modules) {
					roleModules.add(moduleService.getCompanyModuleById(module.getId()));
				}
				role.setModules(roleModules);
			}
			CompanyAdmin companyAdmin = getCurrentCompanyAdmin(req);
			role.setCompanyId(companyAdmin.getCompanyId());
			roleService.saveSysRole(role);
		}
		return "redirect:/company/authority/listCompanyRole.do";
	}

	/**
	 * 删除系统角色
	 * @param id
	 * @param req
	 * @return
	 */
	@RequestMapping("/deleteCompanyRole")
	public String deleteCompanyRole(String id, HttpServletRequest req) {
		roleService.deleteSysRole(id);
		return "redirect:/company/authority/listCompanyRole.do";
	}
}
