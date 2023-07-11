package com.healontrip.service.impl;

import com.healontrip.dto.*;
import com.healontrip.entity.FileEntity;
import com.healontrip.entity.UserEntity;
import com.healontrip.exception.ResourceNotFoundException;
import com.healontrip.repository.FileRepository;
import com.healontrip.repository.UserRepository;
import com.healontrip.service.AuthService;
import com.healontrip.service.FileService;
import com.healontrip.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private FileService fileService;

    @Autowired
    private AuthService authService;

    @Autowired
    HttpSession session;

    @Override
    public void saveUser(UserDto userDto){
        UserEntity userEntity = new UserEntity();
        userEntity.setName(userDto.getName());
        userEntity.setEmail(userDto.getEmail());
        userEntity.setRole(userDto.getRole());

        userEntity.setPassword(passwordEncoder.encode(userDto.getPassword()));

        // Save Default Profile Img (begin)
        String alternateText = "Profile Image";

        FileEntity fileEntity = fileService.findByCode(FileCode.PROFILE_IMG.getCode());

        if (fileEntity == null) {
            fileEntity = new FileEntity();
            fileEntity.setAlt(alternateText);
            fileEntity.setCode(FileCode.PROFILE_IMG);
            fileEntity.setSource(FileSource.ASSETS_GENERAL_IMG.getSrc());
            fileEntity.setName(FileCode.PROFILE_IMG.getCode());
            fileEntity.setExtension(FileExtension.JPG.getExt());

            fileRepository.save(fileEntity);
        }
        // Save Default Profile Img (end)

        userEntity.setProfileImgId(fileEntity.getId());

        userRepository.save(userEntity);
    }

    @Override
    public UserBarDto getUser() {
        Long userId;
        UserEntity userEntity = new UserEntity();
        String userRole = "";

        if(authService.isAuthenticated()) { // Authenticated
            userId = authService.getUserId();
            userEntity = findById(userId);
            userRole = authService.getRole();
        }
        else if(!authService.isAuthenticated()) { // Not Authenticated
            String userEmail = (String) session.getAttribute("userEmail");

            if (userEmail == null)
                return null;

            userEntity = findByEmail(userEmail);
            userRole = userEntity.getRole().toString();
        }

        UserBarDto userBarDto = new UserBarDto();

        userBarDto.setRole(StringUtils.capitalize(userRole.toLowerCase()));

        String extendedName = ((userRole.equals("DOCTOR")) ? RolePrefix.DOCTOR.getPre() : "") + userEntity.getName();
        userBarDto.setUserName(extendedName);

        String imgSrc = fileService.getFileSrc(userEntity.getProfileImgId());
        userBarDto.setProfileImgSrc(imgSrc);

        FileEntity fileEntity = fileService.findById(userEntity.getProfileImgId());
        userBarDto.setProfileImgAlt(fileEntity.getAlt());


        return userBarDto;
    }

    @Override
    public UserEntity findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public UserEntity findById(Long id) {
        return userRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("User not found with id: " + id));
    }
}
