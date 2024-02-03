package com.healontrip.repository;

import com.healontrip.entity.SpecialistEntity;
import com.healontrip.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpecialistRepository extends JpaRepository<SpecialistEntity, Long> {
    @Query(value = "Select *" +
            "       From specialist", nativeQuery = true)
    List<SpecialistEntity> findSpecialists();
}
