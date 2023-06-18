package com.healontrip.controller.doctor;

import com.healontrip.dto.UserBarDto;
import com.healontrip.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller("DoctorDashboardController")
@RequestMapping("/doctor")
public class DashboardController {
    @Autowired
    private UserService userService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        UserBarDto userBarDto = userService.getUser();

        model.addAttribute("user", userBarDto);
        return "doctor/dashboard";
    }
}
