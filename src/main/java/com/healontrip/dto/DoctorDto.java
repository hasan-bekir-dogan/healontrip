package com.healontrip.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Log4j2
public class DoctorDto {
    private Long userId;
    private String userName;
    private String userImgSrc;
    private String userImgAlt;
    private String biography;
    private List<ImgDto> clinicImageList;
    private List<ServiceDto> serviceList;
    private List<SpecializationDto> specialistList;
    private List<EducationDto> educationList;
    private List<ExperienceDto> experienceList;
    private List<AwardDto> awardList;
    private List<MembershipDto> membershipList;
    private String infoShort;
}
