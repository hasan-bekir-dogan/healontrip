package com.healontrip.controller.crm;

import com.healontrip.dto.*;
import com.healontrip.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.text.ParseException;

@Controller("ProfileController")
public class ProfileController {
    @Autowired
    private UserService userService;

    @GetMapping("/profile")
    public String profile(Model model) throws ParseException {
        UserBarDto userBarDto = userService.getUser();
        ProfileDto profileDto = userService.getProfile();

        model.addAttribute("user", userBarDto);
        model.addAttribute("profile", profileDto);
        model.addAttribute("profileClinicImages", profileDto.getClinicImageList());
        model.addAttribute("profileEducationList", profileDto.getEducationList());
        model.addAttribute("profileExperienceList", profileDto.getExperienceList());
        model.addAttribute("profileAwardList", profileDto.getAwardList());
        model.addAttribute("profileMembershipList", profileDto.getMembershipList());

        return "crm/" + profileDto.getRole().toLowerCase() + "/profile-settings";
    }

    @PostMapping("/profile")
    public ResponseEntity<Object> updateProfile(@ModelAttribute @Valid ProfileDto profileDto){
        try {
            userService.updateProfile(profileDto);

            return new ResponseEntity<>(new ProfileResponseDto("success"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
