package com.healontrip.controller;

import com.healontrip.dto.BlogsDto;
import com.healontrip.dto.UserBarDto;
import com.healontrip.service.BlogService;
import com.healontrip.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.text.ParseException;
import java.util.List;

@Controller
public class BlogController {
    @Autowired
    private BlogService blogService;

    @Autowired
    private UserService userService;

    @GetMapping("/blogs")
    public String index(Model model) throws ParseException {
        List<BlogsDto> blogsDtoList = blogService.getAllBlogs();
        UserBarDto userBarDto = userService.getUser();

        model.addAttribute("blogs", blogsDtoList);
        model.addAttribute("user", userBarDto);

        return "blogs";
    }

    @GetMapping("/blogs/{id}")
    public String detail(Model model,
                         @PathVariable Long id) throws ParseException {
        BlogsDto blogsDto = blogService.getBlogById(id);
        UserBarDto userBarDto = userService.getUser();

        model.addAttribute("blog", blogsDto);
        model.addAttribute("user", userBarDto);

        return "blog-detail";
    }
}
