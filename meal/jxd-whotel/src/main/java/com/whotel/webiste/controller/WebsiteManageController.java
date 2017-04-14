package com.whotel.webiste.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.whotel.common.dao.mongo.Page;
import com.whotel.common.enums.FilterModel;
import com.whotel.common.util.BeanUtil;
import com.whotel.company.controller.BaseCompanyController;
import com.whotel.company.dao.CompanyDao;
import com.whotel.company.entity.Company;
import com.whotel.company.entity.CompanyAdmin;
import com.whotel.company.service.CompanyService;
import com.whotel.webiste.entity.ColumnLink;
import com.whotel.webiste.entity.ColumnNews;
import com.whotel.webiste.entity.Theme;
import com.whotel.webiste.entity.ThemeTemplate;
import com.whotel.webiste.entity.Website;
import com.whotel.webiste.entity.WebsiteTemplate;
import com.whotel.webiste.service.ColumnLinkService;
import com.whotel.webiste.service.ColumnNewsService;
import com.whotel.webiste.service.ThemeService;
import com.whotel.webiste.service.ThemeTemplateService;
import com.whotel.webiste.service.WebsiteService;
import com.whotel.webiste.service.WebsiteTemplateService;

/**
 * 微网站管理控制类
 * @author 冯勇
 *
 */
@Controller
@RequestMapping("/company/website")
public class WebsiteManageController extends BaseCompanyController {

	@Autowired
	private ColumnLinkService columnLinkService;
	
	@Autowired
	private WebsiteTemplateService templateService;
	
	@Autowired
	private WebsiteService websiteService;
	
	@Autowired
	private ColumnNewsService columnNewsService;
	
	@Autowired
	private ThemeService themeService;
	
	@Autowired
	private ThemeTemplateService themeTemplateService;
	
	/**
	 * 栏目链接列表
	 * @param page
	 * @param req
	 * @return
	 */
	@RequestMapping("/listColumnLink")
	public String listColumnLink(Page<ColumnLink> page, HttpServletRequest req) {
		CompanyAdmin companyAdmin = getCurrentCompanyAdmin(req);
		columnLinkService.findColumnLinks(page);
		req.setAttribute("companyId", companyAdmin.getCompanyId());
		return "/company/website/columnlink_list";
	}
	
	/**
	 * 微网站列表
	 * @param page
	 * @param req
	 * @return
	 */
	@RequestMapping("/listWebsite")
	public String listWebsite(Page<Website> page, HttpServletRequest req) {
		CompanyAdmin currentCompanyAdmin = getCurrentCompanyAdmin(req);
		
		page.addFilter("companyId", FilterModel.EQ, currentCompanyAdmin.getCompanyId());
		websiteService.findWebsites(page);
		List<Website> result = page.getResult();
		if(result != null && result.size() > 0) {
			return "/company/website/website_list";
		} else {
			return "redirect:/company/website/selectTemplate.do";
		}
	}
	
	/**
	 * 选择模板
	 * @param siteId
	 * @param req
	 * @return
	 */
	@RequestMapping("/selectTemplate")
	public String selectTemplate(String siteId, HttpServletRequest req) {
		Website website = websiteService.getWebsiteById(siteId);
		List<WebsiteTemplate> templates = templateService.findAllWebsiteTemplates();
		req.setAttribute("templates", templates);
		req.setAttribute("website", website);
		return "/company/website/template_select";
	}
	
	/**
	 * 编辑微网站
	 * @param tempId
	 * @param siteId
	 * @param req
	 * @return
	 */
	@RequestMapping("/editTemplateColumn")
	public String editTemplateColumn(String tempId, String siteId, HttpServletRequest req) {
		CompanyAdmin currentCompanyAdmin = getCurrentCompanyAdmin(req);
		Website website = websiteService.getWebsiteById(siteId);
		WebsiteTemplate template = templateService.getWebsiteTemplateById(tempId);
		
		List<ColumnNews> columnNewss = columnNewsService.findColumnNewssByCompanyId(currentCompanyAdmin.getCompanyId());
		if(website == null && template != null) {
			website = new Website();
			BeanUtil.copyProperties(website, template);
			website.setId(null);
		} else if(website != null && !StringUtils.equals(tempId, website.getTemplateId())) {  //如果更换模板则清空现在微网站数据
			String id = website.getId();
			website = new Website();
			BeanUtil.copyProperties(website, template);
			website.setId(id);
		}
		req.setAttribute("website", website);
		req.setAttribute("template", template);
		req.setAttribute("columnNewss", columnNewss);
		
		List<ColumnLink> columnLinks = columnLinkService.findAllColumnLinks();
		req.setAttribute("columnLinks", columnLinks);
		
		req.setAttribute("companyId", currentCompanyAdmin.getCompanyId());
		return "/company/website/template_column_edit";
	}
	
