package com.healontrip.service;

import com.healontrip.dto.*;
import com.healontrip.entity.SocialMediaEntity;

import java.io.IOException;
import java.text.ParseException;

public interface SocialMediaService {

    void updateSocialMedia(SocialMediaDto socialMediaDto) throws IOException, ParseException;
    SocialMediaDto getSocialMedia() throws IOException, ParseException;
    SocialMediaDto getSocialMedia(Long userId) throws IOException, ParseException;
    SocialMediaEntity findById(Long id);

    // model mapper
    SocialMediaDto EntityToDto(SocialMediaEntity socialMediaEntity);
}
