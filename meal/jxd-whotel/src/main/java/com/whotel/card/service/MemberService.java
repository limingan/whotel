package com.whotel.card.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.mongodb.morphia.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import com.whotel.card.dao.ContactAddressDao;
import com.whotel.card.dao.GuestDao;
import com.whotel.card.dao.MarketingFanDao;
import com.whotel.card.dao.MemberDao;
import com.whotel.card.dao.MemberPolicyDao;
import com.whotel.card.dao.RecommendFanDao;
import com.whotel.card.entity.ContactAddress;
import com.whotel.card.entity.Guest;
import com.whotel.card.entity.MarketingFan;
import com.whotel.card.entity.Member;
import com.whotel.card.entity.MemberPolicy;
import com.whotel.card.entity.RecommendFan;
import com.whotel.card.entity.WinningMember;
import com.whotel.common.base.Constants;
import com.whotel.common.dao.mongo.Order;
import com.whotel.common.dao.mongo.Page;
import com.whotel.common.enums.Gender;
import com.whotel.common.enums.TradeType;
import com.whotel.common.util.BeanUtil;
import com.whotel.common.util.DateUtil;
import com.whotel.company.entity.Company;
import com.whotel.company.entity.InterfaceConfig;
import com.whotel.company.entity.SmsConfig;
import com.whotel.company.service.CompanyService;
import com.whotel.company.service.InterfaceConfigService;
import com.whotel.company.service.SmsConfigService;
import com.whotel.ext.redis.RedisCache;
import com.whotel.system.entity.SysOperationLog;
import com.whotel.system.entity.SysSmsLog;
import com.whotel.system.service.SystemLogService;
import com.whotel.thirdparty.jxd.api.JXDMemberService;
import com.whotel.thirdparty.jxd.api.JXDPmsMemberService;
import com.whotel.thirdparty.jxd.api.JXDSmsUtil;
import com.whotel.thirdparty.jxd.mode.MbrCardUpgrade;
import com.whotel.thirdparty.jxd.mode.MbrCardUpgradeQuery;
import com.whotel.thirdparty.jxd.mode.vo.FollowPolicyVO;
import com.whotel.thirdparty.jxd.mode.vo.MbrCardUpgradeResult;
import com.whotel.thirdparty.jxd.mode.vo.MbrCardUpgradeVO;
import com.whotel.thirdparty.jxd.mode.vo.MemberTypeVO;
import com.whotel.thirdparty.jxd.mode.vo.MemberVO;

import net.sf.json.JSONArray;

/**
 * 平台会员数据管理服务类
 *
 * @author 冯勇
 */
@Service
public class MemberService {

    private static final Logger logger = Logger.getLogger(MemberService.class);

    @Autowired
    private MemberDao memberDao;

    @Autowired
    private ContactAddressDao contactAddressDao;

    @Autowired
    private GuestDao guestDao;

    @Autowired
    private MarketingFanDao marketingFanDao;

    @Autowired
    private RecommendFanDao recommendFanDao;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private InterfaceConfigService interfaceConfigService;

    @Autowired
    private SmsConfigService smsConfigService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private MemberTradeService memberTradeService;

    @Autowired
    private SystemLogService systemLogService;

    @Autowired
    private MemberPolicyDao memberPolicyDao;

    @Autowired
    private TaskExecutor taskExecutor;

