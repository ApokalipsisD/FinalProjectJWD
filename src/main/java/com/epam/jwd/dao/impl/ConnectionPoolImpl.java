package com.epam.jwd.dao.impl;

import com.epam.jwd.dao.exception.ConnectionPoolException;
import com.epam.jwd.dao.api.ConnectionPool;
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

    private final BlockingQueue<ProxyConnection> availableConnections = new ArrayBlockingQueue<>(POOL_SIZE);
    private final BlockingQueue<ProxyConnection> usedConnections = new ArrayBlockingQueue<>(POOL_SIZE);

    private static ConnectionPool instance;
    private boolean initialized;

    private ConnectionPoolImpl() {
        initialize();
    }
    //todo check synchronized methods
    public synchronized static ConnectionPool getInstance() {
        if (Objects.isNull(instance)) {
            instance = new ConnectionPoolImpl();
        }
        return instance;
    }

    // todo boolean method if it needs
    @Override
    public synchronized void initialize() {
        if (!initialized) {
            try {
                Class.forName(DRIVER);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            // todo try catch?
            for (int i = 0; i < POOL_SIZE; i++) {
                try {
                    createConnection();
                } catch (ConnectionPoolException e) {
                    // log
                }
            }
            initialized = true;
        }
    }

    private void createConnection() throws ConnectionPoolException {
        try {
            Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            ProxyConnection proxyConnection = new ProxyConnection(connection, this);
            availableConnections.add(proxyConnection);
        } catch (SQLException e) {
            logger.error(CONNECTION_FAILED);
            throw new ConnectionPoolException(CONNECTION_FAILED);
        }
    }

    @Override
    public void shutdown() {
        if (initialized) {
            closeConnections(availableConnections);
            closeConnections(usedConnections);
            availableConnections.clear();
            usedConnections.clear();
            initialized = false;
        }
    }

    private void closeConnections(BlockingQueue<ProxyConnection> connections) {
        connections.forEach(this::closeConnection);
    }

    private void closeConnection(ProxyConnection proxyConnection) {
        try {
            proxyConnection.realCloseConnection();
        } catch (SQLException e) {
            // todo log and custom exception
        }
    }

    @Override
    public synchronized Connection takeConnection() throws InterruptedException {
        while (availableConnections.isEmpty()) {
            this.wait();
        }
        final ProxyConnection connection = availableConnections.poll();
        usedConnections.add(connection);
        return connection;
    }

    @Override
    public synchronized void returnConnection(Connection connection) {
        if (usedConnections.remove((ProxyConnection) connection)) {
            availableConnections.add((ProxyConnection) connection);
            this.notifyAll();
        } else {
            //todo log and something else
        }
    }
}
