package com.lucasirc.servercatalog.core;

import java.io.InputStream;
import java.util.Properties;

public class ServerCatalogConfig {

    private static Properties config;
    private static APP_ENV env;

    public static String getConfig(String key) {
        if (config == null) {
            loadConfig();
        }

        return (String)config.get(key);
    }

    private static void loadConfig() {
        try {
            config = new Properties();

            String file = "config-" + getEMV().name() + ".properties";
            System.out.println("Init Config: "+file);

            InputStream input = ServerCatalogConfig.class.getClassLoader().getResourceAsStream(file);

            if ( input == null) {
                throw new RuntimeException("Erro ao carregar arquivo de configuração, file: " + file);
            }

            config.load(input);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("Erro ao carregar arquivo de configuração", e);
        }
    }

    public static APP_ENV getEMV(){
        if (env == null){
            String envVariable = System.getenv("ENV");
            System.out.println("ENV: " + envVariable);
            env = (envVariable != null && !"".equals(envVariable)) ? APP_ENV.valueOf(envVariable) : APP_ENV.DEV;
        }

        return env;
    }

    public enum APP_ENV {
        PROD, DEV
    }
}

