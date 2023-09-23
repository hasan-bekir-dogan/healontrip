package com.healontrip.service.impl;

import com.healontrip.dto.ProfileDto;
import com.healontrip.dto.SpecialistDto;
import com.healontrip.dto.SpecializationDto;
import com.healontrip.dto.UserBarDto;
import com.healontrip.entity.BlogEntity;
import com.healontrip.entity.SpecialistEntity;
import com.healontrip.entity.UserEntity;
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

    @Override
    public void updateSpecialist(ProfileDto profileDto) {
        String[] specialistArr = profileDto.getSpecialist().split(",");
        List<SpecialistEntity> specialistEntityList = specialistRepository.findSpecialistByUserId(profileDto.getId());
        boolean exist;

        // delete specialist from db if not exist on profile specialist (begin)
        for (SpecialistEntity specialistEntity: specialistEntityList) {
            exist = false;

            for (String specialist: specialistArr) {
                if (specialistEntity.getName().equals(specialist)){
                    exist = true;
                    break;
                }
            }

            if (!exist)
                specialistRepository.delete(specialistEntity);
        }
        // delete specialist from db if not exist on profile specialist (end)

        // insert specialist to db if not exist on db (begin)
        for (String specialist: specialistArr) {
            exist = false;

            for (SpecialistEntity specialistEntity: specialistEntityList) {
                if (specialist.equals(specialistEntity.getName())){
                    exist = true;
                    break;
                }
            }

            if (!exist) {
                SpecialistEntity newSpecialistEntity = new SpecialistEntity();
                newSpecialistEntity.setName(specialist);
                newSpecialistEntity.setUserId(profileDto.getId());
                specialistRepository.save(newSpecialistEntity);
            }
        }
        // insert specialist to db if not exist on db (end)
    }

    @Override
    public String getSpecialists(Long userId) {
        List<SpecialistEntity> specialistEntityList = specialistRepository.findSpecialistByUserId(userId);
        String specialistList = "";

        for (SpecialistEntity specialistEntity: specialistEntityList) {
            if (specialistList.equals(""))
                specialistList += specialistEntity.getName();
            else
                specialistList += "," + specialistEntity.getName();
        }

        return specialistList;
    }

    @Override
    public List<SpecializationDto> getSpecialistList(Long userId) {
        List<SpecialistEntity> specialistEntityList = specialistRepository.findSpecialistByUserId(userId);
        List<SpecializationDto> specializationDtoList = new ArrayList<>();

        for (SpecialistEntity specialistEntity: specialistEntityList) {
            specializationDtoList.add(new SpecializationDto(specialistEntity.getName()));
        }

        return specializationDtoList;
    }

    @Override
    public List<SpecialistDto> getSpecialistDistinctList() {
        List<String> specialistNameList = specialistRepository.findDistinctSpecialistName();
        List<SpecialistDto> specializationDtoList = new ArrayList<>();

        for (String specialistName: specialistNameList) {
            SpecialistEntity specialistEntity = specialistRepository.findDistinctSpecialist(specialistName);

            specializationDtoList.add(
                    new SpecialistDto(
                        specialistEntity.getId(),
                        specialistEntity.getName(),
                        specialistEntity.getUserId()
                    )
            );
        }

        return specializationDtoList;
    }

    @Override
    public SpecialistEntity findById(Long id) {
        return specialistRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Specialist not found with id: " + id));
    }
}
