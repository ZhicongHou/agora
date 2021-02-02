package com.hzcong.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.sql.DriverManager;

public class AppContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent event) {
        ServletContext context = event.getServletContext();
        String rootPath = context.getRealPath("/");
        System.setProperty("rootPath", rootPath);

        //logger.info("global setting,rootPath:{}",rootPath);
        //logger.info("deployed on architecture:{},operation System:{},version:{}",
        //       System.getProperty("os.arch"), System.getProperty("os.name"),
        //   System.getProperty("os.version"));
        //logger.info("app startup completed....");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        try{
            while(DriverManager.getDrivers().hasMoreElements()){
                DriverManager.deregisterDriver(DriverManager.getDrivers().nextElement());
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