    /**
     * 同步会员到会员系统
     *
     * @param member
     * @return
     */
    public MemberVO synchronizeMemberToJXD(Member member, String action) {
        MemberVO memberVO = new MemberVO();
        if (member != null && StringUtils.isNotBlank(member.getOldMobile())) {
            InterfaceConfig interfaceConfig = interfaceConfigService.getEnableInterfaceConfig(member.getCompanyId());

            if (interfaceConfig != null) {
                JXDMemberService memberService = new JXDMemberService();
                String hotelCode = member.getCompany().getCode();
                MemberVO jxdMember = null;
                if (StringUtils.equals(interfaceConfig.getChannel(), "pms")) {
                    JXDPmsMemberService jxdPmsMemberService = new JXDPmsMemberService();
                    jxdMember = jxdPmsMemberService.getMemberByMobile(member.getOldMobile(), hotelCode, interfaceConfig.getHost(), interfaceConfig.getSign());
                } else {
                    jxdMember = getMemberVOByMobile(member.getCompanyId(), member.getOpenId(), member.getOldMobile(), hotelCode);
                }

                if (jxdMember == null || StringUtils.isBlank(jxdMember.getProfileId())) {
//					memberVO.setPwd(StringUtils.substring(member.getMobile(), 5));
                } else {
                    memberVO.setProfileId(jxdMember.getProfileId());
                }

                String name = member.getName();
                String mobile = member.getMobile();
                Gender gender = member.getGender();
                String email = member.getEmail();
                String addr = member.getAddr();
                String weixinId = member.getOpenId();
                String certificateNo = member.getIDCard();

//				RecommendFan recommendFan = marketingFanService.getRecommendFanByOpendId(weixinId);
//				MarketingFan marketingFan = null;
//				if(recommendFan!=null){
//					marketingFan = marketingFanService.getMarketingFanByOpendId(recommendFan.getRecommendOpenId());
//					if(marketingFan != null){
//						Member marketingMember = getMemberByOpendId(marketingFan.getOpenId());
//						memberVO.setIntroducer(marketingMember.getProfileId());
//					}
//				}

                if (StringUtils.isBlank(name)) {
                    name = mobile;
                }
                memberVO.setGuestCName(name);
                memberVO.setMobile(mobile);
                memberVO.setWeixinId(weixinId);
                if (gender != null) {
                    memberVO.setGender(gender);
                }
                if (StringUtils.isNotBlank(email)) {
                    memberVO.setEmail(email);
                }
                if (StringUtils.isNotBlank(addr)) {
                    memberVO.setAddress(addr);
                }
                if (StringUtils.isNotBlank(certificateNo)) {
                    memberVO.setCertificateNo(certificateNo);
                }

                memberVO.setIsSendMsg(member.getIsSendMsg());

                if (StringUtils.equals(interfaceConfig.getChannel(), "pms")) {
                    JXDPmsMemberService jxdPmsMemberService = new JXDPmsMemberService();
                    memberVO = jxdPmsMemberService.registerMember(memberVO, member.getCompany(), action, interfaceConfig.getHost(), interfaceConfig.getSign());
                } else {
                    memberVO = memberService.registerMember(memberVO, member.getCompany(), action);
                }

                boolean isAdd = false;
                if (memberVO != null) {
                    member.setProfileId(memberVO.getProfileId());
                    isAdd = StringUtils.isBlank(member.getId());
                    member = saveMember(member);
                    handleMemberPolicy(member.getId());
                }

                if (isAdd && memberVO != null) {

                    //初次注册调用e运通营销规则，赠送礼品
                    if (!StringUtils.equals(interfaceConfig.getChannel(), "pms")) {
                        List<FollowPolicyVO> vos = memberService.followPolicyQyery(interfaceConfig.getHost());
                        if (vos != null) {
                            for (FollowPolicyVO vo : vos) {
                                if (StringUtils.equals("0", vo.getPolicyRule())) {//返现
                                    memberService.memberTrade(memberVO.getProfileId(), member.getCompany().getOutletCode(), "18", vo.getReturnAmt(), null, "初次关注礼品", "WX", member.getCompany().getCode());
                                } else if (StringUtils.equals("1", vo.getPolicyRule())) {//优惠券
                                    memberService.sendMemberCoupon(member, vo.getTicketTypeCode(), vo.getTicketCount(), null, "初次关注礼品", member.getCompany().getCode(), 1);//***************************************************
                                } else if (StringUtils.equals("2", vo.getPolicyRule())) {//积分
                                    memberTradeService.memberTrade(null, member.getOpenId(), vo.getReturnAmt().longValue(), TradeType.CHARGE, "初次关注礼品", null);
                                }
                            }
                        }
                    }

//					List<WinningMember> wms = activityService.findWinningMember(member.getOpenId());
//					if(wms!=null){
//						for (WinningMember wm : wms) {
//							activityService.updateWinningMember(member.getId(), wm.getId());
//						}
//					}
//					
////					RecommendFan recommendFan = recommendFanDao.getByProperty("openId", member.getOpenId());
//					if(recommendFan!=null){
//						recommendFan.setIsMember(true);
//						recommendFanDao.save(recommendFan);
////						MarketingFan marketingFan = marketingFanDao.getByProperty("openId", recommendFan.getRecommendOpenId());
//						if(marketingFan!=null){
//							marketingFanDao.increaseMemberFanNums(marketingFan.getOpenId(),1);
//						}
//					}
                }
            }
        }
        return memberVO;
    }

