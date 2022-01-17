package com.epam.jwd.dao.api;

import com.epam.jwd.dao.entity.Entity;
import com.epam.jwd.dao.exception.DaoException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

/**
 * @param <T> - entity to be handled
 * @param <K> - the type of the id
 */
public interface Dao<T extends Entity<K>, K> {
    T save(T entity) throws DaoException;

    boolean update(T entity) throws DaoException;

    boolean delete(T entity) throws DaoException;

    T findById(K id) throws DaoException;

    List<T> findAll() throws DaoException;

    default void closeResultSet(ResultSet resultSet) {
        //log
        if (Objects.nonNull(resultSet)) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                //log.error
            }
        }
    }
}
