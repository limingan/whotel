package com.whotel.webiste.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whotel.common.dao.mongo.Page;
import com.whotel.webiste.dao.ColumnLinkDao;
import com.whotel.webiste.entity.ColumnLink;

/**
 * 栏目链接服务管理类
 * @author 冯勇
 *
 */
@Service
public class ColumnLinkService {
	
	@Autowired
	private ColumnLinkDao columnLinkDao;
	
	/**
	 * 保存栏目链接
	 * @param columnLink
	 */
	public void saveColumnLink(ColumnLink columnLink) {
		if(columnLink != null) {
			columnLinkDao.save(columnLink);
		}
	}
	
	/**
	 * 分页查找栏目链接
	 * @param page 分页工具类
	 */
	public void findColumnLinks(Page<ColumnLink> page) {
		columnLinkDao.find(page);
	}
	
	public List<ColumnLink> findAllColumnLinks() {
		return columnLinkDao.findAll();
	}
	
	public ColumnLink getColumnLinkById(String id) {
		return columnLinkDao.get(id);
	}
	
	public void deleteColumnLink(String id) {
		columnLinkDao.delete(id);
	}
	
	/**
	 * 批量删除栏目链接
	 * @param ids
	 */
	public void deleteMoreColumnLink(String ids) {
		if(StringUtils.isNotBlank(ids)) {
			String[] split = ids.split(",");
			for(String id:split) {
				deleteColumnLink(id);
			}
		}
	}
}
