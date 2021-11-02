package com.epam.jwd.dao;

import com.epam.jwd.dao.api.ConnectionPool;
import com.epam.jwd.dao.entity.Account;
import com.epam.jwd.dao.entity.Role;
import com.epam.jwd.dao.impl.ConnectionPoolImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class mainDao {

    public static void main(String[] args) throws InterruptedException, SQLException {
        ConnectionPool connectionPool = ConnectionPoolImpl.getInstance();
        Connection connection = connectionPool.takeConnection();
//        Statement statement = connection.prepareStatement("SELECT * FROM account");
        Statement statement = connection.createStatement();
        ResultSet set = statement.executeQuery("""
                    SELECT\s
                      account.id,  first_name, second_name, email, birth_date, role_name
                    FROM
                        account
                            LEFT JOIN
                        role ON role_id = role.id;""");
        while (set.next()){
            Role role = Role.valueOf(set.getString(6));
            System.out.println(new Account(set.getInt(1), set.getString(2),
                    set.getString(3), set.getString(4), set.getDate(5),
                    role));
        }

        connectionPool.returnConnection(connection);
        connectionPool.shutdown();
        set.close();
    }
}
