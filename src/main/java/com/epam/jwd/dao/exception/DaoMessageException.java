package com.epam.jwd.dao.exception;

public interface DaoMessageException {
    String SAVE_USER_EXCEPTION = "Save user was failed";
    String UPDATE_USER_EXCEPTION = "Update user was failed";
    String DELETE_USER_EXCEPTION = "Delete user was failed";
    String FIND_USER_BY_ID_EXCEPTION = "Find user by id was failed";
    String FIND_ALL_USERS_EXCEPTION = "Find all users was failed";
    String FIND_USER_BY_LOGIN_EXCEPTION = "Find user by login was failed";
    String CHECK_IF_LOGIN_IF_FREE_EXCEPTION = "Check login is free was failed";

    String SAVE_ACCOUNT_EXCEPTION = "Save account was failed";
    String UPDATE_ACCOUNT_EXCEPTION = "Update account was failed";
    String DELETE_ACCOUNT_EXCEPTION = "Delete account was failed";
    String FIND_ACCOUNT_BY_ID_EXCEPTION = "Find account by id was failed";
    String FIND_ALL_ACCOUNTS_EXCEPTION = "Find all accounts was failed";
    String GET_ACCOUNT_BY_USER_ID_EXCEPTION = "Get account by user id was failed";
    String FIND_ALL_TEACHERS_EXCEPTION = "Find all teachers was failed";
    String UPDATE_ROLE_EXCEPTION = "Update role was failed";

    String SAVE_COURSE_EXCEPTION = "Save course was failed";
    String UPDATE_COURSE_EXCEPTION = "Update course was failed";
    String DELETE_COURSE_EXCEPTION = "Delete course was failed";
    String FIND_COURSE_BY_ID_EXCEPTION = "Find course by id was failed";
    String FIND_ALL_COURSES_EXCEPTION = "Find all courses was failed";
    String FIND_COURSE_BY_TEACHER_ID = "Find course by teacher id was failed";

    String SAVE_REVIEW_EXCEPTION = "Save review was failed";
    String UPDATE_REVIEW_EXCEPTION = "Update review was failed";
    String DELETE_REVIEW_EXCEPTION = "Delete review was failed";
    String FIND_REVIEW_BY_ID_EXCEPTION = "Find review by id was failed";
    String FIND_ALL_REVIEWS_EXCEPTION = "Find all reviews was failed";
    String FIND_REVIEW_BY_STUDENT_COURSE_ID_EXCEPTION = "Find review by course id and student id was failed";
    String FIND_REVIEW_BY_COURSE_ID_EXCEPTION = "Find review by course id was failed";

    String SAVE_RECORD_STUDENT_HAS_COURSE_EXCEPTION = "Save record student has course was failed";
    String UPDATE_RECORD_STUDENT_HAS_COURSE_EXCEPTION = "Update record student has course was failed";
    String DELETE_RECORD_STUDENT_HAS_COURSE_EXCEPTION = "Delete record student has course was failed";
    String FIND_RECORD_STUDENT_HAS_COURSE_BY_ID_EXCEPTION = "Find record student has course by id was failed";
    String FIND_ALL_RECORDS_STUDENT_HAS_COURSE_EXCEPTION = "Find all records student has course was failed";
    String FIND_RECORD_BY_COURSE_ID_AND_STUDENT_ID_EXCEPTION = "Find record student has course by student and course id was failed";
    String GET_RECORD_BY_COURSE_ID_AND_STUDENT_ID_EXCEPTION = "Get record student has course by student and course id was failed";
    String GET_RECORDS_BY_COURSE_ID_EXCEPTION = "Get records student has course by course id was failed";
    String GET_RECORDS_STUDENT_ID_EXCEPTION = "Get records student has course by student id was failed";
}
