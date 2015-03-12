package com.lucasirc.servercatalog.service;

import java.util.Map;

public interface ResourceTransformer<T> {

    public T contentToEntity(String content);
    public T contentToEntity(long id,String content);
    public Map entityToMap(T entity);
}
