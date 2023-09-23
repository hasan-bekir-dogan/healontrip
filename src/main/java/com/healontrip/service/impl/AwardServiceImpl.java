package com.healontrip.service.impl;

import com.healontrip.dto.AwardDto;
import com.healontrip.entity.AwardEntity;
import com.healontrip.exception.ResourceNotFoundException;
import com.healontrip.repository.AwardRepository;
import com.healontrip.service.AwardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AwardServiceImpl implements AwardService {
    @Autowired
    private AwardRepository awardRepository;

    @Override
    public void createAward(AwardDto awardDto) {
        AwardEntity awardEntity = new AwardEntity();

        awardEntity.setUserId(awardDto.getUserId());
        awardEntity.setName(awardDto.getName());
        awardEntity.setDescription(awardDto.getDescription());
        awardEntity.setYear(awardDto.getYear());

        awardRepository.save(awardEntity);
    }

    @Override
    public void updateAward(AwardDto awardDto) {
        AwardEntity awardEntity = findById(awardDto.getId());

        awardEntity.setUserId(awardDto.getUserId());
        awardEntity.setName(awardDto.getName());
        awardEntity.setDescription(awardDto.getDescription());
        awardEntity.setYear(awardDto.getYear());

        awardRepository.save(awardEntity);
    }

    @Override
    public void deleteAward(Long id) {
        AwardEntity awardEntity = findById(id);

        awardRepository.delete(awardEntity);
    }

    @Override
    public List<AwardDto> getAwardList(Long userId) {
        List<AwardEntity> awardEntityList = awardRepository.findAwardByUserId(userId);
        List<AwardDto> awardDtoList = new ArrayList<>();

        for (AwardEntity awardEntity: awardEntityList) {
            AwardDto awardDto = new AwardDto();

            awardDto.setId(awardEntity.getId());
            awardDto.setUserId(awardEntity.getUserId());
            awardDto.setName(awardEntity.getName());
            awardDto.setDescription(awardEntity.getDescription());
            awardDto.setYear(awardEntity.getYear());

            awardDtoList.add(awardDto);
        }

        return awardDtoList;
    }

    @Override
    public AwardEntity findById(Long id) {
        return awardRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Award not found with id: " + id));
    }
}
