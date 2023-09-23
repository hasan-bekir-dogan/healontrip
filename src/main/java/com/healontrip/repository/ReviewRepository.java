package com.healontrip.repository;

import com.healontrip.entity.ServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<ServiceEntity, Long> {
}
