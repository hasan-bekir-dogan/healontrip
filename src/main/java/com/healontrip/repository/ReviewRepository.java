package com.healontrip.repository;

import com.healontrip.entity.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {
    @Query(value = "Select *" +
            "       From reviews" +
            "       Where doctor_id = :userId" +
            "       Order By id desc", nativeQuery = true)
    List<ReviewEntity> findReviewByDoctorId(@Param("userId") Long userId);

    @Query(value = "Select *" +
            "       From reviews" +
            "       Where patient_id = :userId" +
            "       Order By id desc", nativeQuery = true)
    List<ReviewEntity> findReviewByPatientId(@Param("userId") Long userId);

    @Query(value = "Select *" +
            "       From reviews" +
            "       Where doctor_id = :doctorId" +
            "       Order By id desc" +
            "       Limit :limit" +
            "       Offset :pageNumber", nativeQuery = true)
    List<ReviewEntity> findReviewByDoctorIdByLimit(@Param("doctorId") Long doctorId,
                                                   @Param("limit") int limit,
                                                   @Param("pageNumber") int pageNumber);

    @Query(value = "Select if(avg(rating) is null, 0, avg(rating)) as ratingAvg" +
            "       From reviews" +
            "       Where doctor_id = :userId", nativeQuery = true)
    double findReviewAvgByDoctorId(@Param("userId") Long userId);

    @Query(value = "Select if(avg(rating) is null, 0, avg(rating)) as ratingAvg" +
            "       From reviews" +
            "       Where patient_id = :userId", nativeQuery = true)
    double findReviewAvgByPatientId(@Param("userId") Long userId);

    @Query(value = "Select count(*) as ratingCount" +
            "       From reviews" +
            "       Where doctor_id = :userId", nativeQuery = true)
    int findReviewCountByDoctorId(@Param("userId") Long userId);

    @Query(value = "Select count(*) as ratingCount" +
            "       From reviews" +
            "       Where patient_id = :userId", nativeQuery = true)
    int findReviewCountByPatientId(@Param("userId") Long userId);
}
