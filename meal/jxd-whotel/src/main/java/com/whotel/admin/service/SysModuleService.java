package com.whotel.admin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whotel.admin.dao.SysModuleDao;
import com.whotel.admin.entity.SysModule;
import com.whotel.common.dao.mongo.Page;

/**
 * 后台模块管理服务
 * @author 冯勇
 *
 */
@Service
public class SysModuleService {
	
	@Autowired
	private SysModuleDao moduleDao;
	/**
	 * 通过ID获取后台模块
	 * @param id：管理ID
	 * @return 后台模块
	 */
	public SysModule getSysModuleById(String id) {
		return moduleDao.get(id);
	}
	

	/**
	 * 创建后台模块对象
	 * @param sysModule 后台模块
	 */
	public void saveSysModule(SysModule sysModule) {
		moduleDao.save(sysModule);
	}

	/**
	 * 通过ID删除后台模块
	 * @param id
	 */
	public void deleteSysModule(String id) {
		moduleDao.delete(id);
	}

	/**
	 * 分页查找后台模块
	 * @param page 分页工具类
	 */
	public void findSysModules(Page<SysModule> page) {
		moduleDao.find(page);
	}

	/**
	 * 查找所有后台模块
	 * @return
	 */
	public List<SysModule> findAllSysModules() {
		return moduleDao.findAll();
	}
	
	/**
	 * 删除后台模块
	 * @param sysModule
	 */
	public void deleteSysModule(SysModule sysModule) {
		moduleDao.delete(sysModule);
	}
}
