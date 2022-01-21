package com.epam.jwd.dao.api;

import com.epam.jwd.dao.exception.DaoException;

import java.sql.Connection;

public interface ConnectionPool {
    void initialize() throws DaoException;

    void shutdown() throws DaoException;

    Connection takeConnection();

    void returnConnection(Connection connection);
}
