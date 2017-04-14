package com.whotel.company.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.whotel.common.dao.mongo.Order;
import com.whotel.common.dao.mongo.Page;
import com.whotel.common.enums.FilterModel;
import com.whotel.company.entity.CompanyAdmin;
import com.whotel.company.entity.MatchKeywordRule;
import com.whotel.company.entity.MenuItem;
import com.whotel.company.entity.PublicNo;
import com.whotel.company.entity.WelcomeMsg;
import com.whotel.company.service.MatchKeywordRuleService;
import com.whotel.company.service.MenuItemService;

@Controller
@RequestMapping("/company/publicno")
public class PublicNoController extends BaseCompanyController {
	
	@Autowired
	private MatchKeywordRuleService matchKeywordRuleService;
	
	@Autowired
	private MenuItemService menuItemService;
	
	/**
	 * 公众号信息
	 * @param req
	 * @return
	 */
	@RequestMapping("/publicNoInfo")
	public String publicNoInfo(HttpServletRequest req) {
		CompanyAdmin currentCompanyAdmin = getCurrentCompanyAdmin(req);
		PublicNo publicNo = publicNoService.getPublicNoByCompanyId(currentCompanyAdmin.getCompanyId());
		req.setAttribute("publicNo", publicNo);
		return "/company/publicno/publicno_info";
	}
	
	/**
	 * 更新公众号信息
	 * @param req
	 * @return
	 */
	@RequestMapping("/updatePublicNo")
	public String updatePublicNo(PublicNo publicNo, HttpServletRequest req) {
		publicNoService.savePublicNo(publicNo);
		return "redirect:/company/publicno/publicNoInfo.do";
	}
	
	/**
	 * 微信欢迎信息
	 * @return
	 */
	@RequestMapping("/welcomeMsg")
	public String welcomeMsg(HttpServletRequest req) {
		CompanyAdmin currentCompanyAdmin = getCurrentCompanyAdmin(req);
		WelcomeMsg welcomeMsg = publicNoService.getWelcomeMsg(currentCompanyAdmin.getCompanyId());
		if(welcomeMsg != null) {
			req.setAttribute("welcomeMsg", welcomeMsg);
			req.setAttribute("responseMsg", welcomeMsg.getResponseMsg());
		}
		return "/company/publicno/welcome_msg";
	}
	
	/**
	 * 更新添加微信欢迎信息
	 * @return
	 */
	@RequestMapping("/updateWelcomeMsg")
	public String updateWelcomeMsg(WelcomeMsg welcomeMsg, HttpServletRequest req) {
		CompanyAdmin currentCompanyAdmin = getCurrentCompanyAdmin(req);
		welcomeMsg.setCompanyId(currentCompanyAdmin.getCompanyId());
		publicNoService.saveWelcomeMsg(welcomeMsg);
		return "redirect:/company/publicno/welcomeMsg.do";
	}
	
	/**
	 * 关键词自动回复
	 * @return
	 */
	@RequestMapping("/listKeywordRule")
	public String listKeywordRule(Page<MatchKeywordRule> page, HttpServletRequest req) {
		CompanyAdmin currentCompanyAdmin = getCurrentCompanyAdmin(req);
		String companyId = currentCompanyAdmin.getCompanyId();
		MatchKeywordRule defMatchKeywordRule = matchKeywordRuleService.getDefMatchKeywordRule(companyId);
		if(defMatchKeywordRule == null) {
			matchKeywordRuleService.createDefMatchKeywordRule(currentCompanyAdmin.getCompany());
		}
		page.addFilter("companyId", FilterModel.EQ, companyId);
		page.addOrder(Order.asc("createTime"));
		matchKeywordRuleService.findMatchKeywordRules(page);
		return "/company/publicno/keyword_rule_list";
	}
	
	/**
	 * 编辑关键词
	 * @return
	 */
	@RequestMapping("/toEditKeywordRule")
	public String toEditKeywordRule(String id, HttpServletRequest req) {
		MatchKeywordRule keywordRule = matchKeywordRuleService.getMatchKeywordRuleById(id);
		if(keywordRule != null) {
			req.setAttribute("keywordRule", keywordRule);
			req.setAttribute("responseMsg", keywordRule.getResponseMsg());
		}
		return "/company/publicno/keyword_rule_edit";
	}
	
