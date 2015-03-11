package com.lucasirc.servercatalog.service;

import com.google.gson.Gson;
import com.lucasirc.servercatalog.dao.DefaultDAO;
import java.util.Map;

public abstract class ResourceService<T>{

    protected ResourceTransformer<T> transformer;
    protected DefaultDAO<T> dao;

    private T type;

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

    public T create(String content) {
        T obj = transformer.contentToEntity(content);

        T entitySaved = dao.save(obj);

        return entitySaved;
    }

    public abstract DefaultDAO<T> getDAO();

}
