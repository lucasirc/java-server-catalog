package com.lucasirc.servercatalog.core;



import de.caluga.morphium.Morphium;
import de.caluga.morphium.MorphiumConfig;
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

    static {
        try {
            MorphiumConfig cfg = new MorphiumConfig();
            cfg.setDatabase("testdb");
            cfg.addHost("localhost", 27017);
            morphium = new Morphium(cfg);
        }catch (Exception e ) {
            e.printStackTrace();
            System.exit(1);
        }

    }

    public static void main(String[] args) throws IOException {
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

}
