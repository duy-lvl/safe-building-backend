package com.safepass.safebuilding.config;

import com.safepass.safebuilding.common.filter.CustomAuthenticationFilter;
import com.safepass.safebuilding.common.filter.CustomAuthorizationFilter;
import com.safepass.safebuilding.common.security.jwt.userprincipal.CustomUserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DefaultAuthenticationEventPublisher;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfiguration {
    @Autowired
    CustomUserDetailService userDetailService;
    String[] apiList = {
            "/api/v1/customer",
            "/api/v1/admin"
    };
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DefaultAuthenticationEventPublisher authenticationEventPublisher() {
        return new DefaultAuthenticationEventPublisher();
    }

    @Bean
    public AuthenticationManager authManager(
            HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .getSharedObject(AuthenticationManagerBuilder.class)
                .authenticationEventPublisher(authenticationEventPublisher())
                .userDetailsService(userDetailService)
                .passwordEncoder(passwordEncoder())
                .and()
                .build();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        CustomAuthenticationFilter filter = new CustomAuthenticationFilter(authManager(httpSecurity));
//        filter.setFilterProcessesUrl("/api/v1/admin/web/login");
        httpSecurity
                .cors()
                .and()
                .csrf()
                .disable()
                ;
        httpSecurity.sessionManagement().sessionCreationPolicy(STATELESS);
        httpSecurity.addFilter(filter);
        httpSecurity
                .authorizeHttpRequests()
                .antMatchers("/api/v1/admin/web/login").permitAll();
        httpSecurity
                .authorizeHttpRequests()
                .antMatchers(apiList).authenticated();
        httpSecurity
                .authorizeHttpRequests()
                .antMatchers("/api/v1/admin").hasRole("ROLE_ADMIN");
        httpSecurity
                .authorizeHttpRequests()
                .antMatchers(GET,"/api/v1/customer").hasRole("ROLE_CUSTOMER");
        httpSecurity.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }

}
