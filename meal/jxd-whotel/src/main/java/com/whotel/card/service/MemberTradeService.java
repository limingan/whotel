package com.whotel.card.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whotel.card.dao.MbrCardTypeDao;
import com.whotel.card.dao.SignInRecordDao;
import com.whotel.card.entity.ContactAddress;
import com.whotel.card.entity.CouponType;
import com.whotel.card.entity.MbrCardType;
import com.whotel.card.entity.Member;
import com.whotel.card.entity.SendCouponRecord;
import com.whotel.card.entity.SignInRecord;
import com.whotel.card.entity.SignInRule;
import com.whotel.card.enums.CouponStates;
import com.whotel.common.enums.TradeType;
import com.whotel.common.util.DateUtil;
import com.whotel.common.util.MoneyUtil;
import com.whotel.company.entity.Company;
import com.whotel.company.entity.InterfaceConfig;
import com.whotel.company.enums.ModuleType;
import com.whotel.company.service.InterfaceConfigService;
import com.whotel.front.service.PayOrderService;
import com.whotel.thirdparty.jxd.api.JXDMemberService;
import com.whotel.thirdparty.jxd.api.JXDPmsMemberService;
import com.whotel.thirdparty.jxd.mode.BuyMbrCard;
import com.whotel.thirdparty.jxd.mode.CategoryCodeQuery;
import com.whotel.thirdparty.jxd.mode.ChargeMoneyQuery;
import com.whotel.thirdparty.jxd.mode.ExchangeGiftDetailQuery;
import com.whotel.thirdparty.jxd.mode.ExchangeGiftQuery;
import com.whotel.thirdparty.jxd.mode.MbrCardBinding;
import com.whotel.thirdparty.jxd.mode.MbrCardQuery;
import com.whotel.thirdparty.jxd.mode.MbrCardTypeQuery;
import com.whotel.thirdparty.jxd.mode.vo.CategoryCodeVO;
import com.whotel.thirdparty.jxd.mode.vo.ChargeMoneyVO;
import com.whotel.thirdparty.jxd.mode.vo.ExchangeGiftVO;
import com.whotel.thirdparty.jxd.mode.vo.GeneralMsg;
import com.whotel.thirdparty.jxd.mode.vo.MbrCardTypeVO;
import com.whotel.thirdparty.jxd.mode.vo.MbrCardVO;
import com.whotel.thirdparty.jxd.mode.vo.MemberCouponVO;
import com.whotel.thirdparty.jxd.mode.vo.MemberTradeVO;
import com.whotel.thirdparty.jxd.mode.vo.MemberVO;
import com.whotel.thirdparty.jxd.mode.vo.PointRecordVO;

/**
 * 会员交易相关操作业务服务类
 * @author Administrator
 *
 */
@Service
public class MemberTradeService {

	@Autowired
	private InterfaceConfigService interfaceConfigService;
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private CouponTypeService couponTypeService;
	
	@Autowired
	private PayOrderService payOrderService;
	
	@Autowired
	private MbrCardTypeDao mbrCardTypeDao;
	
	@Autowired
	private SignInRecordDao signInRecordDao;
	
	
	/**
	 * 查询会员交易明细
	 * @param companyId
	 * @param memberVO
	 * @return
	 */
	public List<MemberTradeVO> findMemberTradeVO(String companyId, MemberVO memberVO, String type,String hotelCode) {
		InterfaceConfig interfaceConfig = interfaceConfigService.getEnableInterfaceConfig(companyId);
		if(interfaceConfig != null) {
			JXDMemberService memberService = new JXDMemberService();
			List<MemberTradeVO> mtvos = memberService.queryMemberTrades(memberVO.getProfileId(), memberVO.getCreateDate(), new Date(), type,hotelCode,null);
			return mtvos;
		}
		return null;
	}
	
	/**
	 * 查询会员积分明细
	 * @param companyId
	 * @param memberVO
	 * @return
	 */
	public List<PointRecordVO> findMemberPointVO(String companyId, MemberVO memberVO,String hotelCode) {
		InterfaceConfig interfaceConfig = interfaceConfigService.getEnableInterfaceConfig(companyId);
		if(interfaceConfig != null) {
			JXDMemberService memberService = new JXDMemberService();
			List<PointRecordVO> points = memberService.queryMemberPoints(memberVO.getProfileId(), memberVO.getCreateDate(), new Date(),hotelCode,null);
			return points;
		}
		return null;
	}
	
