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
            "       Where doctor_id = :doctorId" +
            "       Order By id asc", nativeQuery = true)
    List<ReviewEntity> findReviewByDoctorId(@Param("doctorId") Long doctorId);
}
