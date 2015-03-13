package com.lucasirc.servercatalog.dao;

import java.util.List;

public abstract class DefaultDAO<T> {

    T type;

    public abstract T get(long id) ;

    public abstract List<T> list(int offset, int max);

    public abstract T save(T server);

    public abstract void delete(T entity);

}