	/**
	 * 查询会员积分兑换明细
	 * @param companyId
	 * @param memberVO
	 * @return
	 */
	public List<PointRecordVO> findMemberPointExchangeVO(String companyId, MemberVO memberVO,String hotelCode) {
		InterfaceConfig interfaceConfig = interfaceConfigService.getEnableInterfaceConfig(companyId);
		if(interfaceConfig != null) {
			JXDMemberService memberService = new JXDMemberService();
			List<PointRecordVO> points = memberService.queryMemberPointExchanges(memberVO.getProfileId(), memberVO.getCreateDate(), new Date(),hotelCode);
			return points;
		}
		return null;
	}
	
	/**
	 * 查询会员券
	 * @param companyId
	 * @param memberVO
	 * @return
	 */
	public List<MemberCouponVO> findMemberCouponVO(String companyId, String profileId,String hotelCode,CouponStates couponState,ModuleType moduleType) {
		InterfaceConfig interfaceConfig = interfaceConfigService.getEnableInterfaceConfig(companyId);
		if(interfaceConfig != null) {
			JXDMemberService memberService = new JXDMemberService();
			List<MemberCouponVO> coupons = memberService.queryMemberCoupons(profileId,hotelCode);
			List<MemberCouponVO> returnCoupons = new ArrayList<MemberCouponVO>();
			Date currDate = DateUtil.getCurrDate();
			if(coupons != null) {
				for(MemberCouponVO couponVO:coupons) {
					CouponType couponType = couponTypeService.getCouponType(companyId, couponVO.getTicketType());
					if(couponType != null) {
						if(moduleType!=null && !couponType.hasModuleTypes(moduleType)){
							continue;
						}
						
						couponVO.setPicUrl(couponType.getPicUrl());
					}
					
					if(CouponStates.ALL==couponState){
						returnCoupons.add(couponVO);
					}else if(CouponStates.NOT_USED==couponState&&StringUtils.equals(couponVO.getUseflag(), "0")){
						returnCoupons.add(couponVO);
					}else if(CouponStates.USED==couponState&&StringUtils.equals(couponVO.getUseflag(), "1")){
						returnCoupons.add(couponVO);
					}else if(CouponStates.EXPIRED==couponState&&currDate.getTime()>couponVO.getLimitdate().getTime()){
						returnCoupons.add(couponVO);
					}
				}
			}
			return returnCoupons;
		}
		return null;
	}
	
	/**
	 * 查询会员可用券
	 * @param companyId
	 * @param memberVO
	 * @return
	 */
	public List<MemberCouponVO> findMemberUseAbleCouponVO(String companyId, Float totalFee, String profileId,String hotelCode,ModuleType moduleType) {
		List<MemberCouponVO> memberCouponVOs = findMemberCouponVO(companyId, profileId,hotelCode,CouponStates.ALL,moduleType);
		List<MemberCouponVO> validCoupons = null;
		Map<String, Float> map = couponTypeService.findUseMoneyByCode(companyId);
		
		Date now = new Date();
		if(memberCouponVOs != null && memberCouponVOs.size() > 0) {
			validCoupons = new ArrayList<MemberCouponVO>();
			for(MemberCouponVO coupon:memberCouponVOs) {
				String useflag = coupon.getUseflag();
				Date limitdate = coupon.getLimitdate();
				Float amount = coupon.getAmount();
				if(amount != null && amount > 0 && StringUtils.equals(useflag, "0") && limitdate.compareTo(DateUtil.getStartTime(now)) >= 0) {
					
					if(totalFee != null && totalFee > 0 && coupon.getChargeamtmodel()!=null&&coupon.getChargeamtmodel()==1) {
						if(map.get(coupon.getCode())!=null && map.get(coupon.getCode())>totalFee){
							continue;
						}
						if(amount<totalFee) {//金额比支付总额小
							validCoupons.add(coupon);
						}
					}
				}
			}
		}
		return validCoupons;
	}
	
	/**
	 * 根据券id获取券明细
	 * @param companyId
	 * @param memberVO
	 * @param hotelCode
	 * @param seqId
	 * @return
	 */
	public MemberCouponVO getMemberCouponVOBySeqId(String companyId, String profileId,String hotelCode,String seqId){
		InterfaceConfig interfaceConfig = interfaceConfigService.getEnableInterfaceConfig(companyId);
		if(interfaceConfig != null) {
			JXDMemberService memberService = new JXDMemberService();
			List<MemberCouponVO> coupons = memberService.queryMemberCoupons(profileId,hotelCode);
			if(coupons != null) {
				for(MemberCouponVO couponVO:coupons) {
					if(StringUtils.equals(couponVO.getSeqid(), seqId)){
						return couponVO;
					}
				}
			}
		}
		return null;
	}
	
