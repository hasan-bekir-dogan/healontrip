package com.healontrip.service;

import com.healontrip.dto.ProfileDto;
import com.healontrip.dto.ServiceDto;

import java.util.List;

public interface ServiceService {
    void updateService(ProfileDto profileDto);
    String getServices(Long userId);
    List<ServiceDto> getServiceList(Long userId);
}
