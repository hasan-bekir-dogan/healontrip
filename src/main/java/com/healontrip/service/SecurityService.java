package com.healontrip.service;

import com.healontrip.dto.TokenType;

public interface SecurityService {
    boolean validateToken(String userEmail, String token, TokenType type);
}
