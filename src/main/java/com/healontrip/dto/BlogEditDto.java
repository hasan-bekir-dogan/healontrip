package com.healontrip.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Log4j2
public class BlogEditDto {
    private Long id;

    @NotEmpty(message = "Title must have value")
    private String title;

    @NotNull(message = "Category must have value")
    private Long category;

    @NotEmpty(message = "Preface must have value")
    @Size(max = 255, message = "Preface must be smaller than 255 character")
    private String preface;

    @NotEmpty(message = "Detail must have value")
    @Size(max = 100000, message = "Detail is too long")
    private String detail;

    private MultipartFile image;
    private Long userId;
    private String blogImgAlt;
    private String blogImgSrc;
}
