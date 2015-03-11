package com.lucasirc.servercatalog.dao;

import com.lucasirc.servercatalog.core.ServerCatalog;
import com.lucasirc.servercatalog.model.Application;
import com.lucasirc.servercatalog.model.Server;
import de.caluga.morphium.Morphium;
import de.caluga.morphium.MorphiumConfig;
import de.caluga.morphium.async.AsyncOperationCallback;
import de.caluga.morphium.async.AsyncOperationType;
import de.caluga.morphium.query.Query;

import java.util.*;

public class ServerDAO extends DefaultDAO<Server> {
    Morphium morphium;
    public ServerDAO() {
        morphium = ServerCatalog.morphium;
    }

    @Override
    public Server get(long id) {
        System.out.println("Buscando: " + id);
        Query<Server> query = morphium.createQueryFor(Server.class);

        Server server = query.f("id").eq(id).get();

        return server;
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

        if ( serverTmp.getId() == null || serverTmp.getId() == 0) {
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
        morphium.delete(server, new AsyncOperationCallback<Server>() {
            @Override
            public void onOperationSucceeded(AsyncOperationType type, Query<Server> q, long duration, List<Server> result, Server entity, Object... param) {
                System.out.println("Success! ");
            }

            @Override
            public void onOperationError(AsyncOperationType type, Query<Server> q, long duration, String error, Throwable t, Server entity, Object... param) {
                System.out.println("Error: " + error);
            }
        });

        return true;
    }
}