    public List<FollowPolicyVO> followPolicyQyery(Member member) {
        InterfaceConfig interfaceConfig = interfaceConfigService.getEnableInterfaceConfig(member.getCompanyId());
        List<FollowPolicyVO> vos = null;
        if (interfaceConfig != null) {
            JXDMemberService memberService = new JXDMemberService();
            vos = memberService.followPolicyQyery(interfaceConfig.getHost());
        }
        return vos == null ? new ArrayList<FollowPolicyVO>() : vos;
    }

    /**
     * 通过手机号获取酒店系统会员
     *
     * @param companyId
     * @param mobile
     * @return
     */
    public MemberVO getMemberVOByMobile(String companyId, String openId, String mobile, String hotelCode) {
        MemberVO memberVO = null;
        if (StringUtils.isNotBlank(openId)) {
            memberVO = getMemberVOByOpenId(companyId, openId, hotelCode);
        }
        if (memberVO == null) {
            InterfaceConfig interfaceConfig = interfaceConfigService.getEnableInterfaceConfig(companyId);

            if (interfaceConfig != null) {
                JXDMemberService memberService = new JXDMemberService();
                memberVO = memberService.getMemberByMobile(mobile, null, hotelCode);
            }
        }
        return memberVO;
    }

    /**
     * 通过微信号获取酒店系统会员
     *
     * @param companyId
     * @param openId
     * @return
     */
    public MemberVO getMemberVOByOpenId(String companyId, String openId, String hotelCode) {
        InterfaceConfig interfaceConfig = interfaceConfigService.getEnableInterfaceConfig(companyId);
        MemberVO memberVO = null;
        if (interfaceConfig != null) {
            JXDMemberService memberService = new JXDMemberService();
            memberVO = memberService.getMemberByWeixinId(openId, hotelCode);
        }
        if (memberVO != null) {
            Member member = memberDao.getByProperty("openId", openId);
            if (member == null) {
                member = new Member();
                member.setName(memberVO.getGuestCName());
                member.setCompanyId(companyId);
                member.setCreateTime(new Date());
                member.setGender(memberVO.getGender());
                member.setMobile(memberVO.getMobile());
                member.setOpenId(openId);
            }
            member.setProfileId(memberVO.getProfileId());
            member.setMbrCardNo(memberVO.getMbrCardNo());
            member.setMbrCardTypeCode(memberVO.getMbrCardType());
            member.setSaleName(memberVO.getSaleName());
            member.setIntroducer(memberVO.getIntroducer());
            member.setIntroducerName(memberVO.getIntroducerName());
            member.setMbrCardTypeName(memberVO.getMbrCardTypeName());
            saveMember(member);
        }
        return memberVO;
    }

    public MemberVO getQuickMemberVOByOpenId(String companyId, String openId, String hotelCode) {
        InterfaceConfig interfaceConfig = interfaceConfigService.getEnableInterfaceConfig(companyId);
        MemberVO memberVO = null;
        if (interfaceConfig != null) {
            JXDMemberService memberService = new JXDMemberService();
            memberVO = memberService.getQuickMemberByWeixinId(openId, hotelCode);
            if (memberVO == null) {
                memberVO = memberService.getMemberByWeixinId(openId, hotelCode);
            }
        }
        if (memberVO != null) {
            Member member = memberDao.getByProperty("openId", openId);
            if (member != null) {
                member.setProfileId(memberVO.getProfileId());
                member.setMbrCardNo(memberVO.getMbrCardNo());
                member.setMbrCardTypeCode(memberVO.getMbrCardType());
                member.setSaleName(memberVO.getSaleName());
                member.setMbrCardTypeName(memberVO.getMbrCardTypeName());
                saveMember(member);
            }
        }
        return memberVO;
    }

    /**
     * 通过会员id获取酒店系统会员
     *
     * @param companyId
     * @param openId
     * @return
     */
    public MemberVO getMemberByProfileId(String companyId, String profileId, String hotelCode) {
        InterfaceConfig interfaceConfig = interfaceConfigService.getEnableInterfaceConfig(companyId);
        MemberVO memberVO = null;
        if (interfaceConfig != null) {
            JXDMemberService memberService = new JXDMemberService();
            memberVO = memberService.getMemberByProfileId(profileId, hotelCode);
        }
        return memberVO;
    }

