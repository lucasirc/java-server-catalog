package com.lucasirc.servercatalog.core;


import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;


public class ServerCatalogContext implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServerCatalog.configureLogging();
        ServerCatalog.connectDb();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ServerCatalog.closeDbConnection();
    }
}
