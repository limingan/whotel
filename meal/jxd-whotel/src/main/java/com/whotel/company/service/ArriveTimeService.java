package com.whotel.company.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whotel.company.entity.ArriveTime;
import com.whotel.company.entity.CompanyAdmin;
import com.whotel.common.dao.mongo.Order;
import com.whotel.common.dao.mongo.Page;
import com.whotel.company.dao.ArriveTimeDao;

@Service
public class ArriveTimeService {
	
	@Autowired
	private ArriveTimeDao arriveTimeDao;

	public void findArriveTimes(Page<ArriveTime> page) {
		arriveTimeDao.find(page);
	}
	
	public ArriveTime getArriveTimeById(String companyId,String id) {
		Map<String, Serializable> properties = new HashMap<String, Serializable>();
		properties.put("companyId", companyId);
		properties.put("id", id);
		return arriveTimeDao.getByProperties(properties);
	}
	
	public List<ArriveTime> findArriveTimeAll(String companyId){
		List<ArriveTime> arriveTimes = new ArrayList<ArriveTime>();
		for (int i = 0; i < 24; i++) {
			ArriveTime arriveTime = new ArriveTime();
			
			if(i<10){
				arriveTime.setArriveTime("0"+i+":00");
			}else{
				arriveTime.setArriveTime(i+":00");
			}
			if(i == 18){
				arriveTime.setIsDefault(true);
			}
			arriveTimes.add(arriveTime);
		}
		return arriveTimes;
//		return arriveTimeDao.findByProperty("companyId", companyId,Order.asc("createTime"));
	}
	
	public void saveArriveTime(ArriveTime at) {
		if(at != null) {
			arriveTimeDao.save(at);
		}
	}
	
	public void deleteArriveTime(String id){
		arriveTimeDao.delete(id);
	}
	
	public ArriveTime getDefaultArriveTime(String companyId){
		Map<String, Serializable> properties = new HashMap<String, Serializable>();
		properties.put("companyId", companyId);
		properties.put("isDefault", true);
		return arriveTimeDao.getByProperties(properties);
	}
	
	public ArriveTime findArriveTimeByTime(String time,String companyId){
		Map<String, Serializable> properties = new HashMap<String, Serializable>();
		properties.put("arriveTime", time);
		properties.put("companyId", companyId);
		return arriveTimeDao.getByProperties(properties);
	}
}
