package com.healontrip.controller.doctor;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("DoctorDashboardController")
@RequestMapping("/doctor")
public class DashboardController {
    @GetMapping("/dashboard")
    public String dashboard() {
        return "doctor/dashboard";
    }
}
