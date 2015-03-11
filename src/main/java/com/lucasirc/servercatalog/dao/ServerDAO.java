package com.lucasirc.servercatalog.dao;

import com.lucasirc.servercatalog.core.ServerCatalog;
import com.lucasirc.servercatalog.model.Application;
import com.lucasirc.servercatalog.model.Server;
import de.caluga.morphium.Morphium;
import de.caluga.morphium.MorphiumConfig;
import de.caluga.morphium.query.Query;

import java.util.*;

public class ServerDAO extends DefaultDAO<Server> {
    Morphium morphium;
    public ServerDAO() {
        morphium = ServerCatalog.morphium;
    }
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
    public List<Server> list(int offset, int max) {
        try {
            Query<Server> query=morphium.createQueryFor(Server.class);

            query = query.skip(offset).limit(max);

            return query.asList();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao consultar servers, offset: " + offset + ", max: " + max, e);
        }
    }

    @Override
    public Server save(Server serverTmp) {
        Server server;

        if ( serverTmp.getId() != 0) {
            serverTmp.setId(new Random().nextLong());
            server = serverTmp;
        } else {
            server = get(serverTmp.getId());

            server.setHostname(serverTmp.getHostname());
            server.setApps(serverTmp.getApps());
        }

        ApplicationDAO appDao = new ApplicationDAO();
        for( int i =0; i < server.getApps().size(); i++) {
            Application app = server.getApps().get(i);
            appDao.save(app);
        }


        morphium.store(server);

        return server;
    }



    public boolean delete(Server server) {
        System.out.println("Removendo: " + server.getId());
        map.remove(server.getId());
        return true;
    }
}
