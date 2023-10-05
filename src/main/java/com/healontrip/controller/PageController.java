package com.healontrip.controller;

import com.healontrip.dto.BlogsDto;
import com.healontrip.dto.DoctorsDto;
import com.healontrip.dto.UserBarDto;
import com.healontrip.service.BlogService;
import com.healontrip.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.text.ParseException;
import java.util.List;

@Controller
public class PageController {
    @Autowired
    private BlogService blogService;

    @Autowired
    UserService userService;

    @GetMapping("/")
    public String index(Model model) throws ParseException {
        List<BlogsDto> blogsDtoList = blogService.getAllBlogs(4, 1);
        UserBarDto userBarDto = userService.getUser();
        List<DoctorsDto> doctorsDtoList = userService.getDoctors();

        model.addAttribute("blogs", blogsDtoList);
        model.addAttribute("user", userBarDto);
        model.addAttribute("doctors", doctorsDtoList);

        return "index";
    }
}
