package com.lucasirc.servercatalog.service.v1;

import com.google.gson.Gson;
import com.lucasirc.servercatalog.model.Application;
import com.lucasirc.servercatalog.service.ApplicationResourceService;
import com.lucasirc.servercatalog.service.ResourceTransformer;

import java.util.HashMap;
import java.util.Map;

public class ApplicationV1Transformer implements ResourceTransformer<Application> {


    @Override
    public Application contentToEntity(String content) {
        return new Gson().fromJson(content, Application.class);
    }

    @Override
    public Map entityToMap(Application app) {
        Map<String, Object> map = new HashMap<String, Object>();

        map.put("id", app.getId());
        map.put("owner", app.getOwner());
        map.put("name", app.getName());

        return map;
    }
}
