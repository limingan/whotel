package com.whotel.admin.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.whotel.common.dao.mongo.Page;
import com.whotel.webiste.entity.ColumnLink;
import com.whotel.webiste.entity.ColumnTemplate;
import com.whotel.webiste.entity.WebsiteTemplate;
import com.whotel.webiste.entity.ThemeTemplate;
import com.whotel.webiste.service.ColumnLinkService;
import com.whotel.webiste.service.ColumnTemplateService;
import com.whotel.webiste.service.ThemeTemplateService;
import com.whotel.webiste.service.WebsiteService;
import com.whotel.webiste.service.WebsiteTemplateService;

/**
 * 微网站模板，栏目管理， 栏目链接管理类
 * @author 冯勇
 *
 */
@Controller
@RequestMapping("/admin/website")
public class WebsiteTemplateManageController extends BaseAdminController {

	@Autowired
	private WebsiteTemplateService templateService;
	
	@Autowired
	private ColumnLinkService columnLinkService;
	
	@Autowired
	private ColumnTemplateService columnTemplateService;
	
	@Autowired
	private WebsiteService websiteService;
	
	@Autowired
	private ThemeTemplateService themeTemplateService;
	
	/**
	 * 加载微网站模板列表
	 * @param req
	 * @return
	 */
	@RequestMapping("/listWebsiteTemplate")
	public String listWebsiteTemplate(Page<WebsiteTemplate> page, HttpServletRequest req) {
		templateService.findWebsiteTemplates(page);
		req.setAttribute("page", page);
		return "/admin/website/template_list";
	}
	
	/**
	 * 编辑微网站模板
	 * @param req
	 * @return
	 */
	@RequestMapping("/toEditWebsiteTemplate")
	public String toEditWebsiteTemplate(String id, HttpServletRequest req) {
		WebsiteTemplate template = templateService.getWebsiteTemplateById(id);
		req.setAttribute("websiteTemplate", template);
		List<ColumnLink> columnLinks = columnLinkService.findAllColumnLinks();
		req.setAttribute("columnLinks", columnLinks);
		return "/admin/website/template_edit";
	}
	
	@RequestMapping("/ajaxfindAllColumnLinks")
	@ResponseBody
	public List<ColumnLink> ajaxfindAllColumnLinks(){
		List<ColumnLink> columnLinks = columnLinkService.findAllColumnLinks();
		return columnLinks;
	}
	
	/**
	 * 更新微网站模板
	 * @param req
	 * @return
	 */
	@RequestMapping("/updateWebsiteTemplate")
	public String updateWebsiteTemplate(WebsiteTemplate websiteTemplate, HttpServletRequest req) {
		templateService.saveWebsiteTemplate(websiteTemplate);
		return "redirect:/admin/website/listWebsiteTemplate.do";
	}
	
	/**
	 * 删除微网站模板
	 * @param ids
	 * @param req
	 * @return
	 */
	@RequestMapping("/deleteWebsiteTemplate")
	public String deleteWebsiteTemplate(String ids, HttpServletRequest req) {
		templateService.deleteMoreWebsiteTemplate(ids);
		return "redirect:/admin/website/listWebsiteTemplate.do";
	}
	
	/**
	 * 加载栏目链接列表
	 * @param req
	 * @return
	 */
	@RequestMapping("/listColumnLink")
	public String listColumnLink(Page<ColumnLink> page, HttpServletRequest req) {
		columnLinkService.findColumnLinks(page);
		req.setAttribute("page", page);
		return "/admin/website/columnlink_list";
	}
	
	/**
	 * 编辑栏目链接
	 * @param req
	 * @return
	 */
	@RequestMapping("/toEditColumnLink")
	public String toEditColumnLink(String id, HttpServletRequest req) {
		ColumnLink columnLink = columnLinkService.getColumnLinkById(id);
		req.setAttribute("columnLink", columnLink);
		return "/admin/website/columnlink_edit";
	}
	
