package com.whotel.company.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.whotel.common.dao.mongo.Order;
import com.whotel.common.dao.mongo.Page;
import com.whotel.common.dto.QueryParam;
import com.whotel.common.enums.FilterModel;
import com.whotel.company.entity.CompanyAdmin;
import com.whotel.company.entity.CompanyNotice;
import com.whotel.company.service.CompanyNoticeService;

/**
 * 商户公告管理
 * @author 冯勇
 *
 */
@Controller
@RequestMapping("/company/notice")
public class CompanyNoticeManageController extends BaseCompanyController  {

	@Autowired
	private CompanyNoticeService noticeService;
	
	/**
	 * 加载商户公告列表
	 * @param req
	 * @return
	 */
	@RequestMapping("/listCompanyNotice")
	public String listCompanyNotice(Page<CompanyNotice> page,QueryParam queryParam, HttpServletRequest req) {
		CompanyAdmin currentCompanyAdmin = getCurrentCompanyAdmin(req);
		page.addFilter("companyId", FilterModel.EQ, currentCompanyAdmin.getCompanyId());
		if(queryParam != null && queryParam.getParams() != null) {
			Map<String, String> params = queryParam.getParams();
			String title = params.get("title");
			if(StringUtils.isNotBlank(title)) {
				page.addFilter("title", FilterModel.LIKE, title);
			}
		}
		page.addOrder(Order.desc("createTime"));
		noticeService.findCompanyNotices(page);
		return "/company/notice/notice_list";
	}
	
	/**
	 * 编辑商户公告信息
	 * @param req
	 * @return
	 */
	@RequestMapping("/toEditCompanyNotice")
	public String toEditCompanyNotice(String id, HttpServletRequest req) {
		CompanyNotice notice = noticeService.getCompanyNoticeById(id);
		req.setAttribute("notice", notice);
		return "/company/notice/notice_edit";
	}
	
	/**
	 * 更新保存商户公告信息
	 * @param req
	 * @return
	 */
	@RequestMapping("/updateCompanyNotice")
	public String updateCompanyNotice(CompanyNotice companyNotice, HttpServletRequest req) {
		CompanyAdmin currentCompanyAdmin = getCurrentCompanyAdmin(req);
		companyNotice.setCompanyId(currentCompanyAdmin.getCompanyId());
		noticeService.saveCompanyNotice(companyNotice);
		return "redirect:/company/notice/listCompanyNotice.do";
	}
	
	/**
	 * 删除商户公告
	 * @param id
	 * @param req
	 * @return
	 */
	@RequestMapping("/deleteCompanyNotice")
	public String deleteCompanyNotice(String ids, HttpServletRequest req) {
		CompanyAdmin currentCompanyAdmin = getCurrentCompanyAdmin(req);
		noticeService.deleteMoreCompanyNotice(ids, currentCompanyAdmin.getCompanyId());
		return "redirect:/company/notice/listCompanyNotice.do";
	}
}
