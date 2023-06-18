package com.healontrip.controller.patient;

import com.healontrip.dto.Role;
import com.healontrip.dto.UserDto;
import com.healontrip.entity.UserEntity;
import com.healontrip.service.UserService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("PatientAuthController")
@RequestMapping("/patient")
public class AuthController {
    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login() {
        return "patient/auth/login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        UserDto userDto = new UserDto();
        model.addAttribute("user", userDto);
        return "patient/auth/register";
    }

    @PostMapping("/register")
    public String registration(@Valid @ModelAttribute("user") UserDto userDto, BindingResult result, Model model) {

        UserEntity existing = userService.findByEmail(userDto.getEmail());

        if(existing != null){
            result.rejectValue("email", null, "There is already an account registered with that email");
        }
        if(result.hasErrors()) {
            model.addAttribute("user", userDto);
            return "patient/auth/register";
        }

        userDto.setRole(Role.PATIENT);
        userService.saveUser(userDto);

        return "redirect:/patient/register?success";
    }
}
