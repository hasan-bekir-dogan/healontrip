package com.healontrip.service;

import com.healontrip.dto.*;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;

import java.io.UnsupportedEncodingException;

public interface MailService {
    void sendMailToPatientForAppointment(AppointmentDto data) throws MessagingException, UnsupportedEncodingException;
    void sendMailToDoctorForAppointment(AppointmentDto data) throws MessagingException, UnsupportedEncodingException;
    void sendMailToUserForResetPassword(HttpServletRequest request, String receiverMail) throws MessagingException, UnsupportedEncodingException;
}
