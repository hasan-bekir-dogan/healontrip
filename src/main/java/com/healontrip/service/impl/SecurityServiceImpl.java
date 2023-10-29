package com.healontrip.service.impl;

import com.healontrip.repository.*;
import com.healontrip.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SecurityServiceImpl implements SecurityService {
    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Override
    public boolean validatePasswordResetToken(Long userId, String token) {
        return passwordResetTokenRepository.findPasswordResetTokenByUserIdAndToken(userId, token) != null;
    }
}
