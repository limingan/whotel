package com.whotel.hotel.service;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import com.weixin.core.common.BaseToken;
import com.weixin.core.enums.QrActionType;
import com.whotel.common.dao.mongo.Order;
import com.whotel.common.http.HttpHelper;
import com.whotel.common.http.HttpHelper.Response;
import com.whotel.common.util.DataPackUtil;
import com.whotel.common.util.DateUtil;
import com.whotel.common.util.MD5Util;
import com.whotel.company.entity.Company;
import com.whotel.company.entity.InterfaceConfig;
import com.whotel.company.entity.PublicNo;
import com.whotel.company.service.CompanyService;
import com.whotel.company.service.InterfaceConfigService;
import com.whotel.company.service.PublicNoService;
import com.whotel.hotel.dao.CheckInRecordDao;
import com.whotel.hotel.entity.CheckInRecord;
import com.whotel.hotel.entity.SceneQrcode;
import com.whotel.thirdparty.jxd.api.JXDPmsOrderService;
import com.whotel.thirdparty.jxd.mode.vo.HotelOrderDetailVO;
import com.whotel.thirdparty.jxd.mode.vo.OccupancyManVO;
import com.whotel.weixin.service.SceneService;
import com.whotel.weixin.service.TokenService;
import com.whotel.weixin.service.WeixinInterfaceService;

import net.sf.json.JSONObject;

/**
 * 酒店客房开锁
 * @author 柯鹏程
 */
@Service
public class UnlockService {
	
	private static final Logger logger = Logger.getLogger(UnlockService.class);
	
	private static final String BIND_URL = "https://api.weixin.qq.com/device/compel_bind?access_token=${ACCESS_TOKEN}";
	
	private static final String UNBIND_URL = "https://api.weixin.qq.com/device/compel_unbind?access_token=${ACCESS_TOKEN}";
	
	private static final String TRANSMSG_URL = "https://api.weixin.qq.com/device/transmsg?access_token=${ACCESS_TOKEN}";
	
	@Autowired
	private PublicNoService publicNoService;
	
	@Autowired
	private SceneService sceneService;
	
	@Autowired
	private CheckInRecordDao checkInRecordDao;
	
	@Autowired
	private InterfaceConfigService interfaceConfigService;
	
	@Autowired
	private WeixinInterfaceService weixinInterfaceService;
	
	@Autowired
	private TaskExecutor taskExecutor;
	
	@Autowired
	private CompanyService companyService;
	
