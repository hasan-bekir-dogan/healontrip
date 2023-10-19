package com.healontrip.controller;

import com.healontrip.dto.*;
import com.healontrip.entity.ExperienceYearEntity;
import com.healontrip.repository.ExperienceYearRepository;
import com.healontrip.service.ReviewService;
import com.healontrip.service.SpecialistService;
import com.healontrip.service.UserService;
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
    private UserService userService;

    @Autowired
    private SpecialistService specialistService;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private ExperienceYearRepository experienceYearRepository;

    @GetMapping("/doctors/{id}")
    public String detail(Model model,
                         @PathVariable Long id,
                         HttpServletRequest request) throws ParseException {
        // coming soon
        if(!IpConfigUtil.checkAdminIp(request))
            return IpConfigUtil.getRedirectPage();

        UserBarDto userBarDto = userService.getUser();
        DoctorDto doctorDto = userService.getDoctor(id);
        List<ReviewsDto> reviewsDtoList = reviewService.getReviews(id, 5, 0);
        ReviewsDto reviewsDto = reviewService.getReview(id);

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


        model.addAttribute("user", userBarDto);
        model.addAttribute("genderList", genderDtoList);
        model.addAttribute("specialists", specialistDtoList);
        model.addAttribute("experienceList", experienceYearEntityList);
        model.addAttribute("doctors", doctorsDtoList);

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

    @GetMapping("/doctors/{id}/reviews")
    public String index(Model model,
                        @PathVariable Long id,
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
        DoctorDto doctorDto = userService.getDoctor(id);
        ReviewsDto reviewsDto = reviewService.getReview(id);

        List<ReviewsDto> reviewsDtoList = reviewService.getReviews(id, LIMITPRODUCT, ((pageNumber - 1) * LIMITPRODUCT) + 1);

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

}
