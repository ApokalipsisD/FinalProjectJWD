package com.epam.jwd.service.validator.impl;

import com.epam.jwd.service.dto.CourseDto;
import com.epam.jwd.service.exception.MessageException;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.validator.api.Validator;

import java.sql.Date;
import java.util.Objects;

public class CourseValidator implements Validator<CourseDto, Integer> {
    private static final String DATE_PATTERN = "((19|20)\\d\\d)-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])";

    @Override
    public void validate(CourseDto value) {
        validateId(value.getId());
        validateTitle(value.getTitle());
        validateDescription(value.getDescription());
        validateStartDate(value.getStartDate());
        validateEndDate(value.getEndDate());
        validateCourseStatus(value.getCourseStatus());
        validateTeacherId(value.getTeacherId());
    }

    private void validateTitle(String title) {
        if (Objects.isNull(title)) {
            throw new ServiceException(MessageException.TITLE_IS_NULL_EXCEPTION);
        }
    }

    private void validateDescription(String description) {
        if (Objects.isNull(description)) {
            throw new ServiceException(MessageException.DESCRIPTION_IS_NULL_EXCEPTION);
        }
    }

    private void validateStartDate(Date startDate) {
        if (Objects.isNull(startDate)) {
            throw new ServiceException(MessageException.START_DATE_IS_NULL_EXCEPTION);
        }
        if (!startDate.toString().matches(DATE_PATTERN)) {
            throw new ServiceException(MessageException.INCORRECT_DATE_EXCEPTION);
        }
    }

    private void validateEndDate(Date endDate) {
        if (Objects.isNull(endDate)) {
            throw new ServiceException(MessageException.END_DATE_IS_NULL_EXCEPTION);
        }
        if (!endDate.toString().matches(DATE_PATTERN)) {
            throw new ServiceException(MessageException.INCORRECT_DATE_EXCEPTION);
        }
    }

    private void validateCourseStatus(Integer id) {
        if (Objects.isNull(id)) {
            throw new ServiceException(MessageException.COURSE_STATUS_IS_NULL);
        }
    }

    private void validateTeacherId(Integer id) {
        if (Objects.isNull(id)) {
            throw new ServiceException(MessageException.TEACHER_ID_IS_NULL);
        }
    }
}
