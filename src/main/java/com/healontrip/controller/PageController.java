package com.healontrip.controller;

import com.healontrip.dto.BlogsDto;
import com.healontrip.dto.DoctorsDto;
import com.healontrip.dto.EmailDetailsDto;
import com.healontrip.dto.UserBarDto;
import com.healontrip.service.BlogService;
import com.healontrip.service.EmailService;
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
    private EmailService emailService;

    @GetMapping("/")
    public String index(Model model, HttpServletRequest request) throws ParseException {
        // coming soon
        if(!IpConfigUtil.checkAdminIp(request))
            return IpConfigUtil.getPage();

        List<BlogsDto> blogsDtoList = blogService.getAllBlogs(4, 1);
        UserBarDto userBarDto = userService.getUser();
        List<DoctorsDto> doctorsDtoList = userService.getDoctors();

        model.addAttribute("blogs", blogsDtoList);
        model.addAttribute("user", userBarDto);
        model.addAttribute("doctors", doctorsDtoList);

        /*EmailDetailsDto details = new EmailDetailsDto();
        details.setRecipient("hasanbekir1997@hotmail.com");
        details.setMsgBody("Hey! \n\n Gardaşım Club");
        details.setSubject("Simple Email Message");

        String status = emailService.sendSimpleMail(details);
        System.out.println(status);*/

        return "index";
    }
}
