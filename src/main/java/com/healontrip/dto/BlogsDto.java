package com.healontrip.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Log4j2
public class BlogsDto {
    private Long id;
    private String slug;
    private String title;
    private Long category;
    private String categoryName;
    private String preface;
    private String detail;
    private MultipartFile image;
    private String blogImgSrc;
    private String blogImgAlt;
    private String userImgSrc;
    private String userImgAlt;
    private String userName;
    private String extendedUserName;
    private String biography;
    private String createdAt;
}