	/**
	 * 更新微网站
	 * @param website
	 * @param req
	 * @return
	 */
	@RequestMapping("/updateWebsite")
	public String updateWebsite(Website website, HttpServletRequest req) {
		CompanyAdmin currentCompanyAdmin = getCurrentCompanyAdmin(req);
		website.setCompanyId(currentCompanyAdmin.getCompanyId());
		websiteService.saveWebsite(website);
		return "/company/website/website_name_edit";
	}
	
	/**
	 * 进入编辑微网站名称
	 * @param siteId
	 * @param req
	 * @return
	 */
	@RequestMapping("/editWebsiteName")
	public String editWebsiteName(String siteId, HttpServletRequest req) {
		Website website = websiteService.getWebsiteById(siteId);
		req.setAttribute("website", website);
		return "/company/website/website_name_edit";
	}
	
	/**
	 * 设置微网站名称
	 * @param website
	 * @param req
	 * @return
	 */
	@RequestMapping("/setWebsiteName")
	public String setWebsiteName(Website website, HttpServletRequest req) {
		CompanyAdmin currentCompanyAdmin = getCurrentCompanyAdmin(req);
		website.setCompanyId(currentCompanyAdmin.getCompanyId());
		websiteService.saveWebsite(website);
		return "redirect:/company/website/listWebsite.do";
	}
	
	/**
	 * 开启微网站
	 * @param website
	 * @param req
	 * @return
	 */
	@RequestMapping("/setWebsiteEnable")
	public String setWebsiteEnable(Website website, HttpServletRequest req) {
		CompanyAdmin currentCompanyAdmin = getCurrentCompanyAdmin(req);
		String companyId = currentCompanyAdmin.getCompanyId();
		website.setCompanyId(companyId);
		Boolean enable = website.getEnable();
		if(enable != null && enable) {
			List<Website> websites = websiteService.findWebsitesByCompanyId(companyId);
			for(Website ws:websites) {
				if(ws != null && ws.getEnable() != null && ws.getEnable()) {
					ws.setEnable(false);
					websiteService.saveWebsite(ws);
				}
			}
		}
	
		websiteService.saveWebsite(website);
		return "redirect:/company/website/listWebsite.do";
	}
	
