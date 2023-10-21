package com.healontrip.service;

import com.healontrip.dto.AppointmentDto;
import com.healontrip.entity.AppointmentEntity;
import jakarta.mail.MessagingException;

import java.io.UnsupportedEncodingException;
import java.util.List;

public interface AppointmentService {
    void createAppointment(AppointmentDto appointmentDto) throws MessagingException, UnsupportedEncodingException;
    List<AppointmentDto> getAppointmentList(Long userId);
    AppointmentEntity findById(Long id);

    AppointmentDto EntitytoDto(AppointmentEntity appointmentEntity);
    AppointmentEntity DtoToEntity(AppointmentDto appointmentDto);
}
