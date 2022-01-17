package com.epam.jwd.dao.impl;

import com.epam.jwd.dao.api.ConnectionPool;
import com.epam.jwd.dao.api.Dao;
import com.epam.jwd.dao.entity.Course;
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

public class CourseDao implements Dao<Course, Integer> {
    private static final Logger logger = LogManager.getLogger(CourseDao.class);

    private static final String SQL_SAVE_COURSE = "INSERT INTO course(title, description, start_date, end_date, course_status, teacher_id) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE_COURSE = "UPDATE course SET title=?, description=?, start_date=?, end_date=?, course_status=?, teacher_id=? WHERE id=?";
    private static final String SQL_DELETE_COURSE = "DELETE FROM course WHERE id=?";
    private static final String SQL_FIND_COURSE_BY_ID = "SELECT id, title, description, start_date, end_date, course_status, teacher_id FROM course WHERE id=?";
    private static final String SQL_FIND_COURSE_BY_TEACHER_ID = "SELECT id, title, description, start_date, end_date, course_status, teacher_id FROM course WHERE teacher_id=?";
    private static final String SQL_FIND_ALL_COURSES = "SELECT id, title, description, start_date, end_date, course_status, teacher_id FROM course";

    private final ConnectionPool pool = ConnectionPoolImpl.getInstance();

    @Override
    public Course save(Course course) {
        Connection connection = pool.takeConnection();
        ResultSet resultSet = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SAVE_COURSE, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, course.getTitle());
            preparedStatement.setString(2, course.getDescription());
            preparedStatement.setDate(3, course.getStartDate());
            preparedStatement.setDate(4, course.getEndDate());
            preparedStatement.setInt(5, course.getCourseStatus().getId());
            preparedStatement.setInt(6, course.getTeacherId());
            preparedStatement.executeUpdate();

            resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                course.setId(resultSet.getInt(1));
            }
        } catch (SQLException e) {
            logger.error(DaoMessageException.SAVE_COURSE_EXCEPTION + e);
            throw new DaoException(DaoMessageException.SAVE_COURSE_EXCEPTION);
        } finally {
            closeResultSet(resultSet);
            pool.returnConnection(connection);
        }
        return course;
    }

    @Override
    public boolean update(Course course) throws DaoException {
        Connection connection = pool.takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_COURSE)) {
            preparedStatement.setString(1, course.getTitle());
            preparedStatement.setString(2, course.getDescription());
            preparedStatement.setDate(3, course.getStartDate());
            preparedStatement.setDate(4, course.getEndDate());
            preparedStatement.setInt(5, course.getCourseStatus().getId());
            preparedStatement.setInt(6, course.getTeacherId());
            preparedStatement.setInt(7, course.getId());

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error(DaoMessageException.UPDATE_COURSE_EXCEPTION + e);
            throw new DaoException(DaoMessageException.UPDATE_COURSE_EXCEPTION);
        } finally {
            pool.returnConnection(connection);
        }
    }

    @Override
    public boolean delete(Course course) {
        Connection connection = pool.takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_COURSE)) {
            preparedStatement.setInt(1, course.getId());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error(DaoMessageException.DELETE_COURSE_EXCEPTION);
            throw new DaoException(DaoMessageException.DELETE_COURSE_EXCEPTION);
        } finally {
            pool.returnConnection(connection);
        }
    }

    @Override
    public Course findById(Integer id) {
        Connection connection = pool.takeConnection();
        Course course = null;
        ResultSet resultSet = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_COURSE_BY_ID)) {
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                course = new Course(resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getDate(4),
                        resultSet.getDate(5),
                        resultSet.getInt(6),
                        resultSet.getInt(7));
            }
        } catch (SQLException e) {
            logger.error(DaoMessageException.FIND_COURSE_BY_ID_EXCEPTION);
            throw new DaoException(DaoMessageException.FIND_COURSE_BY_ID_EXCEPTION);
        } finally {
            closeResultSet(resultSet);
            pool.returnConnection(connection);
        }
        return course;
    }

    @Override
    public List<Course> findAll() {
        List<Course> courseList = new ArrayList<>();
        Connection connection = pool.takeConnection();
        ResultSet resultSet = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_ALL_COURSES)) {
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                courseList.add(new Course(resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getDate(4),
                        resultSet.getDate(5),
                        resultSet.getInt(6),
                        resultSet.getInt(7)));
            }
        } catch (SQLException e) {
            logger.error(DaoMessageException.FIND_ALL_COURSES_EXCEPTION + e);
            throw new DaoException(DaoMessageException.FIND_ALL_COURSES_EXCEPTION);
        } finally {
            closeResultSet(resultSet);
            pool.returnConnection(connection);
        }
        return courseList;
    }

    public List<Course> findCoursesByTeacherId(Integer teacherId) {
        List<Course> courseList = new ArrayList<>();
        Connection connection = pool.takeConnection();
        ResultSet resultSet = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_COURSE_BY_TEACHER_ID)) {
            preparedStatement.setInt(1, teacherId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                courseList.add(new Course(resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getDate(4),
                        resultSet.getDate(5),
                        resultSet.getInt(6),
                        resultSet.getInt(7)));
            }
        } catch (SQLException e) {
            logger.error(DaoMessageException.FIND_COURSE_BY_TEACHER_ID + e);
            throw new DaoException(DaoMessageException.FIND_COURSE_BY_TEACHER_ID);
        } finally {
            closeResultSet(resultSet);
            pool.returnConnection(connection);
        }
        return courseList;
    }
}


