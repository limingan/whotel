package com.whotel.company.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.whotel.company.entity.MatchKeyword;
import com.whotel.company.entity.MatchKeywordRule;
import com.whotel.company.entity.MenuItem;
import com.whotel.company.entity.NewsItem;
import com.whotel.company.entity.NewsResource;
import com.whotel.company.entity.ResponseMsg;
import com.whotel.company.entity.WelcomeMsg;
import com.whotel.company.enums.KeywordMatchType;

/**
 * 关键词回复测试
 * @author 冯勇
 *
 */
@ContextConfiguration(locations = { "/applicationContext-*test.xml" })
public class PublicNoServiceTest extends AbstractJUnit4SpringContextTests {
	
	@Autowired
	private NewsResourceService newsResourceService;
	
	@Autowired
	private MatchKeywordRuleService ruleService;
	
	@Autowired
	private PublicNoService publicNoService;
	
	@Autowired
	private MenuItemService menuItemService;
	
	private String companyId = "55c5cdfd8797621e28a26000";
	
	@Test
	public void testUpdateNewsResource() {
		NewsResource newsResource = newsResourceService.getNewsResourceById("55d0814305f80330e49742a3");
		newsResource.setCreateTime(new Date());
		newsResource.setUpdateTime(new Date());
		newsResourceService.saveNewsResource(newsResource);
		
		newsResource = newsResourceService.getNewsResourceById("55d0814305f80330e49742a4");
		newsResource.setCreateTime(new Date());
		newsResource.setUpdateTime(new Date());
		newsResourceService.saveNewsResource(newsResource);
	}
	
	@Test
	public void testSaveNewsResource() {
		NewsResource newsResource = new NewsResource();
		newsResource.setCompanyId(companyId);
		newsResource.setName("图文消息");
		newsResource.setCreateTime(new Date());
		newsResource.setUpdateTime(new Date());
		List<NewsItem> mewsItems = new ArrayList<NewsItem>();
		NewsItem newsItem = new NewsItem();
		newsItem.setTitle("图文一");
		newsItem.setCover("http://7xjki3.com2.z0.glb.qiniucdn.com/dosomi-picture:2w6Kng7Nhb5aqCpYsVPEad");
		newsItem.setBrief("欢迎查看图文一");
		newsItem.setContent("好文章");
		newsItem.setUrl("http://www.baidu.com");
		newsItem.setAuthor("作者");
		mewsItems.add(newsItem);
		
		newsResource.setNews(mewsItems);
		
		NewsResource newsResource2 = new NewsResource();
		newsResource2.setCompanyId(companyId);
		newsResource2.setName("图文消息2");
		newsResource2.setCreateTime(new Date());
		newsResource2.setUpdateTime(new Date());
		List<NewsItem> newsItems2 = new ArrayList<NewsItem>();
		NewsItem newsItem2 = new NewsItem();
		newsItem2.setTitle("图文一");
		newsItem2.setCover("http://7xjki3.com2.z0.glb.qiniucdn.com/dosomi-picture:2w6Kng7Nhb5aqCpYsVPEad");
		newsItem2.setBrief("欢迎查看图文一");
		newsItem2.setContent("好文章");
		newsItem2.setUrl("http://www.baidu.com");
		newsItem2.setAuthor("作者");
		
		NewsItem newsItem3 = new NewsItem();
		newsItem3.setTitle("没有链接图文3");
		newsItem3.setCover("http://7xj009.com1.z0.glb.clouddn.com/dosomi:11Ykc5isN92Gpp18ixEJvg");
		newsItem3.setBrief("欢迎查看图文3");
		newsItem3.setContent("好文章3");
		newsItem3.setAuthor("作者3");
		
		newsItems2.add(newsItem2);
		newsItems2.add(newsItem3);
		
		newsResource2.setNews(newsItems2);
		
		newsResourceService.saveNewsResource(newsResource);
		
		newsResourceService.saveNewsResource(newsResource2);
	}
	
	
	@Test
	public void testSaveMatchKeywordRule() {
		MatchKeywordRule rule1 = new MatchKeywordRule();
		
		rule1.setDef(true);
		rule1.setCompanyId(companyId);
		
		ResponseMsg responseMsg = new ResponseMsg();
		responseMsg.setText("欢迎使用微信服务！");
		
		rule1.setResponseMsg(responseMsg);
		
		MatchKeywordRule rule2 = new MatchKeywordRule();
		rule2.setCompanyId(companyId);
		
		List<MatchKeyword> keywords = new ArrayList<MatchKeyword>();
		MatchKeyword keyword1 = new MatchKeyword();
		keyword1.setType(KeywordMatchType.PART);
		keyword1.setKeyword("微信");
		
		MatchKeyword keyword2 = new MatchKeyword();
		keyword2.setType(KeywordMatchType.ALL);
		keyword2.setKeyword("weixin");
		keywords.add(keyword1);
		keywords.add(keyword2);
		
		rule2.setKeywords(keywords);
		
		ResponseMsg responseMsg2 = new ResponseMsg();
		responseMsg2.setText("您想了解微信哪方面的知识！");
		rule2.setResponseMsg(responseMsg2);
		
		ruleService.saveMatchKeywordRule(rule1);
		ruleService.saveMatchKeywordRule(rule2);
		
		
		
		
		MatchKeywordRule rule3 = new MatchKeywordRule();
		rule3.setCompanyId(companyId);
		
		List<MatchKeyword> keywords2 = new ArrayList<MatchKeyword>();
		keyword1 = new MatchKeyword();
		keyword1.setType(KeywordMatchType.ALL);
		keyword1.setKeyword("图文一");
		
		keywords2.add(keyword1);
		
		rule3.setKeywords(keywords2);
		
		ResponseMsg responseMsg3 = new ResponseMsg();
		responseMsg3.setNewsId("55d0495c4d7d8d19c0322908");
		rule3.setResponseMsg(responseMsg3);
		
		MatchKeywordRule rule4 = new MatchKeywordRule();
		rule4.setCompanyId(companyId);
		
		List<MatchKeyword> keywords3 = new ArrayList<MatchKeyword>();
		keyword1 = new MatchKeyword();
		keyword1.setType(KeywordMatchType.ALL);
		keyword1.setKeyword("图文二");
		
		keywords3.add(keyword1);
		
		rule4.setKeywords(keywords3);
		
		ResponseMsg responseMsg4 = new ResponseMsg();
		responseMsg4.setNewsId("55d0495c4d7d8d19c0322909");
		rule4.setResponseMsg(responseMsg4);
		
		ruleService.saveMatchKeywordRule(rule3);
		ruleService.saveMatchKeywordRule(rule4);
		
	}
	
