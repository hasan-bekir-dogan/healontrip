package com.healontrip.dto;

public enum GenderCode {
    MALE("M", "Male"),
    FEMALE("F", "Female");

    private String id;
    private String name;

    GenderCode(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return this.id;
    }
    public String getName() {
        return this.name;
    }
}
