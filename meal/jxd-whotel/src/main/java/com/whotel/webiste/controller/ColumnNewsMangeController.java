package com.whotel.webiste.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.whotel.common.dao.mongo.Order;
import com.whotel.common.dao.mongo.Page;
import com.whotel.common.enums.FilterModel;
import com.whotel.company.controller.BaseCompanyController;
import com.whotel.company.entity.CompanyAdmin;
import com.whotel.webiste.entity.ColumnNews;
import com.whotel.webiste.entity.ColumnTemplate;
import com.whotel.webiste.entity.NewsArticle;
import com.whotel.webiste.service.ColumnNewsService;
import com.whotel.webiste.service.ColumnTemplateService;

/**
 * 栏目数据管理控制类
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/company/website")
public class ColumnNewsMangeController extends BaseCompanyController {

	@Autowired
	private ColumnNewsService columnNewsService;
	
	@Autowired
	private ColumnTemplateService columnTemplateService;
	
	/**
	 * 商户图文列表
	 * @param page
	 * @param req
	 * @return
	 */
	@RequestMapping("/listColumnNews")
	public String listColumnNews(Page<ColumnNews> page, HttpServletRequest req) {
		CompanyAdmin currentCompanyAdmin = getCurrentCompanyAdmin(req);
		page.addFilter("companyId", FilterModel.EQ, currentCompanyAdmin.getCompanyId());
		columnNewsService.findColumnNewss(page);
		return "/company/website/column_news_list";
	}
	
	/**
	 * 进入编辑栏目图文
	 * @param id
	 * @param req
	 * @return
	 */
	@RequestMapping("/toEditColumnNews")
	public String toEditColumnNews(String id, HttpServletRequest req) {
		ColumnNews columnNews = columnNewsService.getColumnNewsById(id);
		req.setAttribute("columnNews", columnNews);
		return "/company/website/column_news_edit";
	}
	
	/**
	 * 更新栏目图文
	 * @param columnNews
	 * @param req
	 * @return
	 */
	@RequestMapping("/updateColumnNews")
	public String updateColumnNews(ColumnNews columnNews, HttpServletRequest req) {
		CompanyAdmin currentCompanyAdmin = getCurrentCompanyAdmin(req);
		columnNews.setCompanyId(currentCompanyAdmin.getCompanyId());
		columnNewsService.saveColumnNews(columnNews);
		return "redirect:/company/website/listColumnNews.do";
	}
	
	/**
	 * 删除栏目图文
	 * @param ids
	 * @param req
	 * @return
	 */
	@RequestMapping("/deleteColumnNews")
	public String deleteColumnNews(String ids, HttpServletRequest req) {
		CompanyAdmin currentCompanyAdmin = getCurrentCompanyAdmin(req);
		columnNewsService.deleteMoreColumnNews(ids, currentCompanyAdmin.getCompanyId());
		return "redirect:/company/website/listColumnNews.do";
	}
	
	/**
	 * 加载栏目图文模板
	 * @param page
	 * @param req
	 * @return
	 */
	@RequestMapping("/ajaxLoadColumnTemplate")
	@ResponseBody
	public Page<ColumnTemplate> ajaxLoadColumnTemplate(Page<ColumnTemplate> page, HttpServletRequest req) {
		columnTemplateService.findColumnTemplates(page);;
		return page;
	}
	
	/**
	 * 文章管理
	 * @param page
	 * @param req
	 * @return
	 */
	@RequestMapping("/listNewsArticle")
	public String listNewsArticle(Page<NewsArticle> page, String newsId, HttpServletRequest req) {
		page.addFilter("newsId", FilterModel.EQ, newsId);
		page.addOrder(Order.desc("orderIndex"));
		page.addOrder(Order.desc("createTime"));
		columnNewsService.findNewsArticle(page);
		return "/company/website/news_article_list";
	}
	
	/**
	 * 编辑栏目图文文章
	 * @param id
	 * @param newsId
	 * @param req
	 * @return
	 */
	@RequestMapping("/toEditNewsArticle")
	public String toEditNewsArticle(String id, String newsId, HttpServletRequest req) {
		NewsArticle newsArticle = columnNewsService.getNewsArticleById(id);
		req.setAttribute("newsArticle", newsArticle);
		ColumnNews columnNews = columnNewsService.getColumnNewsById(newsId);
		req.setAttribute("columnNews", columnNews);
		return "/company/website/news_article_edit";
	}
	
	/**
	 * 更新栏目图文文章
	 * @param newsArticle
	 * @param req
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/updateNewsArticle")
	public String updateNewsArticle(NewsArticle newsArticle, HttpServletRequest req) throws UnsupportedEncodingException {
		CompanyAdmin currentCompanyAdmin = getCurrentCompanyAdmin(req);
		String newsId = newsArticle.getNewsId();
		
		if(StringUtils.isNotBlank(newsId)) {
			ColumnNews columnNews = columnNewsService.getColumnNewsById(newsId);
			if(columnNews != null && StringUtils.equals(columnNews.getCompanyId(), currentCompanyAdmin.getCompanyId())) {
				columnNewsService.saveNewsArticle(newsArticle);
				return "redirect:/company/website/listNewsArticle.do?newsId="+newsId;
			}
		} 
		return "redirect:/company/website/listColumnNews.do?message=" + URLEncoder.encode("添加图文文章失败", "UTF8");
	}
	
	/**
	 * 删除图文栏目文章
	 * @param ids
	 * @param req
	 * @return
	 */
	@RequestMapping("/deleteNewsArticle")
	public String deleteNewsArticle(String ids, HttpServletRequest req) {
		CompanyAdmin currentCompanyAdmin = getCurrentCompanyAdmin(req);
		String newsId = columnNewsService.deleteMoreNewsArticle(ids, currentCompanyAdmin.getCompanyId());
		return "redirect:/company/website/listNewsArticle.do?newsId="+newsId;
	}
}