    /**
     * 保存会员
     *
     * @param member
     */
    public Member saveMemberAndSyncToJXD(Member member, String action) {
        if (member != null) {
//			saveMember(member);
            MemberVO memberVO = synchronizeMemberToJXD(member, action);
            if (memberVO == null) {
                for (int i = 0; i < 2; i++) {
                    memberVO = synchronizeMemberToJXD(member, action);
                }
            }
            return getMemberByOpendId(member.getOpenId());
        }
        return null;
    }

    public Member saveMember(Member member) {
        String id = member.getId();
        String openId = member.getOpenId();
        if (StringUtils.isNotBlank(id)) {
            Member updateMember = getMemberById(id);
            BeanUtil.copyProperties(updateMember, member, true);
            member = updateMember;
        } else {
            if (StringUtils.isNotBlank(openId)) {
                Member updateMember = getMemberByOpendId(openId);
                if (updateMember != null) {
                    BeanUtil.copyProperties(updateMember, member, true);
                    member = updateMember;
                } else {
                    member.setCreateTime(new Date());
                }
            }
        }
        memberDao.save(member);
        return member;
    }

    public void handleMemberPolicy(final String memberId) {
        taskExecutor.execute(new Runnable() {
            @Override
            public void run() {
                Member member = memberDao.get(memberId);
                if (StringUtils.isNotBlank(member.getName())
                        && member.getGender() != null
                        && StringUtils.isNotBlank(member.getMobile())
                        && StringUtils.isNotBlank(member.getIDCard())
                        && StringUtils.isNotBlank(member.getEmail())
                        && StringUtils.isNotBlank(member.getAddr())
                        && StringUtils.isNotBlank(member.getName())) {
                    MemberPolicy memberPolicy = getMemberPolicy(member.getCompanyId());
                    if (memberPolicy.getScore() != null && memberPolicy.getScore() > 0 && (member.getMemberPolicyScore() == null || member.getMemberPolicyScore() == 0)) {
                        member.setMemberPolicyScore(memberPolicy.getScore());
                        member.setUpdateTime(new Date());
                        memberTradeService.memberTrade(null, member.getOpenId(), memberPolicy.getScore(), TradeType.CHARGE, "完善会员资料送积分", null);
                        memberDao.save(member);
                    }
                }
            }
        });
    }

    /**
     * 分页查找会员
     *
     * @param page 分页工具类
     */
    public void findMembers(Page<Member> page) {
        memberDao.find(page);
    }

    public List<Member> findAllMembers() {
        return memberDao.findAll();
    }

    public List<Member> findMembersByCompanyId(String companyId) {
        return memberDao.findByProperty("companyId", companyId, Order.desc("createTime"));
    }

    public List<Member> findMembersByCompanyIdAll(String companyId,
                                                  Date startDate, Date endDate) {
        Query<Member> query = memberDao.createQuery();
        query.field("companyId").equal(companyId);

        if (startDate != null) {
            query.field("createTime").greaterThanOrEq(startDate);
        }
        if (endDate != null) {
            query.field("createTime").lessThanOrEq(endDate);
        }

        return memberDao.findByQuery(query).asList();
    }

    public List<Member> findMemberByMobile(String mobile, String companyId, Date beginDate, Date endDate) {
        Query<Member> query = memberDao.createQuery();
        query.field("companyId").equal(companyId);
        if (StringUtils.isNotBlank(mobile)) {
            query.field("mobile").contains(mobile);
        }
        if (beginDate != null) {
            query.field("createTime").greaterThanOrEq(DateUtil.getStartTime(beginDate));
        }
        if (endDate != null) {
            query.field("createTime").lessThanOrEq(DateUtil.getEndTime(endDate));
        }
        query.order("-createTime");
        return memberDao.findByQuery(query).asList();
    }

    public Member getMemberById(String id) {
        if (StringUtils.isBlank(id)) {
            return null;
        }
        return memberDao.get(id);
    }

    public Member getMemberByOpendId(String openId) {
        if (StringUtils.isBlank(openId)) {
            return null;
        }
        Member member = memberDao.getByProperty("openId", openId);
        if (member != null && StringUtils.isBlank(member.getProfileId())) {
            MemberVO memberVO = getMemberVOByOpenId(member.getCompanyId(), member.getOpenId(), member.getCompany().getCode());
            if (memberVO != null) {
                member.setProfileId(memberVO.getProfileId());
                saveMember(member);
            }
        }
        return member;
    }

