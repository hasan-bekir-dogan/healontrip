package com.healontrip.repository;

import com.healontrip.entity.BlogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogRepository extends JpaRepository<BlogEntity, Long> {
    @Query(value = "Select *" +
            "       From blogs b " +
            "       Where b.user_id = :userId" +
            "       And b.status = :status" +
            "       Order By b.id Desc", nativeQuery = true)
    List<BlogEntity> findBlogByUserId(@Param("userId") Long userId,
                                      @Param("status") String status);

    @Query(value = "Select *" +
            "       From blogs b" +
            "       Where b.status = :status" +
            "       Order By b.id Desc", nativeQuery = true)
    List<BlogEntity> findBlogByStatus(@Param("status") String status);

    @Query(value = "Select * " +
            "       From blogs b " +
            "       Where b.status = :status " +
            "       Order By b.id Desc" +
            "       Limit :limit" +
            "       Offset :pageNumber", nativeQuery = true)
    List<BlogEntity> findBlogByStatusAndLimit(@Param("status") String status,
                                              @Param("limit") int limit,
                                              @Param("pageNumber") int pageNumber);

    @Query(value = "Select *" +
            "       From blogs b" +
            "       Where b.slug = :slug" +
            "       Order By b.id Desc", nativeQuery = true)
    BlogEntity findBySlug(@Param("slug") String slug);
}
