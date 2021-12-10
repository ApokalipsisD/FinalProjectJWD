package com.epam.jwd.dao.impl;

import com.epam.jwd.dao.api.ConnectionPool;
import com.epam.jwd.dao.api.Dao;
import com.epam.jwd.dao.entity.User;
import com.epam.jwd.dao.impl.connectionPool.ConnectionPoolImpl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDao implements Dao<User, Integer> {
    private static final String SQL_SAVE_USER = "INSERT INTO user(login, password) VALUES (?, ?)";
    private static final String SQL_UPDATE_USER = "UPDATE user SET login=?, password=? WHERE id=?";
    private static final String SQL_DELETE_USER = "DELETE FROM user WHERE id=?";
    private static final String SQL_FIND_USER_BY_ID = "SELECT id, login, password FROM user WHERE id=?";
    private static final String SQL_FIND_USER_BY_LOGIN = "SELECT id, login, password FROM user WHERE login=?";
    private static final String SQL_CHECK_IF_EXISTS_BY_LOGIN = "SELECT EXISTS(SELECT id FROM user WHERE login = ?);";

    private static final String SQL_FIND_ALL_USERS = "SELECT id, login, password FROM user";

    private final ConnectionPool pool = ConnectionPoolImpl.getInstance();

    private static final AccountDao accountDao = new AccountDao();

    //todo transactions and add to account
    @Override
    public User save(User user) {
        Connection connection = pool.takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SAVE_USER, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                user.setId(resultSet.getInt(1));
            }
            if (!accountDao.saveAfterUser(connection, user)) {
                throw new SQLException();
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            pool.returnConnection(connection);
        }
        return user;
    }

    //todo substitution of entered values or not
    @Override
    public boolean update(User user) {
        Connection connection = pool.takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_USER)) {
            preparedStatement.setString(1, "uka");
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setInt(3, 20);

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            pool.returnConnection(connection);
        }
    }

    @Override
    public boolean delete(User user) {
        Connection connection = pool.takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_USER)) {
            preparedStatement.setInt(1, user.getId());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            //
            return false;
        } finally {
            pool.returnConnection(connection);
        }
    }

    @Override
    public User findById(Integer id) {
        Connection connection = pool.takeConnection();
        User user = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_USER_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                user = new User(resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3));
            }
            resultSet.close();
        } catch (SQLException e) {
            //
        } finally {
            pool.returnConnection(connection);
        }
        return user;
    }

    @Override
    public List<User> findAll() {
        List<User> userList = new ArrayList<>();
        Connection connection = pool.takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_ALL_USERS)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                userList.add(new User(resultSet.getInt(1),
                        resultSet.getString(2), resultSet.getString(3)));
            }
            resultSet.close();
        } catch (SQLException e) {
            //
        } finally {
            pool.returnConnection(connection);
        }
        return userList;
    }

    public User findByLogin(String login) {
        Connection connection = pool.takeConnection();
        User user = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_USER_BY_LOGIN)) {
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                user = new User(resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3));
            }
            resultSet.close();
        } catch (SQLException e) {
            //
        } finally {
            pool.returnConnection(connection);
        }
        return user;
    }

    public boolean checkIfLoginFree(String login) {
        Connection connection = pool.takeConnection();
        boolean isLoginFree = false;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_CHECK_IF_EXISTS_BY_LOGIN)) {
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next() && resultSet.getInt(1) == 0) {
                isLoginFree = true;
            }
//            while (resultSet.next()) {
//                System.out.println(resultSet.getInt(1));
//                user = new User(resultSet.getInt(1),
//                        resultSet.getString(2),
//                        resultSet.getString(3));
//            }
            resultSet.close();
        } catch (SQLException e) {
            //
        } finally {
            pool.returnConnection(connection);
        }
        return isLoginFree;
//        return Objects.isNull(findByLogin(login));
    }
}