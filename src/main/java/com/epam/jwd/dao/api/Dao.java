package com.epam.jwd.dao.api;

import com.epam.jwd.dao.entity.Entity;

import java.util.List;

/**
 * @param <T> - entity to be handled
 * @param <K> - the type of the id
 */
public interface Dao<T extends Entity<K>, K> {
    T save(T entity);

    T update(T entity);

    void delete(T entity);

    T findById(K id);

    List<T> findAll();
}
