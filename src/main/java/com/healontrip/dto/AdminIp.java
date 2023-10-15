package com.healontrip.dto;

public enum AdminIp {
    IP(new String[] {
            "88.230.232.153", // Bekir - Windows 10 Pro
            "88.241.79.94", // Adil
            "24.133.52.76", // Okan
            //"192.168.1.127"
    });

    private final String[] ip;

    AdminIp(String[] ip) {
        this.ip = ip;
    }

    public String[] getIp() {
        return this.ip;
    }
}
