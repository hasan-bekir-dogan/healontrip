package com.healontrip.service;

import com.healontrip.dto.AwardDto;
import com.healontrip.entity.AwardEntity;

import java.util.List;

public interface AwardService {
    void createAward(AwardDto awardDto);
    void updateAward(AwardDto awardDto);
    void deleteAward(Long id);
    List<AwardDto> getAwardList(Long userId);
    AwardEntity findById(Long id);
}
