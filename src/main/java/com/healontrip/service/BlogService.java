package com.healontrip.service;

import com.healontrip.dto.BlogDto;
import com.healontrip.dto.BlogFilterDto;
import com.healontrip.dto.BlogsDto;
import com.healontrip.dto.FileDto;
import com.healontrip.entity.BlogEntity;
import com.healontrip.entity.FileEntity;

import java.io.IOException;
import java.util.List;

public interface BlogService {
    void saveBlog(BlogDto blogDto) throws IOException;
    List<BlogsDto> getAllBlogs(String status); // Get all blogs according to status using auth
    List<BlogsDto> getAllBlogs(); // Get all active blogs without auth
    List<BlogsDto> getAllBlogs(int limit, int pageNumber); // Get blogs according to pagination
    List<BlogsDto> getAllBlogs(BlogFilterDto blogFilterDto); // Get blogs by filter
    BlogsDto getBlogById(Long id);
    BlogsDto getBlogBySlug(String slug);
    BlogsDto getBlog(BlogEntity blogEntity);
    void updateBlog(BlogDto blogDto) throws IOException;
    BlogEntity findById(Long id);
    BlogEntity findBySlug(String slug);
    BlogEntity findByEditSlugAndId(String slug, Long id);
    void changeBlogStatus(Long id, String status) throws IOException;
    FileDto FileEntitytoFileDto(FileEntity fileEntity);
    FileEntity FileDtoToFileEntity(FileDto fileDto);
}