	/**
	 * 核销会员券
	 * @param companyId
	 * @param memberVO
	 * @return
	 */
	public Boolean useMemberCoupon(String companyId, String code, String remark, String openId,String hotelCode) {
		InterfaceConfig interfaceConfig = interfaceConfigService.getEnableInterfaceConfig(companyId);
		if(interfaceConfig != null) {
			JXDMemberService memberService = new JXDMemberService();
			MemberVO memberVO = memberService.getMemberByWeixinId(openId,hotelCode);
			if(memberVO != null) {
				return memberService.useMemberCoupon(memberVO.getProfileId(), code, null, remark,hotelCode);
			}
		}
		return false;
	}
	
	/**
	 * 返现
	 */
	public Boolean useMemberIncamount(String companyId,String profileId,String outletCode, Float amount,String hotelCode,String tradeNo,String remark){
		InterfaceConfig interfaceConfig = interfaceConfigService.getEnableInterfaceConfig(companyId);
		if(interfaceConfig != null) {
			if(StringUtils.equals("pms", interfaceConfig.getChannel())){
				JXDPmsMemberService jxdPmsMemberService = new JXDPmsMemberService();
				Map<String,String> map = jxdPmsMemberService.memberTrade(profileId, outletCode, "02", -amount, null, "客房使用返现", "WX", hotelCode,"02",interfaceConfig.getHost(),interfaceConfig.getSign());//？？？？？？？？？？？？？？;
				return Boolean.valueOf(map.get("isOk"));
			}else{
				JXDMemberService memberService = new JXDMemberService();
				return memberService.memberTrade(profileId, outletCode, "18", -amount, tradeNo, remark, "WX", hotelCode);
			}
		}
		return false;
	}
	
	public boolean memberTrade(String tradeNo, String openId, long tradeMoney, TradeType type,String optRemark,String subProfileId) {
		Member member = memberService.getMemberByOpendId(openId);
		if(member != null) {
			InterfaceConfig interfaceConfig = interfaceConfigService.getEnableInterfaceConfig(member.getCompanyId());
			if(interfaceConfig != null) {
				Company company = member.getCompany();
				String profileid = member.getProfileId();
				if(StringUtils.isNotBlank(subProfileId)){
					profileid = subProfileId;
				}
				
				if(StringUtils.equals("pms", interfaceConfig.getChannel())){
					return pmsMemberTrade(company.getOutletCode(),company.getCode(),tradeNo, tradeMoney, type, optRemark, profileid,interfaceConfig.getHost(),interfaceConfig.getSign());
				}
				
				float tm = 0;
				String tradeType = "11";
				if(TradeType.CHARGE.equals(type)) {
					tm = MoneyUtil.round(tradeMoney / 100f);
					tradeType = "11";   //充值
				} else if(TradeType.DEDUCT.equals(type)) {
					tm = MoneyUtil.round(-(tradeMoney / 100f));
					tradeType = "12";   //消费
				}
				
				if(StringUtils.isBlank(tradeNo)) {
					tradeNo = payOrderService.genPayOrderSn();
					if(TradeType.CHARGE.equals(type)) {
						tradeType = "24";   //积分
						tm = tradeMoney;
					} else if(TradeType.DEDUCT.equals(type)) {
						tradeType = "23";   //积分
						tm = -tradeMoney;
					}
				}
				
				JXDMemberService jxdMemberService = new JXDMemberService();
				return jxdMemberService.memberTrade(profileid, company.getOutletCode(), tradeType, tm, tradeNo, optRemark, "WX", company.getCode());
			}
		}
		return false;
	}
	
