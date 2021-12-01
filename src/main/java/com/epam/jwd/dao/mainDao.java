package com.epam.jwd.dao;

import com.epam.jwd.dao.api.ConnectionPool;
import com.epam.jwd.dao.impl.connectionPool.ConnectionPoolImpl;

import java.sql.Connection;
import java.sql.SQLException;

public class mainDao {

    public static void main(String[] args) throws InterruptedException, SQLException {
        ConnectionPool pool = ConnectionPoolImpl.getInstance();
        Connection connection = pool.takeConnection();

//        Statement statement = connection.prepareStatement("SELECT * FROM account");
//        Statement statement = connection.createStatement();

//        Course course = new Course("C#", "mmm hrazyka", Date.valueOf("2002-06-21"), Date.valueOf("2021-01-12"), 1, 18);
//        CourseDao courseDao = new CourseDao();
//        User user = new User("Valentin", "66");
//        UserDao userDao = new UserDao();
//        System.out.println(userDao.save(user));


//        Account account = new Account("EVE", "Baichik", "game@mail.ru", Date.valueOf("2002-06-21"), 1, 1);
//        AccountDao accountDao = new AccountDao();
//        System.out.println(userDao.save(user));
//        System.out.println(userDao.update(user));
//        System.out.println(userDao.delete(20));
//        System.out.println(userDao.findById(18));
//        System.out.println(accountDao.save(account));
//        System.out.println(accountDao.update(account));
//        System.out.println(accountDao.delete(17));
//        System.out.println(accountDao.findAll());

//        System.out.println(courseDao.save(course));
//        System.out.println(courseDao.delete(5));
//        Review review = new Review(6, 18, 10, 12, "eeeeee");
//        ReviewDao reviewDao = new ReviewDao();
//        System.out.println(reviewDao.save(review));
//        System.out.println(reviewDao.delete(5));

//        StudentHasCourse studentHasCourse = new StudentHasCourse(3, 12, Date.valueOf("2001-08-23"));
//        StudentHasCourseDao studentHasCourseDao = new StudentHasCourseDao();

//        System.out.println(studentHasCourseDao.save(studentHasCourse));
//        System.out.println(studentHasCourseDao.findAll());




//        ResultSet set = statement.executeQuery("""
//                    SELECT\s
//                      account.id,  first_name, second_name, email, birth_date, role_name
//                    FROM
//                        account
//                            LEFT JOIN
//                        role ON role_id = role.id;""");
//        while (set.next()){
//            Role role = Role.valueOf(set.getString(6));
//            System.out.println(new Account(set.getInt(1), set.getString(2),
//                    set.getString(3), set.getString(4), set.getDate(5),
//                    role));
//        }

//        pool.returnConnection(connection);
//        pool.shutdown();
//        set.close();
    }
}
