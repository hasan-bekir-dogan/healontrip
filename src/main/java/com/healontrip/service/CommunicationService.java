package com.healontrip.service;

import com.healontrip.dto.CommunicationDto;
import com.healontrip.entity.CommunicationEntity;

import java.util.List;

public interface CommunicationService {
    List<CommunicationDto> getAllCommunications();
    CommunicationEntity findById(Long id);
    CommunicationDto EntitytoDto(CommunicationEntity communicationEntity);
    CommunicationEntity DtoToEntity(CommunicationDto communicationDto);
}
