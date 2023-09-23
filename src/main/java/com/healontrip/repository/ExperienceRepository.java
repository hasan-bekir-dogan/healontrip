package com.healontrip.repository;

import com.healontrip.entity.ExperienceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExperienceRepository extends JpaRepository<ExperienceEntity, Long> {
    @Query(value = "(Select *" +
            "       From experiences" +
            "       Where user_id = :userId" +
            "       And to_date = \"\")" +
            "" +
            "       Union" +
            "" +
            "       (Select *" +
            "       From experiences" +
            "       Where user_id = :userId" +
            "       And to_date != \"\"" +
            "       Order By to_date desc)", nativeQuery = true)
    List<ExperienceEntity> findExperienceByUserId(@Param("userId") Long userId);
}
