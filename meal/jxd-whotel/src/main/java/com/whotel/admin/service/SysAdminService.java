package com.whotel.admin.service;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whotel.admin.dao.SysAdminDao;
import com.whotel.admin.entity.SysAdmin;
import com.whotel.admin.enums.AdminActionType;
import com.whotel.common.dao.mongo.Page;
import com.whotel.common.util.EncryptUtil;

/**
 * 后台管理管理服务
 * @author 冯勇
 *
 */
@Service
public class SysAdminService {
	
	@Autowired
	private SysAdminDao adminDao;
	
	@Autowired
	private AdminActionLogService actionLogService;
	
	/**
	 * 通过ID获取管理员对象
	 * @param id：管理ID
	 * @return 管理员对象
	 */
	public SysAdmin getSysAdminById(String id) {
		return adminDao.get(id);
	}
	
	public SysAdmin getSysAdminByUserName(String userName) {
		return adminDao.getByProperty("userName", userName);
	}

	/**
	 * 创建管理员
	 * @param sysAdmin 管理员对象
	 */
	public void saveSysAdmin(SysAdmin sysAdmin) {
		if(sysAdmin != null) {
			String id = sysAdmin.getId();
			Date now = new Date();
			if(StringUtils.isNotBlank(id)) {
				SysAdmin updateAdmin = getSysAdminById(id);
				sysAdmin.setCreateTime(updateAdmin.getCreateTime());
				sysAdmin.setUpdateTime(now);
			} else {
				sysAdmin.setCreateTime(now);
			}
			adminDao.save(sysAdmin);
		}
	}

	/**
	 * 通过ID删除管理员对象
	 * @param id
	 */
	public void deleteSysAdmin(String id) {
		adminDao.delete(id);
	}

	/**
	 * 分页查找管理员信息
	 * @param page 分页工具类
	 */
	public void findSysAdmins(Page<SysAdmin> page) {
		adminDao.find(page);
	}

	/**
	 * 查找所有管理员信息
	 * @return
	 */
	public List<SysAdmin> findAllSysAdmins() {
		return adminDao.findAll();
	}

	/**
	 * 删除管理员
	 * @param sysAdmin
	 */
	public void deleteSysAdmin(SysAdmin sysAdmin) {
		adminDao.delete(sysAdmin);
	}
	
	/**
	 * 管理员后台登录
	 * @param admin 管理员对象
	 * @return true:登录成功，false:登录失败
	 */
	public boolean login(SysAdmin admin) {
		if(admin != null) {
			String userName = admin.getUserName();
			String password = admin.getPassword();
			
			//通过管理员账号与密码获取管理员对象
			Map<String, Serializable> properties = new HashMap<String, Serializable>();
			properties.put("userName", userName);
			properties.put("password", EncryptUtil.md5(password));
			SysAdmin loginAdmin = adminDao.getByProperties(properties);
			if(loginAdmin != null) {
				admin.setRoleId(loginAdmin.getRoleId());
				actionLogService.recordActionLog(loginAdmin.getId(), AdminActionType.LOGIN, "登录运营后台");
				return true;
			}
		}
		return false;
	}

	public void deleteMoreSysAdmin(String[] ids) {
		if(ids != null) {
			for(String id:ids) {
				adminDao.delete(id);
			}
		}
	}
	
	/**
	 * 修改错误次数
	 */
	public void updateErrorCount(String id,boolean isSuccess){
		SysAdmin admin = getSysAdminById(id);
		if(isSuccess){
			admin.setErrorCount(0);
		}else{
			admin.setErrorCount(admin.getErrorCount()==null?1:admin.getErrorCount()+1);
		}
		adminDao.save(admin);
	}
}