	/**
	 * 更新添加关键词自动回复
	 * @return
	 */
	@RequestMapping("/updateKeywordRule")
	public String updateKeywordRule(MatchKeywordRule rule, HttpServletRequest req) {
		CompanyAdmin currentCompanyAdmin = getCurrentCompanyAdmin(req);
		rule.setCompanyId(currentCompanyAdmin.getCompanyId());
		matchKeywordRuleService.saveMatchKeywordRule(rule);
		return "redirect:/company/publicno/listKeywordRule.do";
	}
	
	/**
	 * 删除关键词自动回复
	 * @return
	 */
	@RequestMapping("/deleteKeywordRule")
	public String deleteKeywordRule(String ids, HttpServletRequest req) {
		CompanyAdmin currentCompanyAdmin = getCurrentCompanyAdmin(req);
		matchKeywordRuleService.deleteMoreMatchKeywordRule(ids, currentCompanyAdmin.getCompanyId());
		return "redirect:/company/publicno/listKeywordRule.do";
	}
	
	/**
	 * 微信公众号菜单
	 * @return
	 */
	@RequestMapping("/publicnoMenu")
	public String publicnoMenu(HttpServletRequest req) {
		CompanyAdmin currentCompanyAdmin = getCurrentCompanyAdmin(req);
		List<MenuItem> menuItemsTree = menuItemService.findMenuItemsTree(currentCompanyAdmin.getCompanyId());
		req.setAttribute("menuItemsTree", menuItemsTree);
		return "/company/publicno/publicno_menu";
	}
	
	/**
	 * 更新添加微信公众号菜单
	 * @return
	 */
	@RequestMapping("/updatePublicnoMenu")
	public String updatePublicnoMenu(MenuItem menuItem, HttpServletRequest req) {
		CompanyAdmin currentCompanyAdmin = getCurrentCompanyAdmin(req);
		menuItem.setCompanyId(currentCompanyAdmin.getCompanyId());
		menuItemService.saveMenuItem(menuItem);
		return "/company/publicno/publicno_menu";
	}
	
	/**
	 * ajax更新添加微信公众号菜单
	 * @return
	 */
	@RequestMapping("/ajaxUpdateMenuItem")
	@ResponseBody
	public MenuItem ajaxUpdateMenuItem(MenuItem menuItem, HttpServletRequest req) {
		CompanyAdmin currentCompanyAdmin = getCurrentCompanyAdmin(req);
		menuItem.setCompanyId(currentCompanyAdmin.getCompanyId());
		menuItemService.saveMenuItem(menuItem);
		return menuItemService.getMenuItemById(menuItem.getId());
	}
	
	/**
	 * ajax获取菜单项
	 * @return
	 */
	@RequestMapping("/ajaxGetMenuItem")
	@ResponseBody
	public MenuItem ajaxGetMenuItem(String id, HttpServletRequest req) {
		return menuItemService.getMenuItemById(id);
	}
	
	/**
	 * 发布同步微信公众号菜单到微信服务器
	 * @return
	 */
	@RequestMapping("/ajaxPublishMenu")
	@ResponseBody
	public boolean ajaxPublishMenu(MenuItem menuItem, HttpServletRequest req) {
		CompanyAdmin currentCompanyAdmin = getCurrentCompanyAdmin(req);
		return menuItemService.publishMenuItem(currentCompanyAdmin.getCompanyId());
	}
	
	@RequestMapping("/ajaxUpdateMenuOrders")
	@ResponseBody
	public boolean ajaxUpdateMenuOrders(@RequestParam(value = "idOrders[]") String[] idOrders, HttpServletRequest req) {
		menuItemService.updateMenuOrders(idOrders);
		return true;
	}
	
	/**
	 * ajax更新添加微信公众号菜单
	 * @return
	 */
	@RequestMapping("/ajaxDeleteMenuItem")
	@ResponseBody
	public boolean ajaxDeleteMenuItem(String ids, HttpServletRequest req) {
		CompanyAdmin currentCompanyAdmin = getCurrentCompanyAdmin(req);
		menuItemService.deleteMoreMenuItem(ids, currentCompanyAdmin.getCompanyId());
		return true;
	}
}
