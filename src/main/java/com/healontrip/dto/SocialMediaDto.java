package com.healontrip.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Log4j2
public class SocialMediaDto {
    private Long id;
    private String facebookLink;
    private String twitterLink;
    private String instagramLink;
    private String linkedinLink;

}
