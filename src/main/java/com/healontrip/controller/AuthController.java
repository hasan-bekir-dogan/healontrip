package com.healontrip.controller;

import com.healontrip.dto.*;
import com.healontrip.entity.UserEntity;
import com.healontrip.service.*;
import com.healontrip.util.IpConfigUtil;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@Controller("AuthController")
public class AuthController {
    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private ValidationService validationService;

    @Autowired
    private MailService mailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

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

        return "auth/register";
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@ModelAttribute UserDto userDto,
                                           HttpServletRequest request) {
        // coming soon
        if(!IpConfigUtil.checkAdminIp(request))
            return new ResponseEntity<>(new GeneralResponseWithDataDto("fail", new Object()), HttpStatus.NOT_FOUND);

        try {
            userService.saveUser(userDto);

            return new ResponseEntity<>(new GeneralResponseWithoutDataDto("success"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/forgot-password")
    public String forgotPassword(HttpServletRequest request) {
        // coming soon
        if(!IpConfigUtil.checkAdminIp(request))
            return IpConfigUtil.getRedirectPage();

        return "auth/forgot-password";
    }

    @PostMapping("/forgot-password")
    public String forgotPassword(HttpServletRequest request,
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

            UserEntity userEntity = userService.findById(resetPasswordDto.getUserId());
            boolean validateToken = securityService.validateToken(userEntity.getEmail(), resetPasswordDto.getResetToken(), TokenType.RESET_PASSWORD);

            if (!validateToken) {
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

            NewPasswordDto newPasswordDto = new NewPasswordDto();
            newPasswordDto.setNewPassword(resetPasswordDto.getPassword());
            newPasswordDto.setUserId(resetPasswordDto.getUserId());

            userService.updatePassword(newPasswordDto);

            return new ResponseEntity<>(new GeneralResponseWithoutDataDto("success"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/change-password")
    public String changePassword(Model model,
                                 HttpServletRequest request) throws ParseException {
        // coming soon
        if(!IpConfigUtil.checkAdminIp(request))
            return IpConfigUtil.getRedirectPage();

        UserBarDto userBarDto = userService.getUser();

        model.addAttribute("user", userBarDto);
        model.addAttribute("password", new PasswordDto());

        return "crm/" + authService.getRole().toLowerCase() + "/change-password";
    }

    @PostMapping("/change-password")
    public ResponseEntity<Object> changePassword(@ModelAttribute("password") PasswordDto passwordDto,
                                 HttpServletRequest request) {
        // coming soon
        if(!IpConfigUtil.checkAdminIp(request))
            return new ResponseEntity<>(new GeneralResponseWithDataDto("fail", new Object()), HttpStatus.NOT_FOUND);


        try {
            // validation (begin)
            List<GeneralErrorsDto> errors = new ArrayList<>();
            GeneralErrorsDto errorsDto;

            Long userId = authService.getUserId();
            UserEntity userEntity = userService.findById(userId);

            if (passwordDto.getOldPassword() == null || passwordDto.getOldPassword().trim().equals("")) {
                errorsDto = new GeneralErrorsDto();
                errorsDto.setField("oldPassword");
                errorsDto.setDefaultMessage("Old Password must have a value");
                errors.add(errorsDto);
            } else if (!passwordEncoder.matches(passwordDto.getOldPassword(), userEntity.getPassword())) {
                errorsDto = new GeneralErrorsDto();
                errorsDto.setField("oldPassword");
                errorsDto.setDefaultMessage("Old Password is not correct");
                errors.add(errorsDto);
            }

            if (passwordDto.getNewPassword() == null || passwordDto.getNewPassword().trim().equals("")) {
                errorsDto = new GeneralErrorsDto();
                errorsDto.setField("newPassword");
                errorsDto.setDefaultMessage("New Password must have a value");
                errors.add(errorsDto);
            } else if (passwordDto.getNewPassword().equals(passwordDto.getOldPassword())) {
                errorsDto = new GeneralErrorsDto();
                errorsDto.setField("newPassword");
                errorsDto.setDefaultMessage("New Password must be different from old password");
                errors.add(errorsDto);
            } else if (!validationService.validPassword(passwordDto.getNewPassword())){
                errorsDto = new GeneralErrorsDto();
                errorsDto.setField("newPassword");
                errorsDto.setDefaultMessage("At least one lowercase letter(a - z).</br>" +
                        "At least one uppercase letter(A - Z).</br>" +
                        "At least one numeric value(0-9).</br>" +
                        "At least one special symbol(!@#$%^&*=+-_)</br>" +
                        "The total length should be greater than or equal to 8 and less or equal to 16.");
                errors.add(errorsDto);
            }

            if (passwordDto.getConfirmPassword() == null || passwordDto.getConfirmPassword().trim().equals("")) {
                errorsDto = new GeneralErrorsDto();
                errorsDto.setField("confirmPassword");
                errorsDto.setDefaultMessage("Confirm Password must have a value");
                errors.add(errorsDto);
            } else if (!passwordDto.getConfirmPassword().equals(passwordDto.getNewPassword())){
                errorsDto = new GeneralErrorsDto();
                errorsDto.setField("confirmPassword");
                errorsDto.setDefaultMessage("Confirm password is not matched new password!");
                errors.add(errorsDto);
            }

            if (!errors.isEmpty())
                return new ResponseEntity<>(new GeneralResponseWithErrorsDto("fail", errors), HttpStatus.BAD_REQUEST);
            // validation (end)


            NewPasswordDto newPasswordDto = new NewPasswordDto();
            newPasswordDto.setNewPassword(passwordDto.getNewPassword());
            newPasswordDto.setUserId(authService.getUserId());

            userService.updatePassword(newPasswordDto);

            return new ResponseEntity<>(new GeneralResponseWithoutDataDto("success"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/check-account-info")
    public ResponseEntity<Object> checkAccountInfo(@ModelAttribute UserDto userDto,
                                                   HttpServletRequest request) {
        // coming soon
        if(!IpConfigUtil.checkAdminIp(request))
            return new ResponseEntity<>(new GeneralResponseWithDataDto("fail", new Object()), HttpStatus.NOT_FOUND);

        try {
            // validation (begin)
            List<GeneralErrorsDto> errors = new ArrayList<>();
            GeneralErrorsDto errorsDto;

            if (userDto.getFirstName() == null || userDto.getFirstName().trim().equals("")) {
                errorsDto = new GeneralErrorsDto();
                errorsDto.setField("firstName");
                errorsDto.setDefaultMessage("First Name must have a value");
                errors.add(errorsDto);
            }
            if (userDto.getLastName() == null || userDto.getLastName().trim().equals("")) {
                errorsDto = new GeneralErrorsDto();
                errorsDto.setField("lastName");
                errorsDto.setDefaultMessage("Last Name must have a value");
                errors.add(errorsDto);
            }
            if (userDto.getPhoneNumber() == null || userDto.getPhoneNumber().trim().equals("")) {
                errorsDto = new GeneralErrorsDto();
                errorsDto.setField("phoneNumber");
                errorsDto.setDefaultMessage("Phone Number must have a value");
                errors.add(errorsDto);
            }
            if (userDto.getPassword() == null || userDto.getPassword().trim().equals("")) {
                errorsDto = new GeneralErrorsDto();
                errorsDto.setField("password");
                errorsDto.setDefaultMessage("Password must have a value");
                errors.add(errorsDto);
            } else if (!validationService.validPassword(userDto.getPassword())){
                errorsDto = new GeneralErrorsDto();
                errorsDto.setField("password");
                errorsDto.setDefaultMessage("At least one lowercase letter(a - z).</br>" +
                                            "At least one uppercase letter(A - Z).</br>" +
                                            "At least one numeric value(0-9).</br>" +
                                            "At least one special symbol(!@#$%^&*=+-_)</br>" +
                                            "The total length should be greater than or equal to 8 and less or equal to 16.");
                errors.add(errorsDto);
            }

            UserEntity existingUserName = userService.findByUserName(userDto.getUserName());

            if (userDto.getUserName() == null || userDto.getUserName().trim().equals("")) {
                errorsDto = new GeneralErrorsDto();
                errorsDto.setField("userName");
                errorsDto.setDefaultMessage("Username must have a value");
                errors.add(errorsDto);
            } else if (!validationService.validUsername(userDto.getUserName())){
                errorsDto = new GeneralErrorsDto();
                errorsDto.setField("userName");
                errorsDto.setDefaultMessage("Username must be between 5 and 29 characters.</br>" +
                                            "Allowed characters: (a-z, A-Z, 0-9, -)</br>" +
                                            "Turkish characters can't be used.");
                errors.add(errorsDto);
            } else if (existingUserName != null){
                errorsDto = new GeneralErrorsDto();
                errorsDto.setField("userName");
                errorsDto.setDefaultMessage("There is already an account registered with that username");
                errors.add(errorsDto);
            }

            UserEntity existingUserEmail = userService.findByEmail(userDto.getEmail());

            if (userDto.getEmail() == null || userDto.getEmail().trim().equals("")) {
                errorsDto = new GeneralErrorsDto();
                errorsDto.setField("email");
                errorsDto.setDefaultMessage("Email must have a value");
                errors.add(errorsDto);
            } else if (!validationService.validEmail(userDto.getEmail())){
                errorsDto = new GeneralErrorsDto();
                errorsDto.setField("email");
                errorsDto.setDefaultMessage("Email must be valid");
                errors.add(errorsDto);
            } else if (existingUserEmail != null){
                errorsDto = new GeneralErrorsDto();
                errorsDto.setField("email");
                errorsDto.setDefaultMessage("There is already an account registered with that email");
                errors.add(errorsDto);
            }


            if (!errors.isEmpty())
                return new ResponseEntity<>(new GeneralResponseWithErrorsDto("fail", errors), HttpStatus.BAD_REQUEST);
            // validation (end)

            mailService.sendMailToUserForMailVerification(userDto.getEmail());

            return new ResponseEntity<>(new GeneralResponseWithoutDataDto("success"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/email-verification-code")
    public ResponseEntity<Object> emailVerificationCode(@ModelAttribute UserDto userDto,
                                                        HttpServletRequest request) {
        // coming soon
        if(!IpConfigUtil.checkAdminIp(request))
            return new ResponseEntity<>(new GeneralResponseWithDataDto("fail", new Object()), HttpStatus.NOT_FOUND);

        try {
            mailService.sendMailToUserForMailVerification(userDto.getEmail());

            return new ResponseEntity<>(new GeneralResponseWithoutDataDto("success"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/verify-account")
    public ResponseEntity<Object> verifyAccount(@ModelAttribute VerifyAccountDto verifyAccountDto,
                                                HttpServletRequest request) {
        // coming soon
        if(!IpConfigUtil.checkAdminIp(request))
            return new ResponseEntity<>(new GeneralResponseWithDataDto("fail", new Object()), HttpStatus.NOT_FOUND);

        try {
            // validation (begin)
            List<GeneralErrorsDto> errors = new ArrayList<>();
            GeneralErrorsDto errorsDto;

            boolean validateToken = securityService.validateToken(verifyAccountDto.getEmail(), verifyAccountDto.getCode(), TokenType.EMAIL_VERIFICATION);

            if (!validateToken) {
                errorsDto = new GeneralErrorsDto();
                errorsDto.setField("verificationCode");
                errorsDto.setDefaultMessage("Verification code is invalid");
                errors.add(errorsDto);
            }

            if (!errors.isEmpty())
                return new ResponseEntity<>(new GeneralResponseWithErrorsDto("fail", errors), HttpStatus.BAD_REQUEST);
            // validation (end)

            return new ResponseEntity<>(new GeneralResponseWithoutDataDto("success"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
