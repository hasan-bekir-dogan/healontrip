package com.healontrip.dto;

public enum RolePrefix {
    DOCTOR("Dr. ");

    private String pre;

    RolePrefix(String pre) {
        this.pre = pre;
    }
    public String getPre() {
        return pre;
    }
}
