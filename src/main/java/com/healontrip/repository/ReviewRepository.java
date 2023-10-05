package com.healontrip.repository;

import com.healontrip.entity.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {
    @Query(value = "Select * " +
            "       From review " +
            "       Where user_id = :userId " +
            "       Order By id", nativeQuery = true)
    List<ReviewEntity> findReviewByUserId(@Param("userId") Long userId);
}