	@Test
	public void testSaveWelcomeMsg() {
		WelcomeMsg welcomeMsg = new WelcomeMsg();
		
		welcomeMsg.setCompanyId(companyId);
		
		ResponseMsg responseMsg = new ResponseMsg();
		//responseMsg.setText("欢迎关注我们，我们会竭诚为您服务");
		responseMsg.setNewsId("55d0495c4d7d8d19c0322909");
		
		welcomeMsg.setResponseMsg(responseMsg);
		
		publicNoService.saveWelcomeMsg(welcomeMsg);
	}
	
	@Test
	public void testSaveMenuItem() {
		MenuItem menuItem = new MenuItem();
		
		menuItem.setCompanyId(companyId);
		menuItem.setName("微官网");
		menuItem.setOrder(1);
		
		ResponseMsg rm = new ResponseMsg();
		rm.setUrl("http://www.baidu.com");
		
		menuItem.setResponseMsg(rm);
		menuItemService.saveMenuItem(menuItem);
		
		menuItem = new MenuItem();
		menuItem.setCompanyId(companyId);
		menuItem.setName("图文展示");
		menuItem.setOrder(2);
		
	    rm = new ResponseMsg();
		rm.setNewsId("55d0495c4d7d8d19c0322908");
		menuItem.setResponseMsg(rm);
		menuItemService.saveMenuItem(menuItem);
		
		menuItem = new MenuItem();
		menuItem.setCompanyId(companyId);
		menuItem.setName("层级菜单");
		menuItem.setOrder(3);
		menuItemService.saveMenuItem(menuItem);
		
		MenuItem subMenuItem = new MenuItem();
		subMenuItem.setParentId(menuItem.getId());
		subMenuItem.setCompanyId(companyId);
		subMenuItem.setName("子菜单一");
		subMenuItem.setOrder(3);
		
	    rm = new ResponseMsg();
		rm.setNewsId("55d0495c4d7d8d19c0322909");
		subMenuItem.setResponseMsg(rm);
		menuItemService.saveMenuItem(subMenuItem);
		
		subMenuItem = new MenuItem();
		subMenuItem.setParentId(menuItem.getId());
		subMenuItem.setCompanyId(companyId);
		subMenuItem.setName("子菜单二");
		subMenuItem.setOrder(3);
		
	    rm = new ResponseMsg();
		rm.setText("您点击了子菜单二");
		subMenuItem.setResponseMsg(rm);
		menuItemService.saveMenuItem(subMenuItem);
	}
	
	@Test
	public void publishMenuItem() {
		boolean rs = menuItemService.publishMenuItem(companyId);
		System.out.println(rs);
	}
}
