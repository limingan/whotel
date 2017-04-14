package com.whotel.system.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.mongodb.morphia.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whotel.common.dao.mongo.Page;
import com.whotel.system.dao.SysMemberLogDao;
import com.whotel.system.dao.SysOperationLogDao;
import com.whotel.system.dao.SysOrderLogDao;
import com.whotel.system.dao.SysRedPackLogDao;
import com.whotel.system.dao.SysSmsLogDao;
import com.whotel.system.dao.SysSubscribeLogDao;
import com.whotel.system.dao.SysTemplateMsgLogDao;
import com.whotel.system.entity.SysMemberLog;
import com.whotel.system.entity.SysOperationLog;
import com.whotel.system.entity.SysOrderLog;
import com.whotel.system.entity.SysRedPackLog;
import com.whotel.system.entity.SysSmsLog;
import com.whotel.system.entity.SysSubscribeLog;
import com.whotel.system.entity.SysTemplateMsgLog;

/**
 * 系统日志服务类
 * @author 柯鹏程
 *
 */
@Service
public class SystemLogService {
	
	@Autowired
	private SysTemplateMsgLogDao templateMsgLogDao;
	
	@Autowired
	private SysMemberLogDao memberLogDao;
	
	@Autowired
	private SysSubscribeLogDao subscribeLogDao;
	
	@Autowired
	private SysSmsLogDao sysSmsLogDao;
	
	@Autowired
	private SysOrderLogDao sysOrderLogDao;
	
	@Autowired
	private SysRedPackLogDao sysRedPackLogDao;

	@Autowired
	private SysOperationLogDao sysOperationLogDao;
	
	public void saveTemplateMsgLog(SysTemplateMsgLog log){
		try {
			log.setCreateDate(new Date());
			templateMsgLogDao.save(log);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public SysTemplateMsgLog getLastSysTemplateMsgLog(){
		Page<SysTemplateMsgLog> page = new Page<>();
		page.addFilter("methodName", "redis报错通知");
		page.addOrder("createDate", false);
		page.setPageSize(1);
		templateMsgLogDao.find(page);
		List<SysTemplateMsgLog> list = page.getResult();
		return list.size()>0?list.get(0):null;
	}
	
	public void saveMemberLog(SysMemberLog log){
		try {
			log.setCreateDate(new Date());
			memberLogDao.save(log);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void saveSubscribeLog(SysSubscribeLog log){
		try {
			if(log.getEventMsg()!=null){
				log.setCreateDate(new Date());
				subscribeLogDao.save(log);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void saveSysSmsLog(SysSmsLog log){
		try {
			log.setCreateDate(new Date());
			sysSmsLogDao.save(log);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void saveSysOrderLog(SysOrderLog log){
		try {
			log.setCreateDate(new Date());
			sysOrderLogDao.save(log);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void saveSysRedPackLog(SysRedPackLog log){
		try {
			if(StringUtils.isBlank(log.getId())){
				log.setCreateDate(new Date());
				sysRedPackLogDao.save(log);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public SysRedPackLog getSysRedPackLogByWmId(String winningMemberId){
		try {
			return sysRedPackLogDao.getByProperty("winningMemberId", winningMemberId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void saveSysOperationLog(SysOperationLog log){
		try {
			log.setCreateTime(new Date());
			sysOperationLogDao.save(log);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void findSysOperationLogs(Page<SysOperationLog> page){
		sysOperationLogDao.find(page);
	}
	
	public void cleanSysOperationLogs(){
		Query<SysOperationLog> query = sysOperationLogDao.createQuery();
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -1);//一个月前
		Date date = cal.getTime();
		query.field("createTime").lessThanOrEq((date));
		List<SysOperationLog> list = sysOperationLogDao.findByQuery(query).asList();
		if(list!=null){
			for (SysOperationLog sysOperationLog : list) {
				sysOperationLogDao.delete(sysOperationLog);
			}
		}
	}
}
