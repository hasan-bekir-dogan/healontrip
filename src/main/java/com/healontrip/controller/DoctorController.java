package com.healontrip.controller;

import com.healontrip.dto.*;
import com.healontrip.entity.CommunicationEntity;
import com.healontrip.entity.ExperienceYearEntity;
import com.healontrip.entity.UserEntity;
import com.healontrip.repository.ExperienceYearRepository;
import com.healontrip.service.*;
import com.healontrip.util.IpConfigUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class DoctorController {
    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    @Autowired
    private SpecialistService specialistService;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private CommunicationService communicationService;

    @Autowired
    private ExperienceYearRepository experienceYearRepository;

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private SocialMediaService socialMediaService;

    @GetMapping("/doctor/{username}")
    public String detail(Model model,
                         @PathVariable String username,
                         HttpServletRequest request) throws ParseException, IOException {
        // coming soon
        if(!IpConfigUtil.checkAdminIp(request))
            return IpConfigUtil.getRedirectPage();

        UserBarDto userBarDto = userService.getUser();
        UserEntity userEntity = userService.findByUserName(username);
        DoctorDto doctorDto = userService.getDoctor(userEntity.getId());
        List<ReviewsDto> reviewsDtoList = reviewService.getReviews(userEntity.getId(), 5, 0);
        ReviewsDto reviewsDto = reviewService.getReview(userEntity.getId());
        List<CommunicationDto> communicationDtoList = communicationService.getAllCommunications();
        SocialMediaDto socialMediaDto = socialMediaService.getSocialMedia(userEntity.getId());
        List<ServiceDto> serviceDtoList = new ArrayList<>();

        int counter = 0;
        char allServiceLinkYN;
        for (ServiceDto serviceDto: doctorDto.getServiceList()) {
            if (counter >= 2)
                break;

            serviceDtoList.add(serviceDto);
            counter ++;
        }
        allServiceLinkYN = (doctorDto.getServiceList().size() > 2) ? 'Y' : 'N';

        model.addAttribute("user", userBarDto);
        model.addAttribute("doctor", doctorDto);
        model.addAttribute("clinicImages", doctorDto.getClinicImageList());
        model.addAttribute("serviceList", doctorDto.getServiceList());
        model.addAttribute("serviceLimitedList", serviceDtoList);
        model.addAttribute("allServiceLinkYN", allServiceLinkYN);
        model.addAttribute("specialistId", doctorDto.getSpecialistId());
        model.addAttribute("educationList", doctorDto.getEducationList());
        model.addAttribute("experienceList", doctorDto.getExperienceList());
        model.addAttribute("awardList", doctorDto.getAwardList());
        model.addAttribute("membershipList", doctorDto.getMembershipList());
        model.addAttribute("reviewList", reviewsDtoList);
        model.addAttribute("reviewInfo", reviewsDto);
        model.addAttribute("communications", communicationDtoList);
        model.addAttribute("socialMedia", socialMediaDto);

        return "doctor-detail";
    }

    @GetMapping("/search")
    public String search(Model model,
                         @RequestParam(value = "gen", required = false) List<String> genderList,
                         @RequestParam(value = "spe", required = false) List<Long> specialityList,
                         @RequestParam(value = "exp", required = false) List<Integer> experienceYearList,
                         @RequestParam(value = "rat", required = false) List<Integer> ratingList,
                         HttpServletRequest request) throws ParseException {
        // coming soon
        if(!IpConfigUtil.checkAdminIp(request))
            return IpConfigUtil.getRedirectPage();

        UserBarDto userBarDto = userService.getUser();

        // gender list
        List<GenderDto> genderDtoList = new ArrayList<>();
        genderDtoList.add(new GenderDto(GenderCode.MALE.getId(), GenderCode.MALE.getName()));
        genderDtoList.add(new GenderDto(GenderCode.FEMALE.getId(), GenderCode.FEMALE.getName()));

        // specialist
        List<SpecialistDto> specialistDtoList = specialistService.getSpecialists();

        // experience years
        List<ExperienceYearEntity> experienceYearEntityList = experienceYearRepository.findAll();

        // doctor list
        List<DoctorsDto> doctorsDtoList;

        SearchFilterDto searchFilter = new SearchFilterDto(genderList, specialityList, experienceYearList, ratingList);

        if((genderList != null && !genderList.isEmpty()) || (specialityList != null && !specialityList.isEmpty()) ||
                (experienceYearList != null && !experienceYearList.isEmpty()) || (ratingList != null && !ratingList.isEmpty()))
            doctorsDtoList = userService.getDoctors(searchFilter);
        else
            doctorsDtoList = userService.getDoctors();

        // filter values (begin)
        FilterDto filterUnselectedValues = new FilterDto();
        FilterDto filterSelectedValues = new FilterDto();
        List<GenderDto> filteredGenderList = new ArrayList<>();
        List<SpecialistDto> filteredSpecialists = new ArrayList<>();
        List<ExperienceYearEntity> filteredExperienceList = new ArrayList<>();

        if (genderList != null && !genderList.isEmpty()) {
            for (GenderDto genderDto : genderDtoList) {
                if (genderList.contains(genderDto.getId()))
                    filteredGenderList.add(genderDto);
            }
        }

        if (specialityList != null && !specialityList.isEmpty()) {
            for (SpecialistDto specialistDto: specialistDtoList) {
                if (specialityList.contains(specialistDto.getId()))
                    filteredSpecialists.add(specialistDto);
            }
        }

        if (experienceYearList != null && !experienceYearList.isEmpty()) {
            for (ExperienceYearEntity experienceYearEntity: experienceYearEntityList) {
                Integer i = experienceYearEntity.getId() != null ? experienceYearEntity.getId().intValue() : null;

                if (experienceYearList.contains(i))
                    filteredExperienceList.add(experienceYearEntity);
            }
        }

        filterSelectedValues.setGenderList(filteredGenderList);
        filterSelectedValues.setSpecialityList(filteredSpecialists);
        filterSelectedValues.setExperienceYearList(filteredExperienceList);

        genderDtoList.removeAll(filteredGenderList);
        specialistDtoList.removeAll(filteredSpecialists);
        experienceYearEntityList.removeAll(filteredExperienceList);

        filterUnselectedValues.setGenderList(genderDtoList);
        filterUnselectedValues.setSpecialityList(specialistDtoList);
        filterUnselectedValues.setExperienceYearList(experienceYearEntityList);
        // filter values (end)

        model.addAttribute("user", userBarDto);
        model.addAttribute("genderList", genderDtoList);
        model.addAttribute("specialists", specialistDtoList);
        model.addAttribute("experienceList", experienceYearEntityList);
        model.addAttribute("doctors", doctorsDtoList);
        model.addAttribute("doctorCount", doctorsDtoList.size());
        model.addAttribute("filterSelectedValues", filterSelectedValues);
        model.addAttribute("filterUnselectedValues", filterUnselectedValues);

        return "doctor-search";
    }

    @PostMapping("/review")
    public ResponseEntity<Object> review(@ModelAttribute @Valid ReviewDto reviewDto,
                                         HttpServletRequest request){
        // coming soon
        if(!IpConfigUtil.checkAdminIp(request))
            return new ResponseEntity<>(new GeneralResponseWithDataDto("fail", new Object()), HttpStatus.NOT_FOUND);

        try {
            List<ReviewsDto> reviewsDtoList = reviewService.addReview(reviewDto);
            ReviewsDto reviewsDto = reviewService.getReview(reviewDto.getDoctorId());

            return new ResponseEntity<>(new ReviewsResponseDto("success", reviewsDtoList, reviewsDto), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/doctor/{username}/reviews")
    public String index(Model model,
                        @PathVariable String username,
                        @RequestParam(value = "page", required = false) Integer pageNumber,
                        HttpServletRequest request) throws ParseException {
        // coming soon
        if(!IpConfigUtil.checkAdminIp(request))
            return IpConfigUtil.getRedirectPage();

        if (pageNumber == null)
            pageNumber = 1;
        else if (!(pageNumber > 0))
            pageNumber = 1;

        int LIMITPRODUCT = 40;
        UserBarDto userBarDto = userService.getUser();
        UserEntity userEntity = userService.findByUserName(username);
        DoctorDto doctorDto = userService.getDoctor(userEntity.getId());
        ReviewsDto reviewsDto = reviewService.getReview(userEntity.getId());

        List<ReviewsDto> reviewsDtoList = reviewService.getReviews(userEntity.getId(), LIMITPRODUCT, ((pageNumber - 1) * LIMITPRODUCT) + 1);

        // page count
        Integer reviewCount = reviewsDto.getRatingCount();
        int pageCount = (int)(Math.ceil(Double.valueOf(reviewCount) / Double.valueOf(LIMITPRODUCT)));

        model.addAttribute("user", userBarDto);
        model.addAttribute("doctor", doctorDto);
        model.addAttribute("reviewList", reviewsDtoList);
        model.addAttribute("reviewInfo", reviewsDto);
        model.addAttribute("pageCount", pageCount);
        model.addAttribute("currentPage", pageNumber);

        return "doctor-reviews";
    }

    @PostMapping("/doctor/book-appointment")
    public ResponseEntity<Object> bookAppointment(@ModelAttribute @Valid AppointmentDto appointmentDto,
                                                  HttpServletRequest request){
        // coming soon
        if(!IpConfigUtil.checkAdminIp(request))
            return new ResponseEntity<>(new GeneralResponseWithDataDto("fail", new Object()), HttpStatus.NOT_FOUND);

        if (!authService.isAuthenticated())
            return new ResponseEntity<>(new GeneralResponseWithoutDataDto("login"), HttpStatus.OK);
        else if (!authService.getRole().equals(Role.PATIENT.toString()))
            return new ResponseEntity<>(new GeneralResponseWithoutDataDto("login"), HttpStatus.OK);

        try {
            appointmentService.createAppointment(appointmentDto);

            return new ResponseEntity<>(new GeneralResponseWithoutDataDto("success"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/doctor/communication-info/{id}")
    public ResponseEntity<Object> getCommunicationInfo(@PathVariable Long id,
                                                       HttpServletRequest request){
        // coming soon
        if(!IpConfigUtil.checkAdminIp(request))
            return new ResponseEntity<>(new GeneralResponseWithDataDto("fail", new Object()), HttpStatus.NOT_FOUND);

        try {
            CommunicationInfoDto communicationInfoDto = new CommunicationInfoDto();

            if (!authService.isAuthenticated())
                return new ResponseEntity<>(new GeneralResponseWithDataDto("success", communicationInfoDto), HttpStatus.OK);
            else if (!authService.getRole().equals(Role.PATIENT.toString()))
                return new ResponseEntity<>(new GeneralResponseWithDataDto("success", communicationInfoDto), HttpStatus.OK);

            communicationInfoDto = userService.getCommunicationInfo();

            CommunicationEntity communicationEntity = communicationService.findById(id);
            communicationInfoDto.setName(communicationEntity.getName().toUpperCase());
            communicationInfoDto.setId(id);

            return new ResponseEntity<>(new GeneralResponseWithDataDto("success", communicationInfoDto), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
