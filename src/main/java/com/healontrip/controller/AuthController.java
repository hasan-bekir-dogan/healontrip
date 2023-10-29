package com.healontrip.controller;

import com.healontrip.dto.*;
import com.healontrip.entity.UserEntity;
import com.healontrip.service.MailService;
import com.healontrip.service.SecurityService;
import com.healontrip.service.UserService;
import com.healontrip.util.IpConfigUtil;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@Controller("AuthController")
public class AuthController {
    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private MailService mailService;

    @GetMapping("/login")
    public String login(HttpServletRequest request) {
        // coming soon
        if(!IpConfigUtil.checkAdminIp(request))
            return IpConfigUtil.getRedirectPage();

        return "auth/login";
    }

    @GetMapping("/register")
    public String register(Model model,
                           HttpServletRequest request) {
        // coming soon
        if(!IpConfigUtil.checkAdminIp(request))
            return IpConfigUtil.getRedirectPage();

        UserDto userDto = new UserDto();

        model.addAttribute("user", userDto);

        return "auth/register-patient";
    }

    @PostMapping("/register")
    public String registration(@Valid @ModelAttribute("user") UserDto userDto,
                               BindingResult result,
                               Model model,
                               HttpServletRequest request) {
        // coming soon
        if(!IpConfigUtil.checkAdminIp(request))
            return IpConfigUtil.getRedirectPage();

        UserEntity existing = userService.findByEmail(userDto.getEmail());

        if(existing != null){
            result.rejectValue("email", null, "There is already an account registered with that email");
        }
        if(result.hasErrors()) {
            model.addAttribute("user", userDto);

            return "auth/register-patient";
        }

        userDto.setRole(Role.PATIENT);
        userService.saveUser(userDto);

        return "redirect:/register?success";
    }

    @GetMapping("/doctor-register")
    public String registerDoctor(Model model,
                                 HttpServletRequest request) {
        // coming soon
        if(!IpConfigUtil.checkAdminIp(request))
            return IpConfigUtil.getRedirectPage();

        UserDto userDto = new UserDto();

        model.addAttribute("user", userDto);

        return "auth/register-doctor";
    }

    @PostMapping("/doctor-register")
    public String registrationDoctor(@Valid @ModelAttribute("user") UserDto userDto,
                                     BindingResult result,
                                     Model model,
                                     HttpServletRequest request) {
        // coming soon
        if(!IpConfigUtil.checkAdminIp(request))
            return IpConfigUtil.getRedirectPage();

        UserEntity existing = userService.findByEmail(userDto.getEmail());

        if(existing != null){
            result.rejectValue("email", null, "There is already an account registered with that email");
        }
        if(result.hasErrors()) {
            model.addAttribute("user", userDto);

            return "auth/register-doctor";
        }

        userDto.setRole(Role.DOCTOR);
        userService.saveUser(userDto);

        return "redirect:/doctor-register?success";
    }

    @GetMapping("/forgot-password")
    public String forgotPassword(HttpServletRequest request) {
        // coming soon
        if(!IpConfigUtil.checkAdminIp(request))
            return IpConfigUtil.getRedirectPage();

        return "auth/forgot-password";
    }

    @PostMapping("/forgot-password")
    public String resetPassword(HttpServletRequest request,
                                @RequestParam("email") String email) throws MessagingException, UnsupportedEncodingException {
        // coming soon
        if(!IpConfigUtil.checkAdminIp(request))
            return IpConfigUtil.getRedirectPage();

        UserEntity userEntity = userService.findByEmail(email);

        if (userEntity == null) {
            return "redirect:/forgot-password?error";
        }

        mailService.sendMailToUserForResetPassword(request, email);

        return "redirect:/forgot-password?success";
    }

    @GetMapping("/reset-password")
    public String resetPassword(HttpServletRequest request,
                                 Model model,
                                 @RequestParam("uid") Long userId,
                                 @RequestParam("token") String token) {
        // coming soon
        if(!IpConfigUtil.checkAdminIp(request))
            return IpConfigUtil.getRedirectPage();

        model.addAttribute("userId", userId);
        model.addAttribute("token", token);

        return "auth/reset-password";
    }

    @PostMapping("/reset-password")
    public ResponseEntity<Object> resetPassword(@ModelAttribute ResetPasswordDto resetPasswordDto,
                                                HttpServletRequest request) {
        // coming soon
        if(!IpConfigUtil.checkAdminIp(request))
            return new ResponseEntity<>(new GeneralResponseWithDataDto("fail", new Object()), HttpStatus.NOT_FOUND);

        try {
            // validation (begin)
            List<GeneralErrorsDto> errors = new ArrayList<>();
            GeneralErrorsDto errorsDto;


            boolean validatePasswordResetToken = securityService.validatePasswordResetToken(resetPasswordDto.getUserId(), resetPasswordDto.getResetToken());

            if (!validatePasswordResetToken) {
                errorsDto = new GeneralErrorsDto();
                errorsDto.setField("resetToken");
                errorsDto.setDefaultMessage("Activation code is invalid");
                errors.add(errorsDto);

                return new ResponseEntity<>(new GeneralResponseWithErrorsDto("fail", errors), HttpStatus.BAD_REQUEST);
            }
            if (resetPasswordDto.getPassword().isEmpty()) {
                errorsDto = new GeneralErrorsDto();
                errorsDto.setField("password");
                errorsDto.setDefaultMessage("Password must have a value");
                errors.add(errorsDto);
            }
            if (resetPasswordDto.getPasswordRepeat().isEmpty()) {
                errorsDto = new GeneralErrorsDto();
                errorsDto.setField("passwordRepeat");
                errorsDto.setDefaultMessage("Password repeat must have a value");
                errors.add(errorsDto);
            } else if (!resetPasswordDto.getPassword().equals(resetPasswordDto.getPasswordRepeat())) {
                errorsDto = new GeneralErrorsDto();
                errorsDto.setField("passwordRepeat");
                errorsDto.setDefaultMessage("Password repeat must match with password");
                errors.add(errorsDto);
            }

            if (!errors.isEmpty())
                return new ResponseEntity<>(new GeneralResponseWithErrorsDto("fail", errors), HttpStatus.BAD_REQUEST);
            // validation (end)

            userService.updatePassword(resetPasswordDto);

            return new ResponseEntity<>(new GeneralResponseWithoutDataDto("success"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
