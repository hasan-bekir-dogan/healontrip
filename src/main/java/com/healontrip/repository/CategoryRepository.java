package com.healontrip.repository;

import com.healontrip.dto.CategoryByCountDto;
import com.healontrip.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

    @Query("""
               Select new com.healontrip.dto.CategoryByCountDto(b.categoryId, c.name, count(b.categoryId))
               From BlogEntity b
               Left Join CategoryEntity c on b.categoryId = c.id
               Group By b.categoryId
           """)
    List<CategoryByCountDto> findAllByCount();

}
