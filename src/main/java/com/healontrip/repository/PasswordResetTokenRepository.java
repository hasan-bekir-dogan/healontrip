package com.healontrip.repository;

import com.healontrip.entity.PasswordResetTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetTokenEntity, Long> {
    @Query(value = "Select * " +
            "       From password_reset_tokens " +
            "       Where user_id = :userId " +
            "       And token = :token " +
            "       And expiry_date >= sysdate()", nativeQuery = true)
    PasswordResetTokenEntity findPasswordResetTokenByUserIdAndToken(@Param("userId") Long userId,
                                                                    @Param("token") String token);

    @Query(value = "Select id " +
            "       From password_reset_tokens" +
            "       Where expiry_date < sysdate() ", nativeQuery = true)
    List<Long> findAllExpiredTokens();
}
