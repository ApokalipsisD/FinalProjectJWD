package com.epam.jwd.service.impl;

import com.epam.jwd.dao.entity.Course;
import com.epam.jwd.dao.exception.DaoException;
import com.epam.jwd.dao.impl.CourseDao;
import com.epam.jwd.service.api.Service;
import com.epam.jwd.service.converter.api.Converter;
import com.epam.jwd.service.converter.impl.CourseConverter;
import com.epam.jwd.service.dto.CourseDto;
import com.epam.jwd.service.exception.MessageException;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.validator.api.Validator;
import com.epam.jwd.service.validator.impl.CourseValidator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CourseServiceImpl implements Service<CourseDto, Integer> {
    private static final Logger logger = LogManager.getLogger(CourseServiceImpl.class);

    private final CourseDao courseDao = new CourseDao();
    private final Validator<CourseDto, Integer> validator = new CourseValidator();
    private final Converter<Course, CourseDto, Integer> converter = new CourseConverter();

    @Override
    public CourseDto create(CourseDto courseDto) throws ServiceException {
        validator.validate(courseDto);
        try {
            return converter.convert(courseDao.save(converter.convert(courseDto)));
        } catch (DaoException e) {
            logger.error(e.getMessage() + e);
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public boolean update(CourseDto courseDto) throws ServiceException {
        validator.validate(courseDto);
        try {
            return courseDao.update(converter.convert(courseDto));
        } catch (DaoException e) {
            logger.error(e.getMessage() + e);
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public boolean delete(CourseDto courseDto) throws ServiceException {
        validator.validate(courseDto);
        try {
            return courseDao.delete(converter.convert(courseDto));
        } catch (DaoException e) {
            logger.error(e.getMessage() + e);
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public CourseDto getById(Integer id) throws ServiceException {
        validator.validateId(id);
        Course result;
        try {
            result = courseDao.findById(id);
            if (Objects.isNull(result)) {
                throw new DaoException(MessageException.COURSE_NOT_FOUND_EXCEPTION);
            }
        } catch (DaoException e) {
            logger.error(e.getMessage() + e);
            throw new ServiceException(e.getMessage());
        }
        return converter.convert(result);
    }

    @Override
    public List<CourseDto> getAll() throws ServiceException {
        List<Course> courseList;
        List<CourseDto> courseDtoList = new ArrayList<>();
        try {
            courseList = courseDao.findAll();
            courseList.forEach(course -> courseDtoList.add(converter.convert(course)));
        } catch (DaoException e) {
            logger.error(e.getMessage() + e);
            throw new ServiceException(e.getMessage());
        }
        return courseDtoList;
    }

    public List<CourseDto> getCoursesByTeacherId(Integer courseId) throws ServiceException {
        validator.validateId(courseId);
        List<Course> courseList;
        List<CourseDto> courseDtoList = new ArrayList<>();
        try {
            courseList = courseDao.findCoursesByTeacherId(courseId);
            courseList.forEach(course -> courseDtoList.add(converter.convert(course)));
        } catch (DaoException e) {
            logger.error(e.getMessage() + e);
            throw new ServiceException(e.getMessage());
        }
        return courseDtoList;
    }
}
