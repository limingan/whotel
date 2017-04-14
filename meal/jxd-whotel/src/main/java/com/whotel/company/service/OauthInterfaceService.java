package com.whotel.company.service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whotel.common.dao.mongo.Order;
import com.whotel.company.dao.OauthInterfaceDao;
import com.whotel.company.entity.OauthInterface;

@Service
public class OauthInterfaceService {
	
	@Autowired
	private OauthInterfaceDao oauthInterfaceDao;
	
	public OauthInterface getEnableOauthInterface(String companyId,String code) {
		Map<String, Serializable> properties = new HashMap<String, Serializable>();
		properties.put("enable", true);
		properties.put("code", code);
		properties.put("companyId", companyId);
		
		OauthInterface config = oauthInterfaceDao.getByProperties(properties);
		return config;
	}
	
	public OauthInterface getOauthInterfaceById(String id){
		return oauthInterfaceDao.get(id);
	}
	
	public List<OauthInterface> findOauthInterfaces(String companyId) {
		return oauthInterfaceDao.findByProperty("companyId", companyId,Order.desc("createTime"));
	}
	
	public OauthInterface getCompanyByCode(String companyId,String code) {
		Map<String, Serializable> properties = new HashMap<String, Serializable>();
		properties.put("companyId", companyId);
		properties.put("code", code);
		return oauthInterfaceDao.getByProperties(properties);
	}
	
	public void saveOauthInterface(OauthInterface oauthInterface) {
		if(oauthInterface != null) {
			oauthInterfaceDao.save(oauthInterface);
		}
	}
	
	public void deleteOauthInterface(String id){
		oauthInterfaceDao.delete(id);
	}
}
