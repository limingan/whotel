package com.whotel.weixin.api;

import org.junit.Test;

import com.weixin.core.api.UserInfoApi;
import com.weixin.core.bean.FanInfo;

public class UserInfoApiTest {

	@Test
	public void testUseInfo() throws Exception {
		String openId = "odPnjjvsSwHumIuvCD7NbW4VukyI";
		String appId = "wx2cc62d6059ded8e9";
		String appSecret = "d93568ad2521b0b9ff214f7778f564be";
		FanInfo userInfo = UserInfoApi.getUserInfo(appId, appSecret, openId);
		System.out.println(userInfo);
	}
}
