package com.epam.jwd.dao.impl;

import com.epam.jwd.dao.api.ConnectionPool;
import com.epam.jwd.dao.api.Dao;
import com.epam.jwd.dao.entity.Course;
import com.epam.jwd.dao.impl.connectionPool.ConnectionPoolImpl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseDao implements Dao<Course, Integer> {
    private static final String SQL_SAVE_COURSE = "INSERT INTO course(title, description, start_date, end_date, course_status, teacher_id) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE_COURSE = "UPDATE course SET title=?, description=?, start_date=?, end_date=?, course_status=?, teacher_id=? WHERE id=?";
    private static final String SQL_DELETE_COURSE = "DELETE FROM course WHERE id=?";
    private static final String SQL_FIND_COURSE_BY_ID = "SELECT id, title, description, start_date, end_date, course_status, teacher_id FROM course WHERE id=?";
    private static final String SQL_FIND_ALL_COURSES = "SELECT id, title, description, start_date, end_date, course_status, teacher_id FROM course";

    private final ConnectionPool pool = ConnectionPoolImpl.getInstance();

    @Override
    public Course save(Course course) {
        Connection connection = pool.takeConnection();

        List<Course> list = findAll();
        for (int i = 0; i < list.size(); i++) {
            if(course.getTitle().equals(list.get(i))){
                System.out.println("fooooooo");
            }
        }
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SAVE_COURSE, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, course.getTitle());
            preparedStatement.setString(2, course.getDescription());
            preparedStatement.setDate(3, course.getStartDate());
            preparedStatement.setDate(4, course.getEndDate());
            preparedStatement.setInt(5, course.getCourseStatus().getId());
            preparedStatement.setInt(6, course.getTeacherId());
            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                course.setId(resultSet.getInt(1));
            }
            resultSet.close();
        } catch (SQLException e) {
            //
            e.printStackTrace();
        } finally {
            pool.returnConnection(connection);
        }
        return course;
    }

    @Override
    public boolean update(Course course) {
        Connection connection = pool.takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_COURSE)) {
            preparedStatement.setString(1, course.getTitle());
            preparedStatement.setString(2, course.getDescription());
            preparedStatement.setDate(3, course.getStartDate());
            preparedStatement.setDate(4, course.getEndDate());
            preparedStatement.setInt(5, course.getCourseStatus().getId());
            preparedStatement.setInt(6, course.getTeacherId());
            preparedStatement.setInt(7, 3);

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
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
            //
            return false;
        } finally {
            pool.returnConnection(connection);
        }
    }

    @Override
    public Course findById(Integer id) {
        Connection connection = pool.takeConnection();
        Course course = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_COURSE_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                course = new Course(resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getDate(4),
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
        return course;
    }

    @Override
    public List<Course> findAll() {
        List<Course> courseList = new ArrayList<>();
        Connection connection = pool.takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_ALL_COURSES)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                courseList.add(new Course(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),
                        resultSet.getDate(4), resultSet.getDate(5), resultSet.getInt(6), resultSet.getInt(7)));
            }
            resultSet.close();
        } catch (SQLException e) {
            //
        } finally {
            pool.returnConnection(connection);
        }
        return courseList;
    }

}


