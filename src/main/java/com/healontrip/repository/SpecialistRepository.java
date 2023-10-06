package com.healontrip.repository;

import com.healontrip.entity.SpecialistEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpecialistRepository extends JpaRepository<SpecialistEntity, Long> {
    @Query(value = "Select * " +
            "       From specialist " +
            "       Where user_id = :userId " +
            "       Order By id", nativeQuery = true)
    List<SpecialistEntity> findSpecialistByUserId(@Param("userId") Long userId);

    @Query(value = "Select * " +
            "       From specialist " +
            "       Where user_id = :userId " +
            "       Order By id" +
            "       Limit 1", nativeQuery = true)
    SpecialistEntity findSpecialistByUserIdLimit1(@Param("userId") Long userId);

    @Query(value = "Select Distinct name" +
            "       From specialist" +
            "       Order By name", nativeQuery = true)
    List<String> findDistinctSpecialistName();

    @Query(value = "Select *" +
            "       From specialist" +
            "       Where name = :name" +
            "       Order By id" +
            "       Limit 1", nativeQuery = true)
    SpecialistEntity findDistinctSpecialist(@Param("name") String name);
}
