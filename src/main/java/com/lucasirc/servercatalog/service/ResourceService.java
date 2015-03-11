package com.lucasirc.servercatalog.service;

import com.lucasirc.servercatalog.dao.DefaultDAO;
import java.util.Map;

public abstract class ResourceService<T>{

    ResourceTransformer<T> transformer;
    DefaultDAO<T> dao;

    public ResourceService(ResourceTransformer<T> transformer) {
        this.transformer = transformer;
    }

    public Map get(long id) {
        DefaultDAO<T> dao = getDAO();
        T entity = dao.get(id);

        if (entity != null) {
            return transformer.entityToMap(entity);
        }

        return null;
    }

    public abstract DefaultDAO<T> getDAO();

}
