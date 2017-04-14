package com.whotel.front.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.weixin.core.api.UserInfoApi;
import com.weixin.core.bean.FanInfo;
import com.weixin.core.common.BaseToken;
import com.whotel.common.dao.mongo.Page;
import com.whotel.company.entity.PublicNo;
import com.whotel.company.service.PublicNoService;
import com.whotel.front.dao.WeixinFanDao;
import com.whotel.front.entity.WeixinFan;
import com.whotel.weixin.service.TokenService;
import com.whotel.weixin.service.WeixinMessageService;

/**
 * 微信粉丝服务类
 * @author 冯勇
 *
 */
@Service
public class WeixinFanService {
	
	private final static Logger logger = Logger.getLogger(WeixinFanService.class);
	
	@Autowired
	private WeixinFanDao weixinFanDao;
	
	@Autowired
	private WeixinMessageService weixinMessageService;
	
	public void saveWeixinFan(WeixinFan weixinFan) {
		if(weixinFan != null) {
			String id = weixinFan.getId();
			if(StringUtils.isBlank(id)) {
				weixinFan.setCreateTime(new Date());
			}
			weixinFanDao.save(weixinFan);
		}
	}
	
	public void updateWeixinFan(FanInfo info, String devCode) {
		if (StringUtils.isBlank(info.getNickname())) {
			return;
		}
		WeixinFan fan = getWeixinFanByOpenId(info.getOpenid());
		if(fan == null) {
			fan = new WeixinFan();
		}
		fan.setDevCode(devCode);
		fan.setOpenId(info.getOpenid());
		fan.setUnionId(info.getUnionid());
		fan.setNickName(info.getNickname());
		fan.setAvatar(info.getHeadimgurl());
		fan.setProvince(info.getProvince());
		fan.setCity(info.getCity());
		fan.setSex(info.getSex());
		
		if(fan.getFocusGifts()==null||fan.getFocusGifts()==false){
			fan.setFocusGifts(true);
			weixinMessageService.sendFocusGiftsMessage(fan.getOpenId(),fan.getNickName());
		}
		saveWeixinFan(fan);
	}
	
	/**
	 * <pre>
	 * 此方法会确保根据openId产生WeixinFan对象，并根据isFocus来设置粉丝的关注状态；
	 * 如果没有与之关联的微信User， 则仅存在WeixinFan而无WeixinUser。
	 * @param openId 微信OpenId
	 * @param devCode 所属公众号的devCode
	 * @param isFocus 是否关注
	 * @return
	 */
	public WeixinFan updateFocus(String openId, String devCode, boolean isFocus) {
		WeixinFan fan = getWeixinFanByOpenId(openId);
		logger.info("fan :"+fan + ",  devCode:"+devCode+", isFocus:"+isFocus);
		if (fan == null) {
			// 粉丝记录不存在则补登
			fan = new WeixinFan();
			fan.setDevCode(devCode);
			fan.setOpenId(openId);
			fan.setFocus(isFocus);
			saveWeixinFan(fan);
			logger.info("Save: " + fan.toString());
		} else if (fan.isFocus() != isFocus) {
			fan.setFocus(isFocus);
			fan.setDevCode(devCode);
			saveWeixinFan(fan);
			logger.info("Save: " + fan.toString());
		}
		return fan;
	}

	public void deleteWeixinFan(String id) {
		if(StringUtils.isNotBlank(id)) {
			WeixinFan weixinFan = getWeixinFanById(id);
			if(weixinFan != null) {
				weixinFanDao.delete(weixinFan);
			}
		}
	}

	public void deleteWeixinFan(WeixinFan weixinFan) {
		if(weixinFan != null) {
			weixinFanDao.delete(weixinFan);
		}
	}
	
	public void deleteMoreWeixinFan(String[] ids) {
		if(ids != null) {
			for(String id:ids) {
				deleteWeixinFan(id);
			}
		}
	}

	public List<WeixinFan> findAllWeixinFans() {
		return weixinFanDao.findAll();
	}

	public void findWeixinFans(Page<WeixinFan> page) {
		weixinFanDao.find(page);
	}

	public WeixinFan getWeixinFanById(String id) {
		if(StringUtils.isNotBlank(id)) {
			return weixinFanDao.get(id);
		}
		return null;
	}
	
	public WeixinFan getWeixinFanByOpenId(String openId) {
		return weixinFanDao.getByProperty("openId", openId);
	}
	
	public void readUserMsg(PublicNo publicNo, WeixinFan fan){
		try {
			if (publicNo.isAuth()) {
				BaseToken token = TokenService.getTokenService().getAccessToken(publicNo.getAppId(), publicNo.getAppSecret());
				logger.info(" 使用API读取访问令牌！"+token);
				FanInfo info = UserInfoApi.getUserInfo(token, fan.getOpenId());
				logger.info(" 使用API读取用户资料！"+info);
				if (info.getErrcode() == null && info.getSubscribe() == 1) {
					updateWeixinFan(info, publicNo.getDeveloperCode(),fan);
				}
			}
		} catch (Exception e) {
			logger.error("Error", e);
		}
	}
	
	public void updateWeixinFan(FanInfo info, String devCode,WeixinFan fan) {
		if (StringUtils.isBlank(info.getNickname())) {
			return;
		}
		fan.setDevCode(devCode);
		fan.setOpenId(info.getOpenid());
		fan.setUnionId(info.getUnionid());
		fan.setNickName(info.getNickname());
		fan.setAvatar(info.getHeadimgurl());
		fan.setProvince(info.getProvince());
		fan.setCity(info.getCity());
		fan.setSex(info.getSex());
		fan.setFocusGifts(true);
		saveWeixinFan(fan);
	}
}
