package com.healontrip.service.impl;

import com.healontrip.dto.ReviewDto;
import com.healontrip.dto.ReviewsDto;
import com.healontrip.entity.FileEntity;
import com.healontrip.entity.ReviewEntity;
import com.healontrip.entity.UserEntity;
import com.healontrip.exception.ResourceNotFoundException;
import com.healontrip.repository.ReviewRepository;
import com.healontrip.service.AuthService;
import com.healontrip.service.FileService;
import com.healontrip.service.ReviewService;
import com.healontrip.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    @Autowired
    private FileService fileService;

    public final String reviewCreatedDatePattern = "dd MMM yyyy";

    @Override
    public ReviewsDto addReview(ReviewDto reviewDto) {
        // save review data
        ReviewEntity reviewEntity = new ReviewEntity();

        reviewEntity.setDoctorId(reviewDto.getDoctorId());
        reviewEntity.setPatientId(authService.getUserId());
        reviewEntity.setDetail(reviewDto.getDetail());
        reviewEntity.setRating(reviewDto.getRating());

        reviewRepository.save(reviewEntity);

        // set review that is added
        if (reviewEntity.getDetail() == null)
            return null;

        ReviewsDto reviewsDto = new ReviewsDto();

        // doctor
        reviewsDto.setDoctorId(reviewEntity.getDoctorId());
        UserEntity doctor = userService.findById(reviewEntity.getDoctorId());
        reviewsDto.setDoctorUserName(doctor.getName());

        // patient
        reviewsDto.setPatientId(reviewEntity.getPatientId());
        UserEntity patient = userService.findById(reviewEntity.getPatientId());
        reviewsDto.setPatientUserName(patient.getName());

        // patient img src
        String imgSrc = fileService.getFileSrc(patient.getProfileImgId());
        reviewsDto.setPatientProfileImgSrc(imgSrc);

        // patient img alt
        FileEntity fileEntity = fileService.findById(patient.getProfileImgId());
        reviewsDto.setPatientProfileImgAlt(fileEntity.getAlt());

        // created date
        SimpleDateFormat format = new SimpleDateFormat(reviewCreatedDatePattern);
        reviewsDto.setCreatedDate("Reviewed on " + format.format(reviewEntity.getCreatedDate()));

        reviewsDto.setRating(reviewEntity.getRating());
        reviewsDto.setDetail(reviewEntity.getDetail());

        return reviewsDto;
    }

    @Override
    public List<ReviewsDto> getReviews(Long doctorId) {
        List<ReviewEntity> reviewEntityList = reviewRepository.findReviewByDoctorId(doctorId);
        List<ReviewsDto> reviewsDtoList = new ArrayList<>();

        for (ReviewEntity reviewEntity: reviewEntityList) {
            if (reviewEntity.getDetail() == null)
                continue;

            ReviewsDto reviewsDto = new ReviewsDto();

            // doctor
            reviewsDto.setDoctorId(reviewEntity.getDoctorId());
            UserEntity doctor = userService.findById(reviewEntity.getDoctorId());
            reviewsDto.setDoctorUserName(doctor.getName());

            // patient
            reviewsDto.setPatientId(reviewEntity.getPatientId());
            UserEntity patient = userService.findById(reviewEntity.getPatientId());
            reviewsDto.setPatientUserName(patient.getName());

            // patient img src
            String imgSrc = fileService.getFileSrc(patient.getProfileImgId());
            reviewsDto.setPatientProfileImgSrc(imgSrc);

            // patient img alt
            FileEntity fileEntity = fileService.findById(patient.getProfileImgId());
            reviewsDto.setPatientProfileImgAlt(fileEntity.getAlt());

            // created date
            SimpleDateFormat format = new SimpleDateFormat(reviewCreatedDatePattern);
            reviewsDto.setCreatedDate("Reviewed on " + format.format(reviewEntity.getCreatedDate()));

            reviewsDto.setRating(reviewEntity.getRating());
            reviewsDto.setDetail(reviewEntity.getDetail());

            reviewsDtoList.add(reviewsDto);
        }

        return reviewsDtoList;
    }

    @Override
    public ReviewEntity findById(Long id) {
        return reviewRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Review not found with id: " + id));
    }
}
