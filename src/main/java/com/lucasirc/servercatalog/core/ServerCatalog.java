package com.lucasirc.servercatalog.core;



import de.caluga.morphium.Morphium;
import de.caluga.morphium.MorphiumConfig;
import de.caluga.morphium.MorphiumSingleton;
import org.apache.log4j.BasicConfigurator;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.URI;


public class ServerCatalog {


    public static final String host = "localhost";
    public static final int port = 8080;
    public static final String link = "http://" + host + ":" + port+"/";
    public static Morphium morphium;

    public static void main(String[] args) throws IOException {
        connectDb();
        BasicConfigurator.configure();
        HttpServer server = startServer();

        System.in.read();
        server.shutdownNow();
    }

    public static HttpServer startServer() {
        System.out.println("Init Server...");

        URI uri = URI.create(link);
        ResourceConfig config = new ResourceConfig().packages("com.lucasirc.servercatalog");

        HttpServer server = GrizzlyHttpServerFactory.createHttpServer(uri, config);

        System.out.println("Server running: "+ link);
        return server;
    }
    public static void connectDb() {
        try {
        //Configure your connection to Mongo
            if (!MorphiumSingleton.isConfigured()) {
                MorphiumConfig cfg = new MorphiumConfig();
                cfg.setDatabase("testdb");
                cfg.addHost("localhost", 27017);
                cfg.setWriteCacheTimeout(100);
                MorphiumSingleton.setConfig(cfg);
                MorphiumSingleton.get();
            }
        }catch (Exception e ) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
