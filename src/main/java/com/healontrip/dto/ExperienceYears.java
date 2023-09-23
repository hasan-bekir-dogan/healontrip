package com.healontrip.dto;

public enum ExperienceYears {
    GREATER_THAN_1_SMALLER_THAN_5(3, "1-5 Years"),
    GREATER_THAN_5(6, "5+ Years");

    private int id;
    private String name;

    ExperienceYears(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return this.id;
    }
    public String getName() {
        return this.name;
    }
}
