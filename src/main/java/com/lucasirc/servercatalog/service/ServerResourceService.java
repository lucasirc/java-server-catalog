package com.lucasirc.servercatalog.service;

import com.lucasirc.servercatalog.dao.DefaultDAO;
import com.lucasirc.servercatalog.dao.ServerDAO;
import com.lucasirc.servercatalog.model.Server;

public class ServerResourceService extends ResourceService<Server> {

    public ServerResourceService(ResourceTransformer<Server> transformer) {
        super(transformer);
        dao = new ServerDAO();
    }

    public ServerResourceService(ResourceTransformer<Server> transformer, ServerDAO dao) {
        super(transformer);
        this.dao = dao;
    }
    @Override
    public DefaultDAO<Server> getDAO() {
        return dao;
    }


}
