package com.whotel.admin.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.whotel.admin.entity.SysAdmin;
import com.whotel.admin.service.SysAdminService;
import com.whotel.common.base.Constants;
import com.whotel.common.base.controller.BaseController;

/**
 * 运营管理后台控制器基类
 * @author 冯勇
 *
 */
@Controller
public class BaseAdminController extends BaseController {
	
	@Autowired
	protected SysAdminService adminService;
	
	/**
	 * 获取当前登录用户信息
	 * @param req
	 * @return
	 */
	public SysAdmin getCurrentAdmin(HttpServletRequest req) {
		HttpSession session = req.getSession();
		String userName = (String) session.getAttribute(Constants.Session.ADMIN_LOGIN_KEY);
		return adminService.getSysAdminByUserName(userName);
	}
}
