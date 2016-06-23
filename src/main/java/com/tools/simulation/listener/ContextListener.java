package com.tools.simulation.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.tools.simulation.config.ConfigManager;

public class ContextListener implements ServletContextListener{

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		try {
			ConfigManager.init();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
