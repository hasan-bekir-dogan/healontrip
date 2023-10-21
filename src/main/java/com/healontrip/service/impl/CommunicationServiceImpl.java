package com.healontrip.service.impl;

import com.healontrip.dto.CommunicationDto;
import com.healontrip.entity.CommunicationEntity;
import com.healontrip.exception.ResourceNotFoundException;
import com.healontrip.repository.CommunicationRepository;
import com.healontrip.service.CommunicationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommunicationServiceImpl implements CommunicationService {
    @Autowired
    private CommunicationRepository communicationRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<CommunicationDto> getAllCommunications() {
        List<CommunicationEntity> communicationEntityList = communicationRepository.findAll();
        List<CommunicationDto> communicationDtoList = new ArrayList<>();

        for (CommunicationEntity entity: communicationEntityList){
            CommunicationDto communicationDto = EntitytoDto(entity);
            communicationDtoList.add(communicationDto);
        }

        return communicationDtoList;
    }

    @Override
    public CommunicationEntity findById(Long id) {
        return communicationRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Communication not found with id: " + id));
    }

    ////////////////////////////////////////////////////////////////////////////////////////
    // Model Mapper
    // Entity => DTO@Override
    @Override
    public CommunicationDto EntitytoDto(CommunicationEntity communicationEntity) {
        return modelMapper.map(communicationEntity, CommunicationDto.class);
    }

    // DTO => Entity
    @Override
    public CommunicationEntity DtoToEntity(CommunicationDto communicationDto) {
        return modelMapper.map(communicationDto, CommunicationEntity.class);
    }
}
