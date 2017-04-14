package com.whotel.webiste.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whotel.common.dao.mongo.Order;
import com.whotel.common.dao.mongo.Page;
import com.whotel.common.util.BeanUtil;
import com.whotel.webiste.dao.WebsiteDao;
import com.whotel.webiste.entity.Website;
import com.whotel.webiste.entity.WebsiteColumn;

/**
 * 微网站信息服务类
 * @author 冯勇
 *
 */
@Service
public class WebsiteService {
	
	@Autowired
	private WebsiteDao websiteDao;
	
	/**
	 * 保存微网站
	 * @param website
	 */
	public void saveWebsite(Website website) {
		if(website != null) {
			String id = website.getId();
			List<WebsiteColumn> columns = website.getColumns();
			if(StringUtils.isNotBlank(id)) {
				Website updateWebsite = getWebsiteById(id);
				BeanUtil.copyProperties(updateWebsite, website, true);
				website = updateWebsite;
				website.setUpdateTime(new Date());
			} else {
				website.setCreateTime(new Date());
			}
			List<WebsiteColumn> updateColumns = null;
			if(columns != null) {
				updateColumns = new ArrayList<WebsiteColumn>();
				for(WebsiteColumn column:columns) {
					if(column != null && StringUtils.isNotBlank(column.getName())) {
						if(StringUtils.isBlank(column.getTarget())) {
							column.setTarget("javascript:");
						}
						updateColumns.add(column);
					}
				}
				website.setColumns(updateColumns);
			}
			websiteDao.save(website);
		}
	}
	
	/**
	 * 分页查找微网站
	 * @param page 分页工具类
	 */
	public void findWebsites(Page<Website> page) {
		websiteDao.find(page);
	}
	
	public List<Website> findAllWebsites() {
		return websiteDao.findAll();
	}
	
	public List<Website> findWebsitesByCompanyId(String companyId) {
		return websiteDao.findByProperty("companyId", companyId, Order.desc("createTime"));
	}
	
	/**
	 * 获取启用微网站数据
	 * @param companyId
	 * @return
	 */
	public Website getEnableWebsite(String companyId) {
		Map<String, Serializable> properties = new HashMap<String, Serializable>();
		properties.put("companyId", companyId);
		properties.put("enable", true);
		return websiteDao.getByProperties(properties);
	}
	
	public Website getWebsiteById(String id) {
		if(StringUtils.isBlank(id)) {
			return null;
		}
		return websiteDao.get(id);
	}
	
	public void deleteWebsite(String id) {
		websiteDao.delete(id);
	}
	
	public void deleteWebsite(Website website) {
		websiteDao.delete(website);
	}
	
	/**
	 * 批量删除微网站
	 * @param ids
	 */
	public void deleteMoreWebsite(String ids, String companyId) {
		if(StringUtils.isNotBlank(ids)) {
			String[] split = ids.split(",");
			for(String id:split) {
				Website website = getWebsiteById(id);
				website.setCompanyId(companyId);
				deleteWebsite(website);
			}
		}
	}
}
