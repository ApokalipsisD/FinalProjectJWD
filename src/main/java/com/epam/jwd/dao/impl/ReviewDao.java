package com.epam.jwd.dao.impl;

import com.epam.jwd.dao.api.ConnectionPool;
import com.epam.jwd.dao.api.Dao;
import com.epam.jwd.dao.entity.Review;
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

public class ReviewDao implements Dao<Review, Integer> {
    private static final Logger logger = LogManager.getLogger(ReviewDao.class);

    private static final String SQL_SAVE_REVIEW = "INSERT INTO review(course_id, student_id, grade, attendance, review) VALUES (?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE_REVIEW = "UPDATE review SET course_id=?, student_id=?, grade=?, attendance=?, review=? WHERE id=?";
    private static final String SQL_DELETE_REVIEW = "DELETE FROM review WHERE id=?";
    private static final String SQL_FIND_REVIEW_BY_ID = "SELECT id, course_id, student_id, grade, attendance, review FROM review WHERE id=?";
    private static final String SQL_FIND_REVIEW_BY_COURSE_AND_STUDENT_ID = "SELECT id, course_id, student_id, grade, attendance, review FROM review WHERE course_id=? AND student_id=?";
    private static final String SQL_FIND_REVIEW_BY_COURSE_ID = "SELECT id, course_id, student_id, grade, attendance, review FROM review WHERE course_id=?";
    private static final String SQL_FIND_ALL_REVIEWS = "SELECT id, course_id, student_id, grade, attendance, review FROM review";

    private final ConnectionPool pool = ConnectionPoolImpl.getInstance();

    @Override
    public Review save(Review review) {
        Connection connection = pool.takeConnection();
        ResultSet resultSet = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SAVE_REVIEW, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, review.getCourseId());
            preparedStatement.setInt(2, review.getStudentId());
            preparedStatement.setInt(3, review.getGrade());
            preparedStatement.setInt(4, review.getAttendance());
            preparedStatement.setString(5, review.getReview());
            preparedStatement.executeUpdate();

            resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                review.setId(resultSet.getInt(1));
            }
        } catch (SQLException e) {
            logger.error(DaoMessageException.SAVE_REVIEW_EXCEPTION + e);
            throw new DaoException(DaoMessageException.SAVE_REVIEW_EXCEPTION);
        } finally {
            closeResultSet(resultSet);
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
            preparedStatement.setString(5, review.getReview());
            preparedStatement.setInt(6, review.getId());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error(DaoMessageException.UPDATE_REVIEW_EXCEPTION + e);
            throw new DaoException(DaoMessageException.UPDATE_REVIEW_EXCEPTION);
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
            logger.error(DaoMessageException.DELETE_REVIEW_EXCEPTION + e);
            throw new DaoException(DaoMessageException.DELETE_REVIEW_EXCEPTION);
        } finally {
            pool.returnConnection(connection);
        }
    }

    @Override
    public Review findById(Integer id) {
        Connection connection = pool.takeConnection();
        Review review = null;
        ResultSet resultSet = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_REVIEW_BY_ID)) {
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                review = new Review(resultSet.getInt(1),
                        resultSet.getInt(2),
                        resultSet.getInt(3),
                        resultSet.getInt(4),
                        resultSet.getInt(5),
                        resultSet.getString(6));
            }
        } catch (SQLException e) {
            logger.error(DaoMessageException.FIND_REVIEW_BY_ID_EXCEPTION + e);
            throw new DaoException(DaoMessageException.FIND_REVIEW_BY_ID_EXCEPTION);
        } finally {
            closeResultSet(resultSet);
            pool.returnConnection(connection);
        }
        return review;
    }

    @Override
    public List<Review> findAll() {
        List<Review> reviewList = new ArrayList<>();
        Connection connection = pool.takeConnection();
        ResultSet resultSet = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_ALL_REVIEWS)) {
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                reviewList.add(new Review(resultSet.getInt(1),
                        resultSet.getInt(2),
                        resultSet.getInt(3),
                        resultSet.getInt(4),
                        resultSet.getInt(5),
                        resultSet.getString(6)));
            }
        } catch (SQLException e) {
            logger.error(DaoMessageException.FIND_ALL_REVIEWS_EXCEPTION + e);
            throw new DaoException(DaoMessageException.FIND_ALL_REVIEWS_EXCEPTION);
        } finally {
            closeResultSet(resultSet);
            pool.returnConnection(connection);
        }
        return reviewList;
    }

    public boolean findReviewByCourseIdAndStudentId(Integer courseId, Integer studentId) {
        Connection connection = pool.takeConnection();
        ResultSet resultSet = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_REVIEW_BY_COURSE_AND_STUDENT_ID)) {
            preparedStatement.setInt(1, courseId);
            preparedStatement.setInt(2, studentId);
            resultSet = preparedStatement.executeQuery();
            return !resultSet.next();
        } catch (SQLException e) {
            logger.error(DaoMessageException.FIND_REVIEW_BY_STUDENT_COURSE_ID_EXCEPTION + e);
            throw new DaoException(DaoMessageException.FIND_REVIEW_BY_STUDENT_COURSE_ID_EXCEPTION);
        } finally {
            closeResultSet(resultSet);
            pool.returnConnection(connection);
        }
    }

    public List<Review> getReviewsByCourseId(Integer courseId) {
        Connection connection = pool.takeConnection();
        List<Review> reviewList = new ArrayList<>();
        ResultSet resultSet = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_REVIEW_BY_COURSE_ID)) {
            preparedStatement.setInt(1, courseId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                reviewList.add(new Review(resultSet.getInt(1),
                        resultSet.getInt(2),
                        resultSet.getInt(3),
                        resultSet.getInt(4),
                        resultSet.getInt(5),
                        resultSet.getString(6)));
            }
        } catch (SQLException e) {
            logger.error(DaoMessageException.FIND_REVIEW_BY_COURSE_ID_EXCEPTION + e);
            throw new DaoException(DaoMessageException.FIND_REVIEW_BY_COURSE_ID_EXCEPTION);
        } finally {
            closeResultSet(resultSet);
            pool.returnConnection(connection);
        }
        return reviewList;
    }
}
