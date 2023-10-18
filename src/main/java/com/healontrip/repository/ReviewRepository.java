package com.healontrip.repository;

import com.healontrip.dto.ReviewBarDto;
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
            "       Where doctor_id = :doctorId" +
            "       Order By id desc", nativeQuery = true)
    List<ReviewEntity> findReviewByDoctorId(@Param("doctorId") Long doctorId);

    @Query(value = "Select *" +
            "       From reviews" +
            "       Where doctor_id = :doctorId" +
            "       Order By id desc" +
            "       Limit :limit", nativeQuery = true)
    List<ReviewEntity> findReviewByDoctorIdByLimit(@Param("doctorId") Long doctorId,
                                                   @Param("limit") int limit);

    @Query(value = "Select avg(rating) as ratingAvg" +
            "       From reviews" +
            "       Where doctor_id = :doctorId", nativeQuery = true)
    double findReviewAvgByDoctorId(@Param("doctorId") Long doctorId);

    @Query(value = "Select count(*) as ratingCount" +
            "       From reviews" +
            "       Where doctor_id = :doctorId", nativeQuery = true)
    int findReviewCountByDoctorId(@Param("doctorId") Long doctorId);
}
