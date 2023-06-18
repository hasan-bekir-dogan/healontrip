package com.healontrip.service;

public interface AuthService {
    String getUserName();
    String getRole();
    String getSessionId();
    String getIpAddress();
    Long getUserId();
    Boolean isAuthenticated();
}
