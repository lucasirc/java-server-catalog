package com.lucasirc.servercatalog.service;

import com.google.gson.Gson;
import com.lucasirc.servercatalog.dao.DefaultDAO;

import java.util.ArrayList;
import java.util.HashMap;
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

    public T update(long id, String content) {
        T entity = dao.get(id);

        if (entity == null) {
            return entity;
        }

        T obj = transformer.contentToEntity(id, content);

        T entitySaved = dao.save(obj);

        return entitySaved;
    }

    public List<Map> list(long offset, long max) {
        List<Map> list = new ArrayList<Map>();

        List<T> entities = dao.list(Long.valueOf(offset).intValue(), Long.valueOf(max).intValue());

        if ( entities != null ) {
            for (int i = 0; i < entities.size(); i++) {
                T server = entities.get(i);
                list.add(transformer.entityToMap(server));
            }
        }
        return list;
    }

    public T delete(long id) {
        try {
            T entity = dao.get(id);
            if ( entity == null ){
                return null;
            }

            dao.delete(entity);
            return entity;
        }catch (Exception e) {
            throw new RuntimeException("Error: cant delete " + type.getClass() + " record, id: " + id, e);
        }
    }

    public abstract DefaultDAO<T> getDAO();

}
