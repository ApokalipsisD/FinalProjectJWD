package com.epam.jwd.service.impl;

import com.epam.jwd.dao.entity.StudentHasCourse;
import com.epam.jwd.dao.exception.DaoException;
import com.epam.jwd.dao.impl.StudentHasCourseDao;
import com.epam.jwd.service.api.Service;
import com.epam.jwd.service.converter.api.Converter;
import com.epam.jwd.service.converter.impl.StudentHasCourseConverter;
import com.epam.jwd.service.dto.StudentHasCourseDto;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.validator.api.Validator;
import com.epam.jwd.service.validator.impl.StudentHasCourseValidator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class StudentHasCourseServiceImpl implements Service<StudentHasCourseDto, Integer> {
    private static final Logger logger = LogManager.getLogger(StudentHasCourseServiceImpl.class);

    private final StudentHasCourseDao studentHasCourseDao = new StudentHasCourseDao();
    private final Validator<StudentHasCourseDto, Integer> validator = new StudentHasCourseValidator();
    private final Converter<StudentHasCourse, StudentHasCourseDto, Integer> converter = new StudentHasCourseConverter();

    @Override
    public StudentHasCourseDto create(StudentHasCourseDto studentHasCourseDto) throws ServiceException {
        try {
            return converter.convert(studentHasCourseDao.save(converter.convert(studentHasCourseDto)));
        } catch (DaoException e) {
            logger.error(e.getMessage() + e);
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public boolean update(StudentHasCourseDto studentHasCourseDto) throws ServiceException {
        try {
            return studentHasCourseDao.update(converter.convert(studentHasCourseDto));
        } catch (DaoException e) {
            logger.error(e.getMessage() + e);
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public boolean delete(StudentHasCourseDto studentHasCourseDto) throws ServiceException {
        try {
            return studentHasCourseDao.delete(converter.convert(studentHasCourseDto));
        } catch (DaoException e) {
            logger.error(e.getMessage() + e);
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public StudentHasCourseDto getById(Integer id) throws ServiceException {
        StudentHasCourse result = null;
        try {
            result = studentHasCourseDao.findById(id);
            if (Objects.isNull(result)) {
                throw new DaoException("Record is not found");
            }
        } catch (DaoException e) {
            logger.error(e.getMessage() + e);
            throw new ServiceException(e.getMessage());
        }
        return converter.convert(result);
    }

    @Override
    public List<StudentHasCourseDto> getAll() throws ServiceException {
        List<StudentHasCourse> studentList;
        List<StudentHasCourseDto> studentDtoList = new ArrayList<>();
        try {
            studentList = studentHasCourseDao.findAll();
            studentList.forEach(studentHasCourse -> studentDtoList.add(converter.convert(studentHasCourse)));
        } catch (DaoException e) {
            logger.error(e.getMessage() + e);
            throw new ServiceException(e.getMessage());
        }
        return studentDtoList;
    }

    public boolean findRecordByCourseIdAndStudentId(Integer courseId, Integer studentId) throws ServiceException {
        try {
            return studentHasCourseDao.findRecordByCourseIdAndStudentId(courseId, studentId);
        } catch (DaoException e) {
            logger.error(e.getMessage() + e);
            throw new ServiceException(e.getMessage());
        }
    }

    public StudentHasCourseDto getRecordByCourseIdAndStudentId(Integer courseId, Integer studentId) throws ServiceException {
        try {
            return converter.convert(studentHasCourseDao.getRecordByCourseIdAndStudentId(courseId, studentId));
        } catch (DaoException e) {
            logger.error(e.getMessage() + e);
            throw new ServiceException(e.getMessage());
        }
    }

    public List<StudentHasCourseDto> getRecordsByStudentId(Integer studentId) throws ServiceException {
        List<StudentHasCourse> studentList;
        List<StudentHasCourseDto> studentDtoList = new ArrayList<>();
        try {
            studentList = studentHasCourseDao.getRecordsByStudentId(studentId);
            studentList.forEach(studentHasCourse -> studentDtoList.add(converter.convert(studentHasCourse)));
        } catch (DaoException e) {
            logger.error(e.getMessage() + e);
            throw new ServiceException(e.getMessage());
        }
        return studentDtoList;
    }

    public List<StudentHasCourseDto> getRecordsByCourseId(Integer courseId) throws ServiceException {
        List<StudentHasCourse> studentList;
        List<StudentHasCourseDto> studentDtoList = new ArrayList<>();
        try {
            studentList = studentHasCourseDao.getRecordsByCourseId(courseId);
            studentList.forEach(studentHasCourse -> studentDtoList.add(converter.convert(studentHasCourse)));
        } catch (DaoException e) {
            logger.error(e.getMessage() + e);
            throw new ServiceException(e.getMessage());
        }
        return studentDtoList;
    }
}
