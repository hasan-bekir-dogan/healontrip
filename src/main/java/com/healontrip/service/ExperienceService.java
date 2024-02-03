package com.healontrip.service;

import com.healontrip.dto.ExperienceDto;
import com.healontrip.entity.ExperienceEntity;

import java.util.List;

public interface ExperienceService {
    void createExperience(ExperienceDto experienceDto);
    void updateExperience(ExperienceDto experienceDto);
    void deleteExperience(Long id);
    List<ExperienceDto> getExperienceList(Long userId);
    int getExperienceYear(Long userId);
    ExperienceEntity findById(Long id);
}
