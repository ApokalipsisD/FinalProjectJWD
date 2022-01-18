package com.epam.jwd.dao.impl;

import com.epam.jwd.dao.api.ConnectionPool;
import com.epam.jwd.dao.api.Dao;
import com.epam.jwd.dao.entity.Account;
import com.epam.jwd.dao.exception.DaoException;
import com.epam.jwd.dao.exception.DaoMessageException;
import com.epam.jwd.dao.impl.connectionPool.ConnectionPoolImpl;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AccountDao implements Dao<Account, Integer> {
    private static final Logger logger = LogManager.getLogger(AccountDao.class);

    private static final String SQL_SAVE_ACCOUNT = "INSERT INTO account(first_name, last_name, email, birth_date, user_id) VALUES (?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE_ACCOUNT = "UPDATE account SET first_name=?, last_name=?, email=?, birth_date=?, role_id=? WHERE id=?";
    private static final String SQL_UPDATE_ROLE_ID = "UPDATE account SET role_id=? WHERE id=?";
    private static final String SQL_DELETE_ACCOUNT = "DELETE FROM account WHERE id=?";
    private static final String SQL_FIND_ACCOUNT_BY_ID = "SELECT id, first_name, last_name, email, birth_date, role_id, user_id FROM account WHERE id=?";
    private static final String SQL_FIND_ALL_ACCOUNTS = "SELECT id, first_name, last_name, email, birth_date, role_id, user_id FROM account";
    private static final String SQL_FIND_ACCOUNT_BY_USER_ID = "SELECT id, first_name, last_name, email, birth_date, role_id, user_id FROM account WHERE user_id=?";
    private static final String SQL_FIND_ALL_TEACHERS = "SELECT id, first_name, last_name, email, birth_date, role_id, user_id FROM account WHERE role_id=2";

    private final ConnectionPool pool = ConnectionPoolImpl.getInstance();

    @Override
    public Account save(Account account) throws DaoException {
        Connection connection = pool.takeConnection();
        ResultSet resultSet = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SAVE_ACCOUNT, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, account.getFirstName());
            preparedStatement.setString(2, account.getLastName());
            preparedStatement.setString(3, account.getEmail());
            preparedStatement.setDate(4, account.getBirthDate());
            preparedStatement.setInt(5, account.getUserId());
            preparedStatement.executeUpdate();

            resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                account.setId(resultSet.getInt(1));
            }
        } catch (SQLException e) {
            logger.error(DaoMessageException.SAVE_ACCOUNT_EXCEPTION + e);
            throw new DaoException(DaoMessageException.SAVE_ACCOUNT_EXCEPTION);
        } finally {
            closeResultSet(resultSet);
            pool.returnConnection(connection);
        }
        return account;
    }

    @Override
    public boolean update(Account account) throws DaoException {
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
            logger.error(DaoMessageException.UPDATE_ACCOUNT_EXCEPTION + e);
            throw new DaoException(DaoMessageException.UPDATE_ACCOUNT_EXCEPTION);
        } finally {
            pool.returnConnection(connection);
        }
    }

    @Override
    public boolean delete(Account account) throws DaoException {
        Connection connection = pool.takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_ACCOUNT)) {
            preparedStatement.setInt(1, account.getId());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error(DaoMessageException.DELETE_ACCOUNT_EXCEPTION + e);
            throw new DaoException(DaoMessageException.DELETE_ACCOUNT_EXCEPTION);
        } finally {
            pool.returnConnection(connection);
        }
    }

    @Override
    public Account findById(Integer id) throws DaoException {
        Connection connection = pool.takeConnection();
        Account account = null;
        ResultSet resultSet = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_ACCOUNT_BY_ID)) {
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                account = new Account(resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getDate(5),
                        resultSet.getInt(6),
                        resultSet.getInt(7));
            }
        } catch (SQLException e) {
            logger.error(DaoMessageException.FIND_ACCOUNT_BY_ID_EXCEPTION + e);
            throw new DaoException(DaoMessageException.FIND_ACCOUNT_BY_ID_EXCEPTION);
        } finally {
            closeResultSet(resultSet);
            pool.returnConnection(connection);
        }
        return account;
    }

    @Override
    public List<Account> findAll() throws DaoException {
        List<Account> accountList = new ArrayList<>();
        Connection connection = pool.takeConnection();
        ResultSet resultSet = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_ALL_ACCOUNTS)) {
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                accountList.add(new Account(resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getDate(5),
                        resultSet.getInt(6),
                        resultSet.getInt(7)));
            }
        } catch (SQLException e) {
            logger.error(DaoMessageException.FIND_ALL_ACCOUNTS_EXCEPTION + e);
            throw new DaoException(DaoMessageException.FIND_ALL_ACCOUNTS_EXCEPTION);
        } finally {
            closeResultSet(resultSet);
            pool.returnConnection(connection);
        }
        return accountList;
    }

    public Account getAccountByUserId(Integer id) throws DaoException {
        Connection connection = pool.takeConnection();
        Account account = null;
        ResultSet resultSet = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_ACCOUNT_BY_USER_ID)) {
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                account = new Account(resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getDate(5),
                        resultSet.getInt(6),
                        resultSet.getInt(7));
            }
        } catch (SQLException e) {
            logger.error(DaoMessageException.GET_ACCOUNT_BY_USER_ID_EXCEPTION + e);
            throw new DaoException(DaoMessageException.GET_ACCOUNT_BY_USER_ID_EXCEPTION);
        } finally {
            closeResultSet(resultSet);
            pool.returnConnection(connection);
        }
        return account;
    }

    public List<Account> findAllTeachers() throws DaoException {
        List<Account> accountList = new ArrayList<>();
        Connection connection = pool.takeConnection();
        ResultSet resultSet = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_ALL_TEACHERS)) {
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                accountList.add(new Account(resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getDate(5),
                        resultSet.getInt(6),
                        resultSet.getInt(7)));
            }
        } catch (SQLException e) {
            logger.error(DaoMessageException.FIND_ALL_TEACHERS_EXCEPTION + e);
            throw new DaoException(DaoMessageException.FIND_ALL_TEACHERS_EXCEPTION);
        } finally {
            closeResultSet(resultSet);
            pool.returnConnection(connection);
        }
        return accountList;
    }

    public void updateRole(Integer accountId, Integer roleId) throws DaoException {
        Connection connection = pool.takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_ROLE_ID)) {
            preparedStatement.setInt(1, roleId);
            preparedStatement.setInt(2, accountId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(DaoMessageException.UPDATE_ROLE_EXCEPTION + e);
            throw new DaoException(DaoMessageException.UPDATE_ROLE_EXCEPTION);
        } finally {
            pool.returnConnection(connection);
        }
    }
}