	/**
	 * 更新保存栏目链接
	 * @param req
	 * @return
	 */
	@RequestMapping("/updateColumnLink")
	public String updateColumnLink(ColumnLink columnLink, HttpServletRequest req) {
		columnLinkService.saveColumnLink(columnLink);
		return "redirect:/admin/website/listColumnLink.do";
	}
	
	/**
	 * 删除栏目链接
	 * @param id
	 * @param req
	 * @return
	 */
	@RequestMapping("/deleteColumnLink")
	public String deleteColumnLink(String ids, HttpServletRequest req) {
		columnLinkService.deleteMoreColumnLink(ids);
		return "redirect:/admin/website/listColumnLink.do";
	}
	
	/**
	 * 加载栏目模板列表
	 * @param req
	 * @return
	 */
	@RequestMapping("/listColumnTemplate")
	public String listColumnTemplate(Page<ColumnTemplate> page, HttpServletRequest req) {
		columnTemplateService.findColumnTemplates(page);
		req.setAttribute("page", page);
		return "/admin/website/columnTemplate_list";
	}
	
	/**
	 * 编辑栏目模板
	 * @param req
	 * @return
	 */
	@RequestMapping("/toEditColumnTemplate")
	public String toEditColumnTemplate(String id, HttpServletRequest req) {
		ColumnTemplate template = columnTemplateService.getColumnTemplateById(id);
		req.setAttribute("columnTemplate", template);
		return "/admin/website/columnTemplate_edit";
	}
	
	/**
	 * 更新栏目模板
	 * @param req
	 * @return
	 */
	@RequestMapping("/updateColumnTemplate")
	public String updateColumnTemplate(ColumnTemplate columnTemplate, HttpServletRequest req) {
		columnTemplateService.saveColumnTemplate(columnTemplate);
		return "redirect:/admin/website/listColumnTemplate.do";
	}
	
	/**
	 * 删除栏目模板
	 * @param ids
	 * @param req
	 * @return
	 */
	@RequestMapping("/deleteColumnTemplate")
	public String deleteColumnTemplate(String ids, HttpServletRequest req) {
		columnTemplateService.deleteMoreColumnTemplate(ids);
		return "redirect:/admin/website/listColumnTemplate.do";
	}
	
	
	/**
	 * 加载主题列表
	 * @param req
	 * @return
	 */
	@RequestMapping("/listThemeTemplate")
	public String listThemeTemplate(HttpServletRequest req) {
		List<ThemeTemplate> themeTemplates = themeTemplateService.findAllThemeTemplate();
		req.setAttribute("themeTemplates", themeTemplates);
		return "/admin/website/themeTemplate_list";
	}
	
	/**
	 * 编辑主题
	 * @param req
	 * @return
	 */
	@RequestMapping("/toEditThemeTemplate")
	public String toEditThemeTemplate(String id, HttpServletRequest req) {
		ThemeTemplate themeTemplate = themeTemplateService.getThemeTemplateById(id);
		req.setAttribute("themeTemplate", themeTemplate);
		List<ColumnLink> columnLinks = columnLinkService.findAllColumnLinks();
		req.setAttribute("columnLinks", columnLinks);
		return "/admin/website/themeTemplate_edit";
	}
	
	/**
	 * 更新主题
	 * @param req
	 * @return
	 */
	@RequestMapping("/updateThemeTemplate")
	public String updateThemeTemplate(ThemeTemplate themeTemplate, HttpServletRequest req) {
		themeTemplateService.saveThemeTemplate(themeTemplate);
		return "redirect:/admin/website/listThemeTemplate.do";
	}
	
	/**
	 * 删除主题
	 * @param id
	 * @param req
	 * @return
	 */
	@RequestMapping("/deleteThemeTemplate")
	public String deleteThemeTemplate(String id, HttpServletRequest req) {
		themeTemplateService.deleteThemeTemplate(id);
		return "redirect:/admin/website/listThemeTemplate.do";
	}
}
