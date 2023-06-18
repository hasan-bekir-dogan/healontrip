package com.healontrip.bean;

import com.healontrip.security.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
public class UserDetailsServiceBean {
    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }
}
