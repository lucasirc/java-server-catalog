package com.lucasirc.servercatalog.dao;

import com.lucasirc.servercatalog.model.Application;
import com.lucasirc.servercatalog.model.Server;
import de.caluga.morphium.Morphium;
import de.caluga.morphium.MorphiumSingleton;
import de.caluga.morphium.async.AsyncOperationCallback;
import de.caluga.morphium.async.AsyncOperationType;
import de.caluga.morphium.query.Query;
import org.apache.log4j.Logger;

import java.util.*;

public class ServerDAO extends DefaultDAO<Server> {

    public static final Logger log = Logger.getLogger(ApplicationDAO.class);

    Morphium morphium;
    public ServerDAO() {
        morphium = MorphiumSingleton.get();
    }

    @Override
    public Server get(long id) {
        log.debug("Buscando Server: " + id);
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
        if ( server.getApps() != null ) {
            for( int i =0; i < server.getApps().size(); i++) {
                Application app = server.getApps().get(i);
                appDao.save(app);
            }
        }
        morphium.store(server);

        return server;
    }



    public void delete( final Server server) {
        log.debug("Buscando Server: " + server.getId());
        morphium.delete(server, new AsyncOperationCallback<Server>() {
            @Override
            public void onOperationSucceeded(AsyncOperationType type, Query<Server> q, long duration, List<Server> result, Server entity, Object... param) {
                log.debug("Sucesso ao remover  " + server.getId());
            }

            @Override
            public void onOperationError(AsyncOperationType type, Query<Server> q, long duration, String error, Throwable t, Server entity, Object... param) {
                log.error("Erro ao remover  " + server.getId() + " Msg: " + error);
            }
        });

    }
}
