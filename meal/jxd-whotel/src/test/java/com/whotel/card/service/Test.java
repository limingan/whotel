package com.whotel.card.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.whotel.common.http.HttpHelper;
import com.whotel.common.http.HttpHelper.Response;
import com.whotel.common.util.DateUtil;
import com.whotel.thirdparty.jxd.JXDConstants;

public class Test {
	
	
	public static void main(String[] args) {
		ExecutorService cachedThreadPool = Executors.newScheduledThreadPool(50);
		for (int i = 0; i < 10000; i++) {
			cachedThreadPool.execute(new Runnable() {
				public void run() {
					findMemberByOpenId();
					//createMember();
				}
			});
		}
	}
	
	static long index = 15810000301l;
	public synchronized static long getIndex(){
		return index++;
	}
	
	public static void createMember(){
		try {
			long mobile = getIndex();
			String xml = "<?xml version=\"1.0\" encoding=\"gbk\"?>\n<RealOperate><XType>GemStar</XType><OpType>会员资料修改</OpType><ProfileUpdate><NotSendMsg>0</NotSendMsg><OtherKeyWord>"+mobile+"</OtherKeyWord><Action>Add</Action><OtherName>微信</OtherName><Mobile>"+mobile+"</Mobile><GuestEName>Yang Xiong Cai</GuestEName><GuestCName>杨雄才</GuestCName></ProfileUpdate></RealOperate>";
			Response res = HttpHelper.connect("http://gsyhzx.vicp.net:7600/httpservice_hexq/ExchangeData.aspx/Exchange").header("Content-Type",
					"application/x-www-form-urlencoded").timeout(JXDConstants.TIMEOUT).post(xml);
			System.out.println(xml+"-------"+res.html());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void findMemberByOpenId(){
		try {
			long bt = System.currentTimeMillis();
			String xml = "<?xml version=\"1.0\" encoding=\"gbk\"?><RealOperate><XType>GemStar</XType><OpType>会员查询</OpType><MbrQuery><OtherKeyWord>oLI_KjsR4DUKt9gp-m8jYrJyggZQ</OtherKeyWord><OtherName>微信</OtherName></MbrQuery></RealOperate>";
			Response res = HttpHelper.connect("http://gsyhzx.vicp.net:7600/httpservice_hexq/ExchangeData.aspx/Exchange").header("Content-Type",
					"application/x-www-form-urlencoded").timeout(JXDConstants.TIMEOUT).post(xml);
			long et = System.currentTimeMillis();
			System.out.println((et-bt)+"============="+DateUtil.getCurrDateTimeStr()+res.html());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
