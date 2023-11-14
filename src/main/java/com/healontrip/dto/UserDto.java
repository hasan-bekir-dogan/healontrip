package com.healontrip.dto;

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
public class UserDto {
    private Long id;
    private String userName;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String password;

    //@Enumerated(EnumType.STRING)
    private Role role;
}
