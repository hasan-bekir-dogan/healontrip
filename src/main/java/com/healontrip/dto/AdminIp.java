package com.healontrip.dto;

public enum AdminIp {
    IP(new String[] {
            "88.230.232.153", // Bekir - İzmir Ev interneti
            "212.252.142.144", // Adil - İzmir Ev interneti
            "88.241.79.94", // Adil - Aydın Ev interneti
            "88.240.167.114", // Adil - Aydın Ev interneti
            "24.133.52.76", // Okan
            "192.168.1.127",
            "192.168.1.101",
            "46.154.70.59", // Adil - Telefon
            "5.47.23.148", // Bekir - Telefon
            "176.220.154.232",
            "176.240.165.34",
            "176.41.47.230",
            "88.230.237.4",
            "46.154.6.116",
            "88.241.75.25"
    });

    private final String[] ip;

    AdminIp(String[] ip) {
        this.ip = ip;
    }

    public String[] getIp() {
        return this.ip;
    }
}
