package com.healontrip.controller.crm;

import com.healontrip.dto.Role;
import com.healontrip.dto.SocialMediaDto;
import com.healontrip.dto.UserBarDto;
import com.healontrip.service.AuthService;
import com.healontrip.service.SocialMediaService;
import com.healontrip.service.UserService;
import com.healontrip.util.IpConfigUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.text.ParseException;

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
    public String update(@Valid @ModelAttribute("socialMedia") SocialMediaDto socialMediaDto,
                         BindingResult result,
                         Model model,
                         HttpServletRequest request) throws IOException, ParseException {
        // coming soon
        if(!IpConfigUtil.checkAdminIp(request))
            return IpConfigUtil.getRedirectPage();

        // check doctor or not
        if (!authService.getRole().equals(Role.DOCTOR.toString()))
            return "redirect:/profile";

        if(result.hasErrors()) {
            UserBarDto userBarDto = userService.getUser();

            model.addAttribute("user", userBarDto);

            return "crm/doctor/social-media";
        }

        socialMediaService.updateSocialMedia(socialMediaDto);

        return "redirect:/social-media?success";
    }

}
