package com.healontrip.service;

import com.healontrip.dto.*;
import jakarta.mail.MessagingException;

import java.io.UnsupportedEncodingException;

public interface EmailService {
    void sendMailToPatientForAppointment(AppointmentDto data) throws MessagingException, UnsupportedEncodingException;
    void sendMailToDoctorForAppointment(AppointmentDto data) throws MessagingException, UnsupportedEncodingException;
}
