package com.healontrip.controller;

import com.healontrip.dto.BlogsDto;
import com.healontrip.dto.UserBarDto;
import com.healontrip.service.BlogService;
import com.healontrip.service.UserService;
import com.healontrip.util.IpConfigUtil;
import jakarta.servlet.http.HttpServletRequest;
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
    public String index(Model model,
                        HttpServletRequest request) throws ParseException {
        // coming soon
        if(!IpConfigUtil.checkAdminIp(request))
            return IpConfigUtil.getRedirectPage();

        List<BlogsDto> blogsDtoList = blogService.getAllBlogs();
        List<BlogsDto> latestBlogsDtoList = blogService.getAllBlogs(5, 1);
        UserBarDto userBarDto = userService.getUser();

        model.addAttribute("blogs", blogsDtoList);
        model.addAttribute("latestBlogs", latestBlogsDtoList);
        model.addAttribute("user", userBarDto);

        return "blogs";
    }

    @GetMapping("/blogs/{slug}")
    public String detail(Model model,
                         @PathVariable String slug,
                         HttpServletRequest request) throws ParseException {
        // coming soon
        if(!IpConfigUtil.checkAdminIp(request))
            return IpConfigUtil.getRedirectPage();

        BlogsDto blogsDto = blogService.getBlogBySlug(slug);
        List<BlogsDto> latestBlogsDtoList = blogService.getAllBlogs(5, 1);
        UserBarDto userBarDto = userService.getUser();

        model.addAttribute("blog", blogsDto);
        model.addAttribute("latestBlogs", latestBlogsDtoList);
        model.addAttribute("user", userBarDto);

        return "blog-detail";
    }
}
