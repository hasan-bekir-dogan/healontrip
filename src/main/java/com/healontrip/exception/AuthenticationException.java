package com.healontrip.exception;

import java.io.Serializable;

public class AuthenticationException extends RuntimeException implements Serializable {
    private static final Long serialVersionUID = 1L;

    public AuthenticationException (String message) {
        super(message);
    }
}
