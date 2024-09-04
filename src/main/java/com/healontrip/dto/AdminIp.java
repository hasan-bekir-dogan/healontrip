package com.healontrip.dto;

public enum AdminIp {
    IP(new String[] {
            "172.20.10.3",
            "192.168.1.109",
            "192.168.1.110",
            "172.17.208.1",
            "192.168.1.122",
            "192.168.1.168",
            "192.168.1.172",
            "172.20.10.11",
            "192.168.1.179",
            "192.168.1.180",
            "192.168.0.12",
            "192.168.1.158"
    });

    private final String[] ip;

    AdminIp(String[] ip) {
        this.ip = ip;
    }

    public String[] getIp() {
        return this.ip;
    }
}
