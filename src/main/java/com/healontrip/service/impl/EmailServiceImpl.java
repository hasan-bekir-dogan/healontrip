package com.healontrip.service.impl;

import com.healontrip.dto.AppointmentDto;
import com.healontrip.dto.EmailDetailsDto;
import com.healontrip.entity.UserEntity;
import com.healontrip.service.*;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Value;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.io.UnsupportedEncodingException;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private SpringTemplateEngine templateEngine;

    @Autowired
    private UserService userService;

    @Value("${spring.mail.username}")
    private String sender;

    @Value("${mail.from.name}")
    private String mailFromName;

    public void sendMailToPatientForAppointment(AppointmentDto data) throws MessagingException, UnsupportedEncodingException {
        String tempplateName = "emails/appointment";
        String SPRING_LOGO_IMAGE = "static/assets/img/favicon-old.png";
        String subject = "Appointment";
        String pngMime = "image/png";
        UserEntity patientUserEntity = userService.findById(data.getPatientId());
        UserEntity doctorUserEntity = userService.findById(data.getDoctorId());

        final MimeMessage mimeMessage = mailSender.createMimeMessage();
        final MimeMessageHelper email = new MimeMessageHelper(mimeMessage, true, "UTF-8");

        // properties
        email.setTo(patientUserEntity.getEmail());
        email.setSubject(subject);
        email.setFrom(new InternetAddress(sender, mailFromName));

        // HTML Body
        final Context ctx = new Context(LocaleContextHolder.getLocale());
        ctx.setVariable("userName", doctorUserEntity.getName());
        ctx.setVariable("shortExplanation", data.getShortExplanation());
        //ctx.setVariable("springLogo", SPRING_LOGO_IMAGE);

        final String htmlContent = templateEngine.process(tempplateName, ctx);

        email.setText(htmlContent, true);

        //ClassPathResource clr = new ClassPathResource(SPRING_LOGO_IMAGE);

        //email.addInline("springLogo", clr, pngMime);

        mailSender.send(mimeMessage);
    }

    public void sendMailToDoctorForAppointment(AppointmentDto data) throws MessagingException, UnsupportedEncodingException {
        String tempplateName = "emails/appointment";
        String SPRING_LOGO_IMAGE = "static/assets/img/favicon-old.png";
        String subject = "Appointment";
        String pngMime = "image/png";
        UserEntity patientUserEntity = userService.findById(data.getPatientId());
        UserEntity doctorUserEntity = userService.findById(data.getDoctorId());

        final MimeMessage mimeMessage = mailSender.createMimeMessage();
        final MimeMessageHelper email = new MimeMessageHelper(mimeMessage, true, "UTF-8");

        // properties
        email.setTo(doctorUserEntity.getEmail());
        email.setSubject(subject);
        email.setFrom(new InternetAddress(sender, mailFromName));

        // HTML Body
        final Context ctx = new Context(LocaleContextHolder.getLocale());
        ctx.setVariable("userName", patientUserEntity.getName());
        ctx.setVariable("shortExplanation", data.getShortExplanation());
        //ctx.setVariable("springLogo", SPRING_LOGO_IMAGE);

        final String htmlContent = templateEngine.process(tempplateName, ctx);

        email.setText(htmlContent, true);

        //ClassPathResource clr = new ClassPathResource(SPRING_LOGO_IMAGE);

        //email.addInline("springLogo", clr, pngMime);

        mailSender.send(mimeMessage);
    }
}
