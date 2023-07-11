package com.healontrip.service.impl;

import com.healontrip.entity.UserEntity;
import com.healontrip.exception.AuthenticationException;
import com.healontrip.repository.UserRepository;
import com.healontrip.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Collection;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public String getUserName() {
        String userName = "";

        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();

            if (auth.getPrincipal() instanceof Principal)
                userName = ((Principal) auth.getPrincipal()).getName();
            else
                userName = ((User) auth.getPrincipal()).getUsername();
        } catch (AuthenticationException noAuth) {
            throw new AuthenticationException("No Authentication!");
        }

        return userName;
    }

    @Override
    public String getRole() {
        String role = "";

        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();

            Collection<SimpleGrantedAuthority> authorities = (Collection<SimpleGrantedAuthority>)auth.getAuthorities();

            role = authorities.toArray()[0].toString();
        } catch (AuthenticationException noAuth) {
            throw new AuthenticationException("No Authentication!");
        }

        return role;
    }

    @Override
    public String getSessionId() {
        String sessionId = "";

        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();

            WebAuthenticationDetails details = (WebAuthenticationDetails)auth.getDetails();

            sessionId = details.getSessionId();
        } catch (AuthenticationException noAuth) {
            throw new AuthenticationException("No Authentication!");
        }

        return sessionId;
    }

    @Override
    public String getIpAddress() {
        String ipAddress = "";

        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();

            WebAuthenticationDetails details = (WebAuthenticationDetails)auth.getDetails();

            ipAddress = details.getRemoteAddress();
        } catch (AuthenticationException noAuth) {
            throw new AuthenticationException("No Authentication!");
        }

        return ipAddress;
    }

    @Override
    public Long getUserId() {
        String email = getUserName();

        UserEntity user = userRepository.findByEmail(email);

        return user.getId();
    }

    @Override
    public boolean isAuthenticated() {
        boolean authCheck = false;

        try {

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();

            if (auth == null)
                authCheck = false;
            /*else
                authCheck = auth.isAuthenticated();*/

        } catch (AuthenticationException noAuth) {
            throw new AuthenticationException("No Authentication!");
        }

        return authCheck;
    }
}
