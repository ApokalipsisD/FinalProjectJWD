package com.epam.jwd.dao.impl;

import com.epam.jwd.dao.api.ConnectionPool;
import com.epam.jwd.dao.api.Dao;
import com.epam.jwd.dao.entity.Account;
import com.epam.jwd.dao.impl.connectionPool.ConnectionPoolImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AccountDao implements Dao<Account, Integer> {
    private static final String SQL_SAVE_ACCOUNT = "INSERT INTO account(first_name, last_name, email, birth_date, role_id, user_id) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE_ACCOUNT = "UPDATE account SET first_name=?, last_name=?, email=?, birth_date=?, role_id=?, user_id=? WHERE id=?";
    private static final String SQL_DELETE_ACCOUNT = "DELETE FROM account WHERE id=?";
    private static final String SQL_FIND_ACCOUNT_BY_ID = "SELECT id, first_name, last_name, email, birth_date, role_id, user_id FROM account WHERE id=?";
    private static final String SQL_FIND_ALL_ACCOUNTS = "SELECT id, first_name, last_name, email, birth_date, role_id, user_id FROM account";

    private final ConnectionPool pool = ConnectionPoolImpl.getInstance();

    @Override
    public Account save(Account account) {
        Connection connection = pool.takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SAVE_ACCOUNT, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, account.getFirstName());
            preparedStatement.setString(2, account.getLastName());
            preparedStatement.setString(3, account.getEmail());
            preparedStatement.setDate(4, account.getBirthDate());
            preparedStatement.setInt(5, account.getRoleId());
            preparedStatement.setInt(6, account.getUserId());
            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                account.setId(resultSet.getInt(1));
            }
            resultSet.close();
        } catch (SQLException e) {
            //
            e.printStackTrace();
        } finally {
            pool.returnConnection(connection);
        }
        return account;
    }

    @Override
    public boolean update(Account account) {
        Connection connection = pool.takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_ACCOUNT)) {
            preparedStatement.setString(1, "Artemka");
            preparedStatement.setString(2, account.getLastName());
            preparedStatement.setString(3, account.getEmail());
            preparedStatement.setDate(4, account.getBirthDate());
            preparedStatement.setInt(5, account.getRoleId());
            preparedStatement.setInt(6, account.getUserId());
            preparedStatement.setInt(7, 16);

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            pool.returnConnection(connection);
        }
    }

    @Override
    public boolean delete(Account account) {
        Connection connection = pool.takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_ACCOUNT)) {
            preparedStatement.setInt(1, account.getId());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            //
            return false;
        } finally {
            pool.returnConnection(connection);
        }
    }

    @Override
    public Account findById(Integer id) {
        Connection connection = pool.takeConnection();
        Account account = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_ACCOUNT_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                account = new Account(resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getDate(5),
                        resultSet.getInt(6),
                        resultSet.getInt(7));
            }
            resultSet.close();
        } catch (SQLException e) {
            //
        } finally {
            pool.returnConnection(connection);
        }
        return account;
    }

    @Override
    public List<Account> findAll() {
        List<Account> accountList = new ArrayList<>();
        Connection connection = pool.takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_ALL_ACCOUNTS)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                accountList.add(new Account(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),
                        resultSet.getString(4), resultSet.getDate(5), resultSet.getInt(6), resultSet.getInt(7)));
            }
            resultSet.close();
        } catch (SQLException e) {
            //
        } finally {
            pool.returnConnection(connection);
        }
        return accountList;
    }
}
