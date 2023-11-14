package com.healontrip.service.impl;

import com.healontrip.dto.TokenType;
import com.healontrip.repository.*;
import com.healontrip.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SecurityServiceImpl implements SecurityService {
    @Autowired
    private TokenRepository tokenRepository;

    @Override
    public boolean validateToken(String userEmail, String token, TokenType type) {
        return tokenRepository.findTokenByUserIdAndTokenAndType(userEmail, token, type.toString()) != null;
    }
}
