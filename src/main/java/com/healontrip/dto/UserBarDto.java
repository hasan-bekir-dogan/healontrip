package com.healontrip.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Log4j2
public class UserBarDto {
    private String userName;
    private String profileImgSrc;
    private String profileImgAlt;
    private String role;
    private String roleInitCap;
    private String department;

    // doctor
    private String infoShort;

    // patient
    private String dateOfBirth;
    private String addressShort;
}
