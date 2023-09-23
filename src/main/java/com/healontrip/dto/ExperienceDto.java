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
public class ExperienceDto {
    private Long id;
    private Long userId;
    private String hospitalName;
    private String designation;
    private String fromDate;
    private String toDate;
    private String year;
}
