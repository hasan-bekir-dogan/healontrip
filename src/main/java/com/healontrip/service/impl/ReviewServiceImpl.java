package com.healontrip.service.impl;

import com.healontrip.dto.ReviewDto;
import com.healontrip.dto.ReviewsDto;
import com.healontrip.dto.Role;
import com.healontrip.dto.RolePrefix;
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

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.round;


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
    public List<ReviewsDto> addReview(ReviewDto reviewDto) {
        // save review data
        ReviewEntity reviewEntity = new ReviewEntity();

        reviewEntity.setDoctorId(reviewDto.getDoctorId());
        reviewEntity.setPatientId(authService.getUserId());
        reviewEntity.setDetail(reviewDto.getDetail());
        reviewEntity.setRating(reviewDto.getRating());

        reviewRepository.save(reviewEntity);

        List<ReviewsDto> reviewsDtoList = getReviews(reviewEntity.getDoctorId(), 5, 0);

        /*// set review that is added
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
        reviewsDto.setDetail(reviewEntity.getDetail());*/

        return reviewsDtoList;
    }

    @Override
    // overloading
    public List<ReviewsDto> getReviews(Long userId) {
        List<ReviewsDto> reviewsDtoList = new ArrayList<>();
        String role = authService.getRole();

        if(role.equals(Role.DOCTOR.toString())) {
            reviewsDtoList = getReviews(reviewRepository.findReviewByDoctorId(userId));
        } else if(role.equals(Role.PATIENT.toString())) {
            reviewsDtoList = getReviews(reviewRepository.findReviewByPatientId(userId));
        }

        return reviewsDtoList;
    }

    @Override
    // overloading
    public List<ReviewsDto> getReviews(Long doctorId, int limit, int pageNumber) {
        List<ReviewsDto> reviewsDtoList = getReviews(reviewRepository.findReviewByDoctorIdByLimit(doctorId, limit, pageNumber));

        return reviewsDtoList;
    }

    // overloading
    public List<ReviewsDto> getReviews(List<ReviewEntity> reviewEntityList) {
        List<ReviewsDto> reviewsDtoList = new ArrayList<>();

        for (ReviewEntity reviewEntity: reviewEntityList) {
            if (reviewEntity.getDetail() == null)
                continue;

            ReviewsDto reviewsDto = new ReviewsDto();

            // doctor
            reviewsDto.setDoctorId(reviewEntity.getDoctorId());
            UserEntity doctor = userService.findById(reviewEntity.getDoctorId());
            reviewsDto.setDoctorUserName(doctor.getUserName());
            reviewsDto.setDoctorFullName(RolePrefix.DOCTOR.getPre() + doctor.getFirstName() + " " + doctor.getLastName());

            // doctor img src
            String doctorImgSrc = fileService.getFileSrc(doctor.getProfileImgId());
            reviewsDto.setDoctorProfileImgSrc(doctorImgSrc);

            // doctor img alt
            FileEntity doctorFileEntity = fileService.findById(doctor.getProfileImgId());
            reviewsDto.setDoctorProfileImgAlt(doctorFileEntity.getAlt());

            // patient
            reviewsDto.setPatientId(reviewEntity.getPatientId());
            UserEntity patient = userService.findById(reviewEntity.getPatientId());
            reviewsDto.setPatientUserName(patient.getUserName());
            reviewsDto.setPatientFullName(patient.getFirstName() + " " + patient.getLastName());

            // patient img src
            String patientImgSrc = fileService.getFileSrc(patient.getProfileImgId());
            reviewsDto.setPatientProfileImgSrc(patientImgSrc);

            // patient img alt
            FileEntity patientFileEntity = fileService.findById(patient.getProfileImgId());
            reviewsDto.setPatientProfileImgAlt(patientFileEntity.getAlt());

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
    public ReviewsDto getReview(Long userId) {
        ReviewsDto reviewsDto = new ReviewsDto();
        UserEntity userEntity = userService.findById(userId);
        String role = userEntity.getRole().toString().toUpperCase();

        if(role.equals(Role.DOCTOR.toString())) {
            reviewsDto.setDoctorUserName(userEntity.getUserName());
            reviewsDto.setDoctorId(userId);

            double ratingAvg = reviewRepository.findReviewAvgByDoctorId(userId);
            BigDecimal roundedRatingAvg = new BigDecimal(ratingAvg).setScale(1, RoundingMode.HALF_UP);
            reviewsDto.setRatingAvg(roundedRatingAvg.doubleValue());
            reviewsDto.setRatingAvgStr(Double.toString(roundedRatingAvg.doubleValue()));

            int ratingCount = reviewRepository.findReviewCountByDoctorId(userId);
            reviewsDto.setRatingCount(ratingCount);
        } else if(role.equals(Role.PATIENT.toString())) {
            reviewsDto.setPatientUserName(userEntity.getUserName());
            reviewsDto.setPatientId(userId);

            int ratingCount = reviewRepository.findReviewCountByPatientId(userId);
            reviewsDto.setRatingCount(ratingCount);
        }


        return reviewsDto;
    }

    @Override
    public ReviewEntity findById(Long id) {
        return reviewRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Review not found with id: " + id));
    }
}
