package com.healontrip.controller.crm;

import com.healontrip.dto.UserBarDto;
import com.healontrip.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller("DashboardController")
public class DashboardController {
    @Autowired
    private UserService userService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        UserBarDto userBarDto = userService.getUser();

        model.addAttribute("user", userBarDto);

        return "crm/doctor/dashboard";
    }
}
