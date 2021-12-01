package com.epam.jwd.service.impl;

import com.epam.jwd.dao.entity.Course;
import com.epam.jwd.dao.impl.CourseDao;
import com.epam.jwd.service.api.Service;
import com.epam.jwd.service.converter.api.Converter;
import com.epam.jwd.service.converter.impl.CourseConverter;
import com.epam.jwd.service.dto.CourseDto;
import com.epam.jwd.service.exception.MessageException;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.validator.api.Validator;
import com.epam.jwd.service.validator.impl.CourseValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CourseServiceImpl implements Service<CourseDto, Integer> {
    private final CourseDao courseDao = new CourseDao();
    private final Validator<CourseDto, Integer> validator = new CourseValidator();
    private final Converter<Course, CourseDto, Integer> converter = new CourseConverter();

    @Override
    public CourseDto create(CourseDto courseDto) {
        validator.validate(courseDto);
        return converter.convert(courseDao.save(converter.convert(courseDto)));
    }

    @Override
    public boolean update(CourseDto courseDto) {
        validator.validate(courseDto);
        return courseDao.update(converter.convert(courseDto));
    }

    @Override
    public boolean delete(CourseDto courseDto) {
        validator.validate(courseDto);
        return courseDao.delete(converter.convert(courseDto));
    }

    @Override
    public CourseDto getById(Integer id) {
        validator.validateId(id);
        Course result = courseDao.findById(id);
        if (Objects.isNull(result)) {
            throw new ServiceException(MessageException.COURSE_NOT_FOUND_EXCEPTION);
        }
        return converter.convert(result);
    }

    @Override
    public List<CourseDto> getAll() {
        List<Course> courseList = courseDao.findAll();
        List<CourseDto> courseDtoList = new ArrayList<>();
        courseList.forEach(course -> courseDtoList.add(converter.convert(course)));
        return courseDtoList;
    }
}
