package com.whotel.common.service;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whotel.common.dao.RegionDao;
import com.whotel.common.dao.mongo.Page;
import com.whotel.common.entity.Region;

@Service
public class RegionService {

	@Autowired
	private RegionDao regionDao;
	
	public Region getRegionByRegionId(Integer regionId) {
		if(regionId != null) {
			return regionDao.getByProperty("regionId", regionId);
		}
		return null;
	}

	public Region getRegionByCode(String code) {
		if(StringUtils.isNotBlank(code)) {
			return regionDao.getByProperty("code", code);
		}
		return null;
	}

	public void findRegions(Page<Region> page) {
		regionDao.find(page);
	}

	public List<Region> findAllRegions() {
		return regionDao.findAll();
	}
}
