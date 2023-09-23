package com.healontrip.repository;

import com.healontrip.entity.MembershipEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MembershipRepository extends JpaRepository<MembershipEntity, Long> {
    @Query(value = "Select * " +
            "       From memberships " +
            "       Where user_id = :userId " +
            "       Order By id", nativeQuery = true)
    List<MembershipEntity> findMembershipByUserId(@Param("userId") Long userId);
}
