package com.healontrip.entity;

import com.healontrip.dto.Gender;
import jakarta.persistence.*;

import com.healontrip.dto.Role;
import lombok.*;
import lombok.extern.log4j.Log4j2;

import java.io.Serializable;
import java.util.Date;


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

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "date_of_birth")
    private Date dateOfBirth;

    @Column(name = "biography", length = 4000)
    private String biography;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "country")
    private String country;

    @Column(name = "postal_code")
    private String postalCode;

    @Column(name = "address_line")
    private String addressLine;

    @Column(name = "clinic_name")
    private String clinicName;

    @Column(name = "clinic_address")
    private String clinicAddress;

    @Column(name = "clinic_img_ids")
    private String clinicImgIds;

    public UserEntity(Long profileImgId, String name, String phoneNumber, String email, String password, Role role, Gender gender, Date dateOfBirth,
                      String biography, String city, String state, String country, String postalCode, String addressLine, String clinicName,
                      String clinicAddress, String clinicImgIds){
        this.profileImgId = profileImgId;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
        this.role = role;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.biography = biography;
        this.city = city;
        this.state = state;
        this.country = country;
        this.postalCode = postalCode;
        this.addressLine = addressLine;
        this.clinicName = clinicName;
        this.clinicAddress = clinicAddress;
        this.clinicImgIds = clinicImgIds;
    }
}
