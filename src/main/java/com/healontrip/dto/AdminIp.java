package com.healontrip.dto;

public enum AdminIp {
    IP(new String[] {
            "172.20.10.3",
            "192.168.1.109"
    });

    private final String[] ip;

    AdminIp(String[] ip) {
        this.ip = ip;
    }

    public String[] getIp() {
        return this.ip;
    }
}
