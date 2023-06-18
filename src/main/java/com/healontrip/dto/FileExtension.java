package com.healontrip.dto;

public enum FileExtension {
    JPG(".jpg"),
    PNG(".png"),
    JPEG(".jpeg");

    private String ext;

    FileExtension(String ext) {
        this.ext = ext;
    }

    public String getExt() {
        return this.ext;
    }
}
