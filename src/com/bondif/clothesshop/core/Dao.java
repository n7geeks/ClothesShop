package com.bondif.clothesshop.core;

import java.util.*;

public interface Dao<T> {
    public Collection<T> findAll();
    //public Collection<T> findAll(String searchString);
    public T findOne(long id);
    public void create(T entity);
    public void update(T entity);
    public void delete(T entity);
}
