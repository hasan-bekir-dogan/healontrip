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
public class DoctorsDto {
    private Long userId;
    private String extendedUserName;
    private String userName;
    private String userImgSrc;
    private String userImgAlt;
}
