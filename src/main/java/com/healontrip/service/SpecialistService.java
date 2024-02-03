package com.healontrip.service;

import com.healontrip.dto.SpecialistDto;
import com.healontrip.entity.SpecialistEntity;

import java.util.List;

public interface SpecialistService {
    List<SpecialistDto> getSpecialists();
    SpecialistEntity findById(Long id);

    SpecialistDto EntityToDto(SpecialistEntity specialistEntity);
}
