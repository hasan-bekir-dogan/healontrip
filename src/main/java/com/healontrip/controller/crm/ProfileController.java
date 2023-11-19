package com.healontrip.controller.crm;

import com.healontrip.dto.*;
import com.healontrip.service.AuthService;
import com.healontrip.service.UserService;
import com.healontrip.util.IpConfigUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;

@Controller("ProfileController")
public class ProfileController {
    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;

    @GetMapping("/profile")
    public String profile(Model model,
                          HttpServletRequest request) throws ParseException {
        // coming soon
        if(!IpConfigUtil.checkAdminIp(request))
            return IpConfigUtil.getRedirectPage();

        String role = authService.getRole();
        UserBarDto userBarDto = userService.getUser();
        ProfileDto profileDto = userService.getProfile();

        model.addAttribute("user", userBarDto);
        model.addAttribute("profile", profileDto);

        if(role.equals(Role.DOCTOR.toString())) {
            model.addAttribute("profileClinicImages", profileDto.getClinicImageList());
            model.addAttribute("profileEducationList", profileDto.getEducationList());
            model.addAttribute("profileExperienceList", profileDto.getExperienceList());
            model.addAttribute("profileAwardList", profileDto.getAwardList());
            model.addAttribute("profileMembershipList", profileDto.getMembershipList());
        }

        return "crm/" + profileDto.getRole().toLowerCase() + "/profile-settings";
    }

    @PostMapping("/profile")
    public ResponseEntity<Object> updateProfile(@ModelAttribute @Valid ProfileDto profileDto,
                                                HttpServletRequest request){
        // coming soon
        if(!IpConfigUtil.checkAdminIp(request))
            return new ResponseEntity<>(new GeneralResponseWithDataDto("fail", new Object()), HttpStatus.NOT_FOUND);

        try {
            String role = authService.getRole();

            if(role.equals(Role.DOCTOR.toString()))
                userService.updateProfile(profileDto);
            else if(role.equals(Role.PATIENT.toString()))
                userService.updatePatientProfile(profileDto);

            return new ResponseEntity<>(new GeneralResponseWithoutDataDto("success"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
