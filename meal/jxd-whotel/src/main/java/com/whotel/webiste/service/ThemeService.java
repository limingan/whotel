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
import com.whotel.company.dao.CompanyDao;
import com.whotel.company.entity.Company;
import com.whotel.company.service.CompanyService;
import com.whotel.webiste.dao.ThemeDao;
import com.whotel.webiste.entity.WebsiteColumn;
import com.whotel.webiste.entity.Theme;
import com.whotel.webiste.entity.ThemeTemplate;

/**
 * 主题信息服务类
 * @author 柯鹏程
 *
 */
@Service
public class ThemeService {
	@Autowired
	private ThemeDao themeDao;
	
	@Autowired
	private CompanyDao companyDao;
	
	@Autowired
	private ThemeTemplateService themeTemplateService;
	
	@Autowired
	private CompanyService companyService;
	
	/**
	 * 保存主题
	 * @param theme
	 */
	public Theme saveTheme(Theme theme) {
		if(theme != null) {
			String id = theme.getId();
			List<WebsiteColumn> columns = theme.getColumns();
			if(StringUtils.isNotBlank(id)) {
				Theme updateTheme = getThemeById(id);
				BeanUtil.copyProperties(updateTheme, theme, true);
				theme = updateTheme;
				theme.setUpdateTime(new Date());
			} else {
				theme.setCreateTime(new Date());
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
				theme.setColumns(updateColumns);
			}
			themeDao.save(theme);
			
			//保存公司对象中的主题设置
			if(Boolean.TRUE.equals(theme.getEnable())){
				updateCompany(theme);
			}
		}
		return theme;
	}
	
	private void updateCompany(Theme theme){
		Company company = companyService.getCompanyById(theme.getCompanyId());
		ThemeTemplate themeTemplate = themeTemplateService.getThemeTemplateById(theme.getTemplateId());
		if(!StringUtils.equals(themeTemplate.getTemplate(), "1")){
			company.setTheme(themeTemplate.getTemplate());
		}else{
			company.setTheme("");
		}
		companyDao.save(company);
	}
	
	/**
	 * 分页查找主题
	 * @param page 分页工具类
	 */
	public void findThemes(Page<Theme> page) {
		themeDao.find(page);
	}
	
	public List<Theme> findAllThemes() {
		return themeDao.findAll();
	}
	
	public List<Theme> findThemeByCompanyId(String companyId) {
		return themeDao.findByProperty("companyId", companyId, Order.desc("createTime"));
	}
	
	/**
	 * 获取启用主题
	 * @param companyId
	 * @return
	 */
	public Theme getEnableTheme(String companyId) {
		Map<String, Serializable> properties = new HashMap<String, Serializable>();
		properties.put("companyId", companyId);
		properties.put("enable", true);
		return themeDao.getByProperties(properties);
	}
	
	public Theme getThemeById(String id) {
		if(StringUtils.isBlank(id)) {
			return null;
		}
		return themeDao.get(id);
	}
	
	public void deleteTheme(String id) {
		themeDao.delete(id);
	}
	
	public void deleteTheme(Theme theme) {
		themeDao.delete(theme);
	}
	
	/**
	 * 批量删除主题
	 * @param ids
	 */
	public void deleteMoreTheme(String ids, String companyId) {
		if(StringUtils.isNotBlank(ids)) {
			String[] split = ids.split(",");
			for(String id:split) {
				Theme theme = getThemeById(id);
				theme.setCompanyId(companyId);
				deleteTheme(theme);
			}
		}
	}
}
