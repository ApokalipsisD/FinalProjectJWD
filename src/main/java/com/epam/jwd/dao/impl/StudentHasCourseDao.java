package com.epam.jwd.dao.impl;

import com.epam.jwd.dao.api.ConnectionPool;
import com.epam.jwd.dao.api.Dao;
import com.epam.jwd.dao.entity.StudentHasCourse;
import com.epam.jwd.dao.impl.connectionPool.ConnectionPoolImpl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentHasCourseDao implements Dao<StudentHasCourse, Integer> {
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
    public StudentHasCourse save(StudentHasCourse student) {
        Connection connection = pool.takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SAVE_RECORD, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, student.getCourseId());
            preparedStatement.setInt(2, student.getStudentId());
            preparedStatement.setDate(3, student.getApplicationDate());
            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                student.setId(resultSet.getInt(1));
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            pool.returnConnection(connection);
        }
        return student;
    }

    @Override
    public boolean update(StudentHasCourse student) {
        Connection connection = pool.takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_RECORD)) {
            preparedStatement.setInt(1, student.getCourseId());
            preparedStatement.setInt(2, student.getStudentId());
            preparedStatement.setDate(3, student.getApplicationDate());
            preparedStatement.setInt(4, student.getId());

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            pool.returnConnection(connection);
        }
    }

    @Override
    public boolean delete(StudentHasCourse student) {
        Connection connection = pool.takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_RECORD)) {
            preparedStatement.setInt(1, student.getId());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            //
            return false;
        } finally {
            pool.returnConnection(connection);
        }
    }

    @Override
    public StudentHasCourse findById(Integer id) {
        Connection connection = pool.takeConnection();
        StudentHasCourse record = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_RECORD_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                record = new StudentHasCourse(resultSet.getInt(1),
                        resultSet.getInt(2),
                        resultSet.getInt(3),
                        resultSet.getDate(4));
            }
            resultSet.close();
        } catch (SQLException e) {
            //
        } finally {
            pool.returnConnection(connection);
        }
        return record;
    }

    @Override
    public List<StudentHasCourse> findAll() {
        List<StudentHasCourse> studentList = new ArrayList<>();
        Connection connection = pool.takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_ALL_RECORDS)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                studentList.add(new StudentHasCourse(resultSet.getInt(1),
                        resultSet.getInt(2), resultSet.getInt(3),
                        resultSet.getDate(4)));
            }
            resultSet.close();
        } catch (SQLException e) {
            //
        } finally {
            pool.returnConnection(connection);
        }
        return studentList;
    }

    public boolean findRecordByCourseIdAndStudentId(Integer courseId, Integer studentId) {
        Connection connection = pool.takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_RECORD_BY_COURSE_AND_STUDENT_ID)) {
            preparedStatement.setInt(1, courseId);
            preparedStatement.setInt(2, studentId);
            ResultSet resultSet = preparedStatement.executeQuery();
            return !resultSet.next();
        } catch (SQLException e) {
            //
            return false;
        } finally {
            pool.returnConnection(connection);
        }
    }

    public StudentHasCourse getRecordByCourseIdAndStudentId(Integer courseId, Integer studentId) {
        Connection connection = pool.takeConnection();
        StudentHasCourse record = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_RECORD_BY_COURSE_AND_STUDENT_ID)) {
            preparedStatement.setInt(1, courseId);
            preparedStatement.setInt(2, studentId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                record = new StudentHasCourse(resultSet.getInt(1),
                        resultSet.getInt(2),
                        resultSet.getInt(3),
                        resultSet.getDate(4));
            }
            resultSet.close();
        } catch (SQLException e) {
            //
        } finally {
            pool.returnConnection(connection);
        }
        return record;
    }

    public List<StudentHasCourse> getRecordsByStudentId(Integer studentId) {
        Connection connection = pool.takeConnection();
        List<StudentHasCourse> studentList = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_RECORD_BY_STUDENT_ID)) {
            preparedStatement.setInt(1, studentId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                studentList.add(new StudentHasCourse(resultSet.getInt(1),
                        resultSet.getInt(2), resultSet.getInt(3),
                        resultSet.getDate(4)));
            }
            resultSet.close();
        } catch (SQLException e) {
            //
        } finally {
            pool.returnConnection(connection);
        }
        return studentList;
    }

    public List<StudentHasCourse> getRecordsByCourseId(Integer courseId) {
        Connection connection = pool.takeConnection();
        List<StudentHasCourse> studentList = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_RECORD_BY_COURSE_ID)) {
            preparedStatement.setInt(1, courseId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                studentList.add(new StudentHasCourse(resultSet.getInt(1),
                        resultSet.getInt(2), resultSet.getInt(3),
                        resultSet.getDate(4)));
            }
            resultSet.close();
        } catch (SQLException e) {
            //
        } finally {
            pool.returnConnection(connection);
        }
        return studentList;
    }
}
