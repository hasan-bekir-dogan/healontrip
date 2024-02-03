package com.healontrip.service.impl;

import com.healontrip.dto.*;
import com.healontrip.entity.SpecialistEntity;
import com.healontrip.exception.ResourceNotFoundException;
import com.healontrip.repository.SpecialistRepository;
import com.healontrip.service.SpecialistService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SpecialistServiceImpl implements SpecialistService {
    @Autowired
    private SpecialistRepository specialistRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<SpecialistDto> getSpecialists() {
        List<SpecialistDto> specialistDtoList = new ArrayList<>();

        List<SpecialistEntity> specialistEntityList = specialistRepository.findSpecialists();

        for (SpecialistEntity specialistEntity : specialistEntityList)
            specialistDtoList.add(EntityToDto(specialistEntity));

        return specialistDtoList;
    }

    @Override
    public SpecialistEntity findById(Long id) {
        return specialistRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Specialist not found with id: " + id));
    }

    ////////////////////////////////////////////////////////////////////////////////////////
    // Model Mapper
    // Entity => DTO
    @Override
    public SpecialistDto EntityToDto(SpecialistEntity specialistEntity) {
        SpecialistDto specialistDto = modelMapper.map(specialistEntity, SpecialistDto.class);
        return specialistDto;
    }
}
