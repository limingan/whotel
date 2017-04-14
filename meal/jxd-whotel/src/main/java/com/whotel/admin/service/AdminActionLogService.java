package com.whotel.admin.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whotel.admin.dao.AdminActionLogDao;
import com.whotel.admin.entity.AdminActionLog;
import com.whotel.admin.enums.AdminActionType;
import com.whotel.common.dao.mongo.Page;

/**
 * 管理员行为日志管理服务
 * @author 冯勇
 *
 */
@Service
public class AdminActionLogService {
	
	@Autowired
	private AdminActionLogDao adminActionLogDao;
	
	public void recordActionLog(String adminId, AdminActionType type, String remark) {
		AdminActionLog log = new AdminActionLog();
		log.setAdminId(adminId);
		log.setAction(type);
		log.setRemark(remark);
		log.setCreateTime(new Date());
		adminActionLogDao.save(log);
	}
	
	/**
	 * 通过ID获取管理员行为日志对象
	 * @param id：管理ID
	 * @return 管理员行为日志对象
	 */
	public AdminActionLog getAdminActionLogById(String id) {
		return adminActionLogDao.get(id);
	}
	
	/**
	 * 创建管理员行为日志
	 * @param adminActionLog 管理员行为日志对象
	 */
	public void createAdminActionLog(AdminActionLog adminActionLog) {
		adminActionLogDao.save(adminActionLog);
	}

	/**
	 * 通过ID删除管理员行为日志对象
	 * @param id
	 */
	public void deleteAdminActionLog(String id) {
		adminActionLogDao.delete(id);
	}

	/**
	 * 分页查找管理员行为日志信息
	 * @param page 分页工具类
	 */
	public void findAdminActionLogs(Page<AdminActionLog> page) {
		adminActionLogDao.find(page);
	}

	/**
	 * 查找所有管理员行为日志信息
	 * @return
	 */
	public List<AdminActionLog> findAllAdminActionLogs() {
		return adminActionLogDao.findAll();
	}

	/**
	 * 删除管理员行为日志
	 * @param adminActionLog
	 */
	public void deleteAdminActionLog(AdminActionLog adminActionLog) {
		adminActionLogDao.delete(adminActionLog);
	}
	
	public void deleteAdminActionLogByAdminId(String adminId) {
		adminActionLogDao.delete("adminId", adminId);
	}
	
	public void deleteMoreAdminActionLog(String[] ids) {
		if(ids != null) {
			for(String id:ids) {
				adminActionLogDao.delete(id);
			}
		}
	}
}
