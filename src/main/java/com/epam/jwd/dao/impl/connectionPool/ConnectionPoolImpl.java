package com.epam.jwd.dao.impl.connectionPool;

import com.epam.jwd.dao.api.ConnectionPool;
import com.epam.jwd.dao.exception.DaoException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public final class ConnectionPoolImpl implements ConnectionPool {
    private static final Logger logger = LogManager.getLogger(ConnectionPoolImpl.class);

    private static final int POOL_SIZE = 5;
    private static final String DB_URL = "jdbc:mysql://localhost:3306/jwd";
    private static final String USER = "root";
    private static final String PASSWORD = "1235";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String CONNECTION_FAILED = "Connection has been failed";

    private final BlockingQueue<ProxyConnection> availableConnections;
    private final BlockingQueue<ProxyConnection> usedConnections;

    private static ConnectionPool instance;
    private boolean initialized;

    private ConnectionPoolImpl() {
        availableConnections = new ArrayBlockingQueue<>(POOL_SIZE);
        usedConnections = new ArrayBlockingQueue<>(POOL_SIZE);
    }

    public static ConnectionPool getInstance() {
        synchronized (ConnectionPool.class) {
            if (Objects.isNull(instance)) {
                instance = new ConnectionPoolImpl();
            }
        }
        return instance;
    }

    @Override
    public synchronized void initialize() throws DaoException {
        if (!initialized) {
            createConnection();
            initialized = true;
        }
    }

    private void createConnection() throws DaoException {
        try {
            for (int i = 0; i < POOL_SIZE; i++) {
                Class.forName(DRIVER);
                Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
                ProxyConnection proxyConnection = new ProxyConnection(connection, this);
                availableConnections.put(proxyConnection);
            }
        } catch (SQLException | ClassNotFoundException e) {
            logger.error(CONNECTION_FAILED);
            throw new DaoException(CONNECTION_FAILED);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error(e.getMessage());
        }
    }

    @Override
    public void shutdown() throws DaoException {
        if (initialized) {
            closeConnections(availableConnections);
            closeConnections(usedConnections);
            availableConnections.clear();
            usedConnections.clear();
            initialized = false;
        }
    }

    private void closeConnections(BlockingQueue<ProxyConnection> connections) throws DaoException {
        for (ProxyConnection connection : connections) {
            closeConnection(connection);
        }
    }

    private void closeConnection(ProxyConnection proxyConnection) throws DaoException {
        try {
            proxyConnection.realCloseConnection();
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new DaoException(e.getMessage());
        }
    }

    @Override
    public synchronized Connection takeConnection() {
        ProxyConnection connection = null;
        try {
            connection = availableConnections.take();
            usedConnections.put(connection);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error(e.getMessage());
        }
        return connection;
    }

    @Override
    public synchronized void returnConnection(Connection connection) {
        try {
            availableConnections.put(usedConnections.take());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error(e.getMessage());
        }
    }
}
