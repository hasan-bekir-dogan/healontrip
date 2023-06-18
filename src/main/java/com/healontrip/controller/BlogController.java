package com.healontrip.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BlogController {
    @GetMapping("/blogs")
    public String index() {
        return "blogs";
    }

    @GetMapping("/blogs/detail")
    public String detail() {
        return "blog-detail";
    }
}
