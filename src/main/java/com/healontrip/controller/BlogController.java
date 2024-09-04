package com.healontrip.controller;

import com.healontrip.dto.BlogFilterDto;
import com.healontrip.dto.BlogsDto;
import com.healontrip.dto.CategoryByCountDto;
import com.healontrip.dto.UserBarDto;
import com.healontrip.service.BlogService;
import com.healontrip.service.CategoryService;
import com.healontrip.service.UserService;
import com.healontrip.util.IpConfigUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class BlogController {
    @Autowired
    private BlogService blogService;

    @Autowired
    private UserService userService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/blogs")
    public String index(Model model,
                        @RequestParam(value = "catid", required = false) Long categoryId,
                        HttpServletRequest request) throws ParseException {
        // coming soon
        if(!IpConfigUtil.checkAdminIp(request))
            return IpConfigUtil.getRedirectPage();

        List<BlogsDto> blogsDtoList;

        if (categoryId != null) { // filtered
            BlogFilterDto blogFilterDto = new BlogFilterDto(categoryId);

            blogsDtoList = blogService.getAllBlogs(blogFilterDto);
        }
        else // all
            blogsDtoList = blogService.getAllBlogs();

        List<CategoryByCountDto> categoryByCountDtoList = categoryService.getBlogCategories();
        List<BlogsDto> latestBlogsDtoList = blogService.getAllBlogs(5, 1);
        UserBarDto userBarDto = userService.getUser();

        model.addAttribute("blogs", blogsDtoList);
        model.addAttribute("categories", categoryByCountDtoList);
        model.addAttribute("selectedCategoryId", categoryId);
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
        List<CategoryByCountDto> categoryByCountDtoList = categoryService.getBlogCategories();
        List<BlogsDto> latestBlogsDtoList = blogService.getAllBlogs(5, 1);
        UserBarDto userBarDto = userService.getUser();

        model.addAttribute("blog", blogsDto);
        model.addAttribute("categories", categoryByCountDtoList);
        model.addAttribute("latestBlogs", latestBlogsDtoList);
        model.addAttribute("user", userBarDto);

        return "blog-detail";
    }
}
