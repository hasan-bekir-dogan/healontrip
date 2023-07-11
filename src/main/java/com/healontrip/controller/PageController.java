package com.healontrip.controller;

import com.healontrip.dto.UserBarDto;
import com.healontrip.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {
    @Autowired
    UserService userService;

    @GetMapping("/")
    public String index(Model model) {
        //UserBarDto userBarDto = new UserBarDto(); //userService.getUser();
        UserBarDto userBarDto = userService.getUser();

        model.addAttribute("user", userBarDto);

        return "index";
    }
}
