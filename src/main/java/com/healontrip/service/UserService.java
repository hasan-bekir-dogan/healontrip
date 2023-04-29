package com.healontrip.service;

import com.healontrip.dto.UserDto;
import com.healontrip.entity.UserEntity;

public interface UserService {
    void saveUser(UserDto userDto);
    UserEntity findByEmail(String email);
}
