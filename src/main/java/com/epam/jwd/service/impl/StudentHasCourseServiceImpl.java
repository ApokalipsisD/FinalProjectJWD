package com.epam.jwd.service.impl;

import com.epam.jwd.dao.entity.StudentHasCourse;
import com.epam.jwd.dao.impl.StudentHasCourseDao;
import com.epam.jwd.service.api.Service;
import com.epam.jwd.service.converter.api.Converter;
import com.epam.jwd.service.converter.impl.StudentHasCourseConverter;
import com.epam.jwd.service.dto.StudentHasCourseDto;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.validator.api.Validator;
import com.epam.jwd.service.validator.impl.StudentHasCourseValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class StudentHasCourseServiceImpl implements Service<StudentHasCourseDto, Integer> {
    private final StudentHasCourseDao studentHasCourseDao = new StudentHasCourseDao();
    private final Validator<StudentHasCourseDto, Integer> validator = new StudentHasCourseValidator();
    private final Converter<StudentHasCourse, StudentHasCourseDto, Integer> converter = new StudentHasCourseConverter();

    @Override
    public StudentHasCourseDto create(StudentHasCourseDto studentHasCourseDto) throws ServiceException {
        return converter.convert(studentHasCourseDao.save(converter.convert(studentHasCourseDto)));
    }

    @Override
    public boolean update(StudentHasCourseDto studentHasCourseDto) throws ServiceException {
        return studentHasCourseDao.update(converter.convert(studentHasCourseDto));
    }

    @Override
    public boolean delete(StudentHasCourseDto studentHasCourseDto) throws ServiceException {
        return studentHasCourseDao.delete(converter.convert(studentHasCourseDto));
    }

    @Override
    public StudentHasCourseDto getById(Integer id) throws ServiceException {
        StudentHasCourse result = studentHasCourseDao.findById(id);
        if (Objects.isNull(result)) {
            throw new ServiceException("record not found");
        }
        return converter.convert(result);
    }

    @Override
    public List<StudentHasCourseDto> getAll() {
        List<StudentHasCourse> studentList = studentHasCourseDao.findAll();
        List<StudentHasCourseDto> studentDtoList = new ArrayList<>();
        studentList.forEach(studentHasCourse -> studentDtoList.add(converter.convert(studentHasCourse)));
        return studentDtoList;
    }

    public boolean findRecordByCourseIdAndStudentId(Integer courseId, Integer studentId) {
        return studentHasCourseDao.findRecordByCourseIdAndStudentId(courseId, studentId);
    }

    public StudentHasCourseDto getRecordByCourseIdAndStudentId(Integer courseId, Integer studentId) {
        return converter.convert(studentHasCourseDao.getRecordByCourseIdAndStudentId(courseId, studentId));
    }

    public List<StudentHasCourseDto> getRecordsByStudentId(Integer studentId) {
        List<StudentHasCourse> studentList = studentHasCourseDao.getRecordsByStudentId(studentId);
        List<StudentHasCourseDto> studentDtoList = new ArrayList<>();
        studentList.forEach(studentHasCourse -> studentDtoList.add(converter.convert(studentHasCourse)));
        return studentDtoList;
    }

    public List<StudentHasCourseDto> getRecordsByCourseId(Integer courseId) {
        List<StudentHasCourse> studentList = studentHasCourseDao.getRecordsByCourseId(courseId);
        List<StudentHasCourseDto> studentDtoList = new ArrayList<>();
        studentList.forEach(studentHasCourse -> studentDtoList.add(converter.convert(studentHasCourse)));
        return studentDtoList;
    }

}
