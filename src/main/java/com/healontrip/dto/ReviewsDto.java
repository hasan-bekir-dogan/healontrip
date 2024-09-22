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
public class ReviewsDto {
    private Long id;
    private Long doctorId;
    private String doctorProfileImgSrc;
    private String doctorProfileImgAlt;
    private String doctorUserName;
    private String doctorFullName;
    private Long patientId;
    private String patientProfileImgSrc;
    private String patientProfileImgAlt;
    private String patientUserName;
    private String patientFullName;
    private String createdDate;
    private int rating;
    private String detail;
    private Double ratingAvg;
    private String ratingAvgStr;
    private int ratingCount;
}
