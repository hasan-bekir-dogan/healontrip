package com.healontrip.service.impl;

import com.healontrip.dto.AppointmentDto;
import com.healontrip.dto.TokenType;
import com.healontrip.entity.UserEntity;
import com.healontrip.service.*;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Value;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.io.UnsupportedEncodingException;
import org.apache.commons.lang3.RandomUtils;

import java.util.Random;
import java.util.UUID;

@Service
public class MailServiceImpl implements MailService {

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
        String templateName = "emails/appointment";
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
        ctx.setVariable("userName", doctorUserEntity.getUserName());
        ctx.setVariable("shortExplanation", data.getShortExplanation());
        //ctx.setVariable("springLogo", SPRING_LOGO_IMAGE);

        final String htmlContent = templateEngine.process(templateName, ctx);

        email.setText(htmlContent, true);

        //ClassPathResource clr = new ClassPathResource(SPRING_LOGO_IMAGE);

        //email.addInline("springLogo", clr, pngMime);

        mailSender.send(mimeMessage);
    }

    public void sendMailToDoctorForAppointment(AppointmentDto data) throws MessagingException, UnsupportedEncodingException {
        String templateName = "emails/appointment";
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
        ctx.setVariable("userName", patientUserEntity.getUserName());
        ctx.setVariable("shortExplanation", data.getShortExplanation());
        //ctx.setVariable("springLogo", SPRING_LOGO_IMAGE);

        final String htmlContent = templateEngine.process(templateName, ctx);

        email.setText(htmlContent, true);

        //ClassPathResource clr = new ClassPathResource(SPRING_LOGO_IMAGE);

        //email.addInline("springLogo", clr, pngMime);

        mailSender.send(mimeMessage);
    }

    public void sendMailToUserForResetPassword(HttpServletRequest request, String receiverMail) throws MessagingException, UnsupportedEncodingException {
        String templateName = "emails/reset-password";
        String subject = "Reset Password";

        final MimeMessage mimeMessage = mailSender.createMimeMessage();
        final MimeMessageHelper email = new MimeMessageHelper(mimeMessage, true, "UTF-8");

        // properties
        email.setTo(receiverMail);
        email.setSubject(subject);
        email.setFrom(new InternetAddress(sender, mailFromName));

        // HTML Body
        final Context ctx = new Context(LocaleContextHolder.getLocale());

        String token = UUID.randomUUID().toString();
        userService.createToken(receiverMail, token, TokenType.RESET_PASSWORD);

        String baseUrl = request.getScheme() + "://" + request.getServerName();
        UserEntity userEntity = userService.findByEmail(receiverMail);
        String url = baseUrl + "/reset-password?uid=" + userEntity.getId() + "&token=" + token;

        ctx.setVariable("subject", subject);
        ctx.setVariable("url", url);

        final String htmlContent = templateEngine.process(templateName, ctx);

        email.setText(htmlContent, true);

        mailSender.send(mimeMessage);
    }

    public void sendMailToUserForMailVerification(String receiverMail) throws MessagingException, UnsupportedEncodingException {
        String templateName = "emails/email-verification";
        String subject = "Email Verification";

        final MimeMessage mimeMessage = mailSender.createMimeMessage();
        final MimeMessageHelper email = new MimeMessageHelper(mimeMessage, true, "UTF-8");

        // properties
        email.setTo(receiverMail);
        email.setSubject(subject);
        email.setFrom(new InternetAddress(sender, mailFromName));

        // HTML Body
        final Context ctx = new Context(LocaleContextHolder.getLocale());

        Random generator = new Random();
        int token = 100000 + generator.nextInt(900000);
        userService.createToken(receiverMail, String.valueOf(token), TokenType.EMAIL_VERIFICATION);

        System.out.println(token);
        ctx.setVariable("verificationCode", token);

        final String htmlContent = templateEngine.process(templateName, ctx);

        email.setText(htmlContent, true);

        mailSender.send(mimeMessage);
    }

}
