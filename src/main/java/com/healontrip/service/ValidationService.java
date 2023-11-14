package com.healontrip.service;

public interface ValidationService {
    boolean validEmail(String email);
    boolean validUsername(String userName);
    boolean validPassword(String password);
}
