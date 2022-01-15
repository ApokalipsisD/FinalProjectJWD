package com.epam.jwd.dao.impl;

import com.epam.jwd.dao.api.ConnectionPool;
import com.epam.jwd.dao.api.Dao;
import com.epam.jwd.dao.entity.Account;
import com.epam.jwd.dao.entity.User;
import com.epam.jwd.dao.impl.connectionPool.ConnectionPoolImpl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDao implements Dao<Account, Integer> {
    private static final String SQL_SAVE_ACCOUNT_AFTER_USER = "INSERT INTO account(user_id) VALUES (?)";
    private static final String SQL_SAVE_ACCOUNT = "INSERT INTO account(first_name, last_name, email, birth_date, role_id, user_id) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE_ACCOUNT = "UPDATE account SET first_name=?, last_name=?, email=?, birth_date=?, role_id=? WHERE id=?";
    private static final String SQL_UPDATE_ROLE_ID = "UPDATE account SET role_id=? WHERE id=?";

    private static final String SQL_DELETE_ACCOUNT = "DELETE FROM account WHERE id=?";
    private static final String SQL_FIND_ACCOUNT_BY_ID = "SELECT id, first_name, last_name, email, birth_date, role_id, user_id FROM account WHERE id=?";
    private static final String SQL_FIND_ALL_ACCOUNTS = "SELECT id, first_name, last_name, email, birth_date, role_id, user_id FROM account";
    private static final String SQL_FIND_ACCOUNT_BY_USER_ID = "SELECT id, first_name, last_name, email, birth_date, role_id, user_id FROM account WHERE user_id=?";
    private static final String SQL_FIND_ALL_TEACHERS = "SELECT id, first_name, last_name, email, birth_date, role_id, user_id FROM account WHERE role_id=2";

    private final ConnectionPool pool = ConnectionPoolImpl.getInstance();

    public boolean saveAfterUser(Connection connection, User user){
        boolean isCreateAccount = false;
//        Connection connection = pool.takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SAVE_ACCOUNT_AFTER_USER, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setInt(1, user.getId());
            preparedStatement.executeUpdate();
            isCreateAccount = true;

//            ResultSet resultSet = preparedStatement.getGeneratedKeys();
//            if (resultSet.next()) {
//                account.setId(resultSet.getInt(1));
//            }
//
//            while (resultSet.next()) {
//                account = new Account(resultSet.getInt(1),
//                        resultSet.getString(2),
//                        resultSet.getString(3),
//                        resultSet.getString(4),
//                        resultSet.getDate(5),
//                        resultSet.getInt(6),
//                        resultSet.getInt(7));
//            }
//            resultSet.close();
        } catch (SQLException e) {
            //
            e.printStackTrace();
        }
        return isCreateAccount;
    }

    @Override
    public Account save(Account account) {
        Connection connection = pool.takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SAVE_ACCOUNT, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, account.getFirstName());
            preparedStatement.setString(2, account.getLastName());
            preparedStatement.setString(3, account.getEmail());
            preparedStatement.setDate(4, account.getBirthDate());
            preparedStatement.setInt(5, account.getRole().getId());
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
            preparedStatement.setString(1, account.getFirstName());
            preparedStatement.setString(2, account.getLastName());
            preparedStatement.setString(3, account.getEmail());
            preparedStatement.setDate(4, account.getBirthDate());
            preparedStatement.setInt(5, account.getRole().getId());
            preparedStatement.setInt(6, account.getUserId());
            preparedStatement.setInt(6, account.getId());

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

    public Account getAccountByUserId(Integer id){
        Connection connection = pool.takeConnection();
        Account account = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_ACCOUNT_BY_USER_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
//            ResultSet resultSet = preparedStatement.getGeneratedKeys();
//            if (resultSet.next()) {
//                account.setId(resultSet.getInt(1));
//            }
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

    public List<Account> findAllTeachers() {
        List<Account> accountList = new ArrayList<>();
        Connection connection = pool.takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_ALL_TEACHERS)) {
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

    public void updateRole(Integer accountId, Integer roleId) {
        Connection connection = pool.takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_ROLE_ID)) {
            preparedStatement.setInt(1, roleId);
            preparedStatement.setInt(2, accountId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            pool.returnConnection(connection);
        }
    }

}
