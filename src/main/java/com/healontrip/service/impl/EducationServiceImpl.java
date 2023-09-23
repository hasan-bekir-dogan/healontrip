package com.healontrip.service.impl;

import com.healontrip.dto.EducationDto;
import com.healontrip.entity.EducationEntity;
import com.healontrip.exception.ResourceNotFoundException;
import com.healontrip.repository.EducationRepository;
import com.healontrip.service.EducationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EducationServiceImpl implements EducationService {
    @Autowired
    private EducationRepository educationRepository;

    @Override
    public void createEducation(EducationDto educationDto) {
        EducationEntity educationEntity = new EducationEntity();

        educationEntity.setUserId(educationDto.getUserId());
        educationEntity.setDegree(educationDto.getDegree());
        educationEntity.setUniversity(educationDto.getUniversity());
        educationEntity.setFromDate(educationDto.getFromDate());
        educationEntity.setToDate(educationDto.getToDate());

        educationRepository.save(educationEntity);
    }

    @Override
    public void updateEducation(EducationDto educationDto) {
        EducationEntity educationEntity = findById(educationDto.getId());

        educationEntity.setUserId(educationDto.getUserId());
        educationEntity.setDegree(educationDto.getDegree());
        educationEntity.setUniversity(educationDto.getUniversity());
        educationEntity.setFromDate(educationDto.getFromDate());
        educationEntity.setToDate(educationDto.getToDate());

        educationRepository.save(educationEntity);
    }

    @Override
    public void deleteEducation(Long id) {
        EducationEntity educationEntity = findById(id);

        educationRepository.delete(educationEntity);
    }

    @Override
    public List<EducationDto> getEducationList(Long userId) {
        List<EducationEntity> educationEntityList = educationRepository.findEducationByUserId(userId);
        List<EducationDto> educationDtoList = new ArrayList<>();

        for (EducationEntity educationEntity: educationEntityList) {
            EducationDto educationDto = new EducationDto();

            String from = educationEntity.getFromDate();
            String to = educationEntity.getToDate();

            educationDto.setId(educationEntity.getId());
            educationDto.setUserId(educationEntity.getUserId());
            educationDto.setDegree(educationEntity.getDegree());
            educationDto.setUniversity(educationEntity.getUniversity());
            educationDto.setFromDate(from);
            educationDto.setToDate(to);

            String year = from + " - " + ((to == null || to.equals("")) ? "Present" : to);
            educationDto.setYear(year);

            educationDtoList.add(educationDto);
        }

        return educationDtoList;
    }

    @Override
    public EducationEntity findById(Long id) {
        return educationRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Education not found with id: " + id));
    }
}
