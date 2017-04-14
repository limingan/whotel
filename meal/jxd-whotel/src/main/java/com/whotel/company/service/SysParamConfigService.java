package com.whotel.company.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whotel.common.util.BeanUtil;
import com.whotel.company.dao.SysParamConfigDao;
import com.whotel.company.entity.SysParamConfig;

/**
 * 系统配置
 * @author kf
 *
 */
@Service
public class SysParamConfigService {
	
	@Autowired
	private SysParamConfigDao systemConfigDao;
	
	public SysParamConfig getSysParamConfigById(String id) {
		return systemConfigDao.get(id);
	}
	
	public SysParamConfig getSysParamConfig(String companyId) {
		SysParamConfig config = systemConfigDao.getByProperty("companyId", companyId);
		if(config==null){
			config = new SysParamConfig();
		}
		return config;
	}
	
	public void saveSystemConfig(SysParamConfig config) {
		if(config != null) {
			if(StringUtils.isNotBlank(config.getId())) {
				SysParamConfig updateConfig = getSysParamConfigById(config.getId());
				BeanUtil.copyProperties(updateConfig, config, true);
				systemConfigDao.save(updateConfig);
			} else {
				systemConfigDao.save(config);
			}
		}
	}

}
