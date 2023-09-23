package com.healontrip.repository;

import com.healontrip.entity.ExperienceYearEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExperienceYearRepository extends JpaRepository<ExperienceYearEntity, Long> {
}
