package com.whotel.company.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whotel.common.util.BeanUtil;
import com.whotel.company.dao.PublicNoDao;
import com.whotel.company.dao.WelcomeMsgDao;
import com.whotel.company.entity.PublicNo;
import com.whotel.company.entity.WelcomeMsg;
import com.whotel.front.entity.WeixinFan;
import com.whotel.front.service.WeixinFanService;

/**
 * 公众号数据管理服务类
 * @author 冯勇
 *
 */
@Service
public class PublicNoService {
	
	@Autowired
	private PublicNoDao publicNoDao;
	
	@Autowired
	private WelcomeMsgDao welcomeMsgDao;
	
	@Autowired
	private WeixinFanService weixinFanService;
	
	/**
	 * 保存公众号信息
	 * @param publicNo
	 */
	public void savePublicNo(PublicNo publicNo) {
		if(publicNo != null) {
			if(StringUtils.isNotBlank(publicNo.getId())) {
				PublicNo updatePublicNo = getPublicNoById(publicNo.getId());
				BeanUtil.copyProperties(updatePublicNo, publicNo, true);
				publicNoDao.save(updatePublicNo);
			} else {
				publicNoDao.save(publicNo);
			}
		}
	}
	
	/**
	 * 根据公司id获取公众号
	 * @param companyId
	 * @return
	 */
	public PublicNo getPublicNoByCompanyId(String companyId) {
		return publicNoDao.getByProperty("companyId", companyId);
	}
	
	/**
	 * 根据公众号开发code获取公众号
	 * @param developerCode
	 * @return
	 */
	public PublicNo getPublicNoByDeveloperCode(String developerCode) {
		return publicNoDao.getByProperty("developerCode", developerCode);
	}
	
	/**
	 * 根据账号获取公众号
	 * @param account
	 * @return
	 */
	public PublicNo getPublicNoByAccount(String account) {
		return publicNoDao.getByProperty("account", account);
	}
	
	public PublicNo getPublicNoByOpenId(String openId) {
		WeixinFan weixinFan = weixinFanService.getWeixinFanByOpenId(openId);
		if(weixinFan != null) {
			return getPublicNoByDeveloperCode(weixinFan.getDevCode());
		}
		return null;
	}
	
	public PublicNo getPublicNoById(String id) {
		return publicNoDao.get(id);
	}
	
	public void saveWelcomeMsg(WelcomeMsg welcomeMsg) {
		welcomeMsgDao.save(welcomeMsg);
	}
	
	public WelcomeMsg getWelcomeMsg(String companyId) {
		return welcomeMsgDao.getByProperty("companyId", companyId);
	}
}
