package com.whotel.card.service;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whotel.card.dao.DepartmentDao;
import com.whotel.card.entity.Department;
import com.whotel.card.entity.MarketingFan;
import com.whotel.common.dao.mongo.Page;

/**
 * 微营销酒店部门管理服务类
 * @author 冯勇
 *
 */
@Service
public class DepartmentService {
	
	private static final Logger logger = Logger.getLogger(DepartmentService.class);

	@Autowired
	private DepartmentDao departmentDao;
	
	public void saveDepartment(Department department){
		if(department==null){
			return;
		}else if(StringUtils.isBlank(department.getId())){
			department.setCreateTime(new Date());
		}
		departmentDao.save(department);
	}

	public Page<Department> findDepartment(Page<Department> page){
		return departmentDao.find(page);
	}
	
	public List<Department> findDepartment(String companyId,String hotelCode){
		Map<String, Serializable> properties = new HashMap<>();
		properties.put("companyId", companyId);
		if(StringUtils.isNotBlank(hotelCode)){
			properties.put("hotelCode", hotelCode);
		}
		return departmentDao.findByProperties(properties);
	}
	
	public Department getDepartmentById(String id){
		return departmentDao.get(id);
	}
	
	public void deleteDepartmentByIds(String ids){
		if(StringUtils.isNotBlank(ids)) {
			String[] split = ids.split(",");
			for(String id:split) {
				departmentDao.delete(id);
			}
		}
	}
}
