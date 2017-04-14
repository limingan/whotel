package com.whotel.company.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whotel.common.dao.mongo.Order;
import com.whotel.common.dao.mongo.Page;
import com.whotel.company.dao.CompanyModuleDao;
import com.whotel.company.entity.CompanyModule;

/**
 * 后台模块管理服务
 * @author 冯勇
 *
 */
@Service
public class CompanyModuleService {
	
	@Autowired
	private CompanyModuleDao moduleDao;
	/**
	 * 通过ID获取后台模块
	 * @param id：管理ID
	 * @return 后台模块
	 */
	public CompanyModule getCompanyModuleById(String id) {
		return moduleDao.get(id);
	}
	

	/**
	 * 创建后台模块对象
	 * @param sysModule 后台模块
	 */
	public void saveCompanyModule(CompanyModule sysModule) {
		moduleDao.save(sysModule);
	}

	/**
	 * 通过ID删除后台模块
	 * @param id
	 */
	public void deleteCompanyModule(String id) {
		moduleDao.delete(id);
	}

	/**
	 * 分页查找后台模块
	 * @param page 分页工具类
	 */
	public void findCompanyModules(Page<CompanyModule> page) {
		Set<Order> set = new HashSet<Order>();
		set.add(Order.asc("displayOrder"));
		page.setOrders(set);
		moduleDao.find(page);
	}

	/**
	 * 查找所有后台模块
	 * @return
	 */
	public List<CompanyModule> findAllCompanyModules() {
		return moduleDao.findAll();
	}
	
	/**
	 * 删除后台模块
	 * @param sysModule
	 */
	public void deleteCompanyModule(CompanyModule sysModule) {
		moduleDao.delete(sysModule);
	}
}
