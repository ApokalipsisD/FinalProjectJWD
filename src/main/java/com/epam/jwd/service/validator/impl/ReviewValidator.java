package com.epam.jwd.service.validator.impl;

import com.epam.jwd.service.dto.ReviewDto;
import com.epam.jwd.service.exception.MessageException;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.validator.api.Validator;

import java.util.Objects;

public class ReviewValidator implements Validator<ReviewDto, Integer> {
    private static final Integer MIN_GRADE = 1;
    private static final Integer MAX_GRADE = 10;
    private static final Integer MIN_ATTENDANCE = 0;
    private static final Integer MAX_ATTENDANCE = 100;
    private static final Integer MIN_ID = 1;


    @Override
    public void validate(ReviewDto value) throws ServiceException {
        validateCourseId(value.getCourseId());
        validateStudentId(value.getStudentId());
        validateGrade(value.getGrade());
        validateAttendance(value.getAttendance());
        validateReview(value.getReview());
    }

    private void validateCourseId(Integer id) throws ServiceException {
        if (Objects.isNull(id)) {
            throw new ServiceException(MessageException.COURSE_ID_IS_NULL_EXCEPTION);
        }
        if (id < MIN_ID) {
            throw new ServiceException(MessageException.INCORRECT_COURSE_ID_EXCEPTION);
        }
    }

    private void validateStudentId(Integer id) throws ServiceException {
        if (Objects.isNull(id)) {
            throw new ServiceException(MessageException.STUDENT_ID_IS_NULL_EXCEPTION);
        }
        if (id < MIN_ID) {
            throw new ServiceException(MessageException.INCORRECT_STUDENT_ID_EXCEPTION);
        }
    }

    private void validateGrade(Integer grade) throws ServiceException {
        if (Objects.isNull(grade)) {
            throw new ServiceException(MessageException.GRADE_IS_NULL_EXCEPTION);
        }

        if (grade < MIN_GRADE || grade > MAX_GRADE) {
            throw new ServiceException(MessageException.INCORRECT_GRADE_EXCEPTION);
        }
    }

    private void validateAttendance(Integer attendance) throws ServiceException {
        if (Objects.isNull(attendance)) {
            throw new ServiceException(MessageException.ATTENDANCE_IS_NULL_EXCEPTION);
        }

        if (attendance < MIN_ATTENDANCE || attendance > MAX_ATTENDANCE) {
            throw new ServiceException(MessageException.INCORRECT_ATTENDANCE_EXCEPTION);
        }
    }

    private void validateReview(String review) throws ServiceException {
        if (Objects.isNull(review)) {
            throw new ServiceException(MessageException.REVIEW_IS_NULL_EXCEPTION);
        }
    }
}