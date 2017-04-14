package com.whotel.common.base.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whotel.card.entity.Member;
import com.whotel.card.service.MemberService;
import com.whotel.card.service.MemberTradeService;
import com.whotel.common.enums.TradeType;
import com.whotel.company.entity.Company;
import com.whotel.company.entity.SysParamConfig;
import com.whotel.company.service.CompanyService;
import com.whotel.company.service.SysParamConfigService;
import com.whotel.hotel.entity.BaseOrder;
import com.whotel.thirdparty.jxd.api.JXDMemberService;

/**
 * @author 柯鹏程
 */
@Service
public class BaseOrderService {
	
	@Autowired
	private CompanyService companyService;
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private MemberTradeService memberTradeService;
	
	@Autowired
	private SysParamConfigService sysParamConfigService;
	
	public void orderRefund(BaseOrder baseOrder){
		SysParamConfig sysParamConfig = sysParamConfigService.getSysParamConfig(baseOrder.getCompanyId());
		if(sysParamConfig==null || !Boolean.TRUE.equals(sysParamConfig.getIsRefund())){
			Company company = companyService.getCompanyById(baseOrder.getCompanyId());
			JXDMemberService memberService = new JXDMemberService();
			Member member = this.memberService.getMemberByOpendId(baseOrder.getOpenId());
			if(member !=null && StringUtils.isNotBlank(member.getProfileId())){
				if(StringUtils.isNotBlank(baseOrder.getCouponCode())){
					memberService.sendMemberCoupon(member, baseOrder.getCouponCode(), 1, null, "订单取消，补发优惠券",company.getCode(),1);
				}
				
				if(baseOrder.getConsumePoint()!=null && baseOrder.getConsumePoint()>0){//赠送积分
					memberTradeService.memberTrade(null, baseOrder.getOpenId(), baseOrder.getConsumePoint(), TradeType.CHARGE, "商城订单取消，补发积分",null);
				}
				
				if(baseOrder.getIncamount()!=null && baseOrder.getIncamount()>0){
					memberService.memberTrade(member.getProfileId(), company.getOutletCode(), "18", baseOrder.getIncamount(), null, "订单取消，返回返现", "WX", company.getCode());
				}
			}
		}
	}

}
