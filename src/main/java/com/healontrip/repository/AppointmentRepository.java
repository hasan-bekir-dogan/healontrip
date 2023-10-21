package com.healontrip.repository;

import com.healontrip.entity.AppointmentEntity;
import com.healontrip.entity.AppointmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<AppointmentEntity, Long> {
    @Query(value = "Select *" +
            "       From appointments" +
            "       Where user_id = :userId" +
            "       Order By from_date desc", nativeQuery = true)
    List<AppointmentEntity> findAppointmentByUserId(@Param("userId") Long userId);
}