	/**
	 * 删除微网站
	 * @param ids
	 * @param req
	 * @return
	 */
	@RequestMapping("/deleteWebsite")
	public String deleteWebsite(String ids, HttpServletRequest req) {
		CompanyAdmin currentCompanyAdmin = getCurrentCompanyAdmin(req);
		websiteService.deleteMoreWebsite(ids, currentCompanyAdmin.getCompanyId());
		return "redirect:/company/website/listWebsite.do";
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * 主题列表
	 * @param page
	 * @param req
	 * @return
	 */
	@RequestMapping("/listTheme")
	public String listTheme(Page<Theme> page, HttpServletRequest req) {
		CompanyAdmin currentCompanyAdmin = getCurrentCompanyAdmin(req);
		
		page.addFilter("companyId", FilterModel.EQ, currentCompanyAdmin.getCompanyId());
		themeService.findThemes(page);
		List<Theme> result = page.getResult();
		if(result != null && result.size() > 0) {
			return "/company/website/theme_list";
		} else {
			return "redirect:/company/website/selectThemeTemplate.do";
		}
	}
	
	/**
	 * 选择主题模板
	 * @param themeId
	 * @param req
	 * @return
	 */
	@RequestMapping("/selectThemeTemplate")
	public String selectThemeTemplate(String themeId, HttpServletRequest req) {
		Theme theme = themeService.getThemeById(themeId);
		List<ThemeTemplate> templates = themeTemplateService.findAllThemeTemplate();
		req.setAttribute("templates", templates);
		req.setAttribute("theme", theme);
		return "/company/website/theme_select";
	}
	
	/**
	 * 编辑主题
	 * @param tempId
	 * @param themeId
	 * @param req
	 * @return
	 */
	@RequestMapping("/editThemeTemplateColumn")
	public String editThemeTemplateColumn(String tempId, String themeId, HttpServletRequest req) {
		CompanyAdmin currentCompanyAdmin = getCurrentCompanyAdmin(req);
		Theme theme = themeService.getThemeById(themeId);
		ThemeTemplate template = themeTemplateService.getThemeTemplateById(tempId);
		
		List<ColumnNews> columnNewss = columnNewsService.findColumnNewssByCompanyId(currentCompanyAdmin.getCompanyId());
		if(theme == null && template != null) {
			theme = new Theme();
			BeanUtil.copyProperties(theme, template);
			theme.setId(null);
		} else if(theme != null && !StringUtils.equals(tempId, theme.getTemplateId())) {  //如果更换模板则清空现在微网站数据
			String id = theme.getId();
			theme = new Theme();
			BeanUtil.copyProperties(theme, template);
			theme.setId(id);
		}
		req.setAttribute("theme", theme);
		req.setAttribute("template", template);
		req.setAttribute("columnNewss", columnNewss);
		
		List<ColumnLink> columnLinks = columnLinkService.findAllColumnLinks();
		req.setAttribute("columnLinks", columnLinks);
		
		req.setAttribute("companyId", currentCompanyAdmin.getCompanyId());
		return "/company/website/theme_column_edit";
	}
	
	/**
	 * 更新主题
	 * @param website
	 * @param req
	 * @return
	 */
	@RequestMapping("/updateTheme")
	public String updateTheme(Theme theme, HttpServletRequest req) {
		CompanyAdmin currentCompanyAdmin = getCurrentCompanyAdmin(req);
		theme.setCompanyId(currentCompanyAdmin.getCompanyId());
		themeService.saveTheme(theme);
		return "/company/website/theme_name_edit";
	}
	
	/**
	 * 进入编辑主题名称
	 * @param themeId
	 * @param req
	 * @return
	 */
	@RequestMapping("/editThemeName")
	public String editThemeName(String themeId, HttpServletRequest req) {
		Theme theme = themeService.getThemeById(themeId);
		req.setAttribute("theme", theme);
		return "/company/website/theme_name_edit";
	}
	
	/**
	 * 设置主题名称
	 * @param website
	 * @param req
	 * @return
	 */
	@RequestMapping("/setThemeName")
	public String setThemeName(Theme theme, HttpServletRequest req) {
		CompanyAdmin currentCompanyAdmin = getCurrentCompanyAdmin(req);
		theme.setCompanyId(currentCompanyAdmin.getCompanyId());
		themeService.saveTheme(theme);
		return "redirect:/company/website/listTheme.do";
	}
	
	/**
	 * 开启主题
	 * @param theme
	 * @param req
	 * @return
	 */
	@RequestMapping("/setThemeEnable")
	public String setThemeEnable(Theme theme, HttpServletRequest req) {
		CompanyAdmin currentCompanyAdmin = getCurrentCompanyAdmin(req);
		String companyId = currentCompanyAdmin.getCompanyId();
		theme.setCompanyId(companyId);
		Boolean enable = theme.getEnable();
		if(enable != null && enable) {
			List<Theme> themes = themeService.findThemeByCompanyId(companyId);
			for(Theme t:themes) {
				if(t != null && t.getEnable() != null && t.getEnable()) {
					t.setEnable(false);
					themeService.saveTheme(t);
				}
			}
		}
	
		theme = themeService.saveTheme(theme);
		return "redirect:/company/website/listTheme.do";
	}
	
	/**
	 * 删除主题
	 * @param ids
	 * @param req
	 * @return
	 */
	@RequestMapping("/deleteTheme")
	public String deleteTheme(String ids, HttpServletRequest req) {
		CompanyAdmin currentCompanyAdmin = getCurrentCompanyAdmin(req);
		themeService.deleteMoreTheme(ids, currentCompanyAdmin.getCompanyId());
		return "redirect:/company/website/listTheme.do";
	}
}
