package com.healontrip.repository;

import com.healontrip.entity.AwardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AwardRepository extends JpaRepository<AwardEntity, Long> {
    @Query(value = "Select * " +
            "       From awards " +
            "       Where user_id = :userId " +
            "       Order By year desc", nativeQuery = true)
    List<AwardEntity> findAwardByUserId(@Param("userId") Long userId);
}
