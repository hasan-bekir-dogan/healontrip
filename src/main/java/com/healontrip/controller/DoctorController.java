package com.healontrip.controller;

import com.healontrip.dto.*;
import com.healontrip.entity.ExperienceYearEntity;
import com.healontrip.repository.ExperienceYearRepository;
import com.healontrip.service.SpecialistService;
import com.healontrip.service.UserService;
import org.hibernate.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class DoctorController {
    @Autowired
    private UserService userService;

    @Autowired
    private SpecialistService specialistService;

    @Autowired
    private ExperienceYearRepository experienceYearRepository;

    @GetMapping("/doctors/{id}")
    public String detail(Model model,
                         @PathVariable Long id) {
        UserBarDto userBarDto = userService.getUser();
        DoctorDto doctorDto = userService.getDoctor(id);

        model.addAttribute("user", userBarDto);
        model.addAttribute("doctor", doctorDto);
        model.addAttribute("clinicImages", doctorDto.getClinicImageList());
        model.addAttribute("serviceList", doctorDto.getServiceList());
        model.addAttribute("specialistList", doctorDto.getSpecialistList());
        model.addAttribute("educationList", doctorDto.getEducationList());
        model.addAttribute("experienceList", doctorDto.getExperienceList());
        model.addAttribute("awardList", doctorDto.getAwardList());
        model.addAttribute("membershipList", doctorDto.getMembershipList());

        return "doctor-detail";
    }

    @GetMapping("/search")
    public String search(Model model,
                         @RequestParam(value = "gen", required = false) List<String> genderList,
                         @RequestParam(value = "spe", required = false) List<Long> specialityList,
                         @RequestParam(value = "exp", required = false) List<Integer> experienceYearList,
                         @RequestParam(value = "rat", required = false) List<Integer> ratingList) {
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
}
