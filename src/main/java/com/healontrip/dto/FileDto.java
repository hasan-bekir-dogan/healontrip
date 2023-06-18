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
public class FileDto {
    private Long id;
    private FileCode code;
    private String alt;
    private String source;
    private String name;
    private String extension;
    private MultipartFile file;
}
