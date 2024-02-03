package com.healontrip.controller;

import com.healontrip.dto.BlogsDto;
import com.healontrip.dto.DoctorsDto;
import com.healontrip.dto.SpecialistDto;
import com.healontrip.dto.UserBarDto;
import com.healontrip.service.BlogService;
import com.healontrip.service.SpecialistService;
import com.healontrip.service.UserService;
import com.healontrip.util.IpConfigUtil;
import jakarta.servlet.http.HttpServletRequest;
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
    private UserService userService;

    @Autowired
    private SpecialistService specialistService;

    @GetMapping("/")
    public String index(Model model, HttpServletRequest request) throws ParseException {
        // coming soon
        if(!IpConfigUtil.checkAdminIp(request))
            return IpConfigUtil.getPage();

        List<BlogsDto> blogsDtoList = blogService.getAllBlogs(4, 1);
        UserBarDto userBarDto = userService.getUser();
        List<DoctorsDto> doctorsDtoList = userService.getDoctors();

        // specialist
        List<SpecialistDto> specialistDtoList = specialistService.getSpecialists();

        model.addAttribute("blogs", blogsDtoList);
        model.addAttribute("specialists", specialistDtoList);
        model.addAttribute("user", userBarDto);
        model.addAttribute("doctors", doctorsDtoList);

        return "index";
    }

    @GetMapping("/privacy-policy")
    public String privacyPolicy(Model model, HttpServletRequest request) throws ParseException {
        // coming soon
        if(!IpConfigUtil.checkAdminIp(request))
            return IpConfigUtil.getPage();

        UserBarDto userBarDto = userService.getUser();

        model.addAttribute("user", userBarDto);

        return "privacy-policy";
    }

    @GetMapping("/terms-condition")
    public String termsCondition(Model model, HttpServletRequest request) throws ParseException {
        // coming soon
        if(!IpConfigUtil.checkAdminIp(request))
            return IpConfigUtil.getPage();

        UserBarDto userBarDto = userService.getUser();

        model.addAttribute("user", userBarDto);

        return "terms-condition";
    }
}
