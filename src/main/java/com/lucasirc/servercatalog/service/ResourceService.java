package com.lucasirc.servercatalog.service;

import com.google.gson.Gson;
import com.lucasirc.servercatalog.dao.DefaultDAO;

import java.util.ArrayList;
import java.util.List;
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

    public List<Map> list(long offset, long max) {
        List<Map> list = new ArrayList<Map>();

        List<T> servers = dao.list(offset, max);

        for( int i = 0; i < servers.size(); i++) {
            T server = servers.get(i);
            list.add( transformer.entityToMap(server) );
        }
        return list;
    }

    public boolean delete(long id) {
        try {
            T entity = dao.get(id);

            return dao.delete(entity);

        }catch (Exception e) {
            throw new RuntimeException("Error: cant delete server", e);
        }
    }

    public abstract DefaultDAO<T> getDAO();

}
