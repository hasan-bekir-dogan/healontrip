package com.healontrip.repository;

import com.healontrip.entity.BlogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BlogRepository extends JpaRepository<BlogEntity, Long> {
    @Query(value = "Select * From blogs b Where b.user_id = :userId And b.status = :status order by b.id desc", nativeQuery = true)
    List<BlogEntity> findBlogByUserId(@Param("userId") Long userId,
                                      @Param("status") String status);

    @Query(value = "Select * From blogs b Where b.status = :status order by b.id desc", nativeQuery = true)
    List<BlogEntity> findBlogByStatus(@Param("status") String status);
}
