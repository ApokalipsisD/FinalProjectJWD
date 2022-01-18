package com.epam.jwd.service.validator.impl;

import com.epam.jwd.service.dto.CourseDto;
import com.epam.jwd.service.exception.MessageException;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.validator.api.Validator;

import java.sql.Date;
import java.util.Objects;

public class CourseValidator implements Validator<CourseDto, Integer> {
    private static final String DATE_PATTERN = "((19|20)\\d\\d)-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])";
    private static final Integer MIN_ID = 1;

    @Override
    public void validate(CourseDto value) throws ServiceException {
        validateTitle(value.getTitle());
        validateDescription(value.getDescription());
        validateDates(value.getStartDate(), value.getEndDate());
        validateCourseStatus(value.getCourseStatus().getId());
        validateTeacherId(value.getTeacherId());
    }

    private void validateTitle(String title) throws ServiceException {
        if (Objects.isNull(title)) {
            throw new ServiceException(MessageException.TITLE_IS_NULL_EXCEPTION);
        }
    }

    private void validateDescription(String description) throws ServiceException {
        if (Objects.isNull(description)) {
            throw new ServiceException(MessageException.DESCRIPTION_IS_NULL_EXCEPTION);
        }
    }

    private void validateDates(Date startDate, Date endDate) throws ServiceException {
        if (Objects.isNull(startDate)) {
            throw new ServiceException(MessageException.START_DATE_IS_NULL_EXCEPTION);
        }
        if (Objects.isNull(endDate)) {
            throw new ServiceException(MessageException.END_DATE_IS_NULL_EXCEPTION);
        }
        if (!startDate.toString().matches(DATE_PATTERN) || !endDate.toString().matches(DATE_PATTERN)) {
            throw new ServiceException(MessageException.INCORRECT_DATE_EXCEPTION);
        }
        if (!startDate.before(endDate)) {
            throw new ServiceException(MessageException.START_AND_END_DATE_EXCEPTION);
        }
    }

    private void validateCourseStatus(Integer id) throws ServiceException {
        if (Objects.isNull(id)) {
            throw new ServiceException(MessageException.COURSE_STATUS_IS_NULL);
        }
        if (id < MIN_ID) {
            throw new ServiceException(MessageException.INCORRECT_COURSE_STATUS_EXCEPTION);
        }
    }

    private void validateTeacherId(Integer id) throws ServiceException {
        if (Objects.isNull(id)) {
            throw new ServiceException(MessageException.TEACHER_ID_IS_NULL);
        }
        if (id < MIN_ID) {
            throw new ServiceException(MessageException.INCORRECT_TEACHER_ID_EXCEPTION);
        }
    }
}
