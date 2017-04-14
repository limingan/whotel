package com.whotel.company.service;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whotel.company.dao.InterfaceConfigDao;
import com.whotel.company.dao.MbrInterfaceConfigDao;
import com.whotel.company.entity.Company;
import com.whotel.company.entity.InterfaceConfig;
import com.whotel.company.entity.MbrInterfaceConfig;
import com.whotel.thirdparty.jxd.JXDConstants;
import com.whotel.thirdparty.jxd.api.JXDMemberService;
import com.whotel.thirdparty.jxd.mode.InterfaceListQuery;
import com.whotel.thirdparty.jxd.mode.vo.InterfaceListVO;

@Service
public class InterfaceConfigService {
	
	@Autowired
	private InterfaceConfigDao interfaceConfigDao;
	
	@Autowired
	private MbrInterfaceConfigDao mbrInterfaceConfigDao;
	
	@Autowired
	private CompanyService companyService;
	
	public InterfaceConfig getEnableInterfaceConfig(String companyId) {
		Map<String, Serializable> properties = new HashMap<String, Serializable>();
		properties.put("enable", true);
		properties.put("companyId", companyId);
		
		InterfaceConfig config = interfaceConfigDao.getByProperties(properties);
		if(config != null) {
			JXDConstants.API_GATEWAY = config.getHost();      //设置商家数据接口
			JXDConstants.API_URL = config.getUrl();
		}
		return config;
	}
	
	public InterfaceConfig getInterfaceConfig(String companyId) {
		return interfaceConfigDao.getByProperty("companyId", companyId);
	}
	
	public InterfaceConfig getInterfaceConfigByCode(String code) {
		Company company = companyService.getCompanyByCode(code);
		Map<String, Serializable> properties = new HashMap<String, Serializable>();
		properties.put("enable", true);
		properties.put("companyId", company.getId());
		
		InterfaceConfig config = interfaceConfigDao.getByProperties(properties);
		if(config!=null){
			return config;
		}
		return null;
	}
	
	public String getHotelUrlByHotelCode(String code){
		Company company = companyService.getCompanyByCode(code);
		
		Map<String, Serializable> properties = new HashMap<String, Serializable>();
		properties.put("enable", true);
		properties.put("companyId", company.getId());
		
		InterfaceConfig config = interfaceConfigDao.getByProperties(properties);
		if(config!=null){
			return config.getHost();
		}
		return null;
	}
	
	public void saveInterfaceConfig(InterfaceConfig ic) {
		if(ic != null) {
			interfaceConfigDao.save(ic);
			String hotelCode = ic.getCompany().getCode();
			InterfaceListQuery query = new InterfaceListQuery();
			query.setCode(hotelCode);
			try {
				InterfaceConfig interfaceConfig = getEnableInterfaceConfig(ic.getCompanyId());
				if(interfaceConfig != null) {
					JXDMemberService memberService = new JXDMemberService();
					List<InterfaceListVO> interfaceLists = memberService.loadInterfaceListVOs(query);
					if(interfaceLists!=null && interfaceLists.size()>0){
						deleteMbrInterfaceConfigAll(hotelCode);
						for (InterfaceListVO vo : interfaceLists) {
							MbrInterfaceConfig mbrInterfaceConfig = new MbrInterfaceConfig();
							mbrInterfaceConfig.setHotelCode(hotelCode);
							mbrInterfaceConfig.setOpType(vo.getInterfaceName());
							mbrInterfaceConfig.setUrl(vo.getInterfaceUrl());
							mbrInterfaceConfig.setCreateDate(new Date());
							saveMbrInterfaceConfig(mbrInterfaceConfig);
						}
					}
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public MbrInterfaceConfig getMbrInterfaceConfig(String hotelCode,String opType){
		Map<String, Serializable> properties = new HashMap<>();
		properties.put("hotelCode",hotelCode);
		properties.put("opType",opType);
		return mbrInterfaceConfigDao.getByProperties(properties);
	}
	
	public void saveMbrInterfaceConfig(MbrInterfaceConfig mbrInterfaceConfig){
		mbrInterfaceConfigDao.save(mbrInterfaceConfig);
	}
	
	public void deleteMbrInterfaceConfigAll(String hotelCode){
		mbrInterfaceConfigDao.delete("hotelCode", hotelCode);
	}
}
