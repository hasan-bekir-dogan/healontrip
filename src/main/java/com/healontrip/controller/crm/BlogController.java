package com.healontrip.controller.crm;

import com.healontrip.dto.*;
import com.healontrip.service.BlogService;
import com.healontrip.service.CategoryService;
import com.healontrip.service.UserService;
import com.healontrip.util.IpConfigUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

@Controller("BlogController")
@RequestMapping("/blogs-crm")
public class BlogController {
    @Autowired
    private BlogService blogService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;

    @GetMapping("")
    public String indexPage(Model model,
                            HttpServletRequest request) throws ParseException {
        // coming soon
        if(!IpConfigUtil.checkAdminIp(request))
            return IpConfigUtil.getRedirectPage();

        List<BlogsDto> blogDtoList = blogService.getAllBlogs(BlogStatus.ACTIVE.toString());
        UserBarDto userBarDto = userService.getUser();

        model.addAttribute("blogs", blogDtoList);
        model.addAttribute("user", userBarDto);

        return "crm/doctor/blog/blogs";
    }

    @GetMapping("/pending")
    public String indexPendingPage(Model model,
                                   HttpServletRequest request) throws ParseException {
        // coming soon
        if(!IpConfigUtil.checkAdminIp(request))
            return IpConfigUtil.getRedirectPage();

        List<BlogsDto> blogsDtoList = blogService.getAllBlogs(BlogStatus.NOT_ACTIVE.toString());
        UserBarDto userBarDto = userService.getUser();

        model.addAttribute("blogs", blogsDtoList);
        model.addAttribute("user", userBarDto);

        return "crm/doctor/blog/blogs-pending";
    }

    @GetMapping("/edit/{slug}")
    public String editPage(@PathVariable String slug,
                           Model model,
                           HttpServletRequest request) throws ParseException {
        // coming soon
        if(!IpConfigUtil.checkAdminIp(request))
            return IpConfigUtil.getRedirectPage();

        BlogsDto blogsDto = blogService.getBlogBySlug(slug);
        List<CategoryDto> categoryDtoList = categoryService.getAllCategories();
        UserBarDto userBarDto = userService.getUser();

        System.out.println(blogsDto);

        model.addAttribute("blog", blogsDto);
        model.addAttribute("categories", categoryDtoList);
        model.addAttribute("user", userBarDto);

        return "crm/doctor/blog/edit";
    }

    @PostMapping("/edit/{slug}")
    public String updateBlog(@Valid @ModelAttribute("blog") BlogEditDto blogEditDto,
                             BindingResult result,
                             Model model,
                             @PathVariable String slug,
                             HttpServletRequest request) throws IOException, ParseException {
        // coming soon
        if(!IpConfigUtil.checkAdminIp(request))
            return IpConfigUtil.getRedirectPage();

        if(result.hasErrors()) {
            BlogsDto blogsDto = blogService.getBlogBySlug(slug);
            List<CategoryDto> categoryDtoList = categoryService.getAllCategories();
            UserBarDto userBarDto = userService.getUser();

            blogEditDto.setBlogImgAlt(blogsDto.getBlogImgAlt());
            blogEditDto.setBlogImgSrc(blogsDto.getBlogImgSrc());

            model.addAttribute("blog", blogEditDto);
            model.addAttribute("categories", categoryDtoList);
            model.addAttribute("user", userBarDto);

            return "crm/doctor/blog/edit";
        }

        BlogDto blogDto = new BlogDto();
        String oldSlug = slug;
        String newSlug = blogEditDto.getSlug();

        blogDto.setOldSlug(oldSlug);
        blogDto.setSlug(newSlug);
        blogDto.setTitle(blogEditDto.getTitle());
        blogDto.setPreface(blogEditDto.getPreface());
        blogDto.setDetail(blogEditDto.getDetail());
        blogDto.setCategory(blogEditDto.getCategory());
        blogDto.setImage(blogEditDto.getImage());

        blogService.updateBlog(blogDto);

        return "redirect:/blogs-crm/edit/" + newSlug + "?success";
    }

    @GetMapping("/add")
    public String addPage(Model model,
                          HttpServletRequest request) throws ParseException {
        // coming soon
        if(!IpConfigUtil.checkAdminIp(request))
            return IpConfigUtil.getRedirectPage();

        BlogDto blogDto = new BlogDto();
        List<CategoryDto> categoryDtoList = categoryService.getAllCategories();
        UserBarDto userBarDto = userService.getUser();

        model.addAttribute("blog", blogDto);
        model.addAttribute("categories", categoryDtoList);
        model.addAttribute("user", userBarDto);

        return "crm/doctor/blog/add";
    }

    @PostMapping("/add")
    public String createBlog(@Valid @ModelAttribute("blog") BlogDto blogDto,
                             BindingResult result,
                             Model model,
                             HttpServletRequest request) throws IOException, ParseException {
        // coming soon
        if(!IpConfigUtil.checkAdminIp(request))
            return IpConfigUtil.getRedirectPage();

        if(result.hasErrors()) {
            List<CategoryDto> categoryDtoList = categoryService.getAllCategories();
            UserBarDto userBarDto = userService.getUser();

            model.addAttribute("blog", blogDto);
            model.addAttribute("categories", categoryDtoList);
            model.addAttribute("user", userBarDto);
            return "crm/doctor/blog/add";
        }

        blogService.saveBlog(blogDto);

        return "redirect:/blogs-crm/add?success";
    }

    @PostMapping(value = "/change-blog-status", produces = "application/json")
    @ResponseBody
    public ResponseEntity<Object> changeBlogStatus(@RequestBody BlogStatusRequestBodyDto blogStatusRequestBodyDto,
                                                   HttpServletRequest request,
                                                   HttpServletResponse response) throws IOException {
        // coming soon
        if(!IpConfigUtil.checkAdminIp(request))
            return new ResponseEntity<>(new GeneralResponseWithDataDto("fail", new Object()), HttpStatus.NOT_FOUND);

        blogService.changeBlogStatus(blogStatusRequestBodyDto.getId(), blogStatusRequestBodyDto.getStatus());

        BlogStatusResponseBodyDto blogStatusResponseBodyDto = new BlogStatusResponseBodyDto();

        blogStatusResponseBodyDto.setStatus("success");
        blogStatusResponseBodyDto.setContent(blogStatusRequestBodyDto);

        return new ResponseEntity<Object>(blogStatusResponseBodyDto, HttpStatus.OK);
    }
}
