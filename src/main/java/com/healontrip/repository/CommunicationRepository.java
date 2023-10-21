package com.healontrip.repository;

import com.healontrip.entity.CommunicationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommunicationRepository extends JpaRepository<CommunicationEntity, Long> {

}
