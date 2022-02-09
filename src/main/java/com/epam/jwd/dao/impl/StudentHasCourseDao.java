package com.epam.jwd.dao.impl;

import com.epam.jwd.dao.api.ConnectionPool;
import com.epam.jwd.dao.api.Dao;
import com.epam.jwd.dao.entity.StudentHasCourse;
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

/**
 * StudentHasCourseDao implementation class of Dao for StudentHasCourse with Integer id
 */
public class StudentHasCourseDao implements Dao<StudentHasCourse, Integer> {
    private static final Logger logger = LogManager.getLogger(StudentHasCourseDao.class);

    private static final String SQL_SAVE_RECORD = "INSERT INTO student_has_course(course_id, student_id, application_date) VALUES (?, ?, ?)";
    private static final String SQL_UPDATE_RECORD = "UPDATE student_has_course SET course_id=?, student_id=?, application_date=? WHERE id=?";
    private static final String SQL_DELETE_RECORD = "DELETE FROM student_has_course WHERE id=?";
    private static final String SQL_FIND_RECORD_BY_ID = "SELECT id, course_id, student_id, application_date FROM student_has_course WHERE id=?";
    private static final String SQL_FIND_RECORD_BY_STUDENT_ID = "SELECT id, course_id, student_id, application_date FROM student_has_course WHERE student_id=?";
    private static final String SQL_FIND_RECORD_BY_COURSE_ID = "SELECT id, course_id, student_id, application_date FROM student_has_course WHERE course_id=?";
    private static final String SQL_FIND_RECORD_BY_COURSE_AND_STUDENT_ID = "SELECT id, course_id, student_id, application_date FROM student_has_course WHERE course_id=? AND student_id=?";
    private static final String SQL_FIND_ALL_RECORDS = "SELECT id, course_id, student_id, application_date FROM student_has_course";

    private final ConnectionPool pool = ConnectionPoolImpl.getInstance();

    @Override
    public StudentHasCourse save(StudentHasCourse student) throws DaoException {
        Connection connection = pool.takeConnection();
        ResultSet resultSet = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SAVE_RECORD, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, student.getCourseId());
            preparedStatement.setInt(2, student.getStudentId());
            preparedStatement.setDate(3, student.getApplicationDate());
            preparedStatement.executeUpdate();

            resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                student.setId(resultSet.getInt(1));
            }
        } catch (SQLException e) {
            logger.error(DaoMessageException.SAVE_RECORD_STUDENT_HAS_COURSE_EXCEPTION + e);
            throw new DaoException(DaoMessageException.SAVE_RECORD_STUDENT_HAS_COURSE_EXCEPTION);
        } finally {
            closeResultSet(resultSet);
            pool.returnConnection(connection);
        }
        return student;
    }

    @Override
    public boolean update(StudentHasCourse student) throws DaoException {
        Connection connection = pool.takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_RECORD)) {
            preparedStatement.setInt(1, student.getCourseId());
            preparedStatement.setInt(2, student.getStudentId());
            preparedStatement.setDate(3, student.getApplicationDate());
            preparedStatement.setInt(4, student.getId());

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error(DaoMessageException.UPDATE_RECORD_STUDENT_HAS_COURSE_EXCEPTION + e);
            throw new DaoException(DaoMessageException.UPDATE_RECORD_STUDENT_HAS_COURSE_EXCEPTION);
        } finally {
            pool.returnConnection(connection);
        }
    }

    @Override
    public boolean delete(StudentHasCourse student) throws DaoException {
        Connection connection = pool.takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_RECORD)) {
            preparedStatement.setInt(1, student.getId());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error(DaoMessageException.DELETE_RECORD_STUDENT_HAS_COURSE_EXCEPTION + e);
            throw new DaoException(DaoMessageException.DELETE_RECORD_STUDENT_HAS_COURSE_EXCEPTION);
        } finally {
            pool.returnConnection(connection);
        }
    }

    @Override
    public StudentHasCourse findById(Integer id) throws DaoException {
        Connection connection = pool.takeConnection();
        StudentHasCourse record = null;
        ResultSet resultSet = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_RECORD_BY_ID)) {
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                record = new StudentHasCourse(resultSet.getInt(1),
                        resultSet.getInt(2),
                        resultSet.getInt(3),
                        resultSet.getDate(4));
            }
        } catch (SQLException e) {
            logger.error(DaoMessageException.FIND_RECORD_STUDENT_HAS_COURSE_BY_ID_EXCEPTION + e);
            throw new DaoException(DaoMessageException.FIND_RECORD_STUDENT_HAS_COURSE_BY_ID_EXCEPTION);
        } finally {
            closeResultSet(resultSet);
            pool.returnConnection(connection);
        }
        return record;
    }

    @Override
    public List<StudentHasCourse> findAll() throws DaoException {
        List<StudentHasCourse> studentList = new ArrayList<>();
        Connection connection = pool.takeConnection();
        ResultSet resultSet = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_ALL_RECORDS)) {
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                studentList.add(new StudentHasCourse(resultSet.getInt(1),
                        resultSet.getInt(2),
                        resultSet.getInt(3),
                        resultSet.getDate(4)));
            }
        } catch (SQLException e) {
            logger.error(DaoMessageException.FIND_ALL_RECORDS_STUDENT_HAS_COURSE_EXCEPTION + e);
            throw new DaoException(DaoMessageException.FIND_ALL_RECORDS_STUDENT_HAS_COURSE_EXCEPTION);
        } finally {
            closeResultSet(resultSet);
            pool.returnConnection(connection);
        }
        return studentList;
    }

    /**
     * Method which finds record by course and student
     *
     * @param courseId  - current course id
     * @param studentId - current student id
     * @return - true if record was found and otherwise false or exception
     * @throws DaoException - if entity wasn't found or SQL exception
     */
    public boolean findRecordByCourseIdAndStudentId(Integer courseId, Integer studentId) throws DaoException {
        Connection connection = pool.takeConnection();
        ResultSet resultSet = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_RECORD_BY_COURSE_AND_STUDENT_ID)) {
            preparedStatement.setInt(1, courseId);
            preparedStatement.setInt(2, studentId);
            resultSet = preparedStatement.executeQuery();
            return !resultSet.next();
        } catch (SQLException e) {
            logger.error(DaoMessageException.FIND_RECORD_BY_COURSE_ID_AND_STUDENT_ID_EXCEPTION + e);
            throw new DaoException(DaoMessageException.FIND_RECORD_BY_COURSE_ID_AND_STUDENT_ID_EXCEPTION);
        } finally {
            closeResultSet(resultSet);
            pool.returnConnection(connection);
        }
    }

    /**
     * Method which gets record by course and student
     *
     * @param courseId  - current course id
     * @param studentId - current student id
     * @return - StudentHasCourse entity record by current user and current course
     * @throws DaoException - if entity wasn't got or SQL exception
     */
    public StudentHasCourse getRecordByCourseIdAndStudentId(Integer courseId, Integer studentId) throws DaoException {
        Connection connection = pool.takeConnection();
        StudentHasCourse record = null;
        ResultSet resultSet = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_RECORD_BY_COURSE_AND_STUDENT_ID)) {
            preparedStatement.setInt(1, courseId);
            preparedStatement.setInt(2, studentId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                record = new StudentHasCourse(resultSet.getInt(1),
                        resultSet.getInt(2),
                        resultSet.getInt(3),
                        resultSet.getDate(4));
            }
        } catch (SQLException e) {
            logger.error(DaoMessageException.GET_RECORD_BY_COURSE_ID_AND_STUDENT_ID_EXCEPTION + e);
            throw new DaoException(DaoMessageException.GET_RECORD_BY_COURSE_ID_AND_STUDENT_ID_EXCEPTION);
        } finally {
            closeResultSet(resultSet);
            pool.returnConnection(connection);
        }
        return record;
    }

    /**
     * Method which gets record by student id
     *
     * @param studentId - current student id
     * @return - list of records courses with student id
     * @throws DaoException - if entity wasn't got or SQL exception
     */
    public List<StudentHasCourse> getRecordsByStudentId(Integer studentId) throws DaoException {
        Connection connection = pool.takeConnection();
        List<StudentHasCourse> studentList = new ArrayList<>();
        ResultSet resultSet = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_RECORD_BY_STUDENT_ID)) {
            preparedStatement.setInt(1, studentId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                studentList.add(new StudentHasCourse(resultSet.getInt(1),
                        resultSet.getInt(2),
                        resultSet.getInt(3),
                        resultSet.getDate(4)));
            }
        } catch (SQLException e) {
            logger.error(DaoMessageException.GET_RECORDS_STUDENT_ID_EXCEPTION + e);
            throw new DaoException(DaoMessageException.GET_RECORDS_STUDENT_ID_EXCEPTION);
        } finally {
            closeResultSet(resultSet);
            pool.returnConnection(connection);
        }
        return studentList;
    }

    /**
     * Method which gets record by course id
     *
     * @param courseId - current course id
     * @return - list of records students with course id
     * @throws DaoException - if entity wasn't got or SQL exception
     */
    public List<StudentHasCourse> getRecordsByCourseId(Integer courseId) throws DaoException {
        Connection connection = pool.takeConnection();
        List<StudentHasCourse> studentList = new ArrayList<>();
        ResultSet resultSet = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_RECORD_BY_COURSE_ID)) {
            preparedStatement.setInt(1, courseId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                studentList.add(new StudentHasCourse(resultSet.getInt(1),
                        resultSet.getInt(2),
                        resultSet.getInt(3),
                        resultSet.getDate(4)));
            }
        } catch (SQLException e) {
            logger.error(DaoMessageException.GET_RECORDS_BY_COURSE_ID_EXCEPTION + e);
            throw new DaoException(DaoMessageException.GET_RECORDS_BY_COURSE_ID_EXCEPTION);
        } finally {
            closeResultSet(resultSet);
            pool.returnConnection(connection);
        }
        return studentList;
    }
}
