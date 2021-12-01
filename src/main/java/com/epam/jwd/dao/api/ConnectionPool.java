package com.epam.jwd.dao.api;

import java.sql.Connection;

public interface ConnectionPool {
    void initialize();

    void shutdown();

    Connection takeConnection();

    void returnConnection(Connection connection);
}
