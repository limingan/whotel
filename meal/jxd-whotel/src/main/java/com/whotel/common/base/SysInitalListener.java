package com.whotel.common.base;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class SysInitalListener implements ServletContextListener{

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		SysCache.getInstance().initialize();
	}

}
