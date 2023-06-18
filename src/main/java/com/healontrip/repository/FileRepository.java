package com.healontrip.repository;

import com.healontrip.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FileRepository extends JpaRepository<FileEntity, Long> {
    @Query(value = "Select * From files Where code = :code", nativeQuery = true)
    FileEntity findByCode(@Param("code") String code);
}
