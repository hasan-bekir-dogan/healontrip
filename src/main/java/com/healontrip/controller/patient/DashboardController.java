package com.healontrip.controller.patient;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("PatientDashboardController")
@RequestMapping("/patient")
public class DashboardController {
    @GetMapping("/dashboard")
    public String login() {
        return "patient/dashboard";
    }
}
