package com.whotel.common.dao;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.whotel.common.util.BeanUtil;

/**
 * 检查权限的具体实现类
 */
@Service
public class OwnerCheckDao<T, PK extends Serializable> {

	public boolean isOwner(T obj, CommonDao<T, PK> dao) {
		if (obj == null)
			return false;

		// TODO: 等统一了公司实体接口后实现
		CheckEntity entity = new CheckEntity();
		BeanUtil.copyProperties(obj, entity);
//
		String entityId = entity.getId();
		if (!StringUtils.isBlank(entityId)) {
			String compId = entity.getCompanyId();
			if (StringUtils.isBlank(compId)) {
				// 如果没有公司信息，返回false
				return false;
			}
			Map<String, Serializable> properties = new HashMap<String, Serializable>();
			properties.put("id", entityId);
			properties.put("companyId", compId);
			obj = dao.getByProperties(properties);
			return obj != null;
		}
		return true;
	}
	
	class CheckEntity {
		private String id;
		private String companyId;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getCompanyId() {
			return companyId;
		}

		public void setCompanyId(String companyId) {
			this.companyId = companyId;
		}
	}
}


