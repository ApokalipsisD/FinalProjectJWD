package com.epam.jwd.service.converter.impl;

import com.epam.jwd.dao.entity.StudentHasCourse;
import com.epam.jwd.service.converter.api.Converter;
import com.epam.jwd.service.dto.StudentHasCourseDto;

public class StudentHasCourseConverter implements Converter<StudentHasCourse, StudentHasCourseDto, Integer> {
    @Override
    public StudentHasCourse convert(StudentHasCourseDto studentDto) {
        return new StudentHasCourse(studentDto.getId(),
                studentDto.getCourseId(),
                studentDto.getStudentId(),
                studentDto.getApplicationDate());
    }

    @Override
    public StudentHasCourseDto convert(StudentHasCourse student) {
        return new StudentHasCourseDto(student.getId(),
                student.getCourseId(),
                student.getStudentId(),
                student.getApplicationDate());
    }
}
