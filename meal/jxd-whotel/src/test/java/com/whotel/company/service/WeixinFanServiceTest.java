package com.whotel.company.service;

import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.whotel.front.entity.WeixinFan;
import com.whotel.front.service.WeixinFanService;

@ContextConfiguration(locations = { "/applicationContext-*test.xml" })
public class WeixinFanServiceTest extends AbstractJUnit4SpringContextTests {

	@Autowired
	private WeixinFanService weixinFanService;
	
	
	@Test
	public void testCreateFan() {
		
		WeixinFan fan = new WeixinFan();
		fan.setDevCode("gh_3cb702811ef1");
		fan.setFocus(true);
		fan.setNickName("fengyong111");
		fan.setOpenId("oSt32s7Yj3b3Dh3EvFSseojt-Eac");
		fan.setCreateTime(new Date());
		fan.setAvatar("http://7xjki3.com2.z0.glb.qiniucdn.com/dosomi-picture:2TteYCjbp81oSfDMmq92I_");
		weixinFanService.saveWeixinFan(fan);
	}
}
