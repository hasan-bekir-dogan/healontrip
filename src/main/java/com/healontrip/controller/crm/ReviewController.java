package com.healontrip.controller.crm;

import com.healontrip.dto.*;
import com.healontrip.entity.UserEntity;
import com.healontrip.service.*;
import com.healontrip.util.IpConfigUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

@Controller("ReviewController")
@RequestMapping("/reviews")
public class ReviewController {
    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    @Autowired
    private ReviewService reviewService;

    @GetMapping("")
    public String index(Model model,
                        HttpServletRequest request) throws ParseException {
        // coming soon
        if(!IpConfigUtil.checkAdminIp(request))
            return IpConfigUtil.getRedirectPage();

        String role = authService.getRole();
        Long userId = authService.getUserId();

        UserBarDto userBarDto = userService.getUser();
        ReviewsDto reviewsDto = reviewService.getReview(userId);

        List<ReviewsDto> reviewsDtoList = reviewService.getReviews(userId);

        model.addAttribute("user", userBarDto);
        model.addAttribute("reviewList", reviewsDtoList);
        model.addAttribute("reviewInfo", reviewsDto);

        return "crm/" + role.toLowerCase() + "/reviews";
    }
}
