package com.healontrip.repository;

import com.healontrip.entity.EducationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EducationRepository extends JpaRepository<EducationEntity, Long> {
    @Query(value = "(Select *" +
            "       From educations" +
            "       Where user_id = :userId" +
            "       And to_date = \"\")" +
            "" +
            "       Union" +
            "" +
            "       (Select *" +
            "       From educations" +
            "       Where user_id = :userId" +
            "       And to_date != \"\"" +
            "       Order By to_date desc)", nativeQuery = true)
    List<EducationEntity> findEducationByUserId(@Param("userId") Long userId);
}
