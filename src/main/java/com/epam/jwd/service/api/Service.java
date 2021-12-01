package com.epam.jwd.service.api;

import com.epam.jwd.service.dto.AbstractDto;

import java.util.List;

public interface Service<T extends AbstractDto<K>, K> {
    T create(T entity);

    boolean update(T entity);

    boolean delete(T entity);

    T getById(K id);

    List<T> getAll();
}
