package com.epam.jwd.dao.api;

import com.epam.jwd.dao.exception.DaoException;

import java.sql.Connection;

/**
 * This interface provides connectionPool description with useful methods which provide
 * user with initializing pool, shutdown poll, take and return connections to the pool
 */
public interface ConnectionPool {

    /**
     * Method which create the connection pool with several connections
     *
     * @throws DaoException - if connection can't be accepted or JDBC driver is unavailable
     */
    void initialize() throws DaoException;

    /**
     * Method which close all connections and stops connection pool and opportunity to use connections
     *
     * @throws DaoException - if connection can't be accepted or JDBC driver is unavailable
     */
    void shutdown() throws DaoException;

    /**
     * Method which provides user with option to take connection from pool
     *
     * @return - taken connection from pool of class {@link Connection}
     */
    Connection takeConnection();

    /**
     * Method which returns taken connection to the pool
     *
     * @param connection - connection to return to pool
     */
    void returnConnection(Connection connection);
}
