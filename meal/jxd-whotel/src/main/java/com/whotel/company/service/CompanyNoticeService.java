package com.whotel.company.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whotel.common.dao.mongo.Order;
import com.whotel.common.dao.mongo.Page;
import com.whotel.company.dao.CompanyNoticeDao;
import com.whotel.company.entity.CompanyNotice;

/**
 * 商户公告管理服务
 * @author 冯勇
 *
 */
@Service
public class CompanyNoticeService {
	
	@Autowired
	private CompanyNoticeDao noticeDao;
	
	/**
	 * 通过ID获取商户公告管理
	 * @param id：公告ID
	 * @return 公告对象
	 */
	public CompanyNotice getCompanyNoticeById(String id) {
		return noticeDao.get(id);
	}

	/**
	 * 创建公告对象
	 * @param companyNotice 公告对象
	 */
	public void saveCompanyNotice(CompanyNotice companyNotice) {
		companyNotice.setCreateTime(new Date());
		if(StringUtils.isNotBlank(companyNotice.getId())){
			companyNotice.setCreateTime(getCompanyNoticeById(companyNotice.getId()).getCreateTime());
		}
		noticeDao.save(companyNotice);
	}

	/**
	 * 分页查找公告对象
	 * @param page 分页工具类
	 */
	public void findCompanyNotices(Page<CompanyNotice> page) {
		noticeDao.find(page);
	}
	
	public CompanyNotice getLastestCompanyNotice(String companyId) {
		return noticeDao.getByProperty("companyId", companyId, Order.desc("createTime"));
	}

	/**
	 * 查找所有公告对象
	 * @return
	 */
	public List<CompanyNotice> findAllCompanyNotices() {
		return noticeDao.findAll();
	}

	/**
	 * 删除公告对象
	 * @param companyNotice
	 */
	public void deleteCompanyNotice(CompanyNotice companyNotice) {
		noticeDao.delete(companyNotice);
	}
	
	public void deleteMoreCompanyNotice(String ids, String companyId) {
		if(StringUtils.isNotBlank(ids)) {
			String[] split = ids.split(",");
			for(String id:split) {
				CompanyNotice companyNotice = getCompanyNoticeById(id);
				companyNotice.setCompanyId(companyId);
				deleteCompanyNotice(companyNotice);
			}
		}
	}
}