	public boolean pmsMemberTrade(String outletCode,String code,String tradeNo, long tradeMoney, TradeType type,String optRemark,String profileid,String url,String key) {
		float amount = 0;
		String paymentDesc = "01";//账户类型:01储值,02赠送金额,11积分,12业主分,31现金券,32项目券
		String balanceType = "01";//交易类型:01充值,02扣款,10获得积分,11使用积分,12调整积分,30获得券,31使用券

		if(StringUtils.isBlank(tradeNo)) {//积分
			if(TradeType.CHARGE.equals(type)) {//充值
				paymentDesc = "11";
				balanceType = "10";
				amount = tradeMoney;
			} else if(TradeType.DEDUCT.equals(type)) {
				paymentDesc = "11";
				balanceType = "11";
				amount = -tradeMoney;
			}
		}else{//金额
			if(TradeType.CHARGE.equals(type)) {//充值
				amount = MoneyUtil.round(tradeMoney / 100f);
			} else if(TradeType.DEDUCT.equals(type)) {
				balanceType = "02";
				amount = MoneyUtil.round(-(tradeMoney / 100f));
			}
		}
		JXDPmsMemberService jxdPmsMemberService = new JXDPmsMemberService();
		
		Map<String,String> map = jxdPmsMemberService.memberTrade(profileid, outletCode, paymentDesc, amount, tradeNo, optRemark, "WX", code,balanceType,url,key);
		return Boolean.valueOf(map.get("isOk"));
	}
	
	/**
	 * 查询积分兑换列表
	 * @param companyId
	 * @return
	 */
	public List<ExchangeGiftVO> findExchangeGiftVO(String companyId,String hotelCode) {
		InterfaceConfig interfaceConfig = interfaceConfigService.getEnableInterfaceConfig(companyId);
		if(interfaceConfig != null) {
			JXDMemberService memberService = new JXDMemberService();
			return memberService.queryExchangeGiftVOs(null, null,hotelCode);
		}
		return null;
	}
	
	public ExchangeGiftVO getExchangeGiftVO(String itemId,String hotelCode) {
		JXDMemberService jxdMemberService = new JXDMemberService();
		ExchangeGiftDetailQuery detailQuery = new ExchangeGiftDetailQuery();
		detailQuery.setItemId(itemId);
		return jxdMemberService.getExchangeGiftVO(detailQuery,hotelCode);
	}

	/**
	 * 积分兑换
	 * @param companyId
	 * @param query
	 * @return
	 */
	public boolean exchangeGift(String companyId, String openId,String contactAddressId, ExchangeGiftQuery query,String hotelCode) {
		InterfaceConfig interfaceConfig = interfaceConfigService.getEnableInterfaceConfig(companyId);
		boolean rs = false;
		if(interfaceConfig != null) {
			String itemId = query.getItemId();
			JXDMemberService jxdMemberService = new JXDMemberService();
			
			ExchangeGiftDetailQuery detailQuery = new ExchangeGiftDetailQuery();
			detailQuery.setItemId(itemId);
			ExchangeGiftVO exchangeGiftVO = jxdMemberService.getExchangeGiftVO(detailQuery,hotelCode);
			MemberVO memberVO = jxdMemberService.getMemberByWeixinId(openId,hotelCode);
			if(exchangeGiftVO != null && memberVO != null) {
				float canUseScore = getCanUseScoreByProfileId(companyId,memberVO.getProfileId(),hotelCode);
				if(canUseScore<exchangeGiftVO.getScore()){
					return false;
				}
				
				String getMode = query.getGetMode();
				if(StringUtils.equals(getMode, "1")) {
					ContactAddress contactAddress = memberService.findContactAddressById(contactAddressId);
					query.setGetterCName(contactAddress.getName());
					query.setGetterPhone(contactAddress.getMobile());
					query.setGetAddress(contactAddress.getAddr());
					query.setGetZipCode(contactAddress.getPostCode());
				}else{
					Member member = memberService.getMemberByOpendId(openId);
					query.setGetterCName(member.getName());
					query.setGetterPhone(member.getMobile());
				}
				query.setItemCName(exchangeGiftVO.getItemCName());
				query.setItemCode(exchangeGiftVO.getItemCode());
				query.setItemEName(exchangeGiftVO.getItemEName());
				query.setQuantity(1);
				query.setOutletCode(exchangeGiftVO.getOutletCode());
				query.setScore(exchangeGiftVO.getScore());
				query.setProfileId(memberVO.getProfileId());
				query.setMbrCardNo(memberVO.getMbrCardNo());
				query.setOpenId(openId);
				rs = jxdMemberService.exchangeGift(query,hotelCode);
			}
		}
		return rs;
	}
	
	/**
	 * 查询充值档次
	 * @param companyId
	 * @return
	 */
	public List<ChargeMoneyVO> getChargeMoneyVO(String companyId,String hotelCode, String openId) {
		InterfaceConfig interfaceConfig = interfaceConfigService.getEnableInterfaceConfig(companyId);
		Member member = memberService.getMemberByOpendId(openId);
		if(interfaceConfig != null) {
			JXDMemberService memberService = new JXDMemberService();
			ChargeMoneyQuery query = new ChargeMoneyQuery();
			query.setMbrCardType(member.getMbrCardTypeCode());
			return memberService.getChargeMoney(query,hotelCode);
		}
		return null;
	}
	
