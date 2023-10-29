package com.healontrip.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@Builder
@Log4j2

@Entity
@Table(name = "password_reset_tokens")
public class PasswordResetTokenEntity extends BaseEntity implements Serializable {

    public static final int EXPIRATION = 30; // 30 min.

    @Column(name = "token", nullable = false)
    private String token;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "expiry_date")
    private Date expiryDate;

    public PasswordResetTokenEntity(String token, Long userId, Date expiryDate) {
        this.token = token;
        this.userId = userId;
        this.expiryDate = expiryDate;
    }
}