package com.lucasirc.servercatalog.dao;

import java.util.List;

public abstract class DefaultDAO<T> {

    T type;

    public abstract T get(long id) ;

    public abstract List<T> list(long offset, long max);

    public abstract T save(T server);
}
