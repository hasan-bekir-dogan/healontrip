package com.healontrip.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/doctor/booking")
    public String booking() {
        return "booking";
    }

    @GetMapping("/patient/doctors")
    public String doctors() {
        return "doctors";
    }
}
