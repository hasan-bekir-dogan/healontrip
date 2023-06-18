package com.healontrip.service;

import com.healontrip.dto.UserBarDto;
import com.healontrip.dto.UserDto;
import com.healontrip.entity.UserEntity;

public interface UserService {
    void saveUser(UserDto userDto);
    UserBarDto getUser();
    UserEntity findByEmail(String email);
    UserEntity findById(Long id);
}
