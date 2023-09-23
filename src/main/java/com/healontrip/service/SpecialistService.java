package com.healontrip.service;

import com.healontrip.dto.ProfileDto;
import com.healontrip.dto.SpecialistDto;
import com.healontrip.dto.SpecializationDto;
import com.healontrip.entity.SpecialistEntity;

import java.util.List;

public interface SpecialistService {
    void updateSpecialist(ProfileDto profileDto);
    String getSpecialists(Long userId);
    List<SpecializationDto> getSpecialistList(Long userId);
    List<SpecialistDto> getSpecialistDistinctList();
    SpecialistEntity findById(Long id);
}
