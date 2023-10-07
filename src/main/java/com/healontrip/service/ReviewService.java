package com.healontrip.service;

import com.healontrip.dto.ReviewDto;
import com.healontrip.dto.ReviewsDto;
import com.healontrip.entity.ReviewEntity;

import java.util.List;

public interface ReviewService {
    ReviewsDto addReview(ReviewDto reviewDto);
    List<ReviewsDto> getReviews(Long doctorId);
    ReviewEntity findById(Long id);
}
