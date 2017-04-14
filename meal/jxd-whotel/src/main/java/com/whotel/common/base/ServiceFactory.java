package com.whotel.common.base;

import org.springframework.context.ApplicationContext;


public class ServiceFactory {
	
	private static ApplicationContext appContext = AppContextManager.getAppContext();
	
    
    static {
    	if (appContext == null) { // 如果ApplicationContext还没有自动加载，就手动加载一次
    		AppContextManager.loadApplicationContext(); // 手动加载ApplicationContext
    		appContext = AppContextManager.getAppContext();
    	}
    }
}
