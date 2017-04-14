package com.whotel.weixin.api;

import org.junit.Test;

import com.weixin.core.api.NewsResourceApi;
import com.weixin.core.bean.MaterialBatch;
import com.weixin.core.common.WeixinConstant;
import com.whotel.common.util.QiniuUtils;

public class NewsResourceApiTest {

	@Test
	public void testBachGetResource() {
		
		String appId = "wx2cc62d6059ded8e9";
		String appSecret = "d93568ad2521b0b9ff214f7778f564be";
		
		try {
			MaterialBatch batchGetNews = NewsResourceApi.batchGetNews(40, 60, WeixinConstant.MsgType.NewsMsg, appId, appSecret);
			System.out.println(batchGetNews);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetResource() {
		
		String appId = "wx2cc62d6059ded8e9";
		String appSecret = "d93568ad2521b0b9ff214f7778f564be";
		
		try {
			byte[] bytes = NewsResourceApi.getImageMaterial("-Y_onCGcyAqstR8TZu4FUsII5KboJ0zKNpXLxOQVqkM", appId, appSecret);
			
			//BaseToken token = TokenManager.getBaseToken(appId, appSecret);
			//String image = MediaUploador.uploadPersistentImage(token, WeixinConstant.MsgType.ImageMsg, bytes);
			//System.out.println(bytes.length + "---------" + image);
			
			String upload = QiniuUtils.upload(bytes);
			
			System.out.println(upload);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
