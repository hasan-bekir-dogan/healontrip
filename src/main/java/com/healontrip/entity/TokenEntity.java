package com.healontrip.entity;

import com.healontrip.dto.TokenType;
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
@Table(name = "tokens")
public class TokenEntity extends BaseEntity implements Serializable {

    public static final int EXPIRATION = 30; // 30 min.

    @Enumerated(EnumType.STRING)
    private TokenType type;

    @Column(name = "token", nullable = false)
    private String token;

    @Column(name = "user_email", nullable = false)
    private String userEmail;

    @Column(name = "expiry_date")
    private Date expiryDate;

    public TokenEntity(TokenType type, String token, String userEmail, Date expiryDate) {
        this.type = type;
        this.token = token;
        this.userEmail = userEmail;
        this.expiryDate = expiryDate;
    }
}