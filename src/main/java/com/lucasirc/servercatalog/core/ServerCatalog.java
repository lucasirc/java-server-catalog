package com.lucasirc.servercatalog.core;



import de.caluga.morphium.Morphium;
import de.caluga.morphium.MorphiumConfig;
import de.caluga.morphium.MorphiumSingleton;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;


public class ServerCatalog {

    public static final Logger log = Logger.getLogger(ServerCatalog.class);
    public static final String host = "localhost";
    public static final int port = 8081;
    public static final String link = "http://" + host + ":" + port+"/";

    public static void main(String[] args) throws IOException {
        configureLogging();
        connectDb();
        HttpServer server = startServer();

        System.in.read();
        server.shutdownNow();

        closeDbConnection();

    }

    public static HttpServer startServer() {
        log.info("Init Server...");

        URI uri = URI.create(link);
        ResourceConfig config = new ResourceConfig().packages("com.lucasirc.servercatalog");
        HttpServer server = GrizzlyHttpServerFactory.createHttpServer(uri, config);


        log.info("Server running: "+ link);
        return server;
    }
    public static void connectDb() {
        try {
        //Configure your connection to Mongo
            if (!MorphiumSingleton.isConfigured()) {
                MorphiumConfig cfg = new MorphiumConfig();

                String dbName = ServerCatalogConfig.getConfig("mongodb.name");
                String user = ServerCatalogConfig.getConfig("mongodb.user");


                String passwd = ServerCatalogConfig.getConfig("mongodb.password");

                int port = Integer.valueOf(ServerCatalogConfig.getConfig("mongodb.port"));
                String host = ServerCatalogConfig.getConfig("mongodb.host");

                log.info("MongoDb config: " + "dbname: " +dbName + ", host: " + host + ", port:"+ port + ", user: " + user);


                if(user != null )
                    cfg.setMongoLogin(user);


                if (passwd != null)
                    cfg.setMongoPassword(passwd);

                cfg.setDatabase(dbName);
                cfg.addHost(host, port);
                cfg.setWriteCacheTimeout(100);

                MorphiumSingleton.setConfig(cfg);
                MorphiumSingleton.get();
            }
        }catch (Exception e ) {
            log.error("Erro ao inicar conexao com banco", e);
            e.printStackTrace();
            System.exit(1);
        }
    }
    public static void closeDbConnection() {
        log.info("Close db connection.");
        MorphiumSingleton.get().close();
    }
    public static void configureLogging() {
        //BasicConfigurator.configure();
        try {
            InputStream config = ServerCatalog.class.getClassLoader().getResourceAsStream("log4j.properties");
            PropertyConfigurator.configure(config);

        } catch(Exception e){
            BasicConfigurator.configure();
            log.error("Erro ao iniciar configuração de log", e);
        }
    }
}
