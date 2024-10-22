package com.healontrip.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SpringSecurity {
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    public PasswordEncoder passwordEncoder;

    public static final String[] ENDPOINTS_WHITELIST = {
            "/register",
            "/assets/**",
            "/uploads/**",
            "/blogs/**",
            "/doctor/**",
            "/search",
            "/review",
            "/forgot-password",
            "/reset-password",
            "/check-account-info",
            "/email-verification-code",
            "/verify-account",
            "/privacy-policy",
            "/terms-condition",
            "/social-media",
            "/change-password"
    };
    public static final String LOGIN_URL = "/login";
    public static final String LOGOUT_URL = "/logout";
    public static final String LOGOUT_SUCCESS_URL = "/";
    public static final String LOGIN_FAIL_URL = LOGIN_URL + "?error=true";
    public static final String DEFAULT_SUCCESS_URL = "/profile";
    public static final String USERNAME = "email";

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests((authorize) ->
            authorize.requestMatchers(ENDPOINTS_WHITELIST).permitAll()
                    .anyRequest().authenticated()
            ).formLogin(
                    form -> form
                            .loginPage(LOGIN_URL)
                            .usernameParameter(USERNAME)
                            .loginProcessingUrl(LOGIN_URL)
                            .defaultSuccessUrl(DEFAULT_SUCCESS_URL, true)
                            .permitAll()
                            .failureUrl(LOGIN_FAIL_URL)
            ).logout(
                    logout -> logout
                            .logoutRequestMatcher(new AntPathRequestMatcher(LOGOUT_URL))
                            .permitAll()
                            .logoutSuccessUrl(LOGOUT_SUCCESS_URL)
            );

        return http.build();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
    }
}
