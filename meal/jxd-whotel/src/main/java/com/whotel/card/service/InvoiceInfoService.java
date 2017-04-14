package com.whotel.card.service;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whotel.card.dao.InvoiceInfoDao;
import com.whotel.card.entity.InvoiceInfo;
import com.whotel.common.dao.mongo.Order;
import com.whotel.common.util.BeanUtil;

/**
 * 发票
 */
@Service
public class InvoiceInfoService {
	
	private static Logger log = LoggerFactory.getLogger(InvoiceInfoService.class);
	
	@Autowired
	private InvoiceInfoDao invoiceInfoDao;
	
	
	public void saveInvoiceInfo(InvoiceInfo invoiceInfo){
		
		if(Boolean.TRUE.equals(invoiceInfo.getDef())){
			InvoiceInfo defInvoiceInfo = getDefInvoiceInfo(invoiceInfo.getOpenId());
			if(defInvoiceInfo != null){
				defInvoiceInfo.setDef(false);
				invoiceInfoDao.save(defInvoiceInfo);
			}
		}
		
		if(StringUtils.isBlank(invoiceInfo.getId())){
			invoiceInfo.setCreateDate(new Date());
		}else{
			InvoiceInfo udpateInvoiceInfo = getInvoiceInfo(invoiceInfo.getId());
			BeanUtil.copyProperties(udpateInvoiceInfo, invoiceInfo, true);
			invoiceInfo = udpateInvoiceInfo;
		}
		invoiceInfoDao.save(invoiceInfo);
	}
	
	public InvoiceInfo getInvoiceInfo(String id){
		return invoiceInfoDao.get(id);
	}
	
	public List<InvoiceInfo> findInvoiceInfos(String openId){
		return invoiceInfoDao.findByProperty("openId", openId,Order.desc("def"));
	}
	
	public void deleteInvoiceInfo(String id){
		invoiceInfoDao.delete(id);
	}
	
	public InvoiceInfo getDefInvoiceInfo(String openId){
		Map<String, Serializable> properties = new HashMap<>();
		properties.put("openId", openId);
		properties.put("def", true);
		return invoiceInfoDao.getByProperties(properties);
	}
}
