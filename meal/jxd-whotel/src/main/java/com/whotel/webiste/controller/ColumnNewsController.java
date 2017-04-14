package com.whotel.webiste.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.whotel.common.base.Constants;
import com.whotel.common.dao.mongo.Order;
import com.whotel.common.dao.mongo.Page;
import com.whotel.common.enums.FilterModel;
import com.whotel.company.entity.Company;
import com.whotel.company.service.CompanyService;
import com.whotel.front.controller.FanBaseController;
import com.whotel.webiste.entity.ColumnNews;
import com.whotel.webiste.entity.ColumnTemplate;
import com.whotel.webiste.entity.NewsArticle;
import com.whotel.webiste.service.ColumnNewsService;

/**
 * 图文栏目展示控制类
 * @author 冯勇
 *
 */
@Controller
@RequestMapping("/front")
public class ColumnNewsController extends FanBaseController {

	@Autowired
	private ColumnNewsService columnNewsService;
	
	@Autowired
	private CompanyService companyService;
	
	/**
	 * 图文栏目文章列表页
	 * @param newsId
	 * @param req
	 * @return
	 */
	@RequestMapping("/listNewsArticle")
	public String listNewsArticle(String newsId, HttpServletRequest req) {
		ColumnNews columnNews = columnNewsService.getColumnNewsById(newsId);
		req.setAttribute("columnNews", columnNews);
		if(columnNews != null) {
			ColumnTemplate columnTemplate = columnNews.getTemplate();
			
			if(columnTemplate != null) {
				Company company = companyService.getCompanyById(columnNews.getCompanyId());
				req.getSession().setAttribute(Constants.Session.THEME, company.getTheme());
				return "/front/website/column_template/"+columnTemplate.getTemplate();  //跳转到指定模板页
			}
		}
		return "/front/website/column_news";
	}
	
	/**
	 * 加载栏目文章列表
	 * @param page
	 * @param newsId
	 * @return
	 */
	@RequestMapping("/loadNewsArticle")
	@ResponseBody
	public Page<NewsArticle> loadNewsArticle(Page<NewsArticle> page, String newsId) {
		page.addFilter("newsId", FilterModel.EQ, newsId);
		page.addOrder(Order.desc("orderIndex"));
		page.addOrder(Order.desc("createTime"));
		columnNewsService.findNewsArticle(page);
		return page;
	}
	
	/**
	 * 显示栏目文章详情
	 * @param id
	 * @param req
	 * @return
	 */
	@RequestMapping("/showNewsArticle")
	public String showNewsArticle(String id, HttpServletRequest req) {
		NewsArticle newsArticle = columnNewsService.getNewsArticleById(id);
		req.setAttribute("newsArticle", newsArticle);
		return "/front/website/news_article";
	}
}
