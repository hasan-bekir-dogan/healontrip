package com.healontrip.controller.crm;

import com.healontrip.dto.UserBarDto;
import com.healontrip.service.UserService;
import com.healontrip.util.IpConfigUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.text.ParseException;

@Controller
public class SocialMediaController {
    @Autowired
    UserService userService;

    @GetMapping("/social-media")
    public String index(Model model, HttpServletRequest request) throws ParseException {
        // coming soon
        if(!IpConfigUtil.checkAdminIp(request))
            return IpConfigUtil.getPage();

        UserBarDto userBarDto = userService.getUser();

        model.addAttribute("user", userBarDto);

        return "crm/doctor/social-media";
    }
}
