package com.healontrip.entity;

import javax.persistence.*;

import com.healontrip.dto.Role;
import lombok.*;
import lombok.extern.log4j.Log4j2;

import java.io.Serializable;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Log4j2

@Entity
@Table(name="users")
public class UserEntity extends BaseEntity implements Serializable{
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;
}
