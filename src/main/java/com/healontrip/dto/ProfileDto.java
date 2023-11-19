package com.healontrip.dto;

import com.healontrip.constraint.EmailUniqueConstraint;
import com.healontrip.constraint.UsernameUniqueConstraint;
import com.healontrip.constraint.ServiceNotNullConstraint;
import com.healontrip.constraint.SpecialistNotNullConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Log4j2
public class ProfileDto {
    private Long id;

    /*@NotEmpty(message = "Email must have value")
    @Email
    @EmailUniqueConstraint(message = "There is already an account registered with that email")*/
    private String email;

    @NotEmpty(message = "Username must have value")
    @UsernameUniqueConstraint(message = "There is already an account registered with that username")
    private String userName;

    @NotEmpty(message = "First Name must have value")
    private String firstName;

    @NotEmpty(message = "Last Name must have value")
    private String lastName;

    private String role;

    @NotEmpty(message = "Phone Number must have value")
    private String phone;

    private String profileImgSrc;
    private String profileImgAlt;
    private String dateOfBirth;
    private Gender gender;

    @Size(max = 4000, message = "Biography is too long")
    private String biography;

    private MultipartFile image;

    private String city;
    private String state;
    private String country;
    private String postalCode;
    private String addressLine;

    private String clinicName;
    private String clinicAddress;
    private MultipartFile[] clinicImages;
    private List<Long> deletedClinicImageList;

    private List<ImgDto> clinicImageList;

    @ServiceNotNullConstraint(message = "Services must have value")
    private String service;

    @SpecialistNotNullConstraint(message = "Specialization must have value")
    private String specialist;

    private List<EducationDto> educationList;
    private List<Long> deletedEducationList;

    private List<ExperienceDto> experienceList;
    private List<Long> deletedExperienceList;

    private List<AwardDto> awardList;
    private List<Long> deletedAwardList;

    private List<MembershipDto> membershipList;
    private List<Long> deletedMembershipList;
}
