package com.healontrip.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Log4j2
public class SearchFilterDto {
    private List<String> genderList;
    private List<Long> specialityList;
    private List<Integer> experienceYearList;
    private List<Integer> ratingList;
}
