package com.healontrip.dto;

public enum AdminIp {
    IP(new String[] {
            "88.230.232.153", // Bekir - İzmir Ev interneti
            "212.252.142.144", // Adil - İzmir Ev interneti
            "88.241.79.94", // Adil - Aydın Ev interneti
            "24.133.52.76", // Okan
            "192.168.1.127",
            "46.154.70.59", // Adil - Telefon
            "5.47.76.62" // Bekir - Telefon
    });

    private final String[] ip;

    AdminIp(String[] ip) {
        this.ip = ip;
    }

    public String[] getIp() {
        return this.ip;
    }
}
