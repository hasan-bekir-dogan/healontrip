package com.healontrip.service.impl;

import com.healontrip.dto.ExperienceDto;
import com.healontrip.entity.ExperienceEntity;
import com.healontrip.exception.ResourceNotFoundException;
import com.healontrip.repository.ExperienceRepository;
import com.healontrip.service.ExperienceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ExperienceServiceImpl implements ExperienceService {
    @Autowired
    private ExperienceRepository experienceRepository;

    @Override
    public void createExperience(ExperienceDto experienceDto) {
        ExperienceEntity experienceEntity = new ExperienceEntity();

        experienceEntity.setUserId(experienceDto.getUserId());
        experienceEntity.setHospitalName(experienceDto.getHospitalName());
        experienceEntity.setDesignation(experienceDto.getDesignation());
        experienceEntity.setFromDate(experienceDto.getFromDate());
        experienceEntity.setToDate(experienceDto.getToDate());

        experienceRepository.save(experienceEntity);
    }

    @Override
    public void updateExperience(ExperienceDto experienceDto) {
        ExperienceEntity experienceEntity = findById(experienceDto.getId());

        experienceEntity.setUserId(experienceDto.getUserId());
        experienceEntity.setHospitalName(experienceDto.getHospitalName());
        experienceEntity.setDesignation(experienceDto.getDesignation());
        experienceEntity.setFromDate(experienceDto.getFromDate());
        experienceEntity.setToDate(experienceDto.getToDate());

        experienceRepository.save(experienceEntity);
    }

    @Override
    public void deleteExperience(Long id) {
        ExperienceEntity experienceEntity = findById(id);

        experienceRepository.delete(experienceEntity);
    }

    @Override
    public List<ExperienceDto> getExperienceList(Long userId) {
        List<ExperienceEntity> experienceEntityList = experienceRepository.findExperienceByUserId(userId);
        List<ExperienceDto> experienceDtoList = new ArrayList<>();

        for (ExperienceEntity experienceEntity: experienceEntityList) {
            ExperienceDto experienceDto = new ExperienceDto();

            String from = experienceEntity.getFromDate();
            String to = experienceEntity.getToDate();

            experienceDto.setId(experienceEntity.getId());
            experienceDto.setUserId(experienceEntity.getUserId());
            experienceDto.setHospitalName(experienceEntity.getHospitalName());
            experienceDto.setDesignation(experienceEntity.getDesignation());
            experienceDto.setFromDate(from);
            experienceDto.setToDate(to);

            // year
            Date currentDate = new Date();
            int currentYear = (int) (currentDate.getYear() + 1900);
            int experienceYear = (to == null || to.equals("") ? currentYear : Integer.parseInt(to)) - Integer.parseInt(from);

            String year = from + " - " + ((to == null || to.equals("")) ? "Present" : to) +
                    " (" + (experienceYear) + " years)";
            experienceDto.setYear(year);

            experienceDtoList.add(experienceDto);
        }

        return experienceDtoList;
    }

    @Override
    public int getExperienceYear(Long userId) {
        List<ExperienceEntity> experienceEntityList = experienceRepository.findExperienceByUserId(userId);
        int totalExperienceYear = 0;

        for (ExperienceEntity experienceEntity: experienceEntityList) {
            String from = experienceEntity.getFromDate();
            String to = experienceEntity.getToDate();

            // year
            Date currentDate = new Date();
            int currentYear = (int) (currentDate.getYear() + 1900);
            int experienceYear = (to == null || to.equals("") ? currentYear : Integer.parseInt(to)) - Integer.parseInt(from);

            totalExperienceYear += experienceYear;
        }

        return totalExperienceYear;
    }

    @Override
    public ExperienceEntity findById(Long id) {
        return experienceRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Experience not found with id: " + id));
    }
}
