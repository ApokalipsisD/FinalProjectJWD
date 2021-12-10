package com.epam.jwd.service.api;

import com.epam.jwd.service.dto.AbstractDto;
import com.epam.jwd.service.exception.ServiceException;

import java.util.List;

public interface Service<T extends AbstractDto<K>, K> {
    T create(T entity) throws ServiceException;

    boolean update(T entity) throws ServiceException;

    boolean delete(T entity) throws ServiceException;

    T getById(K id) throws ServiceException;

    List<T> getAll();
}
