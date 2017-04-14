package com.whotel.weixin.api;

import java.awt.Image;
import java.net.URL;

import javax.imageio.ImageIO;

import org.junit.Test;

import com.weixin.core.api.MediaUploador;
import com.weixin.core.api.TokenManager;
import com.weixin.core.common.BaseToken;
import com.weixin.core.common.WeixinConstant;

public class MediaUploadorTest {

	@Test
	public void testMediaUpload() {
		String appId = "wxb051e8b6dcdf8ff7";
		String appSecret = "6b27e97c3f721995e06410cc9635f851";
		String pic = "http://mmbiz.qpic.cn/mmbiz/poqciaFBIynQy8DBmibZmqic1q2Su8R4Oick91YdWibz278Rcpic1rL4TyBL57rCtDvdTS7fLMyZAyltWJoqiaRgJmBFA/0";
		try {
			URL url = new URL(pic);
		    System.out.println(MediaUploador.uploadTempWebPic(appId, appSecret, WeixinConstant.MsgType.ImageMsg, pic));
			Image src = ImageIO.read(url);
			int wideth = src.getWidth(null);
			int height = src.getHeight(null);
			System.out.println(wideth + "," + height);
			
			//System.out.println(MediaUploador.uploadPersistentImage(appId, appSecret, WeixinConstant.MsgType.ImageMsg, pic));

		 System.out.println(MediaUploador.uploadPersistentWebPic(appId, appSecret, WeixinConstant.MsgType.ImageMsg, pic));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testMediaDownload() {
		String appId = "wx3f43e51f631c3e2b";
		String appSecret = "1cb2b455990805f2905afaeefea476a4";
		
		try {
			byte[] bytes = MediaUploador.downloadMaterial(appId, appSecret, "hWdxFph1NBjuH2z4WD0jF2YA9Lyi96-junsMDED6cyU");
		
			BaseToken token = TokenManager.getBaseToken(appId, appSecret);
			String image = MediaUploador.uploadPersistentImage(token, WeixinConstant.MsgType.ImageMsg, bytes);
			System.out.println(bytes.length + "---------" + image);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
