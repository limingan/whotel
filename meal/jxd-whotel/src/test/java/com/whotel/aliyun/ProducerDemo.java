package com.whotel.aliyun;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.aliyun.mns.client.CloudAccount;
import com.aliyun.mns.client.CloudQueue;
import com.aliyun.mns.client.MNSClient;
import com.aliyun.mns.model.Message;
import com.whotel.common.http.HttpHelper;
import com.whotel.common.http.HttpHelper.Response;

public class ProducerDemo {
	public static void main(String[] args) {
		CloudAccount account = new CloudAccount("LTAIHAAcdeqQwBK1",
				"pgHtruHOEj8xApITwqzrsV2jPzvZjq", "http://35664445.mns.cn-shenzhen.aliyuncs.com/");
		MNSClient client = account.getMNSClient();
		try {
			final CloudQueue queue = client.getQueueRef("wx2eyt");
			ExecutorService cachedThreadPool = Executors.newScheduledThreadPool(50);
			for (int i = 0; i < 10000; i++) {
				cachedThreadPool.execute(new Runnable() {
					public void run() {
						try {
							Message message = new Message();
							String msg = "{\"hotelcode\":\"tm\",\"content\":\"<?xml version=\\\"1.0\\\" encoding=\\\"gbk\\\"?><RealOperate><XType>GemStar</XType><OpType>E云通预订</OpType><Reservation><ProfileId>44600001</ProfileId><MbrCardNo></MbrCardNo><OrderDetails><OrderDetail><DetailId>1</DetailId><Code>104</Code><SalesPromotionId>42</SalesPromotionId><ArriveDate>2016-11-08</ArriveDate><LeaveDate>2016-11-09</LeaveDate><RoomQty>2</RoomQty><TotalAmount>180.0</TotalAmount><PaidAmount>180.0</PaidAmount><PayMethod>储值支付</PayMethod><GuestName>kpc</GuestName><RoomSpecial></RoomSpecial><ArriveTime>2016-11-08 11:11</ArriveTime><GuestRemark>付款方式：储值支付(金额：180.0)  支付状态：已支付</GuestRemark><OrderDetailPrices><OrderDetailPrice><DetailId>1</DetailId><Price>90.0</Price><PriceDate>2016-11-08</PriceDate></OrderDetailPrice></OrderDetailPrices><OrderValueAddedServices></OrderValueAddedServices><OrderAirportPickupServices></OrderAirportPickupServices><AttachProducts></AttachProducts></OrderDetail></OrderDetails><WeixinID>oLI_KjsR4DUKt9gp-m8jYrJyggZQ</WeixinID><Source>weixin</Source><GuestType>会员</GuestType><OrderType>0</OrderType><ContactMobile>15876938124</ContactMobile><MbrCardTypeCode>WXVIP</MbrCardTypeCode><SalesCName>admin</SalesCName><BookChannel>shop</BookChannel><ResortId>2</ResortId><TotalAmount>180.0</TotalAmount><PaidAmount>180.0</PaidAmount><PayMethodType>mbrCardPay</PayMethodType><PaidRefId>01000000000978</PaidRefId><OrderOperate>Add</OrderOperate><confirmationID>201000000000980"+getOrderNo()+"</confirmationID><ContactName>kpc</ContactName></Reservation><OrderCategory></OrderCategory></RealOperate>\"}"; 
							message.setMessageBody(msg);
							Message putMsg = queue.putMessage(message);
							System.out.println("Send message id is: " + putMsg.getMessageId());
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	static int i = 30003;
	
	public synchronized static int getOrderNo(){
		return i+1;
	}
}
