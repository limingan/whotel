package com.whotel.company.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whotel.common.util.BeanUtil;
import com.whotel.company.dao.SmsConfigDao;
import com.whotel.company.entity.SmsConfig;

@Service
public class SmsConfigService {
	
	@Autowired
	private SmsConfigDao smsConfigDao;
	
	public SmsConfig getSmsConfigById(String id) {
		return smsConfigDao.get(id);
	}
	
	public SmsConfig getSmsConfig(String companyId) {
		return smsConfigDao.getByProperty("companyId", companyId);
	}
	
	public void saveSmsConfig(SmsConfig ic) {
		if(ic != null) {
			if(StringUtils.isNotBlank(ic.getId())) {
				SmsConfig updateIc = getSmsConfigById(ic.getId());
				BeanUtil.copyProperties(updateIc, ic, true);
				smsConfigDao.save(updateIc);
			} else {
				smsConfigDao.save(ic);
			}
		}
	}
}
