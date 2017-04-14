package com.whotel.company.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whotel.company.dao.PayConfigDao;
import com.whotel.company.entity.PayConfig;
import com.whotel.company.enums.PayType;

@Service
public class PayConfigService {
	
	@Autowired
	private PayConfigDao payConfigDao;
	
	public PayConfig getPayConfigById(String companyId,String id) {
		Map<String, Serializable> properties = new HashMap<String, Serializable>();
		properties.put("companyId", companyId);
		properties.put("id", id);
		return payConfigDao.getByProperties(properties);
	}
	
	public PayConfig getPayConfigByType(String companyId,PayType payType) {
		Map<String, Serializable> properties = new HashMap<String, Serializable>();
		properties.put("companyId", companyId);
		properties.put("payType", payType);
		return payConfigDao.getByProperties(properties);
	}
	
	public List<PayConfig> findPayConfigAll(String companyId){
		return payConfigDao.findByProperty("companyId", companyId);
	}
	
	public void savePayConfig(PayConfig ic) {
		if(ic != null) {
			payConfigDao.save(ic);
		}
	}
	
	public void deletePayConfig(String id){
		payConfigDao.delete(id);
	}
	
	/**
	 * 查询出数据库中不存在的支付类型
	 * @param companyId
	 * @return
	 */
	public List<PayType> getDbNotExistPayType(String companyId){
		List<PayType> payTypes = new ArrayList<PayType>();
		payTypes.add(PayType.WX);
//		payTypes.add(PayType.ZFB);
//		payTypes.add(PayType.CFT);
//		payTypes.add(PayType.WY);
		payTypes.add(PayType.WX_PROVIDER);
		
		List<PayConfig> removePayConfigs = findPayConfigAll(companyId);
		for (PayConfig removePayConfig : removePayConfigs) {
			payTypes.remove(removePayConfig.getPayType());
		}
		
		return payTypes;
	}
}
