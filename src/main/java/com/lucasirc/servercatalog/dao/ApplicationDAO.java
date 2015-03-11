package com.lucasirc.servercatalog.dao;


import com.lucasirc.servercatalog.model.Application;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class ApplicationDAO extends DefaultDAO<Application> {


    static final Map<Long, Application> map = new HashMap<Long, Application>();
    static {
        map.put(1l, new Application(1l, "Game 1", "jose@gmail.com"));
        map.put(2l, new Application(2l, "Game 2", "joaos@gmkl.com"));
        map.put(3l, new Application(3l, "Email 2", "amand@gmkl.com"));
        map.put(4l, new Application(4l, "Email 2", "pria@gmkl.com"));
    }

    @Override
    public Application get(long id) {
        System.out.println("Buscando App: " + id);
        return map.get(id);
    }

    @Override
    public List<Application> list(long offset, long max) {
        return null;
    }

    @Override
    public Application save(Application appTmp) {
        Application app;

        if ( appTmp.getId() != 0) {
            appTmp.setId(new Random().nextLong());
            map.put(appTmp.getId(), appTmp);
            app = appTmp;
        } else {
            app = get(appTmp.getId());

            app.setName(appTmp.getName());
            app.setOwner(appTmp.getOwner());

        }
        return app;
    }

    @Override
    public boolean delete(Application entity) {
        System.out.println("Removendo: " + entity.getId());
        map.remove(entity.getId());
        return true;
    }

}
