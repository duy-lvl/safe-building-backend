package com.safepass.safebuilding.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedMethods(HttpMethod.GET.name(), HttpMethod.POST.name(), HttpMethod.PUT.name(),
                                HttpMethod.OPTIONS.name(), HttpMethod.DELETE.name())
                        .allowedHeaders("*")
                        .allowedOrigins("http://localhost:80",
                                "http://localhost:3000",
                                "http://localhost:8082",
                                "http://14.225.254.180:8081",
                                "http://14.225.254.180:80",
                                "https://maaz.global",
                                "https://admin.maaz.global",
                                "http://14.225.204.166:80",
                                "https://safe-building-web.vercel.app",
                                "http://safe-building-web.vercel.app");
            }
        };
    }
}
