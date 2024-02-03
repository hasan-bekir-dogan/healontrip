package com.healontrip.service.impl;

import com.healontrip.dto.ProfileDto;
import com.healontrip.dto.ServiceDto;
import com.healontrip.entity.ServiceEntity;
import com.healontrip.entity.ServiceEntity;
import com.healontrip.repository.ServiceRepository;
import com.healontrip.service.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ServiceServiceImpl implements ServiceService {
    @Autowired
    private ServiceRepository serviceRepository;

    @Override
    public void updateService(ProfileDto profileDto) {
        String[] serviceArr = profileDto.getService().split(",");
        List<ServiceEntity> serviceEntityList = serviceRepository.findServiceByUserId(profileDto.getId());
        boolean exist;

        // delete service from db if not exist on profile service (begin)
        for (ServiceEntity serviceEntity: serviceEntityList) {
            exist = false;

            for (String service: serviceArr) {
                if (serviceEntity.getName().equals(service)){
                    exist = true;
                    break;
                }
            }

            if (!exist)
                serviceRepository.delete(serviceEntity);
        }
        // delete service from db if not exist on profile service (end)

        // insert service to db if not exist on db (begin)
        for (String service: serviceArr) {
            exist = false;

            for (ServiceEntity serviceEntity: serviceEntityList) {
                if (service.equals(serviceEntity.getName())){
                    exist = true;
                    break;
                }
            }

            if (!exist) {
                ServiceEntity newServiceEntity = new ServiceEntity();
                newServiceEntity.setName(service);
                newServiceEntity.setUserId(profileDto.getId());
                serviceRepository.save(newServiceEntity);
            }
        }
        // insert service to db if not exist on db (end)
    }

    @Override
    public String getServices(Long userId) {
        List<ServiceEntity> serviceEntityList = serviceRepository.findServiceByUserId(userId);
        String serviceList = "";

        for (ServiceEntity serviceEntity: serviceEntityList) {
            if (serviceList.equals(""))
                serviceList += serviceEntity.getName();
            else
                serviceList += "," + serviceEntity.getName();
        }

        return serviceList;
    }

    @Override
    public List<ServiceDto> getServiceList(Long userId) {
        List<ServiceEntity> serviceEntityList = serviceRepository.findServiceByUserId(userId);
        List<ServiceDto> serviceDtoList = new ArrayList<>();

        for (ServiceEntity serviceEntity: serviceEntityList) {
            serviceDtoList.add(new ServiceDto(serviceEntity.getName()));
        }

        return serviceDtoList;
    }
}
