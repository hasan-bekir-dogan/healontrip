package com.healontrip.service;

import com.healontrip.dto.*;
import com.healontrip.entity.FileEntity;
import com.healontrip.entity.UserEntity;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public interface UserService {
    void saveUser(UserDto userDto);
    void updateProfile(ProfileDto profileDto) throws IOException, ParseException;
    void updatePatientProfile(ProfileDto profileDto) throws IOException, ParseException;
    UserBarDto getUser() throws ParseException;
    ProfileDto getProfile() throws ParseException;
    UserEntity findByEmail(String email);
    UserEntity findById(Long id);
    List<DoctorsDto> getDoctors();
    List<DoctorsDto> getDoctors(SearchFilterDto searchFilterDto);
    DoctorDto getDoctor(Long id);
    CommunicationInfoDto getCommunicationInfo();

    // model mapper
    UserBarDto UserEntityToUserBarDto(UserEntity userEntity);
    ProfileDto UserEntityToProfileDto(UserEntity userEntity);
    FileDto FileEntityToFileDto(FileEntity fileEntity);
    FileDto FileEntitytoFileDto(FileEntity fileEntity);
    UserEntity UserBarDtoToUserEntity(UserBarDto userBarDto);
    UserEntity ProfileDtoToUserEntity(ProfileDto profileDto);
}
