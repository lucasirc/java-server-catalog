package com.lucasirc.servercatalog.service;

import com.lucasirc.servercatalog.dao.ApplicationDAO;
import com.lucasirc.servercatalog.dao.DefaultDAO;
import com.lucasirc.servercatalog.model.Application;

public class ApplicationResourceService  extends ResourceService<Application> {

    public ApplicationResourceService(ResourceTransformer<Application> transformer) {
        super(transformer);
        dao = new ApplicationDAO();
    }
    public ApplicationResourceService(ResourceTransformer<Application> transformer, ApplicationDAO daoTmp) {
        super(transformer);
        this.dao = daoTmp;
    }

    @Override
    public DefaultDAO<Application> getDAO() {
        return dao;
    }
}
