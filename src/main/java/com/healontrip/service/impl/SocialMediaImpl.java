package com.healontrip.service.impl;

import com.healontrip.dto.*;
import com.healontrip.entity.FileEntity;
import com.healontrip.entity.SocialMediaEntity;
import com.healontrip.entity.UserEntity;
import com.healontrip.exception.ResourceNotFoundException;
import com.healontrip.repository.SocialMediaRepository;
import com.healontrip.repository.UserRepository;
import com.healontrip.service.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class SocialMediaImpl implements SocialMediaService {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;

    @Autowired
    private SocialMediaRepository socialMediaRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void updateSocialMedia(SocialMediaDto socialMediaDto) throws IOException, ParseException {
        // get user
        Long userId = authService.getUserId();
        UserEntity userEntity = userService.findById(userId);

        // check user has defined social media or not
        SocialMediaEntity socialMediaEntity = new SocialMediaEntity();

        if (userEntity.getSocialMediaId() != null)
            socialMediaEntity = findById(userEntity.getSocialMediaId());

        // set data
        socialMediaEntity.setFacebookLink(socialMediaDto.getFacebookLink());
        socialMediaEntity.setTwitterLink(socialMediaDto.getTwitterLink());
        socialMediaEntity.setInstagramLink(socialMediaDto.getInstagramLink());
        socialMediaEntity.setLinkedinLink(socialMediaDto.getLinkedinLink());

        // save the social media
        socialMediaRepository.save(socialMediaEntity);
        userEntity.setSocialMediaId(socialMediaEntity.getId());
        userRepository.save(userEntity);
    }

    @Override
    // overloading
    public SocialMediaDto getSocialMedia() throws IOException, ParseException {
        // get user
        Long userId = authService.getUserId();
        UserEntity userEntity = userService.findById(userId);

        // check user has defined social media or not
        SocialMediaEntity socialMediaEntity = new SocialMediaEntity();

        if (userEntity.getSocialMediaId() != null)
            socialMediaEntity = findById(userEntity.getSocialMediaId());

        SocialMediaDto socialMediaDto = EntityToDto(socialMediaEntity);

        return socialMediaDto;
    }

    @Override
    // overloading
    public SocialMediaDto getSocialMedia(Long userId) throws IOException, ParseException {
        // get user
        UserEntity userEntity = userService.findById(userId);

        // check user has defined social media or not
        SocialMediaEntity socialMediaEntity = new SocialMediaEntity();

        if (userEntity.getSocialMediaId() != null)
            socialMediaEntity = findById(userEntity.getSocialMediaId());

        SocialMediaDto socialMediaDto = EntityToDto(socialMediaEntity);

        return socialMediaDto;
    }

    @Override
    public SocialMediaEntity findById(Long id) {
        return socialMediaRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Social Media not found with id: " + id));
    }

    ////////////////////////////////////////////////////////////////////////////////////////
    // Model Mapper
    // Entity => DTO
    @Override
    public SocialMediaDto EntityToDto(SocialMediaEntity socialMediaEntity) {
        SocialMediaDto socialMediaDto = modelMapper.map(socialMediaEntity, SocialMediaDto.class);
        return socialMediaDto;
    }
}
