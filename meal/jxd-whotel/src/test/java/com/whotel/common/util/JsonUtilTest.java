package com.whotel.common.util;

import java.util.List;

import net.sf.json.JSONObject;

import org.junit.Test;

import com.weixin.core.bean.FanInfo;
import com.whotel.ext.json.JSONConvertFactory;

public class JsonUtilTest {

	@Test
	public void testListJson() {
		String str = "[\"1\", \"2\", \"3\"]";
		List<String> list = JSONConvertFactory.getJacksonConverter().listFromString(str, String.class);
		if(list != null) {
			for(String l:list) {
				System.out.println(l);
			}
		}
		
		list = JSONConvertFactory.getGsonConverter().listFromString(str, String.class);
		if(list != null) {
			for(String l:list) {
				System.out.println(l+"---------");
			}
		}
		str = "{\"name\":\"fengyong\", \"age\":1}";
		JSONObject jsonObj = JSONConvertFactory.getJacksonConverter().objectFromString(str, JSONObject.class);
		if(jsonObj != null) {
			System.out.println(jsonObj.get("name")+"--------"+jsonObj.get("age"));
		}
		
		str = "{\"openid\":\"oSt32s7Yj3b3Dh3EvFSseojt-Eac\",\"nickname\":\"fengyong\",\"sex\":1,\"language\":\"zh_CN\",\"city\":\"Shenzhen\",\"province\":\"Guangdong\",\"country\":\"CN\",\"headimgurl\":\"http:\\/\\/wx.qlogo.cn\\/mmopen\\/w9icFOzwicZEYicN4EhZSkXFfEJlYuXq3mhA8SicFZqA5zkuMEdKH2BnPmCUXQOSUxLVrp94NHd8icVRjeMaLibnUCr0uO4MlOp3kp\\/0\",\"privilege\":[]}";
		FanInfo info = JSONConvertFactory.getJacksonConverter().objectFromString(str, FanInfo.class);
		
		System.out.println(info);
	}
}
