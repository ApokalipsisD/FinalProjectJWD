package com.epam.jwd.dao.exception;

public interface DaoMessageException {
    String SAVE_USER_EXCEPTION = "Save user failed";
    String UPDATE_USER_EXCEPTION = "Update user failed";
    String DELETE_USER_EXCEPTION = "Delete user failed";
    String FIND_USER_BY_ID_EXCEPTION = "Find user by id failed";
    String FIND_ALL_USERS_EXCEPTION = "Find all users failed";
    String FIND_USER_BY_LOGIN_EXCEPTION = "Find user by login failed";
    String CHECK_IF_LOGIN_IF_FREE_EXCEPTION = "Check login is free failed";

    String SAVE_ACCOUNT_EXCEPTION = "Save account failed";
    String UPDATE_ACCOUNT_EXCEPTION = "Update account failed";
    String DELETE_ACCOUNT_EXCEPTION = "Delete account failed";
    String FIND_ACCOUNT_BY_ID_EXCEPTION = "Find account by id failed";
    String FIND_ALL_ACCOUNTS_EXCEPTION = "Find all accounts failed";
    String GET_ACCOUNT_BY_USER_ID_EXCEPTION = "Get account by user id failed";
    String FIND_ALL_TEACHERS_EXCEPTION = "Find all teachers failed";
    String UPDATE_ROLE_EXCEPTION = "Update role failed";

    String SAVE_COURSE_EXCEPTION = "Save course failed";
    String UPDATE_COURSE_EXCEPTION = "Update course failed";
    String DELETE_COURSE_EXCEPTION = "Delete course failed";
    String FIND_COURSE_BY_ID_EXCEPTION = "Find course by id failed";
    String FIND_ALL_COURSES_EXCEPTION = "Find all courses failed";
    String FIND_COURSE_BY_TEACHER_ID = "Find course by teacher id failed";

    String SAVE_REVIEW_EXCEPTION = "Save review failed";
    String UPDATE_REVIEW_EXCEPTION = "Update review failed";
    String DELETE_REVIEW_EXCEPTION = "Delete review failed";
    String FIND_REVIEW_BY_ID_EXCEPTION = "Find review by id failed";
    String FIND_ALL_REVIEWS_EXCEPTION = "Find all reviews failed";
    String FIND_REVIEW_BY_STUDENT_COURSE_ID_EXCEPTION = "Find review by course id and student id failed";
    String FIND_REVIEW_BY_COURSE_ID_EXCEPTION = "Find review by course id failed";

    String SAVE_RECORD_STUDENT_HAS_COURSE_EXCEPTION = "Save record student has course failed";
    String UPDATE_RECORD_STUDENT_HAS_COURSE_EXCEPTION = "Update record student has course failed";
    String DELETE_RECORD_STUDENT_HAS_COURSE_EXCEPTION = "Delete record student has course failed";
    String FIND_RECORD_STUDENT_HAS_COURSE_BY_ID_EXCEPTION = "Find record student has course by id failed";
    String FIND_ALL_RECORDS_STUDENT_HAS_COURSE_EXCEPTION = "Find all records student has course failed";
    String FIND_RECORD_BY_COURSE_ID_AND_STUDENT_ID_EXCEPTION = "Find record student has course by student and course id failed";
    String GET_RECORD_BY_COURSE_ID_AND_STUDENT_ID_EXCEPTION = "Get record student has course by student and course id failed";
    String GET_RECORDS_BY_COURSE_ID_EXCEPTION = "Get records student has course by course id failed";
    String GET_RECORDS_STUDENT_ID_EXCEPTION = "Get records student has course by student d failed";


}
