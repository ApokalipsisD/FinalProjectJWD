package com.epam.jwd.dao.impl;

import com.epam.jwd.dao.api.ConnectionPool;
import com.epam.jwd.dao.api.Dao;
import com.epam.jwd.dao.entity.Review;
import com.epam.jwd.dao.impl.connectionPool.ConnectionPoolImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ReviewDao implements Dao<Review, Integer> {
    private static final String SQL_SAVE_REVIEW = "INSERT INTO review(course_id, student_id, grade, attendance, review) VALUES (?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE_REVIEW = "UPDATE review SET course_id=?, student_id=?, grade=?, attendance=?, review=? WHERE id=?";
    private static final String SQL_DELETE_REVIEW = "DELETE FROM review WHERE id=?";
    private static final String SQL_FIND_REVIEW_BY_ID = "SELECT id, course_id, student_id, grade, attendance, review FROM review WHERE id=?";
    private static final String SQL_FIND_ALL_REVIEWS = "SELECT id, course_id, student_id, grade, attendance, review FROM review";

    private final ConnectionPool pool = ConnectionPoolImpl.getInstance();

    @Override
    public Review save(Review review) {
        Connection connection = pool.takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SAVE_REVIEW, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, review.getCourseId());
            preparedStatement.setInt(2, review.getStudentId());
            preparedStatement.setInt(3, review.getGrade());
            preparedStatement.setInt(4, review.getAttendance());
            preparedStatement.setString(5, review.getReview());
            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                review.setId(resultSet.getInt(1));
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            pool.returnConnection(connection);
        }
        return review;
    }

    @Override
    public boolean update(Review review) {
        Connection connection = pool.takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_REVIEW)) {
            preparedStatement.setInt(1, review.getCourseId());
            preparedStatement.setInt(2, review.getStudentId());
            preparedStatement.setInt(3, review.getGrade());
            preparedStatement.setInt(4, review.getAttendance());
            preparedStatement.setString(5, "Too hard to change something in other columns. Navi 1:0");
            preparedStatement.setInt(6, 2);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            pool.returnConnection(connection);
        }
    }

    @Override
    public boolean delete(Review review) {
        Connection connection = pool.takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_REVIEW)) {
            preparedStatement.setInt(1, review.getId());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            //
            return false;
        } finally {
            pool.returnConnection(connection);
        }
    }

    @Override
    public Review findById(Integer id) {
        Connection connection = pool.takeConnection();
        Review review = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_REVIEW_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                review = new Review(resultSet.getInt(1),
                        resultSet.getInt(2),
                        resultSet.getInt(3),
                        resultSet.getInt(4),
                        resultSet.getInt(5),
                        resultSet.getString(6));
            }
            resultSet.close();
        } catch (SQLException e) {
            //
        } finally {
            pool.returnConnection(connection);
        }
        return review;
    }

    @Override
    public List<Review> findAll() {
        List<Review> reviewList = new ArrayList<>();
        Connection connection = pool.takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_ALL_REVIEWS)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                reviewList.add(new Review(resultSet.getInt(1),
                        resultSet.getInt(2), resultSet.getInt(3),
                        resultSet.getInt(4), resultSet.getInt(5),
                        resultSet.getString(6)));
            }
            resultSet.close();
        } catch (SQLException e) {
            //
        } finally {
            pool.returnConnection(connection);
        }
        return reviewList;
    }
}
