package com.lucasirc.servercatalog.service;

import java.util.Map;

public interface ResourceTransformer<T> {

    public T mapToEntity(Map map);
    public Map entityToMap(T entity);
}
