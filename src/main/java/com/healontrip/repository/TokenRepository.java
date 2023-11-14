package com.healontrip.repository;

import com.healontrip.entity.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TokenRepository extends JpaRepository<TokenEntity, Long> {
    @Query(value = "Select * " +
            "       From tokens " +
            "       Where user_email = :userEmail " +
            "       And token = :token " +
            "       And type = :type" +
            "       And expiry_date >= sysdate()", nativeQuery = true)
    TokenEntity findTokenByUserIdAndTokenAndType(@Param("userEmail") String userEmail,
                                                 @Param("token") String token,
                                                 @Param("type") String type);

    @Query(value = "Select id " +
            "       From tokens" +
            "       Where expiry_date < sysdate() ", nativeQuery = true)
    List<Long> findAllExpiredTokens();

    @Query(value = "Select * " +
            "       From tokens" +
            "       Where user_email = :userEmail ", nativeQuery = true)
    TokenEntity findByEmail(@Param("userEmail") String userEmail);
}
