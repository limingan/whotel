package com.whotel.message;

import org.junit.Test;

import com.weixin.core.api.MenuCreator;
import com.weixin.core.bean.CommonButton;
import com.weixin.core.bean.ComplexButton;
import com.weixin.core.bean.Menu;
import com.weixin.core.common.BaseToken;

public class MenuCreateApiTest {

	@Test
	public void testCreateMenu() throws Exception {
		// 第一个菜单及其子菜单
		String appid = "wx240eeec22131d735";
		String appsecret = "6ba45c40668f1bea15962aff4a2db22e";

		
		CommonButton btn11 = new CommonButton();
		btn11.setName("我的订单");
		btn11.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx240eeec22131d735&redirect_uri=http%3a%2f%2ftiantianwutuo.com%2foauth%2fmeal%2flogin.do%3ftype%3dORDER%26comid%3d55e65986cb0d7463a708d003&response_type=code&scope=snsapi_base&state=123#wechat_redirect");
		CommonButton btn12 = new CommonButton();
		btn12.setName("外卖点餐");
		btn12.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx240eeec22131d735&redirect_uri=http%3a%2f%2ftiantianwutuo.com%2foauth%2fmeal%2flogin.do%3ftype%3dLIST%26comid%3d55e65986cb0d7463a708d003&response_type=code&scope=snsapi_base&state=123#wechat_redirect\n");



		Menu menu = new Menu();
		menu.addButton(btn12);
		menu.addButton(btn11);
		BaseToken token = MenuCreator.createMenu(menu, appid, appsecret);
		
		System.out.println(token);
	}
}
