package com.healontrip.repository;

import com.healontrip.entity.ServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceRepository extends JpaRepository<ServiceEntity, Long> {
    @Query(value = "Select * " +
            "       From services " +
            "       Where user_id = :userId " +
            "       Order By id", nativeQuery = true)
    List<ServiceEntity> findServiceByUserId(@Param("userId") Long userId);
}
