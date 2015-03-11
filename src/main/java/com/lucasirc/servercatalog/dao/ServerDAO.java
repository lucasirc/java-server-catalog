package com.lucasirc.servercatalog.dao;

import com.lucasirc.servercatalog.model.Application;
import com.lucasirc.servercatalog.model.Server;

import java.util.*;

public class ServerDAO extends DefaultDAO<Server> {

    static final Map<Long, Server> map = new HashMap<Long, Server>();

    static {
        ApplicationDAO appDao = new ApplicationDAO();
        List<Application> apps = new ArrayList<Application>();
        apps.add(appDao.get(1l));
        apps.add(appDao.get(2l));

        Server server = new Server(1l, "gameserver");
        server.setApps(apps);

        map.put(1l, server);

        server = new Server(2l, "emailserver");

        apps = new ArrayList<Application>();
        apps.add(appDao.get(3l));
        apps.add(appDao.get(4l));
        server.setApps(apps);

        map.put(2l, server);
    }

    @Override
    public Server get(long id) {
        System.out.println("Buscando: " + id);
        return map.get(id);
    }

    @Override
    public List<Server> list(long offset, long max) {
        Iterator<Map.Entry<Long, Server>> iterator = map.entrySet().iterator();

        List<Server> servers = new ArrayList<Server>();
        while (iterator.hasNext()) {

            servers.add(iterator.next().getValue());
        }
        return servers;
    }

    @Override
    public Server save(Server serverTmp) {
        Server server;

        if ( serverTmp.getId() != 0) {
            serverTmp.setId(new Random().nextLong());
            map.put(serverTmp.getId(), serverTmp);
            server = serverTmp;
        } else {
            server = get(serverTmp.getId());

            server.setHostname(serverTmp.getHostname());
            server.setApps(serverTmp.getApps());

        }
        return server;
    }



    public boolean delete(Server server) {
        System.out.println("Removendo: " + server.getId());
        map.remove(server.getId());
        return true;
    }
}
