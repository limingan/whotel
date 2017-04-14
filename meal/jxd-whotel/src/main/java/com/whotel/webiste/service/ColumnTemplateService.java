package com.whotel.webiste.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whotel.common.dao.mongo.Page;
import com.whotel.common.util.BeanUtil;
import com.whotel.webiste.dao.ColumnTemplateDao;
import com.whotel.webiste.entity.ColumnTemplate;

/**
 * 栏目模板管理服务类
 * @author 冯勇
 *
 */
@Service
public class ColumnTemplateService {
	
	@Autowired
	private ColumnTemplateDao columnTemplateDao;
	
	/**
	 * 保存栏目模板
	 * @param ColumnTemplate
	 */
	public void saveColumnTemplate(ColumnTemplate columnTemplate) {
		if(columnTemplate != null) {
			String id = columnTemplate.getId();
			if(StringUtils.isNotBlank(id)) {
				ColumnTemplate updatecolumnTemplate = getColumnTemplateById(id);
				BeanUtil.copyProperties(updatecolumnTemplate, columnTemplate, true);
				columnTemplate = updatecolumnTemplate;
				columnTemplate.setUpdateTime(new Date());
			} else {
				columnTemplate.setCreateTime(new Date());
			}
			columnTemplateDao.save(columnTemplate);
		}
	}
	
	/**
	 * 分页查找栏目模板
	 * @param page 分页工具类
	 */
	public void findColumnTemplates(Page<ColumnTemplate> page) {
		columnTemplateDao.find(page);
	}
	
	public List<ColumnTemplate> findAllColumnTemplates() {
		return columnTemplateDao.findAll();
	}
	
	public ColumnTemplate getColumnTemplateById(String id) {
		if(StringUtils.isBlank(id)) {
			return null;
		}
		return columnTemplateDao.get(id);
	}
	
	public void deleteColumnTemplate(String id) {
		columnTemplateDao.delete(id);
	}
	
	/**
	 * 批量删除栏目模板
	 * @param ids
	 */
	public void deleteMoreColumnTemplate(String ids) {
		if(StringUtils.isNotBlank(ids)) {
			String[] split = ids.split(",");
			for(String id:split) {
				deleteColumnTemplate(id);
			}
		}
	}
}
