package com.lucasirc.servercatalog.dao;


import com.lucasirc.servercatalog.core.ServerCatalog;
import com.lucasirc.servercatalog.model.Application;
import com.lucasirc.servercatalog.model.Server;
import de.caluga.morphium.Morphium;
import de.caluga.morphium.MorphiumConfig;
import de.caluga.morphium.MorphiumSingleton;
import de.caluga.morphium.async.AsyncOperationCallback;
import de.caluga.morphium.async.AsyncOperationType;
import de.caluga.morphium.query.Query;
import org.apache.log4j.Logger;
import org.glassfish.jersey.message.internal.XmlCollectionJaxbProvider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class ApplicationDAO extends DefaultDAO<Application> {
    Morphium morphium;

    public static final Logger log = Logger.getLogger(ApplicationDAO.class);

    public ApplicationDAO() {
        morphium = MorphiumSingleton.get();
    }

    @Override
    public Application get(long id) {
        log.debug("Buscando App: " + id);
        Query<Application> query = morphium.createQueryFor(Application.class);

        Application app = query.f("id").eq(id).get();

        return app;
    }

    @Override
    public List<Application> list(int offset, int max) {
        try {
            Query<Application> query=morphium.createQueryFor(Application.class);

            query = query.skip(offset).limit(max);

            return query.asList();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao consultar apps, offset: " + offset + ", max: " + max, e);
        }
    }

    @Override
    public Application save(Application appTmp) {
        Application app;

        if ( appTmp.getId() == null || appTmp.getId() == 0 ) {
            // @TODO melhorar para gerar id de outra forma, dessa forma pode ter rapeticao
            appTmp.setId(new Random().nextLong());
            app = appTmp;
        } else {
            app = get(appTmp.getId());

            app.setName(appTmp.getName());
            app.setOwner(appTmp.getOwner());
        }
        morphium.store(app);

        return app;
    }

    @Override
    public void delete(final Application app) {
        log.debug("Removendo App: " + app.getId());

        morphium.delete(app , new AsyncOperationCallback<Application>() {
            @Override
            public void onOperationSucceeded(AsyncOperationType type, Query<Application> q, long duration, List<Application> result, Application entity, Object... param) {
                log.debug("Sucesso ao remover  " + app.getId());
            }

            @Override
            public void onOperationError(AsyncOperationType type, Query<Application> q, long duration, String error, Throwable t, Application entity, Object... param) {
                log.error("Erro ao remover  " + app.getId() + " Msg: " + error);
            }
        });
    }

}
