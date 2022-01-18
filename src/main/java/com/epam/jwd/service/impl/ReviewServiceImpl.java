package com.epam.jwd.service.impl;

import com.epam.jwd.dao.entity.Review;
import com.epam.jwd.dao.exception.DaoException;
import com.epam.jwd.dao.impl.ReviewDao;
import com.epam.jwd.service.api.Service;
import com.epam.jwd.service.converter.api.Converter;
import com.epam.jwd.service.converter.impl.ReviewConverter;
import com.epam.jwd.service.dto.ReviewDto;
import com.epam.jwd.service.exception.MessageException;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.validator.api.Validator;
import com.epam.jwd.service.validator.impl.ReviewValidator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ReviewServiceImpl implements Service<ReviewDto, Integer> {
    private static final Logger logger = LogManager.getLogger(ReviewServiceImpl.class);

    private final ReviewDao reviewDao = new ReviewDao();
    private final Validator<ReviewDto, Integer> validator = new ReviewValidator();
    private final Converter<Review, ReviewDto, Integer> converter = new ReviewConverter();

    @Override
    public ReviewDto create(ReviewDto reviewDto) throws ServiceException {
        validator.validate(reviewDto);
        try {
            return converter.convert(reviewDao.save(converter.convert(reviewDto)));
        } catch (DaoException e) {
            logger.error(e.getMessage() + e);
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public boolean update(ReviewDto reviewDto) throws ServiceException {
        validator.validate(reviewDto);
        try {
            return reviewDao.update(converter.convert(reviewDto));
        } catch (DaoException e) {
            logger.error(e.getMessage() + e);
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public boolean delete(ReviewDto reviewDto) throws ServiceException {
        validator.validate(reviewDto);
        try {
            return reviewDao.delete(converter.convert(reviewDto));
        } catch (DaoException e) {
            logger.error(e.getMessage() + e);
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public ReviewDto getById(Integer id) throws ServiceException {
        validator.validateId(id);
        Review result;
        try {
            result = reviewDao.findById(id);
            if (Objects.isNull(result)) {
                throw new DaoException(MessageException.REVIEW_NOT_FOUND_EXCEPTION);
            }
        } catch (DaoException e) {
            logger.error(e.getMessage() + e);
            throw new ServiceException(e.getMessage());
        }

        return converter.convert(result);
    }

    @Override
    public List<ReviewDto> getAll() throws ServiceException {
        List<Review> reviewList;
        List<ReviewDto> reviewDtoList = new ArrayList<>();
        try {
            reviewList = reviewDao.findAll();
            reviewList.forEach(review -> reviewDtoList.add(converter.convert(review)));
        } catch (DaoException e) {
            logger.error(e.getMessage() + e);
            throw new ServiceException(e.getMessage());
        }
        return reviewDtoList;
    }

    public boolean findReviewByCourseIdAndStudentId(Integer courseId, Integer studentId) throws ServiceException {
        validator.validateId(courseId);
        validator.validateId(studentId);
        try {
            return reviewDao.findReviewByCourseIdAndStudentId(courseId, studentId);
        } catch (DaoException e) {
            logger.error(e.getMessage() + e);
            throw new ServiceException(e.getMessage());
        }
    }

    public List<ReviewDto> getReviewsByCourseId(Integer courseId) throws ServiceException {
        validator.validateId(courseId);
        List<Review> reviewList;
        List<ReviewDto> reviewDtoList = new ArrayList<>();
        try {
            reviewList = reviewDao.getReviewsByCourseId(courseId);
            reviewList.forEach(review -> reviewDtoList.add(converter.convert(review)));
        } catch (DaoException e) {
            logger.error(e.getMessage() + e);
            throw new ServiceException(e.getMessage());
        }
        return reviewDtoList;
    }
}
