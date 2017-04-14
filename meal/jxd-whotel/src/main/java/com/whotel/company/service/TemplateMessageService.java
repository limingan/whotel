package com.whotel.company.service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whotel.common.dao.mongo.Page;
import com.whotel.company.entity.TemplateMessage;
import com.whotel.company.enums.MessageType;
import com.whotel.company.dao.TemplateMessageDao;

@Service
public class TemplateMessageService {
	
	@Autowired
	private TemplateMessageDao templateMessageDao;

	public void findTemplateMessages(Page<TemplateMessage> page) {
		templateMessageDao.find(page);
	}
	
	public TemplateMessage getTemplateMessageById(String companyId,String id) {
		Map<String, Serializable> properties = new HashMap<String, Serializable>();
		properties.put("companyId", companyId);
		properties.put("id", id);
		return templateMessageDao.getByProperties(properties);
	}
	
	public List<TemplateMessage> findTemplateMessageAll(String companyId){
		return templateMessageDao.findByProperty("companyId", companyId);
	}
	
	public void saveTemplateMessage(TemplateMessage tm) {
		if(tm != null) {
			templateMessageDao.save(tm);
		}
	}
	
	public void deleteTemplateMessage(String id){
		templateMessageDao.delete(id);
	}
	
	public TemplateMessage findTemplateMessageByType(MessageType messageType,String companyId){
		Map<String, Serializable> properties = new HashMap<String, Serializable>();
		properties.put("messageType", messageType);
		properties.put("companyId", companyId);
		return templateMessageDao.getByProperties(properties);
	}
}