	public Float getCanUseScoreByProfileId(String companyId,String profileId,String hotelCode){
		InterfaceConfig interfaceConfig = interfaceConfigService.getEnableInterfaceConfig(companyId);
		Float canUseScore = null;
		if(interfaceConfig != null) {
			JXDMemberService memberService = new JXDMemberService();
			canUseScore = memberService.getCanUseScoreByProfileId(profileId,hotelCode);
		}
		return canUseScore;
	} 
	/**
	 * 发送优惠劵
	 */
	public String[] sendMemberCoupon(String openId,Company company, Integer qty,String categoryCode,Integer chargeamtmodel){
		InterfaceConfig interfaceConfig = interfaceConfigService.getEnableInterfaceConfig(company.getId());
		if(interfaceConfig != null) {
			JXDMemberService memberService = new JXDMemberService();
			
			Member member = this.memberService.getMemberByOpendId(openId);
			if(member !=null){
				if(StringUtils.isBlank(member.getProfileId())){
					MemberVO memberVO = memberService.getMemberByWeixinId(openId, company.getCode());
					if(memberVO!=null){
						member.setProfileId(memberVO.getProfileId());
						this.memberService.saveMember(member);
					}
				}
				
				if(StringUtils.isNotBlank(member.getProfileId())){
					return memberService.sendMemberCoupon(member, categoryCode, qty, null, "购买优惠劵",company.getCode(),chargeamtmodel);
				}
			}
		}
		return new String[]{"false","-1","未注册会员，不能发放优惠劵"};
	}
	
	/**
	 * 补发优惠劵
	 */
	public SendCouponRecord replaceSendMemberCoupon(Company company,String recordId) {
		InterfaceConfig interfaceConfig = interfaceConfigService.getEnableInterfaceConfig(company.getId());
		if(interfaceConfig != null) {
			JXDMemberService memberService = new JXDMemberService();
			SendCouponRecord record = couponTypeService.getSendCouponRecordById(recordId);
			MemberVO memberVO = memberService.getMemberByMobile(record.getMobile(), null, company.getCode());
			if(memberVO!=null){
				memberService.replaceSendMemberCoupon(memberVO, record);
				return record;
			}
		}
		return null;
	}

	/**
	 * 查询会员卡类型查询
	 * @param company
	 * @return
	 */
	public List<MbrCardTypeVO> findMbrCardTypeVO(Company company){
		List<MbrCardTypeVO> mbrCardTypes = new ArrayList<>();
		MbrCardTypeQuery query = new MbrCardTypeQuery();
		query.setWebSaleFlag("1");
		InterfaceConfig interfaceConfig = interfaceConfigService.getEnableInterfaceConfig(company.getId());
		if(interfaceConfig != null) {
			JXDMemberService memberService = new JXDMemberService();
			mbrCardTypes = memberService.findMbrCardTypeVO(query, company.getCode());
		}
		return mbrCardTypes;
	}
	
