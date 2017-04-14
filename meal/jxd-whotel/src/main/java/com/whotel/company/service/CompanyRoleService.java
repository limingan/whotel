package com.whotel.company.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whotel.common.dao.mongo.Order;
import com.whotel.common.dao.mongo.Page;
import com.whotel.company.dao.CompanyRoleDao;
import com.whotel.company.entity.CompanyRole;

/**
 * 后台角色管理服务
 * @author 冯勇
 *
 */
@Service
public class CompanyRoleService {
	
	@Autowired
	private CompanyRoleDao roleDao;
	
	/**
	 * 通过ID获取管理员角色对象
	 * @param id：管理ID
	 * @return 管理员角色对象
	 */
	public CompanyRole getCompanyRoleById(String id) {
		return roleDao.get(id);
	}

	/**
	 * 创建管理员角色对象
	 * @param sysRole 管理员角色对象
	 */
	public void saveSysRole(CompanyRole sysRole) {
		roleDao.save(sysRole);
	}

	/**
	 * 通过ID删除管理员角色对象
	 * @param id
	 */
	public void deleteSysRole(String id) {
		roleDao.delete(id);
	}

	/**
	 * 分页查找管理员角色对象
	 * @param page 分页工具类
	 */
	public void findCompanyRoles(Page<CompanyRole> page) {
		roleDao.find(page);
	}

	/**
	 * 查找所有管理员角色对象
	 * @return
	 */
	public List<CompanyRole> findAllSysRoles(String companyId) {
		return roleDao.findByProperty("companyId", companyId);
	}

	/**
	 * 删除管理员角色对象
	 * @param sysRole
	 */
	public void deleteSysRole(CompanyRole sysRole) {
		roleDao.delete(sysRole);
	}

	public void deleteMoreRole(String[] ids) {
		if(ids != null) {
			for(String id:ids) {
				roleDao.delete(id);
			}
		}
	}
}
