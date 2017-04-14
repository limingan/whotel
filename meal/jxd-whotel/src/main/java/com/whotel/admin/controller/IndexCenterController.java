package com.whotel.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 运营后台首页
 * @author 冯勇
 */
@Controller
@RequestMapping("/admin")
public class IndexCenterController extends BaseAdminController {

	/**
	 * 首页
	 * @return
	 */
	@RequestMapping("/index")
	public String index() {
		return "/admin/index";
	}
}
