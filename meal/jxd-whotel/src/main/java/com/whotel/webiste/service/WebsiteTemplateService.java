package com.whotel.webiste.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whotel.common.dao.mongo.Page;
import com.whotel.common.util.BeanUtil;
import com.whotel.webiste.dao.WebsiteTemplateDao;
import com.whotel.webiste.entity.WebsiteColumn;
import com.whotel.webiste.entity.WebsiteTemplate;

/**
 * 微网站模板管理服务类
 * @author 冯勇
 *
 */
@Service
public class WebsiteTemplateService {
	
	@Autowired
	private WebsiteTemplateDao websiteTemplateDao;
	
	/**
	 * 保存微网站模板
	 * @param websiteTemplate
	 */
	public void saveWebsiteTemplate(WebsiteTemplate websiteTemplate) {
		if(websiteTemplate != null) {
			String id = websiteTemplate.getId();
			List<WebsiteColumn> columns = websiteTemplate.getColumns();
			if(StringUtils.isNotBlank(id)) {
				WebsiteTemplate updateWebsiteTemplate = getWebsiteTemplateById(id);
				BeanUtil.copyProperties(updateWebsiteTemplate, websiteTemplate, true);
				websiteTemplate = updateWebsiteTemplate;
				websiteTemplate.setUpdateTime(new Date());
			} else {
				websiteTemplate.setCreateTime(new Date());
			}
			List<WebsiteColumn> updateColumns = null;
			if(columns != null) {
				updateColumns = new ArrayList<WebsiteColumn>();
				for(WebsiteColumn column:columns) {
					if(column != null && StringUtils.isNotBlank(column.getName())) {
						updateColumns.add(column);
					}
				}
			}
			websiteTemplate.setColumns(updateColumns);
			websiteTemplateDao.save(websiteTemplate);
		}
	}
	
	/**
	 * 分页查找微网站模板
	 * @param page 分页工具类
	 */
	public void findWebsiteTemplates(Page<WebsiteTemplate> page) {
		websiteTemplateDao.find(page);
	}
	
	public List<WebsiteTemplate> findAllWebsiteTemplates() {
		return websiteTemplateDao.findAll();
	}
	
	public WebsiteTemplate getWebsiteTemplateById(String id) {
		if(StringUtils.isBlank(id)) {
			return null;
		}
		return websiteTemplateDao.get(id);
	}
	
	public void deleteWebsiteTemplate(String id) {
		websiteTemplateDao.delete(id);
	}
	
	/**
	 * 批量删除微网站模板
	 * @param ids
	 */
	public void deleteMoreWebsiteTemplate(String ids) {
		if(StringUtils.isNotBlank(ids)) {
			String[] split = ids.split(",");
			for(String id:split) {
				deleteWebsiteTemplate(id);
			}
		}
	}
}
