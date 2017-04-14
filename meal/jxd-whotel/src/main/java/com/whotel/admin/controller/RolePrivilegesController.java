package com.whotel.admin.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.whotel.admin.entity.SysModule;
import com.whotel.admin.entity.SysRole;
import com.whotel.admin.service.SysModuleService;
import com.whotel.admin.service.SysRoleService;
import com.whotel.common.dao.mongo.Page;

/**
 * 管理员角色管理，角色权限管理
 * @author 冯勇
 *
 */
@Controller
@RequestMapping("/admin/sys")
public class RolePrivilegesController extends BaseAdminController {

	@Autowired
	private SysRoleService roleService;
	
	@Autowired
	private SysModuleService moduleService;
	
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
	@RequestMapping("/listSysRole")
	public String listSysRole(Page<SysRole> page, HttpServletRequest req) {
		roleService.findSysRoles(page);
		req.setAttribute("page", page);
		return "/admin/sys/role_list";
	}
	
	/**
	 * 编辑系统角色
	 * @param req
	 * @return
	 */
	@RequestMapping("/toEditSysRole")
	public String toEditSysRole(String id, HttpServletRequest req) {
		SysRole role = roleService.getSysRoleById(id);
		List<SysModule> roleModules = null;
		if(role != null) {
			roleModules = role.getModules();
		}
		List<SysModule> modules = moduleService.findAllSysModules();
		if(modules != null) {
			for(SysModule module:modules) {
				if(roleModules != null && roleModules.contains(module)) {
					module.setChecked(true);
				}
			}
		}
		req.setAttribute("role", role);
		req.setAttribute("modules", modules);
		return "/admin/sys/role_edit";
	}
	
	/**
	 * 更新保存系统角色
	 * @param req
	 * @return
	 */
	@RequestMapping("/updateSysRole")
	public String updateSysRole(SysRole role, HttpServletRequest req) {
		
		if(role != null) {
			List<SysModule> modules = role.getModules();
			List<SysModule> roleModules = null;
			if(modules != null) {
				roleModules = new ArrayList<SysModule>();
				for(SysModule module:modules) {
					roleModules.add(moduleService.getSysModuleById(module.getId()));
				}
				role.setModules(roleModules);
			}
			
			roleService.saveSysRole(role);
		}
		return "redirect:/admin/sys/listSysRole.do";
	}

	/**
	 * 删除系统角色
	 * @param id
	 * @param req
	 * @return
	 */
	@RequestMapping("/deleteSysRole")
	public String deleteSysRole(String id, HttpServletRequest req) {
		roleService.deleteSysRole(id);
		return "redirect:/admin/sys/listSysRole.do";
	}
}
