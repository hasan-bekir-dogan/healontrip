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

    @GetMapping("/doctor/{username}")
    public String detail(Model model,
                         @PathVariable String username,
                         HttpServletRequest request) throws ParseException {
        // coming soon
        if(!IpConfigUtil.checkAdminIp(request))
            return IpConfigUtil.getRedirectPage();

        System.out.println("test");
        UserBarDto userBarDto = userService.getUser();
        System.out.println("test");
        UserEntity userEntity = userService.findByUserName(username);
        System.out.println("test");
        DoctorDto doctorDto = userService.getDoctor(userEntity.getId());
        System.out.println("test");
        List<ReviewsDto> reviewsDtoList = reviewService.getReviews(userEntity.getId(), 5, 0);
        System.out.println("test");
        ReviewsDto reviewsDto = reviewService.getReview(userEntity.getId());
        System.out.println("test");
        List<CommunicationDto> communicationDtoList = communicationService.getAllCommunications();
        System.out.println("test");

        model.addAttribute("user", userBarDto);
        model.addAttribute("doctor", doctorDto);
        model.addAttribute("clinicImages", doctorDto.getClinicImageList());
        model.addAttribute("serviceList", doctorDto.getServiceList());
        model.addAttribute("specialistList", doctorDto.getSpecialistList());
        model.addAttribute("educationList", doctorDto.getEducationList());
        model.addAttribute("experienceList", doctorDto.getExperienceList());
        model.addAttribute("awardList", doctorDto.getAwardList());
        model.addAttribute("membershipList", doctorDto.getMembershipList());
        model.addAttribute("reviewList", reviewsDtoList);
        model.addAttribute("reviewInfo", reviewsDto);
        model.addAttribute("communications", communicationDtoList);

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

        // specialist list
        List<SpecialistDto> specialistDtoList = specialistService.getSpecialistDistinctList();

        // experience years
        List<ExperienceYearEntity> experienceYearEntityList = experienceYearRepository.findAll();

        // doctor list
        List<DoctorsDto> doctorsDtoList;

        if((genderList != null && !genderList.isEmpty()) || (specialityList != null && !specialityList.isEmpty()) ||
                (experienceYearList != null && !experienceYearList.isEmpty()) || (ratingList != null && !ratingList.isEmpty())) {

            SearchFilterDto searchFilterDto = new SearchFilterDto(genderList, specialityList, experienceYearList, ratingList);
            doctorsDtoList = userService.getDoctors(searchFilterDto);

        }
        else
            doctorsDtoList = userService.getDoctors();

        System.out.println(doctorsDtoList);

        model.addAttribute("user", userBarDto);
        model.addAttribute("genderList", genderDtoList);
        model.addAttribute("specialists", specialistDtoList);
        model.addAttribute("experienceList", experienceYearEntityList);
        model.addAttribute("doctors", doctorsDtoList);
        model.addAttribute("doctorCount", doctorsDtoList.size());

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
