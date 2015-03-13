package com.lucasirc.servercatalog.service;

import com.lucasirc.servercatalog.service.v1.ApplicationV1Transformer;
import com.lucasirc.servercatalog.service.v1.ServerV1Transformer;
import org.apache.log4j.Logger;

public class ResourceFactory {

    public static final Logger log = Logger.getLogger(ResourceFactory.class);

    public static final String DEFAULT_VERSION = "v1";

    public static ServerResourceService getServerService(String version) {
        log.debug("Server Version: " + version);
        if ( version == null || DEFAULT_VERSION.equals(version)) {
            ServerV1Transformer transformer = new ServerV1Transformer();
            return new ServerResourceService(transformer);
        }
        return null;
    }

    public static ApplicationResourceService getAppResourceService(String version) {
        log.debug("Application Version: " + version);
        if ( version == null || DEFAULT_VERSION.equals(version)) {
            ApplicationV1Transformer transformer = new ApplicationV1Transformer();
            return new ApplicationResourceService(transformer);
        }
        return null;
    }

}

