package com.whotel.card.service;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whotel.card.dao.CouponTypeDao;
import com.whotel.card.dao.SendCouponRecordDao;
import com.whotel.card.entity.CouponType;
import com.whotel.card.entity.SendCouponRecord;
import com.whotel.common.dao.mongo.Page;
import com.whotel.company.entity.InterfaceConfig;
import com.whotel.company.service.InterfaceConfigService;
import com.whotel.thirdparty.jxd.api.JXDMemberService;
import com.whotel.thirdparty.jxd.mode.vo.CategoryCodeVO;

@Service
public class CouponTypeService {

	@Autowired
	private CouponTypeDao couponTypeDao;
	
	@Autowired
	private SendCouponRecordDao sendCouponRecordDao;
	
	@Autowired
	private InterfaceConfigService interfaceConfigService;
	
	public List<CategoryCodeVO> listCouponTypes(String companyId,String hotelCode) {
		InterfaceConfig interfaceConfig = interfaceConfigService.getEnableInterfaceConfig(companyId);
		if(interfaceConfig != null) {
			JXDMemberService memberService = new JXDMemberService();
			return memberService.queryCouponType(hotelCode);
		}
		return null;
	}
	
	public CategoryCodeVO getCategoryCodeVO(String companyId, String code,String hotelCode) {
		InterfaceConfig interfaceConfig = interfaceConfigService.getEnableInterfaceConfig(companyId);
		if(interfaceConfig != null) {
			JXDMemberService memberService = new JXDMemberService();
			List<CategoryCodeVO> couponTypes = memberService.queryCouponType(hotelCode);
			if(couponTypes != null) {
				for(CategoryCodeVO categoryCode:couponTypes) {
					if(StringUtils.equals(categoryCode.getCode(), code)) {
						return categoryCode;
					}
				}
			}
		}
		return null;
	}
	
	public List<CouponType> findCouponTypes(String companyId){
		Map<String, Serializable> properties = new HashMap<>();
		properties.put("companyId", companyId);
		return couponTypeDao.findByProperties(properties);
	}
	
	public Map<String, Float> findUseMoneyByCode(String companyId){
		Map<String, Float> map = new HashMap<>();
 		List<CouponType> couponTypes = findCouponTypes(companyId);
		for (CouponType couponType : couponTypes) {
			if(couponType.getUseMoney() != null){
				map.put(couponType.getCode(), couponType.getUseMoney());
			}
		}
		return map;
	}
	
	public CouponType getCouponTypeById(String id){
		return couponTypeDao.get(id);
	}
	
	public void deleteCouponType(String id){
		couponTypeDao.delete(id);
	}
	
	public CouponType getCouponTypeByCode(String companyId,String code){
		Map<String, Serializable> properties = new HashMap<>();
		properties.put("companyId", companyId);
		properties.put("code", code);
		return couponTypeDao.getByProperties(properties);
	}
	
	/**
	 * 同步劵类型
	 * @param couponType
	 */
	public void synchronizeCouponType(String companyId,String hotelCode){
		List<CategoryCodeVO> categoryCodeVOs = listCouponTypes(companyId, hotelCode);
		if(categoryCodeVOs != null){
			for (CategoryCodeVO vo : categoryCodeVOs) {
				CouponType type = getCouponType(companyId, vo.getCode());
				if(type == null){
					type = new CouponType();
					type.setCode(vo.getCode());
					type.setName(vo.getCname());
					type.setCompanyId(companyId);
					couponTypeDao.save(type);
				}
			}
		}
	}
	
	public void saveCouponType(CouponType couponType) {
		if(couponType != null) {
			couponTypeDao.save(couponType);
		}
	}
	
	public CouponType getCouponType(String companyId, String code) {
		Map<String, Serializable> properties = new HashMap<>();
		properties.put("code", code);
		properties.put("companyId", companyId);
		return couponTypeDao.getByProperties(properties);
	}
	
	/**
	 * 派劵记录
	 */
	public void sendCouponRecord(Page<SendCouponRecord> page){
		sendCouponRecordDao.find(page);
	}
	
	public void saveSendCouponRecord(SendCouponRecord record){
		if(record != null) {
			if(StringUtils.isBlank(record.getId())){
				record.setCreateTime(new Date());
			}
			sendCouponRecordDao.save(record);
		}
	}
	
	public SendCouponRecord getSendCouponRecordById(String id){
		return sendCouponRecordDao.get(id);
	}
}
