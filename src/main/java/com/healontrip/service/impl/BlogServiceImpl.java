package com.healontrip.service.impl;

import com.healontrip.dto.*;
import com.healontrip.entity.BlogEntity;
import com.healontrip.entity.FileEntity;
import com.healontrip.entity.UserEntity;
import com.healontrip.exception.ResourceNotFoundException;
import com.healontrip.repository.BlogRepository;
import com.healontrip.service.AuthService;
import com.healontrip.service.BlogService;
import com.healontrip.service.FileService;
import com.healontrip.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class BlogServiceImpl implements BlogService {
    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private AuthService authService;

    @Autowired
    private FileService fileService;

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public void saveBlog(BlogDto blogDto) throws IOException {
        Long userId = authService.getUserId();

        BlogEntity blogEntity = new BlogEntity();
        blogEntity.setTitle(blogDto.getTitle());
        blogEntity.setCategoryId(blogDto.getCategory());
        blogEntity.setPreface(blogDto.getPreface());
        blogEntity.setDetail(blogDto.getDetail());
        blogEntity.setUserId(userId);
        blogEntity.setStatus(BlogStatus.ACTIVE);

        blogRepository.save(blogEntity);

        // Save file (begin)
        String alternateText = "Blog Image";

        FileDto fileDto = new FileDto();
        fileDto.setFile(blogDto.getImage());
        fileDto.setAlt(alternateText);
        fileDto.setSource(FileSource.UPLOADS.getSrc());
        fileDto.setExtension(FileExtension.JPG.getExt());

        Long fileId = fileService.saveFile(fileDto);

        blogEntity.setImgId(fileId);
        // Save file (end)

        blogRepository.save(blogEntity);
    }

    @Override
    // Overloading
    public List<BlogsDto> getAllBlogs(String status) {
        Long userId = authService.getUserId();

        List<BlogEntity> blogEntityList = blogRepository.findBlogByUserId(userId, status);

        return getAllBlogs(blogEntityList);
    }

    @Override
    // Overloading
    public List<BlogsDto> getAllBlogs() {
        List<BlogEntity> blogEntityList = blogRepository.findBlogByStatus(BlogStatus.ACTIVE.toString());

        return getAllBlogs(blogEntityList);
    }

    @Override
    // Overloading
    public List<BlogsDto> getAllBlogs(int limit, int pageNumber) {
        List<BlogEntity> blogEntityList = blogRepository.findBlogByStatusAndLimit(BlogStatus.ACTIVE.toString(), limit, pageNumber - 1);

        return getAllBlogs(blogEntityList);
    }

    // Overloading
    private List<BlogsDto> getAllBlogs(List<BlogEntity> blogEntityList) {
        List<BlogsDto> blogs = new ArrayList<>();
        FileEntity fileEntity;
        UserEntity userEntity;
        String imgSrc;

        for (BlogEntity blogEntity : blogEntityList) {
            BlogsDto blog = new BlogsDto();

            blog.setId(blogEntity.getId());
            blog.setTitle(blogEntity.getTitle());
            blog.setPreface(blogEntity.getPreface());

            // Created time (begin)
            LocalDateTime currentTime = blogEntity.getCreatedDate()
                    .toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy");    // dd MMM yyyy => 30 Jun 2023 || dd.MM.yyyy => 30.06.2023
            String createdAtFormatted = currentTime.format(formatter);

            blog.setCreatedAt(createdAtFormatted);
            // Created time (end)

            // Blog image (begin)
            fileEntity = fileService.findById(blogEntity.getImgId());
            imgSrc = fileService.getFileSrc(fileEntity.getId());

            blog.setBlogImgSrc(imgSrc);
            blog.setBlogImgAlt(fileEntity.getAlt());
            // Blog image (end)

            // User info (begin)
            userEntity = userService.findById(blogEntity.getUserId());

            fileEntity = fileService.findById(userEntity.getProfileImgId());
            imgSrc = fileService.getFileSrc(fileEntity.getId());

            blog.setUserImgSrc(imgSrc);
            blog.setUserName(RolePrefix.DOCTOR.getPre() + userEntity.getFirstName() + userEntity.getLastName());
            blog.setUserImgAlt(fileEntity.getAlt());
            // User info (end)

            blogs.add(blog);
        }

        return blogs;
    }

    @Override
    public BlogsDto getBlogById(Long id) {
        BlogEntity blogEntity = findById(id);

        BlogsDto blogsDto = new BlogsDto();
        FileEntity fileEntity;
        String imgSrc;

        blogsDto.setId(blogEntity.getId());
        blogsDto.setTitle(blogEntity.getTitle());
        blogsDto.setCategory(blogEntity.getCategoryId());
        blogsDto.setPreface(blogEntity.getPreface());
        blogsDto.setDetail(blogEntity.getDetail());

        // Created time (begin)
        LocalDateTime currentTime = blogEntity.getCreatedDate()
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy");    // dd MMM yyyy => 30 Jun 2023 || dd.MM.yyyy => 30.06.2023
        String createdAtFormatted = currentTime.format(formatter);

        blogsDto.setCreatedAt(createdAtFormatted);
        // Created time (end)

        // Blog image (begin)
        fileEntity = fileService.findById(blogEntity.getImgId());
        imgSrc = fileService.getFileSrc(fileEntity.getId());

        blogsDto.setBlogImgSrc(imgSrc);
        blogsDto.setBlogImgAlt(fileEntity.getAlt());
        // Blog image (end)

        // User info (begin)
        UserEntity userEntity = userService.findById(blogEntity.getUserId());

        blogsDto.setUserName(RolePrefix.DOCTOR.getPre() + userEntity.getFirstName() + userEntity.getLastName());
        blogsDto.setBiography(userEntity.getBiography());

        // Profile Photo
        fileEntity = fileService.findById(userEntity.getProfileImgId());

        imgSrc = fileService.getFileSrc(fileEntity.getId());

        blogsDto.setUserImgSrc(imgSrc);
        blogsDto.setUserImgAlt(fileEntity.getAlt());
        // User info (end)

        return blogsDto;
    }

    @Override
    public void updateBlog(BlogDto blogDto) throws IOException {
        BlogEntity blogEntity = findById(blogDto.getId());

        blogEntity.setTitle(blogDto.getTitle());
        blogEntity.setPreface(blogDto.getPreface());
        blogEntity.setDetail(blogDto.getDetail());
        blogEntity.setCategoryId(blogDto.getCategory());

        if (!blogDto.getImage().isEmpty()) {
            // Get old file
            FileEntity fileEntity = fileService.findById(blogEntity.getImgId());

            // Save file (begin)
            String alternateText = "Blog Image";

            FileDto fileDto = new FileDto();

            fileDto.setFile(blogDto.getImage());
            fileDto.setAlt(alternateText);
            fileDto.setSource(FileSource.UPLOADS.getSrc());
            fileDto.setExtension(FileExtension.JPG.getExt());

            Long fileId = fileService.saveFile(fileDto);

            blogEntity.setImgId(fileId);
            // Save file (end)

            // Delete old file (begin)
            fileDto = FileEntitytoFileDto(fileEntity);

            fileService.deleteFile(fileDto);
            // Delete old file (end)
        }

        blogRepository.save(blogEntity);
    }

    @Override
    public BlogEntity findById(Long id) {
        return blogRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Blog not found with id: " + id));
    }

    @Override
    public void changeBlogStatus(Long id, String status) {
        BlogEntity blogEntity = findById(id);

        blogEntity.setStatus(BlogStatus.valueOf(status));

        blogRepository.save(blogEntity);
    }

    ////////////////////////////////////////////////////////////////////////////////////////
    // Model Mapper
    // Entity => DTO
    @Override
    public FileDto FileEntitytoFileDto(FileEntity fileEntity) {
        return modelMapper.map(fileEntity, FileDto.class);
    }

    // DTO => Entity
    @Override
    public FileEntity FileDtoToFileEntity(FileDto fileDto) {
        return modelMapper.map(fileDto, FileEntity.class);
    }
}
