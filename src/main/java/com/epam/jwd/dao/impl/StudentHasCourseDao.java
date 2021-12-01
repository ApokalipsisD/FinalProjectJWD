package com.epam.jwd.dao.impl;

import com.epam.jwd.dao.api.ConnectionPool;
import com.epam.jwd.dao.api.Dao;
import com.epam.jwd.dao.entity.StudentHasCourse;
import com.epam.jwd.dao.impl.connectionPool.ConnectionPoolImpl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class StudentHasCourseDao implements Dao<StudentHasCourse, Integer> {
    private static final String SQL_SAVE_STUDENT = "INSERT INTO student_has_course(course_id, student_id, application_date) VALUES (?, ?, ?)";
    private static final String SQL_UPDATE_STUDENT = "UPDATE student_has_course SET course_id=?, student_id=?, application_date=? WHERE id=?";
    private static final String SQL_DELETE_STUDENT = "DELETE FROM student_has_course WHERE id=?";
    private static final String SQL_FIND_STUDENT_BY_ID = "SELECT id, course_id, student_id, application_date FROM student_has_course WHERE id=?";
    private static final String SQL_FIND_ALL_STUDENTS = "SELECT id, course_id, student_id, application_date FROM student_has_course";

    private final ConnectionPool pool = ConnectionPoolImpl.getInstance();

    @Override
    public StudentHasCourse save(StudentHasCourse student) {
        Connection connection = pool.takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SAVE_STUDENT, Statement.RETURN_GENERATED_KEYS)) {
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
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_STUDENT)) {
            preparedStatement.setInt(1, student.getCourseId());
            preparedStatement.setInt(2, student.getStudentId());
            preparedStatement.setDate(3, Date.valueOf("2025-9-21"));
            preparedStatement.setInt(4, 3);

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
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_STUDENT)) {
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
        StudentHasCourse student = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_STUDENT_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                student = new StudentHasCourse(resultSet.getInt(1),
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
        return student;
    }

    @Override
    public List<StudentHasCourse> findAll() {
        List<StudentHasCourse> studentList = new ArrayList<>();
        Connection connection = pool.takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_ALL_STUDENTS)) {
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
