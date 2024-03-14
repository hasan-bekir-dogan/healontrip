package com.healontrip.repository;

import com.healontrip.entity.SocialMediaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SocialMediaRepository extends JpaRepository<SocialMediaEntity, Long> {
}