	/**
	 * 购买会员卡
	 * @return
	 */
	public String buyMbrCard(Member member,String mbrCardType,Integer quantity,Float amount,String pwd){
		BuyMbrCard buyMbrCard = new BuyMbrCard();
		Map<String,String> mbrCardSaleMap = new HashMap<>();
		mbrCardSaleMap.put("WXCode", member.getOpenId());
		mbrCardSaleMap.put("Mobile", member.getMobile());
		mbrCardSaleMap.put("MbrCardtype", mbrCardType);
		mbrCardSaleMap.put("Quantity", quantity.toString());
		mbrCardSaleMap.put("Amount", amount.toString());
		mbrCardSaleMap.put("PayCode", "微信支付");
		mbrCardSaleMap.put("PayPWD", pwd);
		buyMbrCard.setMbrCardSale(mbrCardSaleMap);
		InterfaceConfig interfaceConfig = interfaceConfigService.getEnableInterfaceConfig(member.getCompanyId());
		if(interfaceConfig != null) {
			JXDMemberService memberService = new JXDMemberService();
			try {
				GeneralMsg generalMsg = memberService.buyMbrCard(buyMbrCard, member.getCompany().getCode());
				if(!generalMsg.getIsSuccess()){//失败
					return generalMsg.getMessage();
				}
				return "";
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "接口列表为空";
	}
	
	/**
	 * 查询会员卡查询
	 * @param company
	 * @return
	 */
	public List<MbrCardVO> findMbrCardQuery(Company company,String profileId){
		List<MbrCardVO> mbrCards = null;
		MbrCardQuery mbrCardQuery = new MbrCardQuery();
		Map<String,String> mbrCardQueryMap = new HashMap<>();
		mbrCardQueryMap.put("ProfileId", profileId);
		mbrCardQuery.setMbrCardQuery(mbrCardQueryMap);
		InterfaceConfig interfaceConfig = interfaceConfigService.getEnableInterfaceConfig(company.getId());
		if(interfaceConfig != null) {
			JXDMemberService memberService = new JXDMemberService();
			mbrCards = memberService.findMbrCardQuery(mbrCardQuery, company.getCode());
		}
		return mbrCards==null? new ArrayList<MbrCardVO>():mbrCards;
	}
	
	/**
	 * 会员卡绑定
	 * @param member
	 * @param nbrCardNo
	 * @return
	 */
	public GeneralMsg mbrCardBinding(Member member,String profileId){
		MbrCardBinding mbrCardBinding = new MbrCardBinding();
		Map<String,String> mbrCardLinkMap = new HashMap<>();
		mbrCardLinkMap.put("ProfileId", profileId);
		mbrCardLinkMap.put("WXCode", member.getOpenId());
		mbrCardLinkMap.put("Mobile", member.getMobile());
		mbrCardLinkMap.put("PertainTo", "1");
		mbrCardBinding.setMbrCardLink(mbrCardLinkMap);
		InterfaceConfig interfaceConfig = interfaceConfigService.getEnableInterfaceConfig(member.getCompanyId());
		if(interfaceConfig != null) {
			JXDMemberService memberService = new JXDMemberService();
			return memberService.mbrCardBinding(mbrCardBinding, member.getCompany().getCode());
		}
		return null;
	}
	
	/**
	 * 会员卡查询
	 * @param query
	 * @param hotelCode
	 * @return code=Profileid cname=Mobile extraValue=MbrCardNo
	 * @throws Exception
	 */
	public CategoryCodeVO getMbrCardByNo(Company company,String mbrCardNo){
		CategoryCodeQuery query = new CategoryCodeQuery();
		query.setCategoryCode("MemberInfo:"+mbrCardNo);
		InterfaceConfig interfaceConfig = interfaceConfigService.getEnableInterfaceConfig(company.getId());
		if(interfaceConfig != null) {
			JXDMemberService memberService = new JXDMemberService();
			try {
				List<CategoryCodeVO> categoryCodes = memberService.getMbrCardByNo(query, company.getCode());
				if(categoryCodes!=null && categoryCodes.size()>0){
					return categoryCodes.get(0);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public MbrCardVO getMbrCardVO(Company company,String profileId,String mbrCardNo){
		List<MbrCardVO> mbrCards = findMbrCardQuery(company,profileId);
		for (MbrCardVO mbrCard : mbrCards) {
			if(StringUtils.equals(mbrCard.getMbrCardNo(), mbrCardNo)){
				return mbrCard;
			}
		}
		return null;
	}
	
	public MbrCardType getMbrCardType(String companyId,String code){
		Map<String, Serializable> properties = new HashMap<>();
		properties.put("companyId", companyId);
		properties.put("code", code);
		return mbrCardTypeDao.getByProperties(properties);
	}
	
	public void saveMbrCardType(MbrCardType mbrCardType){
		mbrCardTypeDao.save(mbrCardType);
	}
	
	public List<MbrCardType> findMbrCardType(String companyId){
		return mbrCardTypeDao.findByProperty("companyId", companyId);
	}
	
	
	/**
	 * 根据日期查询记录
	 */
	public SignInRecord getSignInRecordByDate(String signInId,String openId,Date date){
		Map<String, Serializable> properties = new HashMap<>();
		properties.put("openId", openId);
		properties.put("signInId", signInId);
		properties.put("createDate", DateUtil.format(date, "yyyy-MM-dd"));
		return signInRecordDao.getByProperties(properties);
	}
	
	/**
	 * 查询签到记录
	 */
	public List<SignInRecord> findSignInRecords(String signInId,String openId){
		Map<String, Serializable> properties = new HashMap<>();
		properties.put("openId", openId);
		properties.put("signInId", signInId);
		return signInRecordDao.findByProperties(properties);
	}
}
