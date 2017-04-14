package com.whotel.webiste.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whotel.common.util.BeanUtil;
import com.whotel.webiste.dao.ThemeTemplateDao;
import com.whotel.webiste.entity.ThemeTemplate;
import com.whotel.webiste.entity.WebsiteColumn;

/**
 * 主题模板管理服务类
 * @author 柯鹏程
 *
 */
@Service
public class ThemeTemplateService {
	
	@Autowired
	private ThemeTemplateDao themeTemplateDao;
	
	/**
	 * 获取网站主题
	 * @param id
	 * @return
	 */
	public ThemeTemplate getThemeTemplateById(String id) {
		if(StringUtils.isBlank(id)) {
			return null;
		}
		return themeTemplateDao.get(id);
	}
	
	/**
	 * 删除主题
	 * @param id
	 */
	public void deleteThemeTemplate(String id) {
		themeTemplateDao.delete(id);
	}
	
	/**
	 * 删除主题
	 * @param themeStyle
	 */
	public void deleteThemeTemplate(ThemeTemplate themeTemplate) {
		themeTemplateDao.delete(themeTemplate);
	}
	
	/**
	 * 获取全部的主题
	 * @return
	 */
	public List<ThemeTemplate> findAllThemeTemplate() {
		return themeTemplateDao.findAll();
	}
	
	/**
	 * 保存主题
	 * @param website
	 */
	public void saveThemeTemplate(ThemeTemplate themeTemplate) {
		if(themeTemplate != null) {
			String id = themeTemplate.getId();
			List<WebsiteColumn> columns = themeTemplate.getColumns();
			if(StringUtils.isNotBlank(id)) {
				ThemeTemplate updateThemeTemplate = getThemeTemplateById(id);
				BeanUtil.copyProperties(updateThemeTemplate, themeTemplate, true);
				themeTemplate = updateThemeTemplate;
				themeTemplate.setUpdateTime(new Date());
			} else {
				themeTemplate.setCreateTime(new Date());
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
				themeTemplate.setColumns(updateColumns);
			}
			themeTemplateDao.save(themeTemplate);
		}
	}
}
