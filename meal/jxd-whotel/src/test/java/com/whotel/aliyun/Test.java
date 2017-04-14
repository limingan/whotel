package com.whotel.aliyun;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.whotel.common.http.HttpHelper;
import com.whotel.common.http.HttpHelper.Response;
import com.whotel.common.util.DateUtil;
import com.whotel.thirdparty.jxd.JXDConstants;

public class Test {
	
	public static int count = 20000;
	
//	//柯鹏程，邱晓玉，周望平，姜珊,夏侯,廖纯
//	String[] openIds = new String[]{"oLI_KjsR4DUKt9gp-m8jYrJyggZQ","owuXzv0WPg7WBFmIhfvvy9UkS2y4","oLI_KjocfXT-Fu6RKxA2N8oQrEUQ","oLI_KjkrrRtd8RPJkIiLz17M1XTI","oLI_KjhiKCDw_on5qgHE6IbE4cT8","oLI_KjjY4w4WHULp6CtxXzYdrBLk"};
//	int index = new Random().nextInt(5);
	//findMember();
	
	public static void main(String[] args) {
//		new Thread(new Runnable() {
//			@Override
//			public void run() {
//				hotelOrder();
//			}
//		}).start();
//		new Thread(new Runnable() {
//			@Override
//			public void run() {
//				hotelOrder2();
//			}
//		}).start();
//		new Thread(new Runnable() {
//			@Override
//			public void run() {
//				hotelOrder3();
//			}
//		}).start();
//		new Thread(new Runnable() {
//			@Override
//			public void run() {
//				ticketOrder();
//			}
//		}).start();
//		new Thread(new Runnable() {
//			@Override
//			public void run() {
//				ticketOrder2();
//			}
//		}).start();
//		new Thread(new Runnable() {
//			@Override
//			public void run() {
//				ticketOrder3();
//			}
//		}).start();

		System.out.println("开始");
		ExecutorService cachedThreadPool = Executors.newScheduledThreadPool(3000);
		for (int i = 0; i < count; i++) {
			cachedThreadPool.execute(new Runnable() {
				public void run() {
					try {
//						findMember();
//						testweixin();
						testshop();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}
	}
	
	public static void hotelOrder(){
		ExecutorService cachedThreadPool = Executors.newScheduledThreadPool(10);
		for (int i = 0; i < count; i++) {
			cachedThreadPool.execute(new Runnable() {
				public void run() {
					try {
						String url = "http://wxtest.gshis.net/oauth/shop/toGoodsOrderPay.do?comid=55e65986cb0d7463a708d003&wxid=oLI_KjsR4DUKt9gp-m8jYrJyggZQ";
						
						Map<String, String> data = new HashMap<>();
						data.put("isOtherGoods", "false");
						data.put("contactName", "柯鹏程");
						data.put("contactMobile", "15876938110");
						data.put("name", "客房抢购压力测试");
						data.put("items[0].goodsId", "5821aa86d5e1ea2262a7837b");
						data.put("items[0].price", "1.0");
						data.put("items[0].marketPrice", "1.0");
						data.put("items[0].number", "1");
						data.put("items[0].point", "0");
						data.put("items[0].name", "客房抢购压力测试");
						data.put("items[0].catId", "573aeb55d5e1ea780ac9b75a");
						data.put("items[0].groupId", "-1");
						data.put("items[0].code", "10000001");
						data.put("items[0].catName", "客房");
						data.put("items[0].groupAttributes.seqNumber", "1");
						data.put("items[0].marketingGoodsId", "5822de6ed5e1ea32769b1fdb");
						data.put("checkInTime", "2016-11-11");
						data.put("arriveTime", "12:00");
						data.put("promotionFee", "0");
						data.put("totalFee", "1");
						
						HttpHelper conn = HttpHelper.connect(url).header("Content-Type",
								"application/x-www-form-urlencoded").timeout(JXDConstants.TIMEOUT);
						Response res = conn.data(data).post();
						
						url = "http://wxtest.gshis.net/oauth/shop/balancePay.do?payPwd=123456&comid=55e65986cb0d7463a708d003&wxid=oLI_KjsR4DUKt9gp-m8jYrJyggZQ";
						res = conn.url(url).get();
						
						System.out.println(res.html());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}
	}
	
	public static void hotelOrder2(){
		ExecutorService cachedThreadPool = Executors.newScheduledThreadPool(10);
		for (int i = 0; i < count; i++) {
			cachedThreadPool.execute(new Runnable() {
				public void run() {
					try {
						String url = "http://wxtest.gshis.net/oauth/shop/toGoodsOrderPay.do?comid=55e65986cb0d7463a708d003&wxid=owuXzv0WPg7WBFmIhfvvy9UkS2y4";
						Map<String, String> data = new HashMap<>();
						data.put("isOtherGoods", "false");
						data.put("contactName", "邱晓玉");
						data.put("contactMobile", "15876938110");
						data.put("name", "客房抢购压力测试");
						data.put("items[0].goodsId", "5822bf02d5e1ea038d6cabb9");
						data.put("items[0].price", "2.0");
						data.put("items[0].marketPrice", "2.0");
						data.put("items[0].number", "1");
						data.put("items[0].point", "0");
						data.put("items[0].name", "客房抢购压力测试2");
						data.put("items[0].catId", "573aeb55d5e1ea780ac9b75a");
						data.put("items[0].groupId", "-1");
						data.put("items[0].code", "10000002");
						data.put("items[0].catName", "客房");
						data.put("items[0].groupAttributes.seqNumber", "1");
						data.put("items[0].marketingGoodsId", "5822de6ed5e1ea32769b1fdc");
						data.put("checkInTime", "2016-11-11");
						data.put("arriveTime", "12:00");
						data.put("promotionFee", "0");
						data.put("totalFee", "2");
						
						HttpHelper conn = HttpHelper.connect(url).header("Content-Type",
								"application/x-www-form-urlencoded").timeout(JXDConstants.TIMEOUT);
						conn.followRedirects(true);
						
						Response res = conn.data(data).post();
						
						url = "http://wxtest.gshis.net/oauth/shop/balancePay.do?payPwd=123456";
						res = conn.url(url).get();
						
						System.out.println(res.html());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}
	}
	
	public static void hotelOrder3(){
		
		ExecutorService cachedThreadPool = Executors.newScheduledThreadPool(10);
		for (int i = 0; i < count; i++) {
			cachedThreadPool.execute(new Runnable() {
				public void run() {
					try {
						String url = "http://wxtest.gshis.net/oauth/shop/toGoodsOrderPay.do?comid=55e65986cb0d7463a708d003&wxid=oLI_KjocfXT-Fu6RKxA2N8oQrEUQ";
						
						Map<String, String> data = new HashMap<>();
						data.put("isOtherGoods", "false");
						data.put("contactName", "周望平");
						data.put("contactMobile", "15876938110");
						data.put("name", "客房抢购压力测试");
						data.put("items[0].goodsId", "5822bf34d5e1ea038d6cabbb");
						data.put("items[0].price", "3.0");
						data.put("items[0].marketPrice", "3.0");
						data.put("items[0].number", "1");
						data.put("items[0].point", "0");
						data.put("items[0].name", "客房抢购压力测试3");
						data.put("items[0].catId", "573aeb55d5e1ea780ac9b75a");
						data.put("items[0].groupId", "-1");
						data.put("items[0].code", "10000003");
						data.put("items[0].catName", "客房");
						data.put("items[0].groupAttributes.seqNumber", "1");
						data.put("items[0].marketingGoodsId", "5822de6ed5e1ea32769b1fdd");
						data.put("checkInTime", "2016-11-11");
						data.put("arriveTime", "12:00");
						data.put("promotionFee", "0");
						data.put("totalFee", "3");
						
						HttpHelper conn = HttpHelper.connect(url).header("Content-Type",
								"application/x-www-form-urlencoded").timeout(JXDConstants.TIMEOUT);
						Response res = conn.data(data).post();
						
						url = "http://wxtest.gshis.net/oauth/shop/balancePay.do?payPwd=123456";
						res = conn.url(url).get();
						
						System.out.println(res.html());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}
	}
	
	public static void ticketOrder(){
		
		ExecutorService cachedThreadPool = Executors.newScheduledThreadPool(10);
		for (int i = 0; i < count; i++) {
			cachedThreadPool.execute(new Runnable() {
				public void run() {
					try {
						String url = "http://wxtest.gshis.net/oauth/shop/toGoodsOrderPay.do?comid=55e65986cb0d7463a708d003&wxid=oLI_KjkrrRtd8RPJkIiLz17M1XTI";
						Map<String, String> data = new HashMap<>();
						
						data.put("isOtherGoods", "false");
						data.put("contactName", "姜珊");
						data.put("contactMobile", "15876938110");
						data.put("name", "门票抢购压力测试");
						data.put("items[0].goodsId", "5821aaced5e1ea2262a7838c");
						data.put("items[0].price", "4.0");
						data.put("items[0].marketPrice", "4.0");
						data.put("items[0].number", "1");
						data.put("items[0].point", "0");
						data.put("items[0].name", "门票抢购压力测试");
						data.put("items[0].thumb", "");
						data.put("items[0].catId", "57baa004d5e1ea2303ad8fa9");
						data.put("items[0].groupId", "-1");
						data.put("items[0].code", "10000002");
						data.put("items[0].catName", "温泉门票");
						data.put("items[0].groupAttributes.seqNumber", "1");
						data.put("items[0].marketingGoodsId", "5822de6ed5e1ea32769b1fde");
						data.put("checkInTime", "2016-11-11");
						data.put("promotionFee", "0");
						data.put("totalFee", "4");
						
						HttpHelper conn = HttpHelper.connect(url).header("Content-Type",
								"application/x-www-form-urlencoded").timeout(JXDConstants.TIMEOUT);
						Response res = conn.data(data).post();
						
						url = "http://wxtest.gshis.net/oauth/shop/balancePay.do?payPwd=123456";
						res = conn.url(url).get();
						
						System.out.println(res.html());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}
	}
	
	public static void ticketOrder2(){
		
		ExecutorService cachedThreadPool = Executors.newScheduledThreadPool(10);
		for (int i = 0; i < count; i++) {
			cachedThreadPool.execute(new Runnable() {
				public void run() {
					try {
						String url = "http://wxtest.gshis.net/oauth/shop/toGoodsOrderPay.do?comid=55e65986cb0d7463a708d003&wxid=oLI_KjhiKCDw_on5qgHE6IbE4cT8";
						Map<String, String> data = new HashMap<>();
						
						data.put("isOtherGoods", "false");
						data.put("contactName", "夏侯");
						data.put("contactMobile", "15876938110");
						data.put("name", "门票抢购压力测试");
						data.put("items[0].goodsId", "5822bf7dd5e1ea038d6cabbe");
						data.put("items[0].price", "5.0");
						data.put("items[0].marketPrice", "5.0");
						data.put("items[0].number", "1");
						data.put("items[0].point", "0");
						data.put("items[0].name", "门票抢购压力测试");
						data.put("items[0].thumb", "");
						data.put("items[0].catId", "57baa004d5e1ea2303ad8fa9");
						data.put("items[0].groupId", "-1");
						data.put("items[0].code", "10000002");
						data.put("items[0].catName", "温泉门票");
						data.put("items[0].groupAttributes.seqNumber", "1");
						data.put("items[0].marketingGoodsId", "5822de6ed5e1ea32769b1fdf");
						data.put("checkInTime", "2016-11-11");
						data.put("promotionFee", "0");
						data.put("totalFee", "5");
						
						HttpHelper conn = HttpHelper.connect(url).header("Content-Type",
								"application/x-www-form-urlencoded").timeout(JXDConstants.TIMEOUT);
						Response res = conn.data(data).post();
						
						url = "http://wxtest.gshis.net/oauth/shop/balancePay.do?payPwd=123456";
						res = conn.url(url).get();
						
						System.out.println(res.html());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}
	}
	
	public static void ticketOrder3(){
		
		ExecutorService cachedThreadPool = Executors.newScheduledThreadPool(10);
		for (int i = 0; i < count; i++) {
			cachedThreadPool.execute(new Runnable() {
				public void run() {
					try {
						String url = "http://wxtest.gshis.net/oauth/shop/toGoodsOrderPay.do?comid=55e65986cb0d7463a708d003&wxid=oLI_KjjY4w4WHULp6CtxXzYdrBLk";
						Map<String, String> data = new HashMap<>();
						
						data.put("isOtherGoods", "false");
						data.put("contactName", "廖纯");
						data.put("contactMobile", "15876938110");
						data.put("name", "门票抢购压力测试");
						data.put("items[0].goodsId", "5822c4d3d5e1ea038d6cabc2");
						data.put("items[0].price", "6.0");
						data.put("items[0].marketPrice", "6.0");
						data.put("items[0].number", "1");
						data.put("items[0].point", "0");
						data.put("items[0].name", "门票抢购压力测试");
						data.put("items[0].thumb", "");
						data.put("items[0].catId", "57baa004d5e1ea2303ad8fa9");
						data.put("items[0].groupId", "-1");
						data.put("items[0].code", "10000002");
						data.put("items[0].catName", "温泉门票");
						data.put("items[0].groupAttributes.seqNumber", "1");
						data.put("items[0].marketingGoodsId", "5822de6ed5e1ea32769b1fe0");
						data.put("checkInTime", "2016-11-11");
						data.put("promotionFee", "0");
						data.put("totalFee", "6");
						
						HttpHelper conn = HttpHelper.connect(url).header("Content-Type",
								"application/x-www-form-urlencoded").timeout(JXDConstants.TIMEOUT);
						Response res = conn.data(data).post();
						
						url = "http://wxtest.gshis.net/oauth/shop/balancePay.do?payPwd=123456";
						res = conn.url(url).get();
						
						System.out.println(res.html());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}
	}
	
	public static void localTest(){
		
		try {
			String url = "http://localhost:8080/oauth/shop/toGoodsOrderPay.do?comid=55ed0b51f19aa1122cbd1a7e&wxid=oLI_KjsR4DUKt9gp-m8jYrJyggZQ";
			Map<String, String> data = new HashMap<>();
			data.put("isOtherGoods", "false");
			data.put("contactName", "kpc");
			data.put("contactMobile", "15876938110");
			data.put("name", "客房促销");
			data.put("items[0].goodsId", "5745616d648bbf12748aa61c");
			data.put("items[0].price", "90.0");
			data.put("items[0].marketPrice", "88.0");
			data.put("items[0].number", "2");
			data.put("items[0].point", "123");
			data.put("items[0].name", "客房促销");
			data.put("items[0].thumb", "jxd-res:0EcIlZCpZ5aadcyThmG1ct");
			data.put("items[0].catId", "574560b8648bbf12748aa61a");
			data.put("items[0].groupId", "57160103648bbf1508a1c42d");
			data.put("items[0].code", "132465");
			data.put("items[0].catName", "优惠客房");
			data.put("items[0].groupAttributes.seqNumber", "1");
			data.put("items[0].marketingGoodsId", "57c7eb7e648bbf16f4f4f3ef");
			data.put("checkInTime", "2016-11-11");
			data.put("arriveTime", "11:11");
			data.put("promotionFee", "0");
			data.put("totalFee", "180");
			
			HttpHelper conn = HttpHelper.connect(url).header("Content-Type",
					"application/x-www-form-urlencoded").timeout(JXDConstants.TIMEOUT);
			Response res = conn.data(data).post();
			
			url = "http://localhost:8080/oauth/shop/balancePay.do?payPwd=123456";
			res = conn.url(url).get();
			
			System.out.println(res.html());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void findMember(){
		
		try {
			//柯鹏程，邱晓玉，周望平，姜珊,夏侯,廖纯
			String[] openIds = new String[]{"oLI_KjsR4DUKt9gp-m8jYrJyggZQ","owuXzv0WPg7WBFmIhfvvy9UkS2y4","oLI_KjocfXT-Fu6RKxA2N8oQrEUQ","oLI_KjkrrRtd8RPJkIiLz17M1XTI","oLI_KjhiKCDw_on5qgHE6IbE4cT8","oLI_KjjY4w4WHULp6CtxXzYdrBLk"};
			int index = new Random().nextInt(5);
			String html = "<?xml version=\"1.0\" encoding=\"gbk\"?><RealOperate><XType>GemStar</XType><OpType>会员查询</OpType><MbrQuery><OtherKeyWord>"+"oLI_KjqsmnpQ8w8edId6o_-MJ-r4"+"</OtherKeyWord><OtherName>微信</OtherName></MbrQuery></RealOperate>";
			HttpHelper conn = HttpHelper.connect("http://112.91.151.10/HttpServices/ExchangeData.aspx/Exchange").header("Content-Type",
					"application/x-www-form-urlencoded").timeout(JXDConstants.TIMEOUT);
			Response res = conn.post(html);
			if(res.html().length()>10){
				
				System.out.println(DateUtil.getCurrDateTimeStr()+"======findMember=========");
			}
		} catch (Exception e) {
			System.out.println("---------------------------------------");
		}
	}
	
	public static void testweixin(){
		try {
			String url = "http://wxtest.gshis.net/oauth/activity/toObtainPrize.do?wxid=oLI_KjsR4DUKt9gp-m8jYrJyggZQ&comid=55e65986cb0d7463a708d003&seekTreasureId=5857a295d5e1ea6217e18faf&prizeId=5857a297d5e1ea6217e18fb0";
			Response res = HttpHelper.connect(url).header("Content-Type",
					"application/x-www-form-urlencoded").timeout(JXDConstants.TIMEOUT).get();
			System.out.println(DateUtil.getCurrDateTimeStr()+"===============");
//			if(res.html().length()>10){
//			}
		} catch (Exception e) {
			System.out.println("---------------------------------------");
		}
	}
	
	public static void testshop(){
		try {
			String url = "http://wxtest.gshis.net/oauth/shop/toActivityGoodsList.do?marketingActivityId=58324342d5e1ea5e765d0b46&wxid=oLI_KjsR4DUKt9gp-m8jYrJyggZQ&comid=55e65986cb0d7463a708d003";
//			String url = "http://wxtest.gshis.net:8082/oauth/shop/getActivityTest.do";
			Response res = HttpHelper.connect(url).header("Content-Type",
					"application/x-www-form-urlencoded").timeout(JXDConstants.TIMEOUT*60).get();
			if(res.html().length()>10){
				System.out.println(DateUtil.getCurrDateTimeStr()+"===============");
			}
		} catch (Exception e) {
			System.out.println("---------------------------------------"+e.getMessage());
		}
	}
}

