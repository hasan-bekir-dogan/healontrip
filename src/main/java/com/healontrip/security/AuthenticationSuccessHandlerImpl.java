package com.healontrip.security;

import java.io.IOException;
import java.security.Principal;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.healontrip.entity.UserEntity;
import com.healontrip.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler{

    @Autowired
    HttpSession session; //autowiring session

    @Autowired
    UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationSuccessHandlerImpl.class);

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        String userEmail = "";
        String userRole = "";

        if(authentication.getPrincipal() instanceof Principal) {
            userEmail = ((Principal)authentication.getPrincipal()).getName();

        }else {
            userEmail = ((User)authentication.getPrincipal()).getUsername();
        }

        logger.info("userEmail: " + userEmail);

        UserEntity user = userService.findByEmail(userEmail);
        userRole = user.getRole().toString();

        session.setAttribute("userEmail", userEmail);
        session.setAttribute("userRole", userRole);

        response.sendRedirect("dashboard");
    }

}