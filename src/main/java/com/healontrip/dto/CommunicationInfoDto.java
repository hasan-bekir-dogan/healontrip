package com.healontrip.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Log4j2
public class CommunicationInfoDto {
    private Long id;
    private String name;
    private Long userId;
    private String phone;
    private String whatsapp;
    private String email;
}
