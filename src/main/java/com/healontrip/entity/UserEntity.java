package com.healontrip.entity;

import javax.persistence.*;

import com.healontrip.dto.Role;
import lombok.*;
import lombok.extern.log4j.Log4j2;

import java.io.Serializable;


@Data
@NoArgsConstructor
@Builder
@Log4j2

@Entity
@Table(name="users")
public class UserEntity extends BaseEntity implements Serializable{
    @Column(name = "profile_img_id")
    private Long profileImgId;

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

    public UserEntity(Long profileImgId, String name, String phoneNumber, String email, String password, Role role){
        this.profileImgId = profileImgId;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
        this.role = role;
    }
}
