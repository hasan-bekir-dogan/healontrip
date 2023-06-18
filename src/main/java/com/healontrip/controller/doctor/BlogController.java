package com.healontrip.controller.doctor;

import com.healontrip.dto.*;
import com.healontrip.service.BlogService;
import com.healontrip.service.CategoryService;
import com.healontrip.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Controller("DoctorBlogController")
@RequestMapping("/doctor/blogs")
public class BlogController {
    @Autowired
    private BlogService blogService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;

    @GetMapping("")
    public String indexPage(Model model) {
        List<BlogsDto> blogDtoList = blogService.getAllBlogs(BlogStatus.ACTIVE.toString());
        UserBarDto userBarDto = userService.getUser();

        model.addAttribute("blogs", blogDtoList);
        model.addAttribute("user", userBarDto);

        return "doctor/blog/blogs";
    }

    @GetMapping("/pending")
    public String indexPendingPage(Model model) {
        List<BlogsDto> blogsDtoList = blogService.getAllBlogs(BlogStatus.NOT_ACTIVE.toString());
        UserBarDto userBarDto = userService.getUser();

        model.addAttribute("blogs", blogsDtoList);
        model.addAttribute("user", userBarDto);

        return "doctor/blog/blogs-pending";
    }

    @GetMapping("/edit/{id}")
    public String editPage(@PathVariable Long id, Model model) {
        BlogsDto blogsDto = blogService.getBlogById(id);
        List<CategoryDto> categoryDtoList = categoryService.getAllCategories();
        UserBarDto userBarDto = userService.getUser();

        model.addAttribute("blog", blogsDto);
        model.addAttribute("categories", categoryDtoList);
        model.addAttribute("user", userBarDto);
        return "doctor/blog/edit";
    }

    @PostMapping("/edit/{id}")
    public String updateBlog(@Valid @ModelAttribute("blog") BlogEditDto blogEditDto,
                             BindingResult result,
                             Model model,
                             @PathVariable Long id) throws IOException {
        if(result.hasErrors()) {
            BlogsDto blogsDto = blogService.getBlogById(id);
            List<CategoryDto> categoryDtoList = categoryService.getAllCategories();
            UserBarDto userBarDto = userService.getUser();

            blogEditDto.setBlogImgAlt(blogsDto.getBlogImgAlt());
            blogEditDto.setBlogImgSrc(blogsDto.getBlogImgSrc());

            model.addAttribute("blog", blogEditDto);
            model.addAttribute("categories", categoryDtoList);
            model.addAttribute("user", userBarDto);
            return "doctor/blog/edit";
        }

        BlogDto blogDto = new BlogDto();

        blogDto.setId(id);
        blogDto.setTitle(blogEditDto.getTitle());
        blogDto.setDescription(blogEditDto.getDescription());
        blogDto.setCategory(blogEditDto.getCategory());
        blogDto.setImage(blogEditDto.getImage());

        blogService.updateBlog(blogDto);

        return "redirect:/doctor/blogs/edit/" + id + "?success";
    }

    @GetMapping("/add")
    public String addPage(Model model) {
        BlogDto blogDto = new BlogDto();
        List<CategoryDto> categoryDtoList = categoryService.getAllCategories();
        UserBarDto userBarDto = userService.getUser();

        model.addAttribute("blog", blogDto);
        model.addAttribute("categories", categoryDtoList);
        model.addAttribute("user", userBarDto);

        return "doctor/blog/add";
    }

    @PostMapping("/add")
    public String createBlog(@Valid @ModelAttribute("blog") BlogDto blogDto,
                             BindingResult result,
                             Model model) throws IOException {
        if(result.hasErrors()) {
            List<CategoryDto> categoryDtoList = categoryService.getAllCategories();
            UserBarDto userBarDto = userService.getUser();

            model.addAttribute("blog", blogDto);
            model.addAttribute("categories", categoryDtoList);
            model.addAttribute("user", userBarDto);
            return "doctor/blog/add";
        }

        blogService.saveBlog(blogDto);

        return "redirect:/doctor/blogs/add?success";
    }

    @PostMapping(value = "/change-blog-status", produces = "application/json")
    @ResponseBody
    public ResponseEntity<Object> changeBlogStatus(@RequestBody BlogStatusRequestBodyDto blogStatusRequestBodyDto,
                                                   HttpServletRequest request,
                                                   HttpServletResponse response) throws IOException {
        blogService.changeBlogStatus(blogStatusRequestBodyDto.getId(), blogStatusRequestBodyDto.getStatus());

        BlogStatusResponseBodyDto blogStatusResponseBodyDto = new BlogStatusResponseBodyDto();

        blogStatusResponseBodyDto.setStatus("success");
        blogStatusResponseBodyDto.setContent(blogStatusRequestBodyDto);

        return new ResponseEntity<Object>(blogStatusResponseBodyDto, HttpStatus.OK);
    }
}
