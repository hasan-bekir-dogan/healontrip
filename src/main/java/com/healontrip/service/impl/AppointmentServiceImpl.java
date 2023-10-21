package com.healontrip.service.impl;

import com.healontrip.dto.AppointmentDto;
import com.healontrip.dto.EmailDetailsDto;
import com.healontrip.entity.AppointmentEntity;
import com.healontrip.entity.UserEntity;
import com.healontrip.exception.ResourceNotFoundException;
import com.healontrip.repository.AppointmentRepository;
import com.healontrip.service.AppointmentService;
import com.healontrip.service.AuthService;
import com.healontrip.service.EmailService;
import com.healontrip.service.UserService;
import jakarta.mail.MessagingException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

@Service
public class AppointmentServiceImpl implements AppointmentService {
    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private AuthService authService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public void createAppointment(AppointmentDto appointmentDto) throws MessagingException, UnsupportedEncodingException {
        AppointmentEntity appointmentEntity = new AppointmentEntity();
        Long patientId = authService.getUserId();

        appointmentEntity.setCommunicationId(appointmentDto.getCommunicationId());
        appointmentEntity.setShortExplanation(appointmentDto.getShortExplanation());
        appointmentEntity.setDoctorId(appointmentDto.getDoctorId());
        appointmentEntity.setPatientId(patientId);

        appointmentRepository.save(appointmentEntity);

        // send email to patient
        AppointmentDto patientData = EntitytoDto(appointmentEntity);
        emailService.sendMailToPatientForAppointment(patientData);

        // send email to doctor
        AppointmentDto doctorData = EntitytoDto(appointmentEntity);
        emailService.sendMailToDoctorForAppointment(doctorData); // this is temporary function. It will be adjusted in the future
    }

    @Override
    public List<AppointmentDto> getAppointmentList(Long userId) {
        List<AppointmentEntity> appointmentEntityList = appointmentRepository.findAppointmentByUserId(userId);
        List<AppointmentDto> appointmentDtoList = new ArrayList<>();

        for (AppointmentEntity appointmentEntity: appointmentEntityList) {
            AppointmentDto appointmentDto = new AppointmentDto();

            appointmentDto.setId(appointmentEntity.getId());
            appointmentDto.setDoctorId(appointmentEntity.getDoctorId());
            appointmentDto.setPatientId(appointmentEntity.getPatientId());
            appointmentDto.setShortExplanation(appointmentEntity.getShortExplanation());
            appointmentDto.setCommunicationId(appointmentEntity.getCommunicationId());

            appointmentDtoList.add(appointmentDto);
        }

        return appointmentDtoList;
    }

    @Override
    public AppointmentEntity findById(Long id) {
        return appointmentRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Appointment not found with id: " + id));
    }


    ////////////////////////////////////////////////////////////////////////////////////////
    // Model Mapper
    // Entity => DTO
    @Override
    public AppointmentDto EntitytoDto(AppointmentEntity appointmentEntity) {
        return modelMapper.map(appointmentEntity, AppointmentDto.class);
    }

    // DTO => Entity
    @Override
    public AppointmentEntity DtoToEntity(AppointmentDto appointmentDto) {
        return modelMapper.map(appointmentDto, AppointmentEntity.class);

    }
}
