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

/**
 * StudentHasCourseService implementation class of Service for StudentHasCourseDto with Integer id
 */
public class StudentHasCourseServiceImpl implements Service<StudentHasCourseDto, Integer> {
    private static final Logger logger = LogManager.getLogger(StudentHasCourseServiceImpl.class);

    private final StudentHasCourseDao studentHasCourseDao = new StudentHasCourseDao();
    private final Validator<StudentHasCourseDto, Integer> validator = new StudentHasCourseValidator();
    private final Converter<StudentHasCourse, StudentHasCourseDto, Integer> converter = new StudentHasCourseConverter();

    @Override
    public StudentHasCourseDto create(StudentHasCourseDto studentHasCourseDto) throws ServiceException {
        validator.validate(studentHasCourseDto);
        try {
            return converter.convert(studentHasCourseDao.save(converter.convert(studentHasCourseDto)));
        } catch (DaoException e) {
            logger.error(e.getMessage() + e);
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public boolean update(StudentHasCourseDto studentHasCourseDto) throws ServiceException {
        validator.validate(studentHasCourseDto);
        try {
            return studentHasCourseDao.update(converter.convert(studentHasCourseDto));
        } catch (DaoException e) {
            logger.error(e.getMessage() + e);
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public boolean delete(StudentHasCourseDto studentHasCourseDto) throws ServiceException {
        validator.validate(studentHasCourseDto);
        try {
            return studentHasCourseDao.delete(converter.convert(studentHasCourseDto));
        } catch (DaoException e) {
            logger.error(e.getMessage() + e);
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public StudentHasCourseDto getById(Integer id) throws ServiceException {
        validator.validateId(id);
        StudentHasCourse result;
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

    /**
     * Method which finds record by course and student
     *
     * @param courseId  - current course id
     * @param studentId - current student id
     * @return - true if record was found and otherwise false or exception
     * @throws ServiceException - if any DAOExceptions were thrown
     */
    public boolean findRecordByCourseIdAndStudentId(Integer courseId, Integer studentId) throws ServiceException {
        validator.validateId(courseId);
        validator.validateId(studentId);
        try {
            return studentHasCourseDao.findRecordByCourseIdAndStudentId(courseId, studentId);
        } catch (DaoException e) {
            logger.error(e.getMessage() + e);
            throw new ServiceException(e.getMessage());
        }
    }

    /**
     * Method which gets record by course and student
     *
     * @param courseId  - current course id
     * @param studentId - current student id
     * @return - StudentHasCourseDto entity record by current user and current course
     * @throws ServiceException - if any DAOExceptions were thrown
     */
    public StudentHasCourseDto getRecordByCourseIdAndStudentId(Integer courseId, Integer studentId) throws ServiceException {
        validator.validateId(courseId);
        validator.validateId(studentId);
        try {
            return converter.convert(studentHasCourseDao.getRecordByCourseIdAndStudentId(courseId, studentId));
        } catch (DaoException e) {
            logger.error(e.getMessage() + e);
            throw new ServiceException(e.getMessage());
        }
    }

    /**
     * Method which gets record by student id
     *
     * @param studentId - current student id
     * @return - list of records courses with student id
     * @throws ServiceException - if any DAOExceptions were thrown
     */
    public List<StudentHasCourseDto> getRecordsByStudentId(Integer studentId) throws ServiceException {
        validator.validateId(studentId);
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

    /**
     * Method which gets record by course id
     *
     * @param courseId - current course id
     * @return - list of records students with course id
     * @throws ServiceException - if any DAOExceptions were thrown
     */
    public List<StudentHasCourseDto> getRecordsByCourseId(Integer courseId) throws ServiceException {
        validator.validateId(courseId);
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
