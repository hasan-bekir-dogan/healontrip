package com.healontrip.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.HeaderWriterLogoutHandler;
import org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter;

import static org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter.Directive.*;

@Configuration
@EnableWebSecurity
@Order(2)
public class DoctorSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    AuthenticationSuccessHandler authenticationSuccessHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/doctor/register").permitAll();

        http.antMatcher("/doctor/**")
            .authorizeRequests(
                    (authorize) ->authorize
                            .antMatchers("/").permitAll()
                            .anyRequest().hasAuthority("DOCTOR")
            ).formLogin(
                    form -> form
                            .loginPage("/doctor/login")
                            .usernameParameter("email")
                            .loginProcessingUrl("/doctor/login")
                            .permitAll()
                            .successHandler(authenticationSuccessHandler)
                            .failureUrl("/doctor/login?error=true")
            ).logout(
                    logout -> logout
                            .logoutUrl("/doctor/logout")
                            .addLogoutHandler(new HeaderWriterLogoutHandler(
                                    new ClearSiteDataHeaderWriter(CACHE, COOKIES, STORAGE)))
                            .logoutSuccessUrl("/")
                );
    }
}
