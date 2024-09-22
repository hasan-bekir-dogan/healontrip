package com.healontrip.service;

import com.healontrip.dto.ReviewDto;
import com.healontrip.dto.ReviewsDto;
import com.healontrip.entity.ReviewEntity;

import java.util.List;

public interface ReviewService {
    List<ReviewsDto> addReview(ReviewDto reviewDto);
    List<ReviewsDto> getReviews(Long userId);
    List<ReviewsDto> getReviews(Long doctorId, int limit, int pageNumber);
    ReviewsDto getReview(Long userId);
    ReviewEntity findById(Long id);
}
