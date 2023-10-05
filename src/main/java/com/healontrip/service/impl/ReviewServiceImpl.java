package com.healontrip.service.impl;

import com.healontrip.dto.ReviewDto;
import com.healontrip.entity.ReviewEntity;
import com.healontrip.exception.ResourceNotFoundException;
import com.healontrip.repository.ReviewRepository;
import com.healontrip.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    @Override
    public void addReview(ReviewDto reviewDto) {
        System.out.println("add review");
    }

    @Override
    public String getReviews(Long userId) {
        
        return null;
    }

    @Override
    public ReviewEntity findById(Long id) {
        return reviewRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Review not found with id: " + id));
    }
}
