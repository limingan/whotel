package com.whotel.weixin.service;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.mongodb.morphia.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import com.weixin.core.api.SceneQrcodeCreator;
import com.weixin.core.bean.EventMsg;
import com.weixin.core.bean.WeixinMsg;
import com.weixin.core.enums.QrActionType;
import com.whotel.company.entity.PublicNo;
import com.whotel.company.service.PublicNoService;
import com.whotel.hotel.dao.CheckInRecordDao;
import com.whotel.hotel.dao.SceneQrcodeDao;
import com.whotel.hotel.entity.SceneQrcode;
import com.whotel.hotel.service.UnlockService;

/**
 * 场景处理类
 * @author 冯勇
 *
 */
@Service 
public class SceneService {
	
	@Autowired
	private TaskExecutor taskExecutor;
	
	@Autowired
	private SceneQrcodeDao sceneQrcodeDao;
	
	@Autowired
	private PublicNoService publicNoService;
	
	@Autowired
	private CheckInRecordDao checkInRecordDao;
	
	@Autowired
	private UnlockService unlockService;
	
	/**
	 * 处理相应场景值
	 * @param event
	 * @param respMsg
	 * @return
	 */
	public WeixinMsg handleScene(EventMsg event, WeixinMsg respMsg) {
		
		if(event != null) {
			final String ticket = event.getTicket();
			final String openId = event.getFromUserName();
			final String devCode = event.getToUserName();
		}
		return respMsg;
	}
	
	public String getSceneVal(String scene) {
		if(StringUtils.isNotBlank(scene) && StringUtils.startsWith(scene, "qrscene_")) {
			return StringUtils.substringAfter(scene, "qrscene_");
		}
		return scene;
	}
	
	public void saveSceneQrcode(SceneQrcode sceneQrcode){
		if(StringUtils.isBlank(sceneQrcode.getId())){
			sceneQrcode.setCreateDate(new Date());
		}
		sceneQrcodeDao.save(sceneQrcode);
	}
	
	public void findSceneQrcodeByTicket(String ticket){
		sceneQrcodeDao.getByProperty("ticket", ticket);
	}
	
	/**
	 * 生成带参数的二维码
	 */
	public SceneQrcode createSceneQrcode(String companyId, QrActionType type,Integer time){
		PublicNo publicNo = publicNoService.getPublicNoByCompanyId(companyId);
		SceneQrcode sceneQrcode = new SceneQrcode();
		sceneQrcode.setCompanyId(companyId);
		sceneQrcode.setSceneId(getSceneId(companyId));
		sceneQrcode.setType(type);
		sceneQrcode.setTime(time);
		String ticket = SceneQrcodeCreator.create(publicNo.getAppId(), publicNo.getAppSecret(), type, sceneQrcode.getSceneId().toString(),time);
		sceneQrcode.setTicket(ticket);
		sceneQrcode.setQrUrl(SceneQrcodeCreator.getQrUrl(ticket));
		saveSceneQrcode(sceneQrcode);
		return sceneQrcode;
	}
	
	public synchronized Integer getSceneId(String companyId){
		Query<SceneQrcode> query = sceneQrcodeDao.createQuery();
		query.field("companyId").equal(companyId);
		query.order("-sceneId");
		SceneQrcode sceneQrcode = query.get();
		int sceneId = 100;
		if(sceneQrcode != null) {
			sceneId = sceneQrcode.getSceneId() + 1;
		}
		return sceneId;
	}
}
