package com.epam.jwd.service.converter.impl;

import com.epam.jwd.dao.entity.Review;
import com.epam.jwd.service.converter.api.Converter;
import com.epam.jwd.service.dto.ReviewDto;

public class ReviewConverter implements Converter<Review, ReviewDto, Integer> {
    @Override
    public Review convert(ReviewDto reviewDto) {
        return new Review(reviewDto.getId(),
                reviewDto.getCourseId(),
                reviewDto.getStudentId(),
                reviewDto.getGrade(),
                reviewDto.getAttendance(),
                reviewDto.getReview());
    }

    @Override
    public ReviewDto convert(Review review) {
        return new ReviewDto(review.getId(),
                review.getCourseId(),
                review.getStudentId(),
                review.getGrade(),
                review.getAttendance(),
                review.getReview());
    }
}
