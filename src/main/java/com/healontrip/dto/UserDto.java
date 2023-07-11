package com.healontrip.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
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
public class UserDto {
    private Long id;

    @NotEmpty(message = "Name must have value")
    private String name;

    private String phoneNumber;

    @NotEmpty(message = "Email must have value")
    @Email
    private String email;

    @NotEmpty(message = "Password must have value")
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;
}
