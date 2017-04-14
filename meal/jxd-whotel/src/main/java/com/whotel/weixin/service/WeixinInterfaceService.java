package com.whotel.weixin.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.weixin.core.common.BaseToken;
import com.whotel.common.http.HttpHelper;
import com.whotel.thirdparty.jxd.mode.vo.ServiceListVO;
import com.whotel.weixin.controller.WeixinInterfaceController;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * @author 柯鹏程
 */
@Service
public class WeixinInterfaceService {

	//添加客服add,修改客服update
	public static final String OPERATION_CUSTOM = "https://api.weixin.qq.com/customservice/kfaccount/{$OPERATION}?access_token={$ACCESS_TOKEN}";
	
	//发送消息send
	public static final String SEND_CUSTOM = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token={$ACCESS_TOKEN}";
	
	//删除客服
	public static final String DELETE_CUSTOM = "https://api.weixin.qq.com/customservice/kfaccount/del?access_token={$ACCESS_TOKEN}&kf_account={$KFACCOUNT}";
	
	//设置客服头像 ---kf_account=KFACCOUNT
	public static final String SET_CUSTOM_HEADIMG = "http://api.weixin.qq.com/customservice/kfaccount/uploadheadimg?access_token={$ACCESS_TOKEN}&kf_account=KFACCOUNT";

	public static final String CUSTOM_LIST = "https://api.weixin.qq.com/cgi-bin/customservice/getkflist?access_token={$ACCESS_TOKEN}";
	
	private static final Logger log = LoggerFactory.getLogger(WeixinInterfaceService.class);

	public String sendTemplateMessage(String roomMessage,String openId,String appId,String appSecret) throws Exception{
		BaseToken accessToken = TokenService.getTokenService().getAccessToken(appId, appSecret);
		String url = WeixinInterfaceController.SEND_TEMPLATE_MESSAGE.replace("{$ACCESS_TOKEN}", accessToken.getAccess_token());
		return HttpHelper.connect(url).post(JSONObject.fromObject(roomMessage).toString()).html();
	}
	
	public String operationCustomMessage(String roomMessage,String type,String appId,String appSecret) throws Exception{
		BaseToken accessToken = TokenService.getTokenService().getAccessToken(appId, appSecret);
		String url = OPERATION_CUSTOM.replace("{$OPERATION}", type).replace("{$ACCESS_TOKEN}", accessToken.getAccess_token());
		return HttpHelper.connect(url).post(JSONObject.fromObject(roomMessage).toString()).html();
	}
	
	public String sendCustomMessage(String roomMessage,String appId,String appSecret) throws Exception{
		BaseToken accessToken = TokenService.getTokenService().getAccessToken(appId, appSecret);
		String url = SEND_CUSTOM.replace("{$ACCESS_TOKEN}", accessToken.getAccess_token());
		return HttpHelper.connect(url).post(JSONObject.fromObject(roomMessage).toString()).html();
	}
	
	public String deleteCustom(String kfAccount,String appId,String appSecret) throws Exception{
		BaseToken accessToken = TokenService.getTokenService().getAccessToken(appId, appSecret);
		String url = DELETE_CUSTOM.replace("{$ACCESS_TOKEN}", accessToken.getAccess_token()).replace("{$KFACCOUNT}", kfAccount);
		return HttpHelper.connect(url).get().html();
	}
	
	/**
	 * 查出客服
	 * @throws Exception 
	 */
	public List<ServiceListVO> findCustomerService(String appId,String appSecret){
		List<ServiceListVO> serviceListVOs = new ArrayList<ServiceListVO>();
		String html = "";
		try {
			BaseToken accessToken = TokenService.getTokenService().getAccessToken(appId, appSecret);
			String url = CUSTOM_LIST.replace("{$ACCESS_TOKEN}", accessToken.getAccess_token());
			html = HttpHelper.connect(url).get().html();
			html = html.substring(html.indexOf("["), html.indexOf("]")+1);
			JSONArray arrays = JSONArray.fromObject(html);
			for (int i = 0; i < arrays.size(); i++) {
				ServiceListVO serviceListVO = new ServiceListVO();
				JSONObject array = arrays.getJSONObject(i);
				serviceListVO.setKfAccount(array.getString("kf_account"));
				serviceListVO.setKfId(array.getString("kf_id"));
				serviceListVO.setKfNick(array.getString("kf_nick"));
				serviceListVO.setKfHeadimgurl(array.getString("kf_headimgurl"));
				serviceListVOs.add(serviceListVO);
			}
		}catch (Exception e) {
			e.printStackTrace();
			log.error("查询客服："+"html="+html);
		}
		return serviceListVOs;
	}
}