    public Member getMemberByOpendId(String companyId, String openId) {
        if (StringUtils.isBlank(openId)) {
            return null;
        }
        Map<String, Serializable> properties = new HashMap<>();
        properties.put("openId", openId);
        properties.put("companyId", companyId);
        return memberDao.getByProperties(properties);
    }

    public Member getMemberByMobile(String mobile, String companyId) {
        if (StringUtils.isBlank(mobile)) {
            return null;
        }
        Map<String, Serializable> properties = new HashMap<>();
        properties.put("mobile", mobile);
        properties.put("companyId", companyId);
        return memberDao.getByProperties(properties);
    }

    public void deleteMember(String id) {
        memberDao.delete(id);
    }

    public void deleteMember(Member member) {
        memberDao.delete(member);
    }

    public void deleteMember(String id, String adminId) {
        Member member = getMemberById(id);
        memberDao.delete(member);
        systemLogService.saveSysOperationLog(new SysOperationLog(member.getCompanyId(), adminId, "SYSTEM", "delete", JSONArray.fromObject(member).toString(), "商户会员：" + member.getName()));

    }

    /**
     * 批量删除会员
     *
     * @param ids
     */
    public void deleteMoreMember(String ids, String companyId) {
        if (StringUtils.isNotBlank(ids)) {
            String[] split = ids.split(",");
            for (String id : split) {
                Member member = getMemberById(id);
                member.setCompanyId(companyId);
                deleteMember(member);
            }
        }
    }

    public Boolean checkMobileVerifyCode(String mobile, String verifyCode) {

        // 手机验证码
        String key = Constants.MOBILE_VERIFY + mobile;
        String yzm = (String) redisCache.get(key);
        if (!StringUtils.equals(yzm, verifyCode)) {
            return false;
        } else {
            redisCache.evict(key);
        }
        return true;
    }

    public List<ContactAddress> findContactAddressByOpendId(String openId) {
        if (StringUtils.isBlank(openId)) {
            return null;
        }
        return contactAddressDao.findByProperty("openId", openId);
    }

    public ContactAddress findContactAddressById(String id) {
        return contactAddressDao.get(id);
    }

    public ContactAddress getDefContactAddress(String openId) {
        Map<String, Serializable> properties = new HashMap<String, Serializable>();
        properties.put("openId", openId);
        properties.put("def", true);
        return contactAddressDao.getByProperties(properties);
    }

    public void updateContactAddress(ContactAddress contactAddress) {
        contactAddressDao.save(contactAddress);
    }

    public void deleteContactAddress(String id) {
        contactAddressDao.delete(id);
    }

    public void saveGuest(Guest guest) {
        String id = guest.getId();
        if (StringUtils.isNotBlank(id)) {
            Guest updateGuest = getGuestById(id);
            BeanUtil.copyProperties(updateGuest, guest, true);
            guest = updateGuest;
        } else {
            guest.setCreateTime(new Date());
        }
        guestDao.save(guest);
    }


    public Guest getGuestById(String id) {
        if (StringUtils.isBlank(id)) {
            return null;
        }
        return guestDao.get(id);
    }

    public List<Guest> findGuestByOpendId(String openId) {
        if (StringUtils.isBlank(openId)) {
            return null;
        }
        return guestDao.findByProperty("openId", openId);
    }

    /**
     * 发送捷信达验证码短信
     *
     * @param companyId
     * @param content
     * @param time
     * @param mobile
     * @return
     */
    public boolean sendJxdSms(String companyId, String content, String time, String mobile) {

        SmsConfig smsConfig = smsConfigService.getSmsConfig(companyId);
        if (smsConfig != null) {
            Company company = companyService.getCompanyById(companyId);

            String sendConent = "【" + company.getName() + "】您的验证码是" + content + "，有效时长" + time + "分钟";
            logger.info("短信内容：" + sendConent);
            long sendRs = JXDSmsUtil.send(smsConfig.getAccount(), smsConfig.getPwd(), mobile, sendConent);
            systemLogService.saveSysSmsLog(new SysSmsLog(companyId, mobile, sendRs));
            if (sendRs > 0) {
                int surplus = JXDSmsUtil.surplus(smsConfig.getAccount(), smsConfig.getPwd());

                Integer alarmNum = smsConfig.getAlarmNum();
                if (alarmNum == null) {
                    alarmNum = 500;
                }
                if (surplus <= alarmNum && StringUtils.isNotBlank(smsConfig.getMobiles())) {
                    sendConent = "【" + company.getName() + "】您的短信条数还剩余" + surplus + "条，请及时联系供应商充值，以免影响短信正常发送";
                    sendRs = JXDSmsUtil.send(smsConfig.getAccount(), smsConfig.getPwd(), smsConfig.getMobiles(), sendConent);
                    if (sendRs > 0) {
                        surplus--;
                    }
                }
                smsConfig.setBalance(surplus);
                smsConfigService.saveSmsConfig(smsConfig);
                return true;
            }
        }
        return false;
    }

