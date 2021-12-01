package com.epam.jwd.service.converter.impl;

import com.epam.jwd.dao.entity.Course;
import com.epam.jwd.service.converter.api.Converter;
import com.epam.jwd.service.dto.CourseDto;

public class CourseConverter implements Converter<Course, CourseDto, Integer> {
    @Override
    public Course convert(CourseDto courseDto) {
        return new Course(courseDto.getId(),
                courseDto.getTitle(),
                courseDto.getDescription(),
                courseDto.getStartDate(),
                courseDto.getEndDate(),
                courseDto.getCourseStatus(),
                courseDto.getTeacherId());
    }

    @Override
    public CourseDto convert(Course course) {
        return new CourseDto(course.getId(),
                course.getTitle(),
                course.getDescription(),
                course.getStartDate(),
                course.getEndDate(),
                course.getCourseStatus(),
                course.getTeacherId());
    }
}
