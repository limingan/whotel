package com.whotel.admin.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whotel.admin.dao.SysNoticeDao;
import com.whotel.admin.entity.SysNotice;
import com.whotel.common.dao.mongo.Page;

/**
 * 系统公告管理服务
 * @author 冯勇
 *
 */
@Service
public class SysNoticeService {
	
	@Autowired
	private SysNoticeDao noticeDao;
	
	/**
	 * 通过ID获取系统公告管理
	 * @param id：公告ID
	 * @return 公告对象
	 */
	public SysNotice getSysNoticeById(String id) {
		return noticeDao.get(id);
	}

	/**
	 * 创建公告对象
	 * @param sysNotice 公告对象
	 */
	public void saveSysNotice(SysNotice sysNotice) {
		if(StringUtils.isBlank(sysNotice.getId())){
			sysNotice.setCreateTime(new Date());
		}
		noticeDao.save(sysNotice);
	}

	/**
	 * 通过ID删除公告对象
	 * @param id
	 */
	public void deleteSysNotice(String id) {
		noticeDao.delete(id);
	}

	/**
	 * 分页查找公告对象
	 * @param page 分页工具类
	 */
	public void findSysNotices(Page<SysNotice> page) {
		noticeDao.find(page);
	}

	/**
	 * 查找所有公告对象
	 * @return
	 */
	public List<SysNotice> findAllSysNotices() {
		return noticeDao.findAll();
	}

	/**
	 * 删除公告对象
	 * @param sysNotice
	 */
	public void deleteSysNotice(SysNotice sysNotice) {
		noticeDao.delete(sysNotice);
	}

	public void deleteMoreSysNotice(String ids) {
		if(StringUtils.isNotBlank(ids)) {
			String[] split = ids.split(",");
			for(String id:split) {
				deleteSysNotice(id);
			}
		}
	}
}
