package com.healontrip.dto;

public enum FileCode {
    PROFILE_IMG("profile-default");

    private String code;

    FileCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }
}
