package com.whotel.weixin.api;

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
		String appid = "wx81de3fa7817d9e69";
		String appsecret = "2241912a39488c7510b733e564dadf4b";

		
		CommonButton btn11 = new CommonButton();
		btn11.setName("菜单一4");
		btn11.setUrl("http://www.baidu.com");
		CommonButton btn12 = new CommonButton();
		btn12.setName("菜单二7个汉字");
		btn12.setUrl("http://www.mi.com");

		ComplexButton mainBtn3 = new ComplexButton();
		mainBtn3.setName("父菜单");

		CommonButton btn33 = new CommonButton();
		btn33.setName("子菜单一7汉字");
		btn33.setUrl("http://www.163.com");
		mainBtn3.addButton(btn33);

		CommonButton btn45 = new CommonButton();
		btn45.setName("子菜单二8个汉字");
		btn45.setUrl("http://www.hao123.com");
		mainBtn3.addButton(btn45);

		// MenuCreator.deleteMenu(appId, appSecret);
		Menu menu = new Menu();
		menu.addButton(btn11);
		menu.addButton(btn12);
		menu.addButton(mainBtn3);
		// String appid2= "wxa1f46474b4f7f84b";
		// String appsecret2= "f8d59b952ab070c1600d906191e3eed5";
		BaseToken token = MenuCreator.createMenu(menu, appid, appsecret);
		
		System.out.println(token);
	}
}
