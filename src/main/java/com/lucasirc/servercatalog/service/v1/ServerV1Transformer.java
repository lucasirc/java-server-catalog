package com.lucasirc.servercatalog.service.v1;

import com.lucasirc.servercatalog.model.Application;
import com.lucasirc.servercatalog.model.Server;
import com.lucasirc.servercatalog.service.ResourceTransformer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServerV1Transformer implements ResourceTransformer<Server> {


    public Map entityToMap(Server server){
        Map<String, Object> serverMap = new HashMap<String, Object>();

        serverMap.put("hostname", server.getHostname());
        serverMap.put("id", server.getId());

        List<Map> apps = new ArrayList<Map>();
        serverMap.put("apps", apps);

        for (int i = 0; i <server.getApps().size(); i++) {
            Application app = server.getApps().get(i);
            Map map = new ApplicationV1Transformer().entityToMap(app);
            apps.add(map);
        }

        return serverMap;
    }

    @Override
    public Server mapToEntity(Map map) {
        return null;
    }

}
