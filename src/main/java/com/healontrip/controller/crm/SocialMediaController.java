package com.healontrip.controller.crm;

import com.healontrip.dto.*;
import com.healontrip.service.AuthService;
import com.healontrip.service.SocialMediaService;
import com.healontrip.service.UserService;
import com.healontrip.util.IpConfigUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Controller("SocialMediaController")
public class SocialMediaController {
    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;

    @Autowired
    private SocialMediaService socialMediaService;

    @GetMapping("/social-media")
    public String index(Model model, HttpServletRequest request) throws ParseException, IOException {
        // coming soon
        if(!IpConfigUtil.checkAdminIp(request))
            return IpConfigUtil.getPage();

        // check doctor or not
        if (!authService.getRole().equals(Role.DOCTOR.toString()))
            return "redirect:/profile";

        UserBarDto userBarDto = userService.getUser();
        SocialMediaDto socialMediaDto = socialMediaService.getSocialMedia();

        model.addAttribute("user", userBarDto);
        model.addAttribute("socialMedia", socialMediaDto);

        return "crm/doctor/social-media";
    }

    @PostMapping("/social-media")
    public ResponseEntity<Object> update(@ModelAttribute("socialMedia") SocialMediaDto socialMediaDto,
                                         HttpServletRequest request) throws IOException, ParseException {
        // coming soon
        if(!IpConfigUtil.checkAdminIp(request))
            return new ResponseEntity<>(new GeneralResponseWithDataDto("fail", new Object()), HttpStatus.NOT_FOUND);

        // check doctor or not
        if (!authService.getRole().equals(Role.DOCTOR.toString()))
            return new ResponseEntity<>(new GeneralResponseWithDataDto("fail", new Object()), HttpStatus.NOT_FOUND);

        try {
            // validation (begin)
            List<GeneralErrorsDto> errors = new ArrayList<>();
            GeneralErrorsDto errorsDto;

            if (socialMediaDto.getFacebookLink() != null && !socialMediaDto.getFacebookLink().trim().equals("")) {
                try {
                    new URL(socialMediaDto.getFacebookLink()).toURI();
                } catch (MalformedURLException e) {
                    errorsDto = new GeneralErrorsDto();
                    errorsDto.setField("facebookLink");
                    errorsDto.setDefaultMessage("Facebook link has no legal protocol. It must contain string of 'http://' or 'https://'");
                    errors.add(errorsDto);
                } catch (URISyntaxException e) {
                    errorsDto = new GeneralErrorsDto();
                    errorsDto.setField("facebookLink");
                    errorsDto.setDefaultMessage("Facebook link has syntax error.</br>" +
                                                "Allowed characters: (a-z, A-Z, 0-9, -, _, ~, .)</br>" +
                                                "Turkish characters can't be used.");
                    errors.add(errorsDto);
                }
            }
            if (socialMediaDto.getInstagramLink() != null && !socialMediaDto.getInstagramLink().trim().equals("")) {
                try {
                    new URL(socialMediaDto.getInstagramLink()).toURI();
                } catch (MalformedURLException e) {
                    errorsDto = new GeneralErrorsDto();
                    errorsDto.setField("instagramLink");
                    errorsDto.setDefaultMessage("Instagram link has no legal protocol. It must contain string of 'http://' or 'https://'");
                    errors.add(errorsDto);
                } catch (URISyntaxException e) {
                    errorsDto = new GeneralErrorsDto();
                    errorsDto.setField("instagramLink");
                    errorsDto.setDefaultMessage("Instagram link has syntax error.</br>" +
                                                "Allowed characters: (a-z, A-Z, 0-9, -, _, ~, .)</br>" +
                                                "Turkish characters can't be used.");
                    errors.add(errorsDto);
                }
            }
            if (socialMediaDto.getTwitterLink() != null && !socialMediaDto.getTwitterLink().trim().equals("")) {
                try {
                    new URL(socialMediaDto.getTwitterLink()).toURI();
                } catch (MalformedURLException e) {
                    errorsDto = new GeneralErrorsDto();
                    errorsDto.setField("twitterLink");
                    errorsDto.setDefaultMessage("Twitter link has no legal protocol. It must contain string of 'http://' or 'https://'");
                    errors.add(errorsDto);
                } catch (URISyntaxException e) {
                    errorsDto = new GeneralErrorsDto();
                    errorsDto.setField("twitterLink");
                    errorsDto.setDefaultMessage("Twitter link has syntax error.</br>" +
                                                "Allowed characters: (a-z, A-Z, 0-9, -, _, ~, .)</br>" +
                                                "Turkish characters can't be used.");
                    errors.add(errorsDto);
                }
            }
            if (socialMediaDto.getLinkedinLink() != null && !socialMediaDto.getLinkedinLink().trim().equals("")) {
                try {
                    new URL(socialMediaDto.getLinkedinLink()).toURI();
                } catch (MalformedURLException e) {
                    errorsDto = new GeneralErrorsDto();
                    errorsDto.setField("linkedinLink");
                    errorsDto.setDefaultMessage("Linkedin link has no legal protocol. It must contain string of 'http://' or 'https://'");
                    errors.add(errorsDto);
                } catch (URISyntaxException e) {
                    errorsDto = new GeneralErrorsDto();
                    errorsDto.setField("linkedinLink");
                    errorsDto.setDefaultMessage("Linkedin link has syntax error.</br>" +
                                                "Allowed characters: (a-z, A-Z, 0-9, -, _, ~, .)</br>" +
                                                "Turkish characters can't be used.");
                    errors.add(errorsDto);
                }
            }

            if (!errors.isEmpty())
                return new ResponseEntity<>(new GeneralResponseWithErrorsDto("fail", errors), HttpStatus.BAD_REQUEST);
            // validation (end)

            socialMediaService.updateSocialMedia(socialMediaDto);

            return new ResponseEntity<>(new GeneralResponseWithoutDataDto("success"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
