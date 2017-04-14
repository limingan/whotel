package com.whotel.admin.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.whotel.admin.entity.SysAdmin;
import com.whotel.admin.entity.SysModule;
import com.whotel.admin.entity.SysRole;
import com.whotel.admin.service.AdminActionLogService;
import com.whotel.admin.service.SysAdminService;
import com.whotel.admin.service.SysModuleService;
import com.whotel.admin.service.SysRoleService;
import com.whotel.common.base.Constants;
import com.whotel.common.dao.mongo.Page;
import com.whotel.common.util.EncryptUtil;

/**
 * 后台管理员登录与注销控制类
 * @author 冯勇
 */
@Controller
@RequestMapping("/admin")
public class AdminManageController extends BaseAdminController {
	
	private static final Logger logger = Logger.getLogger(AdminManageController.class);

	@Autowired
	private SysRoleService roleService;
	
	@Autowired
	private SysModuleService moduleService;
	
	@Autowired
	private AdminActionLogService actionLogService;
	
	/**
	 * 进入登录页面
	 * @return
	 */
	@RequestMapping("/toLogin")
	public String toLogin() {
		return "/admin/login";
	}
	
	/**
	 * 登录
	 * @return
	 */
	@RequestMapping("/login")
	public String login(SysAdmin admin, String captcha, HttpServletRequest req, HttpServletResponse res) {
		
//		if(StringUtils.isNotBlank(admin.getUserName()) && StringUtils.isNotBlank(admin.getPassword())){
//			admin.setPassword(EncryptUtil.md5(admin.getPassword()));
//			adminService.saveSysAdmin(admin);
//		}
		
		if (StringUtils.isNotBlank(captcha)) {
			String captchaStr = (String) req.getSession().getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
			req.getSession().removeAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
			// 验证码检验
			System.out.println("input:"+captcha+"-----------------session:"+captchaStr);
			if (StringUtils.equalsIgnoreCase(captchaStr, captcha)) {
				SysAdmin sysAdmin = adminService.getSysAdminByUserName(admin.getUserName());
				Integer count = 0;
				if(sysAdmin == null ){
					req.setAttribute("message", "账户或密码不正确!");
					return "/admin/login";
				}
				if(sysAdmin.getErrorCount() != null){
					count = sysAdmin.getErrorCount();
				}
				
				if(count < 10){
					if(adminService.login(admin)) {
						logger.debug(admin.getUserName() + " Login success."); 
						HttpSession session = req.getSession();
						session.setAttribute(Constants.Session.ADMIN_LOGIN_KEY, admin.getUserName());
						SysRole role = admin.getRole();
						List<SysModule> allModules = moduleService.findAllSysModules();
						List<SysModule> modules = role==null?null:role.getModules();
						List<String> noPrivilegeUrls = null;
						if(allModules != null) {
							noPrivilegeUrls = new ArrayList<String>();
							for(SysModule module:allModules) {
								if(modules != null && !modules.contains(module)) {
									noPrivilegeUrls.add(module.getLinkUrl());
								}
							}
						}
						session.setAttribute(Constants.Session.ADMIN_PRIVILEGE_KEY, noPrivilegeUrls);
						return "redirect:/admin/index.do";
					}  else {
						logger.debug("Login failed.");
						req.setAttribute("message", "账户或密码不正确!");
						adminService.updateErrorCount(sysAdmin.getId(), false);
					}
				}else{
					req.setAttribute("message", "此账户已锁定!");
				}
			} else {
				req.setAttribute("message", "验证码不正确!");
			}
		}
		return "/admin/login";
	}
	
	@RequestMapping("/logout")
	public String logout(HttpServletRequest req) {
		HttpSession session = req.getSession();
		if (session != null) {
			session.removeAttribute(Constants.Session.ADMIN_LOGIN_KEY);
			session.removeAttribute(Constants.Session.ADMIN_PRIVILEGE_KEY);
		}
		return "/admin/login";
	}
	
	@RequestMapping("/auth/modifyPassword")
	public String modifyPassword(String oldPassword, String password, HttpServletRequest req, HttpServletResponse res) {
		if(StringUtils.isNotBlank(oldPassword) && StringUtils.isNotBlank(password)) {
			String userName = (String) req.getSession().getAttribute(Constants.Session.ADMIN_LOGIN_KEY);
			SysAdmin currentAdmin = adminService.getSysAdminByUserName(userName);
			if(currentAdmin != null && StringUtils.equals(EncryptUtil.md5(oldPassword), currentAdmin.getPassword())) {
				currentAdmin.setPassword(EncryptUtil.md5(password));
				adminService.saveSysAdmin(currentAdmin);
				req.setAttribute("message", "密码修改成功");
			} else {
				req.setAttribute("message", "旧密码不正确");
			}
		} 
		return "/admin/auth/admin_pwd";
	}
	
	/**
	 * 加载系统管理员
	 * @param req
	 * @return
	 */
	@RequestMapping("/sys/listSysAdmin")
	public String listSysAdmin(Page<SysAdmin> page, Integer limit, HttpServletRequest req) {
		adminService.findSysAdmins(page);
		req.setAttribute("page", page);
		return "/admin/sys/admin_list";
	}
	
	/**
	 * 编辑系统管理员
	 * @param req
	 * @return
	 */
	@RequestMapping("/sys/toEditSysAdmin")
	public String toEditSysAdmin(String id, HttpServletRequest req) {
		SysAdmin admin = adminService.getSysAdminById(id);
		
		List<SysRole> roles = roleService.findAllSysRoles();
		req.setAttribute("admin", admin);
		req.setAttribute("roles", roles);
		return "/admin/sys/admin_edit";
	}
	
	/**
	 * 更新保存系统管理员
	 * @param req
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("/sys/updateSysAdmin")
	public String updateSysAdmin(SysAdmin admin, HttpServletRequest req) throws UnsupportedEncodingException {
		
		if(admin != null) {
			
			String id = admin.getId();
			if(StringUtils.isBlank(id)) {
				SysAdmin existAdmin = adminService.getSysAdminByUserName(admin.getUserName());
				if(existAdmin != null) {
					return "redirect:/admin/sys/listSysAdmin.do?message="+URLEncoder.encode("账号已经存在！", "UTF8");
				}
			}
			
			String password = admin.getPassword();
			if(StringUtils.isNotBlank(password)) {
				admin.setPassword(EncryptUtil.md5(password));
			} else {
				admin.setPassword(admin.getOldPassword());
			}
			adminService.saveSysAdmin(admin);
		}
		return "redirect:/admin/sys/listSysAdmin.do";
	}

	/**
	 * 删除系统管理员
	 * @param id
	 * @param req
	 * @return
	 */
	@RequestMapping("/sys/deleteSysAdmin")
	public String deleteSysAdmin(String id, HttpServletRequest req) {
		adminService.deleteSysAdmin(id);
		return "redirect:/admin/sys/listSysAdmin.do";
	}
}
