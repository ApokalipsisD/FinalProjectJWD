package com.epam.jwd.service.validator.impl;

import com.epam.jwd.service.dto.StudentHasCourseDto;
import com.epam.jwd.service.exception.MessageException;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.validator.api.Validator;

import java.sql.Date;
import java.util.Objects;

public class StudentHasCourseValidator implements Validator<StudentHasCourseDto, Integer> {
    private static final Integer MIN_ID = 1;
    private static final String DATE_PATTERN = "((19|20)\\d\\d)-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])";

    @Override
    public void validate(StudentHasCourseDto value) throws ServiceException {
        validateCourseId(value.getCourseId());
        validateStudentId(value.getStudentId());
        validateApplicationDate(value.getApplicationDate());
    }

    private void validateCourseId(Integer courseId) throws ServiceException {
        if (Objects.isNull(courseId)) {
            throw new ServiceException(MessageException.COURSE_ID_IS_NULL_EXCEPTION);
        }
        if (courseId < MIN_ID) {
            throw new ServiceException(MessageException.INCORRECT_COURSE_ID_EXCEPTION);
        }
    }

    private void validateStudentId(Integer studentId) throws ServiceException {
        if (Objects.isNull(studentId)) {
            throw new ServiceException(MessageException.STUDENT_ID_IS_NULL_EXCEPTION);
        }
        if (studentId < MIN_ID) {
            throw new ServiceException(MessageException.INCORRECT_STUDENT_ID_EXCEPTION);
        }
    }

    private void validateApplicationDate(Date date) throws ServiceException {
        if (Objects.isNull(date)) {
            throw new ServiceException(MessageException.APPLICATION_DATE_IS_NULL_EXCEPTION);
        }
        if (date.toString().matches(DATE_PATTERN)) {
            throw new ServiceException(MessageException.INCORRECT_DATE_EXCEPTION);
        }
    }
}