    public void deleteGuest(String id) {
        guestDao.delete(id);
    }

    /**
     * 会员卡升级列表
     */
    public List<MbrCardUpgradeVO> listMbrCardUpgrade(String companyId, String hotelCode, MbrCardUpgradeQuery query) {
        InterfaceConfig interfaceConfig = interfaceConfigService.getEnableInterfaceConfig(companyId);
        if (interfaceConfig != null) {
            try {
                Map<String, String> map = getMemberTypeMap(companyId, hotelCode);
                JXDMemberService memberService = new JXDMemberService();
                List<MbrCardUpgradeVO> mvos = memberService.listMbrCardUpgrade(query, hotelCode);
                if (mvos != null) {
                    for (MbrCardUpgradeVO mvo : mvos) {
                        mvo.setRemark(map.get(mvo.getMbrCardTypeCode()));//+"_"+mvo.getMbrCardTypeCname()
                    }
                }
                return mvos;
            } catch (Exception e) {
                logger.error("会员卡升级列表接口出错！");
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 会员卡升级
     */
    public boolean mbrCardUpgrade(String companyId, String hotelCode, MbrCardUpgrade query) {
        InterfaceConfig interfaceConfig = interfaceConfigService.getEnableInterfaceConfig(companyId);
        if (interfaceConfig != null) {
            try {
                JXDMemberService memberService = new JXDMemberService();
                MbrCardUpgradeResult result = memberService.mbrCardUpgrade(query, hotelCode);
                if (result.getRtnCode().equals("0")) {
                    return true;
                }
            } catch (Exception e) {
                logger.error("会员卡升级接口出错！");
                e.printStackTrace();
            }
        }
        return false;
    }

    public Map<String, String> getMemberTypeMap(String companyId, String hotelCode) {
        Map<String, String> map = new HashMap<>();
        JXDMemberService memberService = new JXDMemberService();
        List<MemberTypeVO> types = memberService.queryMemberTypes(hotelCode);
        if (types != null) {
            for (MemberTypeVO type : types) {
                map.put(type.getCode(), type.getRemark());//+"_"+type.getName()
            }
        }
        return map;
    }

    /**
     * 查询会员政策
     */
    public MemberPolicy getMemberPolicy(String companyId) {
        MemberPolicy memberPolicy = memberPolicyDao.getByProperty("companyId", companyId);
        if (memberPolicy == null) {
            memberPolicy = new MemberPolicy();
        }
        return memberPolicy;
    }

    /**
     * 保存会员政策
     */
    public void saveMemberPolicy(MemberPolicy memberPolicy) {
        if (StringUtils.isNotBlank(memberPolicy.getId())) {
            MemberPolicy oldMemberPolicy = memberPolicyDao.get(memberPolicy.getId());
            BeanUtil.copyProperties(oldMemberPolicy, memberPolicy, true);
            memberPolicy = oldMemberPolicy;
        } else {
            memberPolicy.setCreateTime(new Date());
        }
        memberPolicyDao.save(memberPolicy);
    }

    /**
     * 检查公司代码为HYYLJT 公司的注册资料是否合法
     * 华义裕林度假村注册会员必须在线下存在会员才能在线上注册
     */
    public boolean checkRegisterInfoOfHyyljt(Member member) {
        JXDMemberService memberService = new JXDMemberService();
        MemberVO memberVO = memberService.getMemberByMobile(member.getMobile(), null, "hyyljt");//
        if (memberVO != null) {
            if (StringUtils.equals(memberVO.getMbrCardNo(), member.getMbrCardNo())) {
                member.setName(memberVO.getGuestCName());
                return true;
            }
        }
        return false;
    }

    public Member getByOpendId(String openId) {
        if (StringUtils.isBlank(openId)) {
            return null;
        }
        Member member = memberDao.getByProperty("openId", openId);
        return member;
    }

}
