package com.epam.jwd.service.impl;

import com.epam.jwd.dao.entity.Review;
import com.epam.jwd.dao.impl.ReviewDao;
import com.epam.jwd.service.api.Service;
import com.epam.jwd.service.converter.api.Converter;
import com.epam.jwd.service.converter.impl.ReviewConverter;
import com.epam.jwd.service.dto.ReviewDto;
import com.epam.jwd.service.exception.MessageException;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.validator.api.Validator;
import com.epam.jwd.service.validator.impl.ReviewValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ReviewServiceImpl implements Service<ReviewDto, Integer> {
    private final ReviewDao reviewDao = new ReviewDao();
    private final Validator<ReviewDto, Integer> validator = new ReviewValidator();
    private final Converter<Review, ReviewDto, Integer> converter = new ReviewConverter();

    @Override
    public ReviewDto create(ReviewDto reviewDto) throws ServiceException {
        validator.validate(reviewDto);
        return converter.convert(reviewDao.save(converter.convert(reviewDto)));
    }

    @Override
    public boolean update(ReviewDto reviewDto) throws ServiceException {
        validator.validate(reviewDto);
        return reviewDao.update(converter.convert(reviewDto));
    }

    @Override
    public boolean delete(ReviewDto reviewDto) throws ServiceException {
        validator.validate(reviewDto);
        return reviewDao.delete(converter.convert(reviewDto));
    }

    @Override
    public ReviewDto getById(Integer id) throws ServiceException {
        validator.validateId(id);
        Review result = reviewDao.findById(id);
        if (Objects.isNull(result)) {
            throw new ServiceException(MessageException.REVIEW_NOT_FOUND_EXCEPTION);
        }
        return converter.convert(result);
    }

    @Override
    public List<ReviewDto> getAll() {
        List<Review> reviewList = reviewDao.findAll();
        List<ReviewDto> reviewDtoList = new ArrayList<>();
        reviewList.forEach(review -> reviewDtoList.add(converter.convert(review)));
        return reviewDtoList;
    }
}
