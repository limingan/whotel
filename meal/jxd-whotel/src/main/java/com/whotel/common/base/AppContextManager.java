package com.whotel.common.base;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * spring application context manager
 *
 */
public class AppContextManager implements ApplicationContextAware {
	
	public static void loadApplicationContext() {
		try {
			String[] configFiles = {
					"classpath:applicationContext.xml",
					"classpath:applicationContext-database.xml"
			};
			appContext = new FileSystemXmlApplicationContext(configFiles);
		} catch(Exception e) {
			System.out.println("手动加载Spring配置文件出错：" + e);
			e.printStackTrace();
		}
	}
	
	private static ApplicationContext appContext;
	
	public static ApplicationContext getAppContext() {
		return appContext;
	}

	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		appContext = context;
	}
}
