package com.healontrip.service;

public interface SecurityService {
    boolean validatePasswordResetToken(Long userId, String token);
}
