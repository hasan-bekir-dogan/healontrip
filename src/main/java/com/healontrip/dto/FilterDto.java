package com.healontrip.dto;

import com.healontrip.entity.ExperienceYearEntity;
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
public class FilterDto {
    private List<GenderDto> genderList;
    private List<SpecialistDto> specialityList;
    private List<ExperienceYearEntity> experienceYearList;
    //private List<Integer> ratingList;
}
