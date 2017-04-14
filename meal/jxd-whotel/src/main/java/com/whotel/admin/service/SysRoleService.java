package com.whotel.admin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whotel.admin.dao.SysRoleDao;
import com.whotel.admin.entity.SysRole;
import com.whotel.common.dao.mongo.Page;

/**
 * 后台角色管理服务
 * @author 冯勇
 *
 */
@Service
public class SysRoleService {
	
	@Autowired
	private SysRoleDao roleDao;
	
	/**
	 * 通过ID获取管理员角色对象
	 * @param id：管理ID
	 * @return 管理员角色对象
	 */
	public SysRole getSysRoleById(String id) {
		return roleDao.get(id);
	}

	/**
	 * 创建管理员角色对象
	 * @param sysRole 管理员角色对象
	 */
	public void saveSysRole(SysRole sysRole) {
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
	public void findSysRoles(Page<SysRole> page) {
		roleDao.find(page);
	}

	/**
	 * 查找所有管理员角色对象
	 * @return
	 */
	public List<SysRole> findAllSysRoles() {
		return roleDao.findAll();
	}

	/**
	 * 删除管理员角色对象
	 * @param sysRole
	 */
	public void deleteSysRole(SysRole sysRole) {
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
