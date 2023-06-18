package com.healontrip.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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

    @NotEmpty(message = "Description must have value")
    @Size(max = 4000, message = "Description must be smaller than 4000 character")
    private String description;

    private MultipartFile image;
    private Long userId;
    private String blogImgAlt;
    private String blogImgSrc;
}
