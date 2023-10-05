package com.healontrip.service;

import com.healontrip.dto.ReviewDto;
import com.healontrip.entity.ReviewEntity;

public interface ReviewService {
    void addReview(ReviewDto reviewDto);
    String getReviews(Long userId);
    ReviewEntity findById(Long id);
}
