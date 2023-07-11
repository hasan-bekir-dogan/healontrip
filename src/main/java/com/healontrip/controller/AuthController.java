package com.healontrip.controller;

import com.healontrip.dto.Role;
import com.healontrip.dto.UserDto;
import com.healontrip.entity.UserEntity;
import com.healontrip.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import jakarta.validation.Valid;

@Controller("AuthController")
public class AuthController {
    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        UserDto userDto = new UserDto();

        model.addAttribute("user", userDto);

        return "auth/register-patient";
    }

    @PostMapping("/register")
    public String registration(@Valid @ModelAttribute("user") UserDto userDto,
                               BindingResult result,
                               Model model) {

        UserEntity existing = userService.findByEmail(userDto.getEmail());

        if(existing != null){
            result.rejectValue("email", null, "There is already an account registered with that email");
        }
        if(result.hasErrors()) {
            model.addAttribute("user", userDto);

            return "auth/register-patient";
        }

        userDto.setRole(Role.PATIENT);
        userService.saveUser(userDto);

        return "redirect:/register-patient?success";
    }

    @GetMapping("/doctor-register")
    public String registerDoctor(Model model) {
        UserDto userDto = new UserDto();

        model.addAttribute("user", userDto);

        return "auth/register-doctor";
    }

    @PostMapping("/doctor-register")
    public String registrationDoctor(@Valid @ModelAttribute("user") UserDto userDto,
                                     BindingResult result,
                                     Model model) {

        UserEntity existing = userService.findByEmail(userDto.getEmail());

        if(existing != null){
            result.rejectValue("email", null, "There is already an account registered with that email");
        }
        if(result.hasErrors()) {
            model.addAttribute("user", userDto);

            return "auth/register-doctor";
        }

        userDto.setRole(Role.DOCTOR);
        userService.saveUser(userDto);

        return "redirect:/register-doctor?success";
    }
}
