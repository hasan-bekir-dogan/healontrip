package com.healontrip.controller.crm;

import com.healontrip.dto.Role;
import com.healontrip.dto.UserBarDto;
import com.healontrip.service.AuthService;
import com.healontrip.service.UserService;
import com.healontrip.util.IpConfigUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.text.ParseException;


@Controller("DashboardController")
public class DashboardController {
    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;

    @GetMapping("/dashboard")
    public String dashboard(Model model,
                            HttpServletRequest request) throws ParseException {
        // coming soon
        if(!IpConfigUtil.checkAdminIp(request))
            return IpConfigUtil.getRedirectPage();

        // check doctor or not
        if (!authService.getRole().equals(Role.DOCTOR.toString()))
            return "redirect:/profile";

        UserBarDto userBarDto = userService.getUser();

        model.addAttribute("user", userBarDto);

        return "crm/" + userBarDto.getRole().toLowerCase() + "/dashboard";
    }
}
