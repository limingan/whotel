package com.whotel.company.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.whotel.common.base.Constants;
import com.whotel.common.base.controller.BaseController;
import com.whotel.company.entity.CompanyAdmin;
import com.whotel.company.entity.PublicNo;
import com.whotel.company.service.CompanyService;
import com.whotel.company.service.PublicNoService;

/**
 * 商户后台控制器基类
 * @author 冯勇
 *
 */
@Controller
public class BaseCompanyController extends BaseController {
	
	@Autowired
	protected CompanyService companyService;
	
	@Autowired
	protected PublicNoService publicNoService;
	
	/**
	 * 获取当前登录用户信息
	 * @param req
	 * @return
	 */
	public CompanyAdmin getCurrentCompanyAdmin(HttpServletRequest req) {
		HttpSession session = req.getSession();
		String code = (String) session.getAttribute(Constants.Session.COMPANY_ADMIN_LOGIN_CODE);
		String account = (String) session.getAttribute(Constants.Session.COMPANY_ADMIN_LOGIN_KEY);
		return companyService.getCompanyAdmin(code, account);
	}
	
	/**
	 * 获取当前公司绑定的公众号
	 * @param req
	 * @return
	 */
	public PublicNo getCurrentPublicNo(HttpServletRequest req) {
		CompanyAdmin companyAdmin = getCurrentCompanyAdmin(req);
		return publicNoService.getPublicNoByCompanyId(companyAdmin.getCompanyId());
	}
}
