package com.healontrip.dto;

public enum FileSource {
    BASE("src/main/resources/static"),
    UPLOADS("/uploads/"),
    ASSETS_GENERAL_IMG("/assets/img/general-images/");

    private String src;

    FileSource(String src) {
        this.src = src;
    }

    public String getSrc() {
        return src;
    }
}