	public String compelBind(String checkInRecordId,String openId,String ticket){
		logger.info("compelBind====checkInRecordId="+checkInRecordId+" openId"+openId+" ticket"+ticket);
		CheckInRecord checkInRecord = checkInRecordDao.get(checkInRecordId);
		HotelOrderDetailVO orderDetail = getOrderDetailVO(checkInRecord.getCompanyId(), checkInRecord.getOrderNo());
		if(orderDetail!=null && StringUtils.equals(orderDetail.getStatus(), "在住")){
			
			try {
				//获取开锁的动态秘钥
				PublicNo publicNo = publicNoService.getPublicNoByCompanyId(checkInRecord.getCompanyId());
				InterfaceConfig interfaceConfig = interfaceConfigService.getEnableInterfaceConfig(publicNo.getCompanyId());
				JSONObject json = getContent(publicNo.getAppId(), publicNo.getAppSecret(), orderDetail.getLockId(), orderDetail.getArriveDate(), orderDetail.getLeaveDate(), checkInRecord.getMobile(),interfaceConfig.getUnlock());
				String content = json.get("keyData").toString();//秘钥
				String deviceId = json.get("deviceId").toString();//设备号
				
				Map<String,String> param = new HashMap<>();
				if(StringUtils.isNotBlank(ticket)){
					param.put("ticket", ticket);
				}
				param.put("device_id", deviceId);
				param.put("openid", openId);
			
				BaseToken accessToken = TokenService.getTokenService().getAccessToken(publicNo.getAppId(), publicNo.getAppSecret());
				String url = BIND_URL.replace("${ACCESS_TOKEN}", accessToken.getAccess_token());
				String data = JSONObject.fromObject(param).toString();
				logger.info("param====="+param);
				Response res = HttpHelper.connect(url).post(data);
				String html = res.html();
				logger.info(html);
				String errmsg = JSONObject.fromObject(html).getJSONObject("base_resp").get("errmsg").toString();
				if(StringUtils.equals(errmsg, "ok")){
					checkInRecord.setDeviceId(deviceId);
					checkInRecord.setContent(content);
					checkInRecord.setOpenId(openId);
					checkInRecordDao.save(checkInRecord);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			return "您还未入住";
		}
		
		return "ok";
	}
	
	public Boolean compelUnbind(String appId,String appSecret,String deviceId,String openId){
		Map<String,String> param = new HashMap<>();
		param.put("device_id", deviceId);
		param.put("openid", openId);
		
		try {
			BaseToken accessToken = TokenService.getTokenService().getAccessToken(appId, appSecret);
			String url = UNBIND_URL.replace("${ACCESS_TOKEN}", accessToken.getAccess_token());
			String data = JSONObject.fromObject(param).toString();
			logger.info("param====="+param);
			Response res = HttpHelper.connect(url).post(data);
			String html = res.html();
			logger.info(html);
			String errmsg = JSONObject.fromObject(html).getJSONObject("base_resp").get("errmsg").toString();
			if(StringUtils.equals(errmsg, "ok")){
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public String transmsg(String appId,String appSecret,String developerCode,String deviceId,String openId,String content){
		Map<String,String> param = new HashMap<>();
		param.put("device_type", developerCode);
		param.put("device_id", deviceId);
		param.put("open_id", openId);
		param.put("content", content);
		
		for (int i = 0; i < 2; i++) {
			try {
				BaseToken accessToken = TokenService.getTokenService().getAccessToken(appId, appSecret);
				String url = TRANSMSG_URL.replace("${ACCESS_TOKEN}", accessToken.getAccess_token());
				String data = JSONObject.fromObject(param).toString();
				logger.info("transmsg==param====="+param);
				logger.info("执行开锁");
				Response res = HttpHelper.connect(url).post(data);
				String html = res.html();
				logger.info(html);
				
				JSONObject json = JSONObject.fromObject(res.html());
				if(checkTokenError(json.get("errcode"))){
					TokenService.getTokenService().refreshAccessToken(appId, appSecret, accessToken);
					continue;
				}
				String errmsg = json.get("ret_info").toString();
				if(StringUtils.equals(errmsg, "ok")){
					return "正在开锁，请稍后...";
				}else{
					return errmsg;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "开锁失败";
	}
	
	public Boolean checkTokenError(Object code){
		if(code!=null){
			LinkedList<String> errorCodes = new LinkedList<>();
			errorCodes.add("42001");
			errorCodes.add("42002");
			errorCodes.add("42003");
			errorCodes.add("40001");
			return errorCodes.contains(code.toString());
		}
		return false;
	}
	
	/**
	 * 开锁
	 * @param appId
	 * @param appSecret
	 * @param developerCode
	 * @param openId
	 */
	public void unlock(final String openId){
		
		taskExecutor.execute(new Runnable() {
			@Override
			public void run() {
				PublicNo publicNo = publicNoService.getPublicNoByOpenId(openId);
				CheckInRecord checkInRecord = checkInRecordDao.getByProperty("openId", openId, Order.desc("createDate"));
				if(checkInRecord==null){
					sendTextMessage(openId, "您没有绑定客房，请先去前台扫描二维码。", publicNo.getAppId(), publicNo.getAppSecret());
					return;
				}
				
				//获取订单明细中的入住和离店日期
				HotelOrderDetailVO orderDetail = getOrderDetailVO(publicNo.getCompanyId(), checkInRecord.getOrderNo());
				if(orderDetail!=null){
					//检查入住情况
					if(!StringUtils.equals(orderDetail.getStatus(), "在住")){
						sendTextMessage(openId, "您还未入住，请先去前台办理入住。", publicNo.getAppId(), publicNo.getAppSecret());
						return;
					}
					
					//调用开锁
					String msg = transmsg(publicNo.getAppId(), publicNo.getAppSecret(), publicNo.getDeveloperCode(), checkInRecord.getDeviceId(), openId,checkInRecord.getContent());
					System.out.println("transmsg========"+msg);
				}
			}
		});
	}
	
	public void sendTextMessage(final String openId,final String text,final String appId,final String appSecret){
		String param = "{'touser':'"+openId+"','msgtype':'text','text':{'content':'"+text+"'}}";
		try {
			String html = weixinInterfaceService.sendCustomMessage(param, appId, appSecret);
			System.out.println(html);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//获取门锁秘钥
	public JSONObject getContent(String appId,String appSecret,String lockNo,String startTime,String endTime,String moblie,String url){
		
		String nonceStr = DataPackUtil.getNonceStr();
		String timestamp = DataPackUtil.getTimeStamp();
		Map<String,String> paras = new HashMap<>();
		paras.put("appId", appId);
		paras.put("nonceStr", nonceStr);
		paras.put("timestamp", timestamp);
		paras.put("sign", getSign(appId, appSecret, nonceStr, timestamp));
		paras.put("version", "1.0");
		paras.put("lockNo", lockNo);
		paras.put("startTime", DateUtil.format(DateUtil.parseDatetime(startTime), "yyyyMMddHHmm"));
		paras.put("endTime", DateUtil.format(DateUtil.parseDatetime(endTime), "yyyyMMddHHmm"));
		paras.put("keyFlag", "1");
		paras.put("mobile", StringUtils.isBlank(moblie)?"0":moblie);
		paras.put("breakfast", "0");
		
		try {
			System.out.println("getContent="+JSONObject.fromObject(paras).toString()+" url="+url);
			Response res = HttpHelper.connect(url).timeout(1000*30).data(paras).post();
			String html = res.html();
			System.out.println(html);
			JSONObject json = JSONObject.fromObject(html);
			if(StringUtils.equals(json.get("statusCode").toString(), "success")){
				return JSONObject.fromObject(json.get("result"));
			}else{
				System.out.println(json.getString("message").toString());
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String getSign(String appId,String appSecret,String nonceStr,String timestamp){
		StringBuilder sb = new StringBuilder();
		sb.append(appId);
//		sb.append(appSecret);
		sb.append(nonceStr);
		sb.append(timestamp);
		System.out.println(sb.toString());
		return MD5Util.MD5(sb.toString()).toLowerCase();
	}
	
	public String guestCheckInQrcode(String companyId,String name,String mobile,String orderNo,String idNo,String roomNo){
		
		//保存入住记录
		CheckInRecord checkInRecord = new CheckInRecord();
		checkInRecord.setCreateDate(new Date());
		checkInRecord.setOrderNo(orderNo);
		checkInRecord.setCompanyId(companyId);
		checkInRecord.setName(name);
		checkInRecord.setMobile(mobile);
		checkInRecord.setIdNo(idNo);
		checkInRecord.setRoomNo(roomNo);
		checkInRecord.setOpenId(null);
		checkInRecord.setContent(null);
		checkInRecord.setUpdateDate(new Date());
		SceneQrcode sceneQrcode = sceneService.createSceneQrcode(companyId, QrActionType.QR_SCENE, 2592000);
		checkInRecord.setTicket(sceneQrcode.getTicket());
		checkInRecordDao.save(checkInRecord);
		
		return sceneQrcode.getQrUrl();
	}
	
	public HotelOrderDetailVO getOrderDetailVO(String companyId,String orderNo){
		
		InterfaceConfig interfaceConfig = interfaceConfigService.getEnableInterfaceConfig(companyId);
		if(interfaceConfig != null) {
			JXDPmsOrderService pmsOrderService = new JXDPmsOrderService();
			HotelOrderDetailVO orderDetailVO;
			try {
				orderDetailVO = pmsOrderService.getHotelOrderDetailVO(orderNo, interfaceConfig.getCompany().getCode(),interfaceConfig.getSign(),interfaceConfig.getHost());
				return orderDetailVO;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	/**
	 * 验证是否在住
	 */
	public CheckInRecord validateCheckInRecord(String companyId,String idNo,String roomNo,String openId){
		
		CheckInRecord checkInRecord = new CheckInRecord();
		
		//检查是否在住
		Map<String,OccupancyManVO> map = queryOccupancyManMap(companyId, idNo, roomNo);
		System.out.println("OccupancyManVOMap="+JSONObject.fromObject(map).toString()+"  idNo+roomNo="+(idNo+roomNo));
		OccupancyManVO occupancyManVO = map.get(idNo+roomNo);
		if(occupancyManVO==null){
			checkInRecord.setErrorMsg("您未入住"+roomNo+"号客房");
			return checkInRecord;
		}
		
		//查询是否已经生成入住记录
		List<CheckInRecord> checkInRecords = findCheckInRecord(companyId, idNo, roomNo, openId,occupancyManVO.getRegId());
		if(checkInRecords.isEmpty()){
			//新增入住记录
			checkInRecord.setCompanyId(companyId);
			checkInRecord.setName(occupancyManVO.getGuestCname());
			checkInRecord.setOrderNo(occupancyManVO.getRegId());//这里的登记号等于捷云的订单号
			checkInRecord.setOpenId(openId);
			checkInRecord.setRoomNo(occupancyManVO.getRoomNo());
			checkInRecord.setIdNo(idNo);
			checkInRecord.setMobile(occupancyManVO.getMobile());
			checkInRecord.setCreateDate(new Date());
			checkInRecord.setUpdateDate(new Date());
			
			PublicNo publicNo = publicNoService.getPublicNoByCompanyId(checkInRecord.getCompanyId());
			InterfaceConfig interfaceConfig = interfaceConfigService.getEnableInterfaceConfig(publicNo.getCompanyId());
			JSONObject json = getContent(publicNo.getAppId(), publicNo.getAppSecret(), occupancyManVO.getLockNo(), occupancyManVO.getArrDate(), occupancyManVO.getDepDate(), checkInRecord.getMobile(),interfaceConfig.getUnlock());
			String content = json.get("keyData").toString();//秘钥
			String deviceId = json.get("deviceId").toString();//设备号
			checkInRecord.setContent(content);
			checkInRecord.setDeviceId(deviceId);
			checkInRecordDao.save(checkInRecord);
		}else{
			//返回第一条入住记录，理论上单号加openId再加房号不会重复
			checkInRecord = checkInRecords.get(0);
			checkInRecord.setUpdateDate(new Date());
			checkInRecordDao.save(checkInRecord);
		}
		return checkInRecord;
	}
	
	public List<CheckInRecord> findCheckInRecord(String companyId,String idNo,String roomNo,String openId,String orderNo){
		Map<String, Serializable> properties = new HashMap<>();
		properties.put("companyId", companyId);
		if(StringUtils.isNotBlank(idNo)){
			properties.put("idNo", idNo);
		}
		if(StringUtils.isNotBlank(roomNo)){
			properties.put("roomNo", roomNo);
		}
		if(StringUtils.isNotBlank(openId)){
			properties.put("openId", openId);
		}
		if(StringUtils.isNotBlank(orderNo)){
			properties.put("orderNo", orderNo);
		}
		List<CheckInRecord> checkInRecords = checkInRecordDao.findByProperties(properties,Order.desc("updateDate"));
		return checkInRecords;
	}
	
	/**
	 * 获取线下入住人
	 * @param companyId
	 * @param idNo
	 * @param roomNo
	 * @return
	 */
	public Map<String,OccupancyManVO> queryOccupancyManMap(String companyId,String idNo,String roomNo){
		
		Map<String,OccupancyManVO> map = new HashMap<>();
		InterfaceConfig interfaceConfig = interfaceConfigService.getEnableInterfaceConfig(companyId);
		if(interfaceConfig != null) {
			JXDPmsOrderService pmsOrderService = new JXDPmsOrderService();
			List<OccupancyManVO> occupancyMans;
			try {
				occupancyMans = pmsOrderService.queryOccupancyManVOs(idNo,roomNo, interfaceConfig.getCompany().getCode(),interfaceConfig.getSign(),interfaceConfig.getHost());
				if(occupancyMans!=null){
					for (OccupancyManVO occupancyManVO : occupancyMans) {
						map.put(occupancyManVO.getIdNo()+occupancyManVO.getRoomNo(), occupancyManVO);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return map;
	}
}
