package com.healontrip.service;

import com.healontrip.dto.EducationDto;
import com.healontrip.entity.EducationEntity;

import java.util.List;

public interface EducationService {
    void createEducation(EducationDto educationDto);
    void updateEducation(EducationDto educationDto);
    void deleteEducation(Long id);
    List<EducationDto> getEducationList(Long userId);
    EducationEntity findById(Long id);
}
