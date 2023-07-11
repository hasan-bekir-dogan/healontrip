package com.healontrip.service;

import com.healontrip.dto.BlogDto;
import com.healontrip.dto.BlogsDto;
import com.healontrip.dto.FileDto;
import com.healontrip.entity.FileEntity;

import java.io.IOException;
import java.util.List;

public interface BlogService {
    void saveBlog(BlogDto blogDto) throws IOException;
    List<BlogsDto> getAllBlogs(String status); // Gets all blogs according to status using auth
    List<BlogsDto> getAllBlogs(); // Gets all active blogs without auth
    BlogsDto getBlogById(Long id);
    void updateBlog(BlogDto blogDto) throws IOException;
    void changeBlogStatus(Long id, String status) throws IOException;
    FileDto FileEntitytoFileDto(FileEntity fileEntity);
    FileEntity FileDtoToFileEntity(FileDto fileDto);
}
