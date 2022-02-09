package com.epam.jwd.dao.impl;

import com.epam.jwd.dao.api.ConnectionPool;
import com.epam.jwd.dao.api.Dao;
import com.epam.jwd.dao.entity.Account;
import com.epam.jwd.dao.entity.User;
import com.epam.jwd.dao.exception.DaoException;
import com.epam.jwd.dao.exception.DaoMessageException;
import com.epam.jwd.dao.impl.connectionPool.ConnectionPoolImpl;
import com.epam.jwd.service.passwordHashing.api.PasswordManager;
import com.epam.jwd.service.passwordHashing.impl.PasswordManagerImpl;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * UserDao implementation class of Dao for User with Integer id
 */
public class UserDao implements Dao<User, Integer> {
    private static final Logger logger = LogManager.getLogger(UserDao.class);

    private static final String SQL_SAVE_USER = "INSERT INTO user(login, password) VALUES (?, ?)";
    private static final String SQL_UPDATE_USER = "UPDATE user SET login=?, password=? WHERE id=?";
    private static final String SQL_DELETE_USER = "DELETE FROM user WHERE id=?";
    private static final String SQL_FIND_USER_BY_ID = "SELECT id, login, password FROM user WHERE id=?";
    private static final String SQL_FIND_USER_BY_LOGIN = "SELECT id, login, password FROM user WHERE login=?";
    private static final String SQL_CHECK_IF_EXISTS_BY_LOGIN = "SELECT EXISTS(SELECT id FROM user WHERE login = ?);";
    private static final String SQL_FIND_ALL_USERS = "SELECT id, login, password FROM user";

    private final ConnectionPool pool = ConnectionPoolImpl.getInstance();
    private final PasswordManager passwordManager = new PasswordManagerImpl();
    private static final AccountDao accountDao = new AccountDao();

    @Override
    public User save(User user) throws DaoException {
        Connection connection = pool.takeConnection();
        ResultSet resultSet = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SAVE_USER, Statement.RETURN_GENERATED_KEYS)) {
            connection.setAutoCommit(false);
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, passwordManager.encode(user.getPassword()));
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                user.setId(resultSet.getInt(1));
            }
            Account account = new Account(user.getId());
            accountDao.saveAccountAfterUser(account, connection);
            connection.commit();
            connection.setAutoCommit(true);
        } catch (DaoException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                logger.error(ex.getMessage() + ex);
                throw new DaoException(DaoMessageException.SAVE_USER_EXCEPTION);
            }
            logger.error(e.getMessage() + e);
            throw new DaoException(e.getMessage());
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                logger.error(ex.getMessage() + ex);
                throw new DaoException(DaoMessageException.SAVE_USER_EXCEPTION);
            }
            logger.error(DaoMessageException.SAVE_USER_EXCEPTION + e);
            throw new DaoException(DaoMessageException.SAVE_USER_EXCEPTION);
        } finally {
            closeResultSet(resultSet);
            pool.returnConnection(connection);
        }
        return user;
    }

    @Override
    public boolean update(User user) throws DaoException {
        Connection connection = pool.takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_USER)) {
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, passwordManager.encode(user.getPassword()));
            preparedStatement.setInt(3, user.getId());

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error(DaoMessageException.UPDATE_USER_EXCEPTION + e);
            throw new DaoException(DaoMessageException.UPDATE_USER_EXCEPTION);
        } finally {
            pool.returnConnection(connection);
        }
    }

    @Override
    public boolean delete(User user) throws DaoException {
        Connection connection = pool.takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_USER)) {
            connection.setAutoCommit(false);
            accountDao.deleteAfterUser(accountDao.getAccountByUserId(user.getId()), connection);
            preparedStatement.setInt(1, user.getId());
            int status = preparedStatement.executeUpdate();
            connection.commit();
            connection.setAutoCommit(true);
            return status > 0;
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                logger.error(ex.getMessage() + ex);
                throw new DaoException(DaoMessageException.DELETE_USER_EXCEPTION);
            }
            logger.error(DaoMessageException.DELETE_USER_EXCEPTION + e);
            throw new DaoException(DaoMessageException.DELETE_USER_EXCEPTION);
        } finally {
            pool.returnConnection(connection);
        }
    }

    @Override
    public User findById(Integer id) throws DaoException {
        Connection connection = pool.takeConnection();
        User user = null;
        ResultSet resultSet = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_USER_BY_ID)) {
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                user = new User(resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3));
            }
        } catch (SQLException e) {
            logger.error(DaoMessageException.FIND_USER_BY_ID_EXCEPTION + e);
            throw new DaoException(DaoMessageException.FIND_USER_BY_ID_EXCEPTION);
        } finally {
            closeResultSet(resultSet);
            pool.returnConnection(connection);
        }
        return user;
    }

    @Override
    public List<User> findAll() throws DaoException {
        List<User> userList = new ArrayList<>();
        Connection connection = pool.takeConnection();
        ResultSet resultSet = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_ALL_USERS)) {
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                userList.add(new User(resultSet.getInt(1),
                        resultSet.getString(2),
                        passwordManager.decode(resultSet.getString(3))));
            }
        } catch (SQLException e) {
            logger.error(DaoMessageException.FIND_ALL_USERS_EXCEPTION + e);
            throw new DaoException(DaoMessageException.FIND_ALL_USERS_EXCEPTION);
        } finally {
            closeResultSet(resultSet);
            pool.returnConnection(connection);
        }
        return userList;
    }

    /**
     * Method for finding User entity by login
     *
     * @param login - user login
     * @return - User entity
     * @throws DaoException - if it's unable to take query from DB
     */
    public User findByLogin(String login) throws DaoException {
        Connection connection = pool.takeConnection();
        User user = null;
        ResultSet resultSet = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_USER_BY_LOGIN)) {
            preparedStatement.setString(1, login);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                user = new User(resultSet.getInt(1),
                        resultSet.getString(2),
                        passwordManager.decode(resultSet.getString(3)));
            }
        } catch (SQLException e) {
            logger.error(DaoMessageException.FIND_USER_BY_LOGIN_EXCEPTION + e);
            throw new DaoException(DaoMessageException.FIND_USER_BY_LOGIN_EXCEPTION);
        } finally {
            closeResultSet(resultSet);
            pool.returnConnection(connection);
        }
        return user;
    }

    /**
     * Method to check an existing login
     *
     * @param login - user login
     * @return - true if login is free and false otherwise
     * @throws DaoException - if it's unable to take query from DB
     */
    public boolean checkIfLoginFree(String login) throws DaoException {
        Connection connection = pool.takeConnection();
        boolean isLoginFree = false;
        ResultSet resultSet = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_CHECK_IF_EXISTS_BY_LOGIN)) {
            preparedStatement.setString(1, login);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next() && resultSet.getInt(1) == 0) {
                isLoginFree = true;
            }
        } catch (SQLException e) {
            logger.error(DaoMessageException.CHECK_IF_LOGIN_IF_FREE_EXCEPTION + e);
            throw new DaoException(DaoMessageException.CHECK_IF_LOGIN_IF_FREE_EXCEPTION);
        } finally {
            closeResultSet(resultSet);
            pool.returnConnection(connection);
        }
        return isLoginFree;
    }
}