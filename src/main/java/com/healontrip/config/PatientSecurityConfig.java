package com.healontrip.config;

import com.healontrip.security.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.HeaderWriterLogoutHandler;
import org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter;

import static org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter.Directive.*;

@Configuration
@EnableWebSecurity
@Order(1)
public class PatientSecurityConfig extends WebSecurityConfigurerAdapter {
    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    AuthenticationSuccessHandler authenticationSuccessHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/").permitAll();
        http.authorizeRequests().antMatchers("/patient/register").permitAll();
        http.authorizeRequests().antMatchers("/assets/**").permitAll();

        http.antMatcher("/patient/**")
            .authorizeRequests(
                    (authorize) -> authorize
                            .anyRequest().hasAuthority("PATIENT")
            ).formLogin(
                    form -> form
                            .loginPage("/patient/login")
                            .usernameParameter("email")
                            .loginProcessingUrl("/patient/login")
                            .permitAll()
                            .successHandler(authenticationSuccessHandler)
                            .failureUrl("/patient/login?error=true")
            ).logout(
                    logout -> logout
                            .logoutUrl("/patient/logout")
                            .addLogoutHandler(new HeaderWriterLogoutHandler(
                                    new ClearSiteDataHeaderWriter(CACHE, COOKIES, STORAGE)))
                            .logoutSuccessUrl("/")
                );
    }
}
